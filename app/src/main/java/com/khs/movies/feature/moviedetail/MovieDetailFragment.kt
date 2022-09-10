package com.khs.movies.feature.moviedetail

import android.annotation.SuppressLint
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.WindowManager
import androidx.core.view.isVisible
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import androidx.transition.TransitionInflater
import com.khs.movies.R
import com.khs.movies.appbase.core.BaseFragment
import com.khs.movies.appbase.helper.*
import com.khs.movies.databinding.FragmentMovieDetailBinding
import com.khs.movies.feature.common.adapter.ReviewAdapter
import com.khs.movies.feature.common.adapter.VideoAdapter
import com.khs.movies.network.api.Api
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch


@AndroidEntryPoint
class MovieDetailFragment : BaseFragment<FragmentMovieDetailBinding>() {

    private val navArgs by navArgs<MovieDetailFragmentArgs>()
    private var _videoAdapter by autoCleared<VideoAdapter>()
    private var _reviewAdapter by autoCleared<ReviewAdapter>()
    private val viewModel: MovieDetailViewModel by viewModels()

    override fun bindView(inflater: LayoutInflater): FragmentMovieDetailBinding {
        requireActivity().window.setFlags(
            WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS,
            WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS
        )
        return FragmentMovieDetailBinding.inflate(inflater)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initializeUI()
        setUpAdapters()
        setListeners()
        subscribeUiStates()
        viewModel.setNavMovieIdAndConnectionStatus(
            navMovieId = navArgs.movieId,
            isConnected = ConnectivityHelper.isOnline()
        )
    }

    private fun initializeUI() {
        sharedElementEnterTransition =
            TransitionInflater.from(requireContext()).inflateTransition(android.R.transition.move)
        applyToolbarMargin(binding.toolbar)
        binding.ivBackdrop.transitionName = navArgs.movieId.toString()
    }

    private fun setUpAdapters() {
        val videoAdapter = VideoAdapter {
            startActivity(
                Intent(Intent.ACTION_VIEW, Uri.parse(Api.getYoutubeVideoPath(it.key)))
            )
        }
        _videoAdapter = videoAdapter
        val reviewAdapter = ReviewAdapter()
        _reviewAdapter = reviewAdapter
        with(binding.lyDetailBody) {
            rvTrailers.adapter = _videoAdapter
            rvReviews.adapter = _reviewAdapter
        }
    }

    private fun setListeners() {
        binding.toolbar.setNavigationOnClickListener {
            findNavController().navigateUp()
        }
    }

    @SuppressLint("UnsafeRepeatOnLifecycleDetector")
    private fun subscribeUiStates() {
        with(binding) {
            // Loading
            viewLifecycleOwner.lifecycleScope.launch {
                repeatOnLifecycle(Lifecycle.State.CREATED) {
                    viewModel.uiState
                        .distinctUntilChangedBy { it.isLoading }
                        .map { it.isLoading }
                        .collect {
                            pg.isVisible = it
                        }
                }
            }

            // Movie Data
            viewLifecycleOwner.lifecycleScope.launch {
                repeatOnLifecycle(Lifecycle.State.CREATED) {
                    viewModel.uiState
                        .distinctUntilChangedBy { it.movie }
                        .map { it.movie }
                        .collect { movie ->
                            movie?.let {
                                with(lyDetailHeader) {
                                    ivBackdrop.loadWithCircularRevealed(
                                        it.backdropPath ?: it.posterPath!!
                                    )
                                    tvTitle.text = it.title
                                    tvReleaseDate.text =
                                        getString(R.string.release_date_format, it.releaseDate)
                                    ratingBar.rating = it.voteAverage / 2
                                    ratingBar.isVisible = true
                                }
                                with(lyDetailBody) {
                                    tvSummaryBody.text = it.overview
                                    cgSummary.isVisible = true
                                }
                            }
                        }
                }
            }

            // Keywords Data
            viewLifecycleOwner.lifecycleScope.launch {
                repeatOnLifecycle(Lifecycle.State.CREATED) {
                    viewModel.uiState
                        .map { it.keywords }
                        .distinctUntilChanged()
                        .collectLatest {
                            it.onEach { keyword ->
                                lyDetailBody.cpKeywords.addChip(
                                    chipText = keyword.name,
                                    textStyle = R.style.App_ChipTextStyle,
                                    bgColor = R.color.purple_700
                                )
                            }
                        }
                }
            }

            // Trailers Data
            viewLifecycleOwner.lifecycleScope.launch {
                repeatOnLifecycle(Lifecycle.State.CREATED) {
                    viewModel.uiState
                        .map { it.videos }
                        .distinctUntilChanged()
                        .collectLatest {
                            _videoAdapter.submitList(it)
                            lyDetailBody.cgTrailers.isVisible = it.isNotEmpty()
                        }
                }
            }

            // Reviews Data
            viewLifecycleOwner.lifecycleScope.launch {
                repeatOnLifecycle(Lifecycle.State.CREATED) {
                    viewModel.uiState
                        .map { it.reviews }
                        .distinctUntilChanged()
                        .collectLatest {
                            _reviewAdapter.submitList(it)
                            lyDetailBody.cgReviews.isVisible = it.isNotEmpty()
                        }
                }
            }
        }

        // Error
        viewLifecycleOwner.lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.CREATED) {
                viewModel.uiState.collect { uiState ->
                    uiState.userMessages.firstOrNull()?.let { userMessage ->
                        requireActivity().displaySnackBar(
                            messageStr = userMessage.message,
                            actionText = R.string.retry,
                            onClickAction = { viewModel.retry() }
                        )
                        viewModel.userMessageShown(userMessage.id)
                    }
                }
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        requireActivity().window.clearFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS)
    }
}