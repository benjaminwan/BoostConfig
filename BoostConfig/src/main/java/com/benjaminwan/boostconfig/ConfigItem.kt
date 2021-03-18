package com.benjaminwan.boostconfig

import android.view.View
import android.widget.CompoundButton
import com.benjaminwan.swipemenulayout.SwipeMenuItem
import com.benjaminwan.swipemenulayout.SwipeMenuLayout
import java.util.*

data class ConfigItem constructor(
    val typeFunc: (() -> ConfigType) = { ConfigType.Text },
    inline val borderWidthFunc: (() -> Int) = { 0 },
    inline val visibilityFuc: (() -> Int) = { View.VISIBLE },
    inline val enableFunc: (() -> Boolean) = { true },
    inline val headerTextFunc: (() -> CharSequence) = { "" },
    inline val headerIconFunc: (() -> Int) = { 0 },
    inline val leftMenusFunc: (() -> List<SwipeMenuItem>) = { emptyList() },
    inline val rightMenusFunc: (() -> List<SwipeMenuItem>) = { emptyList() },
    inline val leftTextFunc: (() -> String) = { "" },
    inline val rightTextFunc: (() -> String) = { "" },
    inline val checkFunc: (() -> Boolean) = { true },
    inline val click: ((view: View) -> Unit) = {},
    inline val checkedChange: ((button: CompoundButton, isChecked: Boolean) -> Unit) = { button, isChecked -> },
    inline val leftMenuClick: ((swipe: SwipeMenuLayout, swItem: SwipeMenuItem, view: View) -> Unit) = { swipe, swItem, view -> },
    inline val rightMenuClick: ((swipe: SwipeMenuLayout, swItem: SwipeMenuItem, view: View) -> Unit) = { swipe, swItem, view -> },
    inline val tagFunc: (() -> Any) = {}
) {
    val type get() = typeFunc()
    val borderWidth get() = borderWidthFunc()
    val headerText get() = headerTextFunc()
    val headerIcon get() = headerIconFunc()
    val leftMenus get() = leftMenusFunc()
    val rightMenus get() = rightMenusFunc()
    val visibility get() = visibilityFuc()
    val isEnabled get() = enableFunc()
    val isChecked get() = checkFunc()
    val leftText get() = leftTextFunc()
    val rightText get() = rightTextFunc()
    val tag get() = tagFunc()
}

@DslMarker
annotation class ConfigItemDsl

@ConfigItemDsl
class ConfigItemBuilder {
    var type: (() -> ConfigType) = { ConfigType.Text }
    var borderWidth: (() -> Int) = { 0 }
    var visibility: (() -> Int) = { View.VISIBLE }
    var isEnabled: (() -> Boolean) = { true }
    var headerText: (() -> CharSequence) = { "" }
    var headerIcon: (() -> Int) = { 0 }
    var leftMenus: (() -> List<SwipeMenuItem>) = { emptyList() }
    var rightMenus: (() -> List<SwipeMenuItem>) = { emptyList() }
    var isChecked: (() -> Boolean) = { true }
    var leftText: (() -> String) = { "" }
    var rightText: (() -> String) = { "" }
    var click: ((view: View) -> Unit) = {}
    var checkedChange: ((button: CompoundButton, isChecked: Boolean) -> Unit) =
        { button, isChecked -> }
    var leftMenuClick: ((swipe: SwipeMenuLayout, swItem: SwipeMenuItem, view: View) -> Unit) =
        { swipe, swItem, view -> }
    var rightMenuClick: ((swipe: SwipeMenuLayout, swItem: SwipeMenuItem, view: View) -> Unit) =
        { swipe, swItem, view -> }
    var tag: (() -> Any) = {}

    fun build(): ConfigItem =
        ConfigItem(
            type,
            borderWidth,
            visibility,
            isEnabled,
            headerText,
            headerIcon,
            leftMenus,
            rightMenus,
            leftText,
            rightText,
            isChecked,
            click,
            checkedChange,
            leftMenuClick,
            rightMenuClick,
            tag
        )
}

inline fun configItem(block: ConfigItemBuilder.() -> Unit): ConfigItem =
    ConfigItemBuilder().apply(block).build()

@ConfigItemDsl
class ConfigItemsAdder : ArrayList<ConfigItem>() {
    inline fun configItem(block: ConfigItemBuilder.() -> Unit) {
        add(ConfigItemBuilder().apply(block).build())
    }

    fun configItem(config: ConfigItem) {
        add(config)
    }

    fun configItem(configs: List<ConfigItem>) {
        addAll(configs)
    }
}

inline fun configItems(block: ConfigItemsAdder.() -> Unit): List<ConfigItem> =
    ConfigItemsAdder().apply(block)