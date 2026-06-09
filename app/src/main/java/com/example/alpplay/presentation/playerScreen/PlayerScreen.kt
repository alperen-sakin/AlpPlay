package com.example.alpplay.presentation.playerScreen

import androidx.annotation.OptIn
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
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleEventObserver
import androidx.lifecycle.compose.LocalLifecycleOwner
import androidx.media3.common.MediaItem
import androidx.media3.common.util.UnstableApi
import androidx.media3.exoplayer.DefaultLoadControl
import androidx.media3.exoplayer.DefaultRenderersFactory
import androidx.media3.exoplayer.ExoPlayer
import androidx.media3.ui.PlayerView
import com.example.alpplay.domain.model.Channel

@OptIn(UnstableApi::class)
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

    val exoPlayer = remember {
        val renderersFactory = DefaultRenderersFactory(context)
            .setEnableDecoderFallback(true)

        val loadControl = DefaultLoadControl.Builder()
            .setBufferDurationsMs(
                32000,
                64000,
                2500,
                5000
            ).build()

        ExoPlayer.Builder(context)
            .setRenderersFactory(renderersFactory)
            .setLoadControl(loadControl)
            .build().apply {
                playWhenReady = true
            }
    }

    LaunchedEffect(url) {
        exoPlayer.setMediaItem(MediaItem.fromUri(url))
        exoPlayer.prepare()
        exoPlayer.play()
        isPlaying = true
    }

    DisposableEffect(lifecycleOwner) {
        val observer = LifecycleEventObserver { _, event ->
            when (event) {
                Lifecycle.Event.ON_STOP -> exoPlayer.pause()
                Lifecycle.Event.ON_START -> if (isPlaying) exoPlayer.play()
                else -> {}
            }
        }
        lifecycleOwner.lifecycle.addObserver(observer)
        onDispose {
            lifecycleOwner.lifecycle.removeObserver(observer)
            exoPlayer.release()
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
                                if (isPlaying) exoPlayer.pause() else exoPlayer.play()
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
        LaunchedEffect(isDrawerVisible) {
            if (isDrawerVisible) isOverlayVisible = false
        }

        AndroidView(
            modifier = Modifier.fillMaxSize(),
            factory = { ctx ->
                PlayerView(ctx).apply {
                    player = exoPlayer
                    useController = false
                }
            }
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
                exoPlayer.setMediaItem(MediaItem.fromUri(selected.streamUrl))
                exoPlayer.prepare()
                exoPlayer.play()
                isPlaying = true

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