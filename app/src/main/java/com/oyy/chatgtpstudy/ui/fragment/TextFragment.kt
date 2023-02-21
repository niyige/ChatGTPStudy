package com.oyy.chatgtpstudy.ui.fragment

import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.View
import android.view.inputmethod.InputMethodManager
import androidx.fragment.app.viewModels
import androidx.lifecycle.observe
import com.oyy.chatgtpstudy.R
import com.oyy.chatgtpstudy.base.BaseFragment
import com.oyy.chatgtpstudy.vm.MainVm
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.fragment_text.*

@AndroidEntryPoint
class TextFragment : BaseFragment() {

    private val TAG = "TextFragment"

    override val vm: MainVm by viewModels(ownerProducer = { requireActivity() })
    override val layoutId = R.layout.fragment_text

    override fun initView(savedInstanceState: Bundle?) {
        super.initView(savedInstanceState)

        generateTv.setOnClickListener {
            var question = questionTv.text.toString()
            Log.i(TAG, "question=" + question)
            hideContent()
            hideKeyboard(questionTv)
            progress_pb.visibility = View.VISIBLE
            vm.textGenerateAnswer(question);
        }
    }

    /**
     * 数据获取监听
     */
    override fun createObserver() {
        super.createObserver()
        Log.i(TAG, "createObserver")
        vm.textAnswer.observe(viewLifecycleOwner) {
            progress_pb.visibility = View.GONE
            //显示到view上面
            val sb = StringBuilder()
            for (i in it.indices) {
                sb.append((i + 1).toString() + " " + it[i].text + "\r\n")
            }
            Log.i(TAG, "textAnswer answer=$sb")
            showContent(sb.toString())
        }
    }

    private fun showContent(result: String) {
        contentTv.visibility = View.VISIBLE
        contentTv.text = result
    }

    private fun hideContent() {
        contentTv.visibility = View.GONE
        contentTv.text = ""
    }

    private fun hideKeyboard(view: View) {
        val imm: InputMethodManager = view.context.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        imm.hideSoftInputFromWindow(
            view.windowToken,
            InputMethodManager.HIDE_NOT_ALWAYS
        )
    }

}