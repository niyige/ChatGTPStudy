package com.oyy.chatgtpstudy.base

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

fun ViewModel.launch(block: ViewModelLaunchBuild.() -> Unit) {
    val also = ViewModelLaunchBuild().also(block)
    if (also.job == null) {
        throw RuntimeException("job is null")
    }
    launch(also.job!!, also.onError, also.onComplete)
}

class ViewModelLaunchBuild {
    internal var job: (suspend CoroutineScope.() -> Unit)? = null
    internal var onError: (Throwable) -> Unit = {}
    internal var onComplete: () -> Unit = {}
    fun job(block: suspend CoroutineScope.() -> Unit) {
        job = block
    }

    fun onError(block: (Throwable) -> Unit) {
        onError = block
    }

    fun onComplete(block: () -> Unit) {
        onComplete = block
    }
}

fun ViewModel.launch(
    block: suspend CoroutineScope.() -> Unit,
    onError: (e: Throwable) -> Unit = {},
    onComplete: () -> Unit = {}
) {
    viewModelScope.launch(kotlinx.coroutines.Dispatchers.Main + kotlinx.coroutines.CoroutineExceptionHandler { _, e ->
        run {
            e.printStackTrace()
            onError(e)
        }
    }) {
        try {
            block.invoke(this)
        } finally {
            onComplete()
        }
    }
}