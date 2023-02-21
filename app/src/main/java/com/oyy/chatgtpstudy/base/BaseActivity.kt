package com.oyy.chatgtpstudy.base

import android.os.Build
import android.os.Bundle
import android.view.View
import android.view.Window
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.observe

/**
 * 基类
 */
abstract class BaseActivity : AppCompatActivity() {

    abstract val vm: BaseVm

    abstract val layoutId: Int

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        supportRequestWindowFeature(Window.FEATURE_NO_TITLE)
        window.decorView.setOnSystemUiVisibilityChangeListener { translucent() }
        if (layoutId != 0) {
            setContentView(layoutId)
        }
        init(savedInstanceState)
    }

    private fun init(saveInstanceState: Bundle?) {
        initView(saveInstanceState)
        defaultObserver()
        createObserver()
    }

    open fun initView(saveInstanceState: Bundle?) {}


    private fun defaultObserver() {
        vm.toastLV.observe(this) {
            Toast.makeText(this, it, Toast.LENGTH_LONG).show()
        }
    }

    /**
     * 创建LiveData数据观察者
     */
    open fun createObserver() {}

    override fun onWindowFocusChanged(hasFocus: Boolean) {
        super.onWindowFocusChanged(hasFocus)
        translucent()
    }


    private fun translucent() {
        if (Build.VERSION.SDK_INT >= 19) {
            val decorView = window.decorView
            decorView.systemUiVisibility = (View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                    or View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                    or View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                    or View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
                    or View.SYSTEM_UI_FLAG_FULLSCREEN
                    or View.SYSTEM_UI_FLAG_IMMERSIVE)
        }
    }

}