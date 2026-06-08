package com.example.alpplay.presentation

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.viewinterop.AndroidView
import androidx.core.net.toUri
import org.videolan.libvlc.LibVLC
import org.videolan.libvlc.Media
import org.videolan.libvlc.MediaPlayer
import org.videolan.libvlc.util.VLCVideoLayout

@Composable
fun PlayerScreen(url: String) {
    val context = LocalContext.current

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

//    LaunchedEffect(mediaPlayer) {
//        while (isActive) {
//            delay(3000)
//
//            val currentMedia = mediaPlayer.media
//            if (currentMedia != null) {
//                for (i in 0 until currentMedia.trackCount) {
//                    val track = currentMedia.getTrack(i)
//                    if (track is IMedia.VideoTrack) {
//                        android.util.Log.d("ALP_PLAY_INFO", "🎬 VİDEO: ${track.width} x ${track.height}")
//                    }
//                }
//
//                val stats = currentMedia.stats
//                if (stats != null) {
//                    val kbps = (stats.demuxBitrate * 8000).toInt()
//                    android.util.Log.d("ALP_PLAY_INFO", "📊 BİTRATE: $kbps Kbps")
//                }
//            }
//        }
//    }

    DisposableEffect(Unit) {
        val media = Media(libVLC, url.toUri())

        media.setHWDecoderEnabled(true, false)

        mediaPlayer.media = media
        media.release()

        mediaPlayer.play()

        onDispose {
            mediaPlayer.stop()
            mediaPlayer.detachViews()
            mediaPlayer.release()
            libVLC.release()
        }
    }

    AndroidView(
        modifier = Modifier.fillMaxSize(),
        factory = { ctx ->
            VLCVideoLayout(ctx).apply {
                mediaPlayer.attachViews(this, null, false, false)
                keepScreenOn = true
            }
        }
    )
}
