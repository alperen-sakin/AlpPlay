package com.example.alpplay.presentation.playerScreen

import androidx.compose.foundation.focusable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.input.key.Key
import androidx.compose.ui.input.key.KeyEventType
import androidx.compose.ui.input.key.key
import androidx.compose.ui.input.key.onKeyEvent
import androidx.compose.ui.input.key.type
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.viewinterop.AndroidView
import androidx.core.net.toUri
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleEventObserver
import androidx.lifecycle.compose.LocalLifecycleOwner
import com.example.alpplay.domain.model.Channel
import org.videolan.libvlc.LibVLC
import org.videolan.libvlc.Media
import org.videolan.libvlc.MediaPlayer
import org.videolan.libvlc.util.VLCVideoLayout

@Composable
fun PlayerScreen(
    url: String,
    channelName: String,
    channelCategory: String,
    channels: List<Channel>,
    onNextChannel: () -> Unit,
    onPreviousChannel: () -> Unit
) {
    val context = LocalContext.current
    val lifecycleOwner = LocalLifecycleOwner.current

    var isDrawerVisible by remember { mutableStateOf(false) }
    var isOverlayVisible by remember { mutableStateOf(true) }
    var isPlaying by remember { mutableStateOf(true) }
    val focusRequester = remember { FocusRequester() }

    val libVLC = remember { LibVLC(context, arrayListOf("-vvv", "--network-caching=5000")) }
    val mediaPlayer = remember { MediaPlayer(libVLC) }

    LaunchedEffect(url) {
        val media = Media(libVLC, url.toUri()).apply { setHWDecoderEnabled(false, false) }
        mediaPlayer.media = media
        media.release()
        mediaPlayer.play()
    }

    DisposableEffect(lifecycleOwner) {
        val observer = LifecycleEventObserver { _, event ->
            if (event == Lifecycle.Event.ON_STOP) {
                mediaPlayer.stop()
                mediaPlayer.detachViews()
            }
        }
        lifecycleOwner.lifecycle.addObserver(observer)
        onDispose {
            lifecycleOwner.lifecycle.removeObserver(observer)
            mediaPlayer.release()
            libVLC.release()
        }
    }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .focusRequester(focusRequester)
            .focusable()
            .onKeyEvent { event ->
                if (isDrawerVisible) return@onKeyEvent false

                if (event.type == KeyEventType.KeyDown) {
                    when (event.key) {
                        Key.DirectionLeft -> {
                            isDrawerVisible = true
                            true
                        }
                        Key.DirectionUp -> {
                            onNextChannel()
                            true
                        }
                        Key.DirectionDown -> {
                            onPreviousChannel()
                            true
                        }
                        Key.DirectionCenter, Key.Enter -> {
                            if (isOverlayVisible) {
                                if (isPlaying) mediaPlayer.pause() else mediaPlayer.play()
                                isPlaying = !isPlaying
                            } else { isOverlayVisible = true }
                            true
                        }
                        else -> false
                    }
                } else {
                    false
                }
            }
    ) {
        LaunchedEffect(isDrawerVisible) {
            if (isDrawerVisible) isOverlayVisible = false
        }
        AndroidView(
            modifier = Modifier.fillMaxSize(),
            factory = { ctx -> VLCVideoLayout(ctx).apply { mediaPlayer.attachViews(this, null, false, false) } }
        )

        PlayerControlsOverlay(
            isVisible = isOverlayVisible,
            isPlaying = isPlaying,
            channelName = channelName,
            channelCategory = channelCategory
        )

        PlayListSideDrawer(
            isVisible = isDrawerVisible,
            channels = channels,
            onChannelSelected = { selected ->
                val newMedia = Media(libVLC, selected.streamUrl.toUri()).apply { setHWDecoderEnabled(false, false) }
                mediaPlayer.stop()
                mediaPlayer.media = newMedia
                mediaPlayer.play()

                isDrawerVisible = false
                focusRequester.requestFocus()
            },
            onBack = {
                isDrawerVisible = false
                focusRequester.requestFocus()
            }
        )
    }
}
