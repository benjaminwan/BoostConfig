package com.benjaminwan.boostconfigdemo.ui.fragment4

import android.view.View
import android.view.ViewGroup
import com.airbnb.mvrx.activityViewModel
import com.benjaminwan.boostconfig.itemviews.checkBoxItemView
import com.benjaminwan.boostconfig.models.ColorStateRes
import com.benjaminwan.boostconfigdemo.R
import com.benjaminwan.boostconfigdemo.models.Student
import com.benjaminwan.boostconfigdemo.ui.ConfigViewModel
import com.benjaminwan.boostconfigdemo.ui.base.BaseMavericksEpoxyFragment
import com.benjaminwan.boostconfigdemo.utils.current
import com.benjaminwan.boostconfigdemo.utils.getColor
import com.benjaminwan.boostconfigdemo.utils.simpleController
import com.benjaminwan.swipemenulayout.menuItems

class Page4Fragment : BaseMavericksEpoxyFragment() {

    override val vm: ConfigViewModel by activityViewModel(ConfigViewModel::class) { "page4" }

    private val contentViewBackgroundColor by lazy {
        ColorStateRes(
            listOf(ColorStateRes.unPressed, ColorStateRes.pressed, ColorStateRes.disabled),
            listOf(
                getColor(R.color.material_blue_500),
                getColor(R.color.material_blue_700),
                getColor(R.color.material_grey_300)
            )
        )
    }

    private val rightButtonColor by lazy {
        ColorStateRes(
            listOf(ColorStateRes.unChecked, ColorStateRes.checked, ColorStateRes.disabled),
            listOf(
                getColor(R.color.material_grey_600),
                getColor(R.color.material_amber_600),
                getColor(R.color.material_grey_300)
            )
        )
    }

    override fun epoxyController() = simpleController(vm) { config ->
        checkBoxItemView {
            id("CheckBox_test")
            borderWidthDp(1)
            contentViewBackgroundColorState(contentViewBackgroundColor)
            headerIcon(R.drawable.ic_filter_1)
            leftText(config.students.size.toString())
            rightIsChecked(config.students.size % 2 == 0)
            rightButtonColorState(rightButtonColor)
            onCheckedChangeListener { buttonView, isChecked ->
                val students = vm.current.students
                vm.addStudent(Student.new("Student${students.size}", 10, true))
            }
        }
        config.students.forEachIndexed { index, student ->
            checkBoxItemView {
                id("CheckBox_${student.id}")
                borderWidthDp(if (index % 2 == 0) 2 else 0)
                visibility(if (student.isGone) View.GONE else View.VISIBLE)
                contentViewEnable(index % 2 == 0)
                headerText(if (index % 2 == 0) index.toString() else "")
                headerIcon(if (index % 2 == 0) 0 else R.drawable.ic_filter_2)
                leftText(student.name)
                rightIsChecked(student.isMale)
                onCheckedChangeListener { buttonView, isChecked ->
                    val studentState = vm.current.getStudent(student.id)
                    vm.setStudent(studentState.copy(isMale = isChecked))
                }
                leftMenu(
                    if (index % 2 == 0)
                        menuItems {
                            menuItem {
                                this.id = 0
                                width = ViewGroup.LayoutParams.WRAP_CONTENT
                                height = ViewGroup.LayoutParams.MATCH_PARENT
                                title = student.isMale.toString()
                                titleColorRes = R.color.selector_blue5_to_blue7
                                backgroundRes = R.drawable.bg_white_to_transparent_half
                            }
                        } else emptyList()
                )
                rightMenu(
                    if (index % 2 == 0)
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
                        } else emptyList()
                )
                rightMenuClickListener { swLayout, swItem, view ->
                    val studentState = vm.current.getStudent(student.id)
                    when (swItem.id) {
                        0 -> {
                            vm.setStudent(studentState.copy(isMale = !studentState.isMale))
                        }
                        1 -> {
                            vm.setStudent(studentState.copy(isGone = true))
                        }
                        else -> {
                        }
                    }
                    swLayout.closeMenu()
                }
            }
        }
    }
}