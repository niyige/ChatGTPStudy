package com.oyy.chatgtpstudy.ui.fragment

import android.content.Context
import android.graphics.drawable.Drawable
import android.os.Bundle
import android.text.TextUtils
import android.util.Log
import android.view.View
import android.view.inputmethod.InputMethodManager
import androidx.fragment.app.viewModels
import androidx.lifecycle.observe
import com.bumptech.glide.Glide
import com.bumptech.glide.load.DataSource
import com.bumptech.glide.load.engine.GlideException
import com.bumptech.glide.request.RequestListener
import com.bumptech.glide.request.target.Target
import com.oyy.chatgtpstudy.R
import com.oyy.chatgtpstudy.base.BaseFragment
import com.oyy.chatgtpstudy.vm.MainVm
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.fragment_img.*

@AndroidEntryPoint
class ImgFragment : BaseFragment() {

    private val TAG = "ImgFragment"

    override val vm: MainVm by viewModels(ownerProducer = { requireActivity() })
    override val layoutId = R.layout.fragment_img

    override fun initView(savedInstanceState: Bundle?) {
        super.initView(savedInstanceState)
        imgGenerateTv.setOnClickListener {
            var question = imgQuestionETv.text.toString()
            Log.i(TAG, "question=" + question)
            hideContent()
            hideKeyboard(imgQuestionETv)
            imgProgressPb.visibility = View.VISIBLE
            vm.imgGenerateAnswer(question);
        }
    }

    /**
     * 数据获取监听
     */
    override fun createObserver() {
        super.createObserver()
        vm.imgAnswer.observe(viewLifecycleOwner) {
            imgProgressPb.visibility = View.GONE
            if (it.isNotEmpty() && TextUtils.isEmpty(it[0].message)) {
                showContent(it[0].url)
            } else {
                vm.toast(it[0].message)
            }

        }
    }

    private fun showContent(result: String) {
        contentImg.visibility = View.VISIBLE
        activity?.let {
            Glide.with(it)
                .load(result)
//                .placeholder()
                .centerCrop()
                .addListener(object : RequestListener<Drawable?> {
                    override fun onLoadFailed(
                        e: GlideException?,
                        model: Any?,
                        target: Target<Drawable?>?,
                        isFirstResource: Boolean
                    ): Boolean {
                        //下载失败
                        return false;
                    }

                    override fun onResourceReady(
                        resource: Drawable?,
                        model: Any?,
                        target: Target<Drawable?>?,
                        dataSource: DataSource?,
                        isFirstResource: Boolean
                    ): Boolean {
                        //下载成功
                        return false;
                    }

                })
                .into(contentImg)
        }
    }

    private fun hideContent() {
        contentImg.visibility = View.GONE
    }

    private fun hideKeyboard(view: View) {
        val imm: InputMethodManager =
            view.context.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        imm.hideSoftInputFromWindow(
            view.windowToken,
            InputMethodManager.HIDE_NOT_ALWAYS
        )
    }
}