package com.benjaminwan.boostconfigdemo.ui.base

import android.os.Bundle
import android.view.View
import androidx.annotation.LayoutRes
import androidx.fragment.app.Fragment
import com.airbnb.epoxy.EpoxyController
import com.airbnb.mvrx.MavericksView
import com.airbnb.mvrx.MavericksViewModel
import com.airbnb.mvrx.withState
import com.benjaminwan.boostconfigdemo.R
import com.benjaminwan.boostconfigdemo.databinding.FragmentListBinding
import com.benjaminwan.boostconfigdemo.utils.setMarginItemDecorationAndDrawBottomSeparator
import com.benjaminwan.boostconfigdemo.utils.viewBinding

abstract class BaseMavericksEpoxyFragment(
    @LayoutRes contentLayoutId: Int = R.layout.fragment_list
) : Fragment(contentLayoutId), MavericksView {

    private val epoxyController by lazy { epoxyController() }
    protected val binding: FragmentListBinding by viewBinding()
    abstract val vm: MavericksViewModel<*>

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
        binding.chatDevRv.setMarginItemDecorationAndDrawBottomSeparator(6, 4, 6, 4, 0)
        binding.chatDevRv.setController(epoxyController)
    }

    override fun invalidate() = withState(vm) { config ->
        requireActivity().invalidateOptionsMenu()
        binding.chatDevRv.requestModelBuild()
    }

    abstract fun epoxyController(): EpoxyController
}