package com.benjaminwan.boostconfigdemo.ui.fragment2

import android.view.View
import android.view.ViewGroup
import com.airbnb.mvrx.activityViewModel
import com.benjaminwan.boostconfig.itemviews.editItemView
import com.benjaminwan.boostconfigdemo.R
import com.benjaminwan.boostconfigdemo.models.Student
import com.benjaminwan.boostconfigdemo.ui.ConfigViewModel
import com.benjaminwan.boostconfigdemo.ui.base.BaseMavericksEpoxyFragment
import com.benjaminwan.boostconfigdemo.utils.current
import com.benjaminwan.boostconfigdemo.utils.simpleController
import com.benjaminwan.swipemenulayout.menuItems

class Page2Fragment : BaseMavericksEpoxyFragment() {

    override val vm: ConfigViewModel by activityViewModel(ConfigViewModel::class) { "page2" }

    override fun epoxyController() = simpleController(vm) { config ->
        editItemView {
            id("Edit_test")
            borderWidthDp(1)
            headerIcon(R.drawable.ic_filter_1)
            leftText("+1")
            rightText(config.students.size.toString())
            onClickListener { view ->
                val students = vm.current.students
                vm.addStudent(Student.new("Student${students.size}", 10, true))
            }
        }
        config.students.forEachIndexed { index, student ->
            editItemView {
                id("Edit_${student.id}")
                borderWidthDp(if (index % 2 == 0) 2 else 0)
                visibility(if (student.isGone) View.GONE else View.VISIBLE)
                contentViewEnable(index % 2 == 0)
                headerText(if (index % 2 == 0) index.toString() else "")
                headerIcon(if (index % 2 == 0) 0 else R.drawable.ic_filter_2)
                leftText(student.name)
                rightText(student.age.toString())
                onRightTextChanged {
                    val studentState = vm.current.getStudent(student.id)
                    vm.setStudent(studentState.copy(age = it.toIntOrNull() ?: 0))
                }
                onClickListener { view ->
                    val studentState = vm.current.getStudent(student.id)
                    vm.setStudent(studentState.copy(age = studentState.age + 1))
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