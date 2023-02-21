package com.oyy.chatgtpstudy.ui

import android.os.Bundle
import android.util.Log
import android.view.WindowManager
import androidx.activity.viewModels
import androidx.fragment.app.Fragment
import androidx.fragment.app.commit
import com.oyy.chatgtpstudy.R
import com.oyy.chatgtpstudy.base.BaseActivity
import com.oyy.chatgtpstudy.base.BaseVm
import com.oyy.chatgtpstudy.ui.fragment.ImgFragment
import com.oyy.chatgtpstudy.ui.fragment.TextFragment
import com.oyy.chatgtpstudy.vm.MainVm
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.activity_main_page.*

@AndroidEntryPoint
class MainPageActivity : BaseActivity() {

    private val TAG = "MainPageActivity"
    override val vm: MainVm by viewModels()

    override val layoutId: Int = R.layout.activity_main_page

    private val textFragment by lazy { TextFragment() }

    private val imgFragment by lazy { ImgFragment() }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        savedInstanceState?.toString()?.let { Log.e(TAG, it) }
        window.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN)
        replaceFragment(textFragment)
    }

    override fun initView(saveInstanceState: Bundle?) {
        super.initView(saveInstanceState)

        qaText.setOnClickListener {
            replaceFragment(textFragment)
        }
        qaImgText.setOnClickListener {
            replaceFragment(imgFragment)
        }
    }


    private fun replaceFragment(fragment: Fragment) {
        supportFragmentManager.commit {
            replace(R.id.mainLayout, fragment)
        }
    }

}