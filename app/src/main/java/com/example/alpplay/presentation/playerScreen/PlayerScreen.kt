package com.example.alpplay.presentation.playerScreen

import androidx.compose.foundation.clickable
import androidx.compose.foundation.focusable
import androidx.compose.foundation.interaction.MutableInteractionSource
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
import androidx.lifecycle.LifecycleObserver
import androidx.lifecycle.compose.LocalLifecycleOwner
import com.example.alpplay.domain.model.Channel
import kotlinx.coroutines.delay
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
    var lifecycleEvent by remember { mutableStateOf(Lifecycle.Event.ON_ANY) }
    var isDrawerVisible by remember { mutableStateOf(false) }


    val libVLC = remember {
        val options = arrayListOf(
            "-vvv",
            "--network-caching=5000",
            "--drop-late-frames",
            "--skip-frames"
        )
        LibVLC(context, options)
    }

    val mediaPlayer = remember { MediaPlayer(libVLC) }

    LaunchedEffect(url) {
        val media = Media(libVLC, url.toUri()).apply {
            setHWDecoderEnabled(true, false)
        }
        mediaPlayer.media = media
        media.release() // MediaPlayer zaten referansı tutar
        mediaPlayer.play()
    }

    DisposableEffect(lifecycleOwner) {

        val observer = LifecycleEventObserver { _, event ->
            lifecycleEvent = event
            when (event) {
                Lifecycle.Event.ON_STOP -> {
                    mediaPlayer.stop()
                    mediaPlayer.detachViews()
                }
                else -> {}
            }
        }

        lifecycleOwner.lifecycle.addObserver(observer)



        onDispose {
            lifecycleOwner.lifecycle.removeObserver(observer)
            mediaPlayer.stop()
            mediaPlayer.detachViews()
            mediaPlayer.release()
            libVLC.release()
        }
    }

    var isOverlayVisible by remember { mutableStateOf(true) }
    var isPlaying by remember { mutableStateOf(true) }
    val focusRequester = remember { FocusRequester() }

    LaunchedEffect(isOverlayVisible) {
        if (isOverlayVisible) {
            delay(4000)
            isOverlayVisible = false
        }
    }
    LaunchedEffect(Unit) {
        focusRequester.requestFocus()
    }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .focusable()
            .onKeyEvent { event ->

                if (event.type == KeyEventType.KeyDown) {
                    when (event.key) {
                        Key.DirectionLeft -> {
                            if (!isDrawerVisible){
                                isDrawerVisible = true
                            }else{
                                isDrawerVisible = false
                            }
                            true
                        }

                        Key.DirectionUp -> {
                            onNextChannel()
                            isOverlayVisible = true
                            true
                        }

                        Key.DirectionDown -> {
                            onPreviousChannel()
                            isOverlayVisible = true
                            true
                        }

                        Key.DirectionCenter, Key.Enter -> {
                            if (isOverlayVisible) {
                                if (isPlaying) mediaPlayer.pause() else mediaPlayer.play()
                                isPlaying = !isPlaying
                            } else {
                                isOverlayVisible = true
                            }
                            true
                        }

                        else -> false
                    }
                } else {
                    false
                }


            }

    ) {
        AndroidView(
            modifier = Modifier.fillMaxSize(),
            factory = { ctx ->
                VLCVideoLayout(ctx).apply {
                    mediaPlayer.attachViews(this, null, false, false)
                }
            },
            update = {videoLayout ->
                if (!mediaPlayer.vlcVout.areViewsAttached()) {
                    mediaPlayer.attachViews(videoLayout, null, false, false)
                }

                if (lifecycleEvent == Lifecycle.Event.ON_RESUME && !mediaPlayer.isPlaying) {
                    mediaPlayer.play()
                    isPlaying = true
                }
            }

        )

        PlayerControlsOverlay(
            isVisible = isOverlayVisible,
            isPlaying = isPlaying,
            channelName = channelName,
            channelCategory = channelCategory,
            modifier = Modifier.fillMaxSize()
        )


    }


}
