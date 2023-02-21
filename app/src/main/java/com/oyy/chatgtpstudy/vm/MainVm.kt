package com.oyy.chatgtpstudy.vm

import androidx.lifecycle.MutableLiveData
import com.oyy.chatgtpstudy.api.completion.CompletionChoice
import com.oyy.chatgtpstudy.api.image.Image
import com.oyy.chatgtpstudy.base.BaseVm
import com.oyy.chatgtpstudy.base.launch
import com.oyy.chatgtpstudy.base.toLiveData
import com.oyy.chatgtpstudy.repo.MainRepo
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class MainVm @Inject constructor(private val mainRepo: MainRepo) : BaseVm() {

    private val _textAnswer = MutableLiveData<List<CompletionChoice>>()

    val textAnswer = _textAnswer.toLiveData()

    private val _imgAnswer = MutableLiveData<List<Image>>()
    val imgAnswer = _imgAnswer.toLiveData()

    /**
     * 生成文字答案
     */
    fun textGenerateAnswer(question: String) = launch {
        job {
            mainRepo.textGenerateAnswer(question).run {
                if (this.isNotEmpty()) {
                    _textAnswer.postValue(this)
                } else {
                    _textAnswer.postValue(ArrayList<CompletionChoice>())
                }
            }
        }
        onError {
            var result = ArrayList<CompletionChoice>();
            result.add(CompletionChoice("出现错误：${it.message}"))
            _textAnswer.postValue(result)
        }
    }

    /**
     * 生成图片答案
     */
    fun imgGenerateAnswer(question: String) = launch {
        job {
            mainRepo.imgGenerateAnswer(question).run {
                if (this.isNotEmpty()) {
                    _imgAnswer.postValue(this)
                } else {
                    _imgAnswer.postValue(ArrayList<Image>())
                }
            }
        }
        onError {
            var result = ArrayList<Image>();
            result.add(Image("错误信息: ${it.message}"))
            _imgAnswer.postValue(result)
        }
    }

}