package com.benjaminwan.boostconfig.itemviews

import android.content.Context
import android.text.Editable
import android.text.Spanned
import android.text.TextWatcher
import android.util.AttributeSet
import android.view.MotionEvent
import android.view.View
import android.widget.EditText
import android.widget.FrameLayout
import androidx.annotation.DrawableRes
import androidx.appcompat.widget.AppCompatEditText
import androidx.appcompat.widget.AppCompatImageView
import androidx.appcompat.widget.AppCompatTextView
import androidx.appcompat.widget.LinearLayoutCompat
import androidx.core.content.ContextCompat
import androidx.core.view.setPadding
import com.airbnb.epoxy.CallbackProp
import com.airbnb.epoxy.ModelProp
import com.airbnb.epoxy.ModelView
import com.airbnb.epoxy.TextProp
import com.benjaminwan.boostconfig.R
import com.benjaminwan.boostconfig.utils.dp2px
import com.benjaminwan.boostconfig.utils.getColorStateListPrimary
import com.benjaminwan.swipemenulayout.SwipeMenuItem
import com.benjaminwan.swipemenulayout.SwipeMenuLayout

@ModelView(autoLayout = ModelView.Size.MATCH_WIDTH_WRAP_HEIGHT)
class EditItemView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : FrameLayout(context, attrs, defStyleAttr) {
    private val swipeMenuLayout by lazy { findViewById<SwipeMenuLayout>(R.id.swipeMenuLayout) }
    private val contentLayout by lazy { findViewById<LinearLayoutCompat>(R.id.contentLayout) }
    private val swipeDirectionIV by lazy { findViewById<AppCompatImageView>(R.id.swipeDirectionIV) }
    private val headerIV by lazy { findViewById<AppCompatImageView>(R.id.headerIV) }
    private val headerTV by lazy { findViewById<AppCompatTextView>(R.id.headerTV) }
    private val leftTV by lazy { findViewById<AppCompatTextView>(R.id.leftTV) }
    private val rightET by lazy { findViewById<AppCompatEditText>(R.id.rightET) }
    private val rightTextWatcher = SimpleTextWatcher { onRightTextChanged?.invoke(it) }

    init {
        View.inflate(context, R.layout.rv_edit_item, this)
        swipeMenuLayout.addOnMenuRightOpenedListener {
            swipeDirectionIV.setImageResource(R.drawable.ic_swipe_right)
        }
        swipeMenuLayout.addOnMenuClosedListener {
            swipeDirectionIV.setImageResource(R.drawable.ic_swipe_left)
        }
        rightET.addTextChangedListener(rightTextWatcher)
        contentLayout.backgroundTintList = getColorStateListPrimary(context)
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
            rightET.isEnabled = isEnable
            val colorRes = if (isEnable) R.color.material_blue_500 else R.color.material_grey_500
            rightET.setTextColor(ContextCompat.getColorStateList(context, colorRes))
        } else {
            contentLayout.isEnabled = true
            rightET.isEnabled = true
            rightET.setTextColor(
                ContextCompat.getColorStateList(
                    context,
                    R.color.material_blue_500
                )
            )
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
        leftTV.text = content ?: ""
    }

    @TextProp
    fun setRightText(content: CharSequence?) {
        rightET.setTextIfDifferent(content ?: "")
    }

    @set:CallbackProp
    var onRightTextChanged: ((newText: String) -> Unit)? = null

    @CallbackProp
    fun onClickListener(listener: View.OnClickListener?) {
        contentLayout.setOnClickListener(listener)
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

    @Suppress("Detekt.EmptyFunctionBlock")
    inner class SimpleTextWatcher(val onTextChanged: (newText: String) -> Unit) : TextWatcher {
        override fun afterTextChanged(s: Editable) {}

        override fun beforeTextChanged(s: CharSequence, start: Int, count: Int, after: Int) {}

        override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {
            onTextChanged(s.toString())
        }
    }

    /**
     * Set the given text on the textview.
     *
     * @return True if the given text is different from the previous text on the textview.
     */
    private fun EditText.setTextIfDifferent(newText: CharSequence?): Boolean {
        if (!isTextDifferent(newText, text)) {
            // Previous text is the same. No op
            return false
        }

        setText(newText)
        // Since the text changed we move the cursor to the end of the new text.
        // This allows us to fill in text programmatically with a different value,
        // but if the user is typing and the view is rebound we won't lose their cursor position.
        setSelection(newText?.length ?: 0)
        return true
    }

    /**
     * @return True if str1 is different from str2.
     *
     *
     * This is adapted from how the Android DataBinding library binds its text views.
     */
    private fun isTextDifferent(str1: CharSequence?, str2: CharSequence?): Boolean {
        if (str1 === str2) {
            return false
        }
        if (str1 == null || str2 == null) {
            return true
        }
        val length = str1.length
        if (length != str2.length) {
            return true
        }

        if (str1 is Spanned) {
            return str1 != str2
        }

        for (i in 0 until length) {
            if (str1[i] != str2[i]) {
                return true
            }
        }
        return false
    }
}