package com.khs.movies.appbase.core

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatDelegate
import androidx.databinding.ViewDataBinding
import androidx.fragment.app.Fragment
import com.khs.movies.appbase.helper.autoCleared

abstract class BaseFragment<VDB : ViewDataBinding> : Fragment() {

    protected var binding by autoCleared<VDB>()
        private set

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        AppCompatDelegate.setCompatVectorFromResourcesEnabled(true)
        binding = bindView(inflater)
        return binding.root
    }

    abstract fun bindView(inflater: LayoutInflater): VDB

}
