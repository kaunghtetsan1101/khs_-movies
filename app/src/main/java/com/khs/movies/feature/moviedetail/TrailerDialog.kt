package com.khs.movies.feature.moviedetail

import android.app.Dialog
import android.graphics.Rect
import android.os.Bundle
import android.util.SparseArray
import android.view.LayoutInflater
import android.view.MotionEvent
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.DialogFragment
import at.huber.youtubeExtractor.VideoMeta
import at.huber.youtubeExtractor.YouTubeExtractor
import at.huber.youtubeExtractor.YtFile
import com.google.android.exoplayer2.ExoPlayer
import com.google.android.exoplayer2.MediaItem
import com.google.android.exoplayer2.Player
import com.google.android.exoplayer2.source.MediaSource
import com.google.android.exoplayer2.source.MergingMediaSource
import com.google.android.exoplayer2.source.ProgressiveMediaSource
import com.google.android.exoplayer2.upstream.DefaultHttpDataSource
import com.google.android.exoplayer2.util.Util
import com.khs.movies.R
import com.khs.movies.appbase.helper.autoCleared
import com.khs.movies.databinding.FragmentTrailerDialogBinding
import com.khs.movies.network.api.Api
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class TrailerDialog(
    private val key: String,
) : DialogFragment(), Player.Listener {

    private var _binding by autoCleared<FragmentTrailerDialogBinding>()
    private var player: ExoPlayer? = null
    private var playWhenReady = true
    private var currentWindow = 0
    private var playbackPosition = 0L

    override fun getTheme(): Int = R.style.App_RoundedCornersDialog

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val binding =
            FragmentTrailerDialogBinding.inflate(inflater, container, false).apply {
                lifecycleOwner = this@TrailerDialog.viewLifecycleOwner
            }
        _binding = binding
        return _binding.root
    }

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        return object : Dialog(requireContext(), theme) {
            override fun dispatchTouchEvent(ev: MotionEvent): Boolean {
                val dialogBounds = Rect()
                window!!.decorView.getHitRect(dialogBounds)
                return super.dispatchTouchEvent(ev)
            }
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        isCancelable = true
        initializePlayer()
    }

    private fun initializePlayer() {
        player = ExoPlayer.Builder(requireContext()).build()
        _binding.videoView.player = player

        object : YouTubeExtractor(requireContext()) {
            override fun onExtractionComplete(
                ytFiles: SparseArray<YtFile>?,
                videoMeta: VideoMeta?
            ) {
                if (ytFiles != null) {

                    val iTag = 137//tag of video 1080
                    val audioTag = 140 //tag m4a audio
                    // 720, 1080, 480
                    var videoUrl = ""
                    val iTags: List<Int> = listOf(22, 137, 18)
                    for (i in iTags) {
                        val ytFile = ytFiles.get(i)
                        if (ytFile != null) {
                            val downloadUrl = ytFile.url
                            if (downloadUrl != null && downloadUrl.isNotEmpty()) {
                                videoUrl = downloadUrl
                            }
                        }
                    }
                    if (videoUrl == "")
                        videoUrl = ytFiles[iTag].url
                    val audioUrl = ytFiles[audioTag].url
                    val audioSource: MediaSource = ProgressiveMediaSource
                        .Factory(DefaultHttpDataSource.Factory())
                        .createMediaSource(MediaItem.fromUri(audioUrl))
                    val videoSource: MediaSource = ProgressiveMediaSource
                        .Factory(DefaultHttpDataSource.Factory())
                        .createMediaSource(MediaItem.fromUri(videoUrl))
                    player?.setMediaSource(
                        MergingMediaSource(true, videoSource, audioSource), true
                    )
                    player?.prepare()
                    player?.playWhenReady = playWhenReady
                    player?.seekTo(currentWindow, playbackPosition)
                    player?.addListener(this@TrailerDialog)

                }

            }

        }.extract(Api.getYoutubeVideoPath(key))

    }

    private fun releasePlayer() {
        player?.let { exoPlayer ->
            playbackPosition = exoPlayer.currentPosition
            currentWindow = exoPlayer.currentMediaItemIndex
            playWhenReady = exoPlayer.playWhenReady
            exoPlayer.release()
        }
        player = null
    }

    override fun onStart() {
        super.onStart()
        val widthPx = resources.getDimensionPixelSize(R.dimen._300dp)
        val heightPx = resources.getDimensionPixelSize(R.dimen._280dp)
        dialog?.window?.setLayout(
            widthPx,
            heightPx
        )
        if (Util.SDK_INT > 23) {
            initializePlayer()
        }
    }

    override fun onResume() {
        super.onResume()
        if ((Util.SDK_INT <= 23 || player == null)) {
            initializePlayer()
        }
    }

    override fun onPause() {
        super.onPause()
        if (Util.SDK_INT <= 23) {
            releasePlayer()
        }
    }

    override fun onStop() {
        super.onStop()
        if (Util.SDK_INT > 23) {
            releasePlayer()
        }
    }

    override fun onPlaybackStateChanged(playbackState: Int) {
        if (playbackState == Player.STATE_READY) {
            _binding.progressBar.visibility = View.INVISIBLE
        } else {
            _binding.progressBar.visibility = View.VISIBLE
        }
    }

}