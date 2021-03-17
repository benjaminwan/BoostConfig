package com.benjaminwan.boostconfigdemo.ui.fragment1

import android.os.Bundle
import android.view.View
import android.view.ViewGroup
import androidx.annotation.LayoutRes
import androidx.fragment.app.Fragment
import com.airbnb.mvrx.MavericksView
import com.airbnb.mvrx.activityViewModel
import com.airbnb.mvrx.withState
import com.benjaminwan.boostconfig.ConfigItem
import com.benjaminwan.boostconfig.ConfigType
import com.benjaminwan.boostconfig.configItems
import com.benjaminwan.boostconfig.itemviews.textItemView
import com.benjaminwan.boostconfigdemo.R
import com.benjaminwan.boostconfigdemo.databinding.FragmentListBinding
import com.benjaminwan.boostconfigdemo.ui.ConfigViewModel
import com.benjaminwan.boostconfigdemo.utils.setMarginItemDecorationAndDrawBottomSeparator
import com.benjaminwan.boostconfigdemo.utils.simpleController
import com.benjaminwan.boostconfigdemo.utils.viewBinding
import com.benjaminwan.swipemenulayout.menuItems
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.runBlocking

class Page1Fragment(@LayoutRes contentLayoutId: Int = R.layout.fragment_list) :
    Fragment(contentLayoutId), MavericksView {

    private val epoxyController by lazy { epoxyController() }
    private val binding: FragmentListBinding by viewBinding()
    private val configVM: ConfigViewModel by activityViewModel()
    private val state
        get() = runBlocking(Dispatchers.IO) {
            configVM.awaitState()
        }

    private val configItems: List<ConfigItem> = configItems {
        (0..100).forEach { id ->
            configItem {
                type = { ConfigType.Text }
                visibility = { if (state.students[id].isGone) View.GONE else View.VISIBLE }
                borderWidth = {
                    if (id % 2 == 0) 2 else 0
                }
                headerText = {
                    if (id % 2 == 0) id.toString() else ""
                }
                headerIcon = {
                    if (id % 2 == 0) 0 else R.drawable.ic_filter_2
                }
                leftText = {
                    state.students[id].name
                }
                rightText =
                    { state.students[id].age.toString() }
                click = {
                    configVM.setStudent(state.students[id].copy(age = state.students[id].age + 1))
                }
                leftMenus = {
                    menuItems {
                        menuItem {
                            this.id = 0
                            width = ViewGroup.LayoutParams.WRAP_CONTENT
                            height = ViewGroup.LayoutParams.MATCH_PARENT
                            title = state.students[id].isMale.toString()
                            titleColorRes = R.color.selector_blue5_to_blue7
                            backgroundRes = R.drawable.bg_white_to_transparent_half
                        }
                    }
                }
                leftMenuClick = { swipe, swItem, view ->
                    swipe.closeMenu()
                }
                rightMenus = {
                    menuItems {
                        menuItem {
                            this.id = 0
                            width = 50
                            height = ViewGroup.LayoutParams.MATCH_PARENT
                            title = "修改"
                            titleColorRes = R.color.selector_white_to_grey5
                            backgroundRes = R.drawable.bg_green5_to_green7
                            iconRes = R.drawable.ic_back
                            iconColorRes = R.color.selector_white_to_grey5
                        }
                        menuItem {
                            this.id = 1
                            width = 50
                            height = ViewGroup.LayoutParams.MATCH_PARENT
                            title = "隐藏"
                            titleColorRes = R.color.selector_white_to_grey5
                            backgroundRes = R.drawable.bg_orange5_to_orange7
                        }
                    }
                }
                rightMenuClick = { swipe, swItem, view ->
                    when (swItem.id) {
                        0 -> {
                            configVM.setStudent(state.students[id].copy(isMale = !state.students[id].isMale))
                        }
                        1 -> {
                            configVM.setStudent(state.students[id].copy(isGone = true))
                        }
                        else -> {
                        }
                    }
                    swipe.closeMenu()
                }
            }
        }
    }


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

    override fun invalidate() = withState(configVM) { config ->
        requireActivity().invalidateOptionsMenu()
        binding.chatDevRv.requestModelBuild()
    }

    private fun epoxyController() = simpleController(configVM) { config ->
        configItems.forEachIndexed { index, it ->
            when (it.type) {
                ConfigType.Text -> {
                    textItemView {
                        id(it.id + index)
                        visibility(it.visibility)
                        borderWidthDp(it.borderWidth)
                        headerText(it.headerText)
                        headerIcon(it.headerIcon)
                        leftText(it.leftText)
                        rightText(it.rightText)
                        onClickListener(it.click)
                        leftMenu(it.leftMenus)
                        leftMenuClickListener(it.leftMenuClick)
                        rightMenu(it.rightMenus)
                        rightMenuClickListener(it.rightMenuClick)
                    }
                }
                else -> {

                }
            }
        }
        /*config.titles.forEachIndexed { index, title ->
            switchtemView {
                id("switch$title$index")
                borderWidthDp(2)
                leftText(title)
                rightIsChecked(index % 2 == 0)
                rightMenu(rightMenus)
                onClickListener { view ->
                    showToast("$title $index")
                }
            }
            checkBoxItemView {
                id("checkbox$title$index")
                //borderWidthDp(2)
                leftText(title)
                rightIsChecked(index % 2 == 0)
                rightMenu(rightMenus)
                onClickListener { view ->
                    showToast("$title $index")
                }
            }
            editItemView {
                id("edit$title$index")
                //borderWidthDp(2)
                leftText(title)
                rightText(config.contents[index])
                rightMenu(rightMenus)
                onClickListener { view ->
                    showToast("$title $index")
                }
            }
            textItemView {
                id("text$title$index")
                //borderWidthDp(2)
                leftText(title)
                rightText(config.contents[index])
                rightMenu(rightMenus)
                leftMenu(leftMenus)
                onClickListener { view ->
                    showToast("$title $index")
                }
            }*/
    }
}