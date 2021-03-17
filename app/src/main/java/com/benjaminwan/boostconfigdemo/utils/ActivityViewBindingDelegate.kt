package com.benjaminwan.boostconfigdemo.utils

import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleObserver
import androidx.lifecycle.OnLifecycleEvent
import androidx.viewbinding.ViewBinding
import kotlin.properties.ReadOnlyProperty
import kotlin.reflect.KProperty

/**
 * Create bindings for a view similar to bindView.
 *
 * To use, just call
 * private val binding: FHomeWorkoutDetailsBinding by viewBinding()
 * with your binding class and access it as you normally would.
 */
inline fun <reified T : ViewBinding> AppCompatActivity.viewBinding() =
    ActivityViewBindingDelegate(T::class.java, this)

class ActivityViewBindingDelegate<T : ViewBinding>(
    private val bindingClass: Class<T>,
    val acitivty: AppCompatActivity
) : ReadOnlyProperty<AppCompatActivity, T> {
    private var binding: T? = null

    private val bindMethod = bindingClass.getMethod("bind", View::class.java)

    override fun getValue(thisRef: AppCompatActivity, property: KProperty<*>): T {
        binding?.let { return it }

        val lifecycle = acitivty.lifecycle
        if (!lifecycle.currentState.isAtLeast(Lifecycle.State.INITIALIZED)) {
            error("Cannot access view bindings. View lifecycle is ${lifecycle.currentState}!")
        }
        acitivty.lifecycle.addObserver(object : LifecycleObserver {
            @OnLifecycleEvent(Lifecycle.Event.ON_DESTROY)
            fun onDestroy() {
                binding = null
            }
        })
        val rootView = (thisRef.findViewById(android.R.id.content) as ViewGroup).getChildAt(0)
        @Suppress("UNCHECKED_CAST")
        binding = bindMethod.invoke(null, rootView) as T
        return binding!!
    }
}
