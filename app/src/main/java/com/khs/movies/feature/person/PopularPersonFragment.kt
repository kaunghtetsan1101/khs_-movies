package com.khs.movies.feature.person

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import androidx.core.view.isVisible
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.FragmentNavigatorExtras
import androidx.navigation.fragment.findNavController
import androidx.paging.LoadState
import com.khs.movies.appbase.core.BaseFragment
import com.khs.movies.appbase.helper.autoCleared
import com.khs.movies.databinding.FragmentPopularPersonBinding
import com.khs.movies.domain.exception.ExceptionToStringMapper
import com.khs.movies.feature.PagingLoadStateAdapter
import com.khs.movies.feature.person.PopularPersonViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch
import javax.inject.Inject

@AndroidEntryPoint
class PopularPersonFragment : BaseFragment<FragmentPopularPersonBinding>() {

    private var _adapter by autoCleared<PopularPersonAdapter>()
    private val viewModel: PopularPersonViewModel by viewModels()

    @Inject
    lateinit var exceptionToStringMapper: ExceptionToStringMapper

    override fun bindView(inflater: LayoutInflater): FragmentPopularPersonBinding {
        return FragmentPopularPersonBinding.inflate(inflater)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setUpAdapter()
        subscribeUiStates()
        viewModel.fetchPersons()
    }

    private fun setUpAdapter() {
        val adapter = PopularPersonAdapter { vw, person ->
            val action =
                PopularPersonFragmentDirections.actionPopularPersonFragmentToPersonDetailFragment(
                    person.id
                )
            val extras = FragmentNavigatorExtras(vw to person.id.toString())
            findNavController().navigate(
                action,
                extras
            )
        }
        _adapter = adapter
        binding.rv.apply {
            this.adapter = _adapter.withLoadStateHeaderAndFooter(
                header = PagingLoadStateAdapter(exceptionToStringMapper, _adapter::retry),
                footer = PagingLoadStateAdapter(exceptionToStringMapper, _adapter::retry)
            )
            postponeEnterTransition()
            viewTreeObserver
                .addOnPreDrawListener {
                    startPostponedEnterTransition()
                    true
                }
        }
    }

    @SuppressLint("UnsafeRepeatOnLifecycleDetector")
    private fun subscribeUiStates() {
        with(binding) {
            // Data
            viewLifecycleOwner.lifecycleScope.launch {
                repeatOnLifecycle(Lifecycle.State.CREATED) {
                    viewModel.uiState
                        .map { it.personPage }
                        .collect { _adapter.submitData(it) }
                }
            }
            // Loading
            viewLifecycleOwner.lifecycleScope.launch {
                repeatOnLifecycle(Lifecycle.State.CREATED) {
                    _adapter.loadStateFlow.collectLatest { loadStates ->
                        pg.isVisible = loadStates.refresh is LoadState.Loading
                    }
                }
            }
        }
    }

}