package com.khs.movies.feature.persondetail

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import androidx.core.view.isVisible
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import androidx.transition.TransitionInflater
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.khs.movies.R
import com.khs.movies.appbase.core.BaseFragment
import com.khs.movies.appbase.helper.ConnectivityHelper
import com.khs.movies.appbase.helper.addChip
import com.khs.movies.appbase.helper.displaySnackBar
import com.khs.movies.databinding.FragmentPersonDetailBinding
import com.khs.movies.network.api.Api
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.distinctUntilChangedBy
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch


@AndroidEntryPoint
class PersonDetailFragment : BaseFragment<FragmentPersonDetailBinding>() {

    private val navArgs by navArgs<PersonDetailFragmentArgs>()
    private val viewModel: PersonDetailViewModel by viewModels()

    override fun bindView(inflater: LayoutInflater): FragmentPersonDetailBinding {
        return FragmentPersonDetailBinding.inflate(inflater)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initializeUI()
        setListeners()
        subscribeUiStates()
        viewModel.setNavPersonIdAndConnectionStatus(
            navPersonId = navArgs.personId,
            isConnected = ConnectivityHelper.isOnline()
        )
    }

    private fun initializeUI() {
        sharedElementEnterTransition =
            TransitionInflater.from(requireContext()).inflateTransition(android.R.transition.move)
        binding.ivPerson.transitionName = navArgs.personId.toString()
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
                        .collect { pg.isVisible = it }
                }
            }

            // Movie Data
            viewLifecycleOwner.lifecycleScope.launch {
                repeatOnLifecycle(Lifecycle.State.CREATED) {
                    viewModel.uiState
                        .distinctUntilChangedBy { it.person }
                        .map { it.person }
                        .collect { person ->
                            person?.let {
                                it.profilePath?.let { profile ->
                                    Glide.with(root.context)
                                        .load(Api.getPosterPath(profile))
                                        .apply(RequestOptions().circleCrop())
                                        .into(ivPerson)
                                }
                                tvName.text = it.name
                                tvBio.text = it.detail?.biography
                                it.detail?.alsoKnownAs?.onEach { nameTag ->
                                    cpNames.addChip(
                                        chipText = nameTag,
                                        textStyle = R.style.App_ChipTextStyle,
                                        bgColor = R.color.purple_700
                                    )
                                    cpNames.isVisible = true
                                }
                            }
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
}