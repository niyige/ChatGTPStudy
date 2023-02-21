package com.oyy.chatgtpstudy.base

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.observe

abstract class BaseFragment: Fragment() {

    private var isFirstLoad: Boolean = true

    abstract val vm: BaseVm

    abstract val layoutId: Int

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(layoutId, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        isFirstLoad = true
        initView(savedInstanceState)
        createDefaultObserver()
        createObserver()
        initData()
    }

    private fun createDefaultObserver() {
        vm.toastLV.observe(viewLifecycleOwner) {
           //
        }
    }

    open fun initView(savedInstanceState: Bundle?) {}

    open fun createObserver() {}

    override fun onResume() {
        super.onResume()
    }

    /**
     * Fragment执行onCreate后触发的方法
     */
    open fun initData() {}
}