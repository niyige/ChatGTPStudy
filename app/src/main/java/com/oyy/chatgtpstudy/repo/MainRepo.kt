package com.oyy.chatgtpstudy.repo

import android.util.Log
import com.oyy.chatgtpstudy.api.completion.CompletionChoice
import com.oyy.chatgtpstudy.api.completion.CompletionRequest
import com.oyy.chatgtpstudy.api.completion.CompletionResult
import com.oyy.chatgtpstudy.api.image.CreateImageRequest
import com.oyy.chatgtpstudy.api.image.Image
import com.oyy.chatgtpstudy.base.BaseRepo
import com.oyy.chatgtpstudy.service.OpenAiService
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.time.Duration
import javax.inject.Inject

class MainRepo @Inject constructor() : BaseRepo() {
    private var service: OpenAiService

    init {
        var token = "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" //唯一token
        service = OpenAiService(token, Duration.ofSeconds(60))
    }

    suspend fun textGenerateAnswer(question: String): List<CompletionChoice> =
        withContext(Dispatchers.IO) {
            val completionRequest =
                CompletionRequest("text-davinci-003", question, 1, false, "niyige")
            Log.d("oyy", "生成中")
            val results: CompletionResult = service.createCompletion(completionRequest)
            Log.d("oyy", "生成结束, data=$results")
            val choses: List<CompletionChoice> = results.choices
            return@withContext choses;
        }

    /**
     * 生成图片
     */
    suspend fun imgGenerateAnswer(question: String): List<Image> = withContext(Dispatchers.IO) {
        val CreateImageRequest = CreateImageRequest(question)
        Log.d("oyy", "生成中")
        val results = service.createImage(CreateImageRequest).data
        Log.d("oyy", "生成结束, data=$results")
        return@withContext results
    }

    /**
     * 生成图片
     */
//    fun imgGenerateAnswer(question: String): List<Image> {
//        val CreateImageRequest = CreateImageRequest(question)
//        Log.d("oyy", "生成中")
//        val results = service.createImage(CreateImageRequest).data
//        Log.d("oyy", "生成结束, data=$results")
//        return results
//    }

}