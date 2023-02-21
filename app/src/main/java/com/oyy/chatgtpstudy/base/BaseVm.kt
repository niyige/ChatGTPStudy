package com.oyy.chatgtpstudy.base

import androidx.annotation.StringRes
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

abstract class BaseVm: ViewModel() {

    private val _toastLV = MutableLiveData("")
    val toastLV = _toastLV.toLiveData()

    fun toast(content: String) {
        _toastLV.postValue(content)
    }

}

fun ViewModel.getString(@StringRes idRes: Int): String {
    return idRes.toString()
}

fun ViewModel.getString(@StringRes idRes: Int, vararg formatArgs: Any?): String {
    return idRes.toString()
}

