package com.oyy.chatgtpstudy.base

import androidx.lifecycle.LiveData

fun <T> LiveData<T>.toLiveData(): LiveData<T> = this