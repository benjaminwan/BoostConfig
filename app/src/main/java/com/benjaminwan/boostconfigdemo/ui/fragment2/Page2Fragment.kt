package com.benjaminwan.boostconfigdemo.ui.fragment2

import android.os.Bundle
import android.view.View
import androidx.annotation.LayoutRes
import androidx.fragment.app.Fragment
import com.airbnb.mvrx.MavericksView
import com.airbnb.mvrx.activityViewModel
import com.airbnb.mvrx.withState
import com.benjaminwan.boostconfigdemo.R
import com.benjaminwan.boostconfigdemo.databinding.FragmentListBinding
import com.benjaminwan.boostconfigdemo.ui.ConfigViewModel
import com.benjaminwan.boostconfigdemo.utils.setMarginItemDecorationAndDrawBottomSeparator
import com.benjaminwan.boostconfigdemo.utils.simpleController
import com.benjaminwan.boostconfigdemo.utils.viewBinding

class Page2Fragment(@LayoutRes contentLayoutId: Int = R.layout.fragment_list) :
    Fragment(contentLayoutId), MavericksView {

    private val epoxyController by lazy { epoxyController() }
    private val binding: FragmentListBinding by viewBinding()
    private val configVM: ConfigViewModel by activityViewModel()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        epoxyController.onRestoreInstanceState(savedInstanceState)
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        epoxyController.onSaveInstanceState(outState)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setHasOptionsMenu(true)
        initViews()
    }

    private fun initViews() {
        binding.chatDevRv.setHasFixedSize(true)
        binding.chatDevRv.setMarginItemDecorationAndDrawBottomSeparator(4, 2, 4, 2, 1)
        binding.chatDevRv.setController(epoxyController)
    }

    override fun invalidate() = withState(configVM) { config ->
        requireActivity().invalidateOptionsMenu()
        binding.chatDevRv.requestModelBuild()
    }

    private fun epoxyController() = simpleController(configVM) { config ->

    }
}