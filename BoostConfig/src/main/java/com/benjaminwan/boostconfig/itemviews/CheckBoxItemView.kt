package com.benjaminwan.boostconfig.itemviews

import android.content.Context
import android.util.AttributeSet
import android.view.MotionEvent
import android.view.View
import android.widget.CompoundButton
import android.widget.FrameLayout
import androidx.annotation.DrawableRes
import androidx.appcompat.widget.AppCompatCheckBox
import androidx.appcompat.widget.AppCompatImageView
import androidx.appcompat.widget.AppCompatTextView
import androidx.appcompat.widget.LinearLayoutCompat
import androidx.core.view.setPadding
import com.airbnb.epoxy.CallbackProp
import com.airbnb.epoxy.ModelProp
import com.airbnb.epoxy.ModelView
import com.airbnb.epoxy.TextProp
import com.benjaminwan.boostconfig.R
import com.benjaminwan.boostconfig.utils.dp2px
import com.benjaminwan.swipemenulayout.SwipeMenuItem
import com.benjaminwan.swipemenulayout.SwipeMenuLayout

@ModelView(autoLayout = ModelView.Size.MATCH_WIDTH_WRAP_HEIGHT)
class CheckBoxItemView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : FrameLayout(context, attrs, defStyleAttr) {
    private val swipeMenuLayout by lazy { findViewById<SwipeMenuLayout>(R.id.swipeMenuLayout) }
    private val contentLayout by lazy { findViewById<LinearLayoutCompat>(R.id.contentLayout) }
    private val swipeDirectionIV by lazy { findViewById<AppCompatImageView>(R.id.swipeDirectionIV) }
    private val headerIV by lazy { findViewById<AppCompatImageView>(R.id.headerIV) }
    private val headerTV by lazy { findViewById<AppCompatTextView>(R.id.headerTV) }
    private val checkBox by lazy { findViewById<AppCompatCheckBox>(R.id.checkBox) }

    init {
        View.inflate(context, R.layout.rv_check_box_item, this)
        swipeMenuLayout.addOnMenuRightOpenedListener {
            swipeDirectionIV.setImageResource(R.drawable.ic_swipe_right)
        }
        swipeMenuLayout.addOnMenuClosedListener {
            swipeDirectionIV.setImageResource(R.drawable.ic_swipe_left)
        }
    }

    private var downX = 0
    private var downY = 0
    private var hasSwipeMenu = false

    override fun dispatchTouchEvent(ev: MotionEvent): Boolean {
        if (!hasSwipeMenu) {
            return super.dispatchTouchEvent(ev)
        }
        when (ev.action) {
            MotionEvent.ACTION_DOWN -> {
                downX = ev.x.toInt()
                downY = ev.y.toInt()
                parent?.requestDisallowInterceptTouchEvent(true)
            }
            MotionEvent.ACTION_MOVE -> {
                val moveX = ev.x.toInt()
                val moveY = ev.y.toInt()
                val diffX = moveX - downX
                val diffY = moveY - downY
                if (Math.abs(diffX) > Math.abs(diffY)) { // 当前是横向滑动, 不让父元素拦截
                    parent?.requestDisallowInterceptTouchEvent(true)
                } else {
                    parent?.requestDisallowInterceptTouchEvent(false)
                }
            }
            else -> {
            }
        }
        return super.dispatchTouchEvent(ev)
    }

    @ModelProp
    fun setVisibility(visibility: Int?) {
        when (visibility) {
            null -> {
                swipeMenuLayout.visibility = View.VISIBLE
            }
            else -> {
                swipeMenuLayout.visibility = visibility
            }
        }
    }

    @ModelProp
    fun setBorderWidthDp(padding: Int?) {
        if (padding == null) {
            contentLayout.setPadding(0)
        } else {
            contentLayout.setPadding(dp2px(context, padding))
        }
    }

    @ModelProp
    fun setContentViewEnable(isEnable: Boolean?) {
        if (isEnable != null) {
            contentLayout.isEnabled = isEnable
            checkBox.isEnabled = isEnable
        } else {
            contentLayout.isEnabled = true
            checkBox.isEnabled = true
        }
    }

    @ModelProp
    fun setHeaderIcon(@DrawableRes idRes: Int?) {
        if (idRes == null || idRes == 0) {
            headerIV.visibility = View.GONE
        } else {
            headerIV.visibility = View.VISIBLE
            headerIV.setImageResource(idRes)
        }
    }

    @TextProp
    fun setHeaderText(content: CharSequence?) {
        if (content.isNullOrEmpty()) {
            headerTV.visibility = View.GONE
        } else {
            headerTV.visibility = View.VISIBLE
            headerTV.text = content
        }
    }

    @TextProp
    fun setLeftText(content: CharSequence?) {
        checkBox.text = content ?: ""
    }

    @ModelProp
    fun setRightIsChecked(isChecked: Boolean?) {
        checkBox.setCheckedIfDifferent(isChecked ?: false)
    }

    @CallbackProp
    fun onCheckedChangeListener(listener: CompoundButton.OnCheckedChangeListener?) {
        checkBox.setOnCheckedChangeListener(listener)
    }

    @ModelProp
    @JvmOverloads
    fun setLeftMenu(leftMenu: List<SwipeMenuItem>? = null) {
        if (leftMenu == null) {
            hasSwipeMenu = false
            swipeMenuLayout.leftMenuView.removeAllViews()
            swipeMenuLayout.leftMenuEnable = false
            swipeDirectionIV.visibility = View.GONE
        } else {
            hasSwipeMenu = true
            swipeMenuLayout.leftMenuView.createMenu(leftMenu)
            swipeMenuLayout.leftMenuEnable = true
            swipeDirectionIV.visibility = View.VISIBLE
        }
    }

    @CallbackProp
    fun setLeftMenuClickListener(listener: ((swLayout: SwipeMenuLayout, swItem: SwipeMenuItem, view: View) -> Unit)?) {
        swipeMenuLayout.leftMenuView.setOnMenuItemClickListener { item ->
            listener?.invoke(swipeMenuLayout, item, swipeMenuLayout.leftMenuView)
        }
    }

    @ModelProp
    @JvmOverloads
    fun setRightMenu(rightMenu: List<SwipeMenuItem>? = null) {
        if (rightMenu == null) {
            hasSwipeMenu = false
            swipeMenuLayout.rightMenuView.removeAllViews()
            swipeMenuLayout.rightMenuEnable = false
            swipeDirectionIV.visibility = View.GONE
        } else {
            hasSwipeMenu = true
            swipeMenuLayout.rightMenuView.createMenu(rightMenu)
            swipeMenuLayout.rightMenuEnable = true
            swipeDirectionIV.visibility = View.VISIBLE
        }
    }

    @CallbackProp
    fun setRightMenuClickListener(listener: ((swLayout: SwipeMenuLayout, swItem: SwipeMenuItem, view: View) -> Unit)?) {
        swipeMenuLayout.rightMenuView.setOnMenuItemClickListener { item ->
            listener?.invoke(swipeMenuLayout, item, swipeMenuLayout.rightMenuView)
        }
    }

    private fun AppCompatCheckBox.setCheckedIfDifferent(newState: Boolean) {
        if (newState != this.isChecked) {
            this.isChecked = newState
        }
    }
}