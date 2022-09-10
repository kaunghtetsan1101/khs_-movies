package com.khs.movies.feature.discovermovie

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.inputmethod.EditorInfo
import androidx.core.view.isVisible
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.FragmentNavigatorExtras
import androidx.navigation.fragment.findNavController
import com.khs.movies.appbase.core.BaseFragment
import com.khs.movies.appbase.core.UiAction
import com.khs.movies.appbase.helper.autoCleared
import com.khs.movies.appbase.helper.bindList
import com.khs.movies.appbase.helper.hideSoftKeyboard
import com.khs.movies.databinding.FragmentDiscoverMovieBinding
import com.khs.movies.domain.exception.ExceptionToStringMapper
import com.khs.movies.feature.PagingLoadStateAdapter
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch
import javax.inject.Inject

@AndroidEntryPoint
class DiscoverMovieFragment : BaseFragment<FragmentDiscoverMovieBinding>() {

    private var _adapter by autoCleared<DiscoverMovieAdapter>()
    private val viewModel: DiscoverMoviesViewModel by viewModels()

    @Inject
    lateinit var exceptionToStringMapper: ExceptionToStringMapper

    override fun bindView(inflater: LayoutInflater): FragmentDiscoverMovieBinding {
        return FragmentDiscoverMovieBinding.inflate(inflater).apply {
            query = viewModel.query
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setUpAdapter()
        setListeners()
        subscribeUiStates()
    }

    private fun setListeners(){
        with(binding) {
            with(viewModel) {
                tlSearch.setEndIconOnClickListener {
                    it.hideSoftKeyboard()
                    search(query.value)

                }

                txtSearch.setOnEditorActionListener { v, actionId, event ->
                    if (actionId == EditorInfo.IME_ACTION_DONE) {
                        search(query.value)
                    }
                    false
                }
            }
        }
    }

    private fun search(queries: String?) {
        binding.rv.scrollToPosition(0)
        viewModel.doAction(
            UiAction.Search(
                queries = queries
            )
        )
    }

    private fun setUpAdapter() {
        val adapter = DiscoverMovieAdapter { vw, movie ->
            val action =
                DiscoverMovieFragmentDirections.actionMovieFragmentToMovieDetailFragment(movie.id)
            val extras = FragmentNavigatorExtras(vw to movie.id.toString())
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
        _adapter.bindList(
            rv = binding.rv,
            scope = viewLifecycleOwner.lifecycleScope,
            uiState = viewModel.state,
            onScrollChanged = viewModel.doAction
        ) { _, isLoading, _, _ ->
            binding.pg.isVisible = isLoading
        }

    }

    @SuppressLint("UnsafeRepeatOnLifecycleDetector")
    private fun subscribeUiStates() {
        viewLifecycleOwner.lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.CREATED) {
                viewModel.state
                    .map { it.pagingData }
                    .collect { _adapter.submitData(it) }
            }
        }
    }

}