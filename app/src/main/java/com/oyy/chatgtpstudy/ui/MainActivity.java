package com.oyy.chatgtpstudy.ui;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.oyy.chatgtpstudy.R;
import com.oyy.chatgtpstudy.api.completion.CompletionChoice;
import com.oyy.chatgtpstudy.api.completion.CompletionRequest;
import com.oyy.chatgtpstudy.service.OpenAiService;

import java.time.Duration;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    String token = "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"; //唯一token

    EditText questionTv;
    TextView generateTv;
    TextView contentTv;


    Handler handler = new Handler(Looper.getMainLooper()) {
        @Override
        public void handleMessage(@NonNull Message msg) {
            super.handleMessage(msg);
            List<CompletionChoice> results = (List<CompletionChoice>) msg.obj;
            if (null != results && results.size() > 0) {
                StringBuilder sb = new StringBuilder();
                for (int i = 0; i < results.size(); i++) {
                    sb.append((i + 1) + " " + results.get(i).getText() + "\r\n");
                }
                showContent(sb.toString());
            } else {
                showContent("请问点正常的!!!");
            }

        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initView();
        initClick();
    }

    @SuppressLint("CutPasteId")
    private void initView() {
        questionTv = findViewById(R.id.questionTv);
        generateTv = findViewById(R.id.generateTv);
        contentTv = findViewById(R.id.contentTv);
    }

    private void initClick() {
        generateTv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.d("oyy", "开始生成");
//                questionTv.setText("模拟谈恋爱");
                hideContent();
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        toGenerate();
                    }
                }).start();

            }
        });
    }

    /**
     * 开始生成
     */
    private void toGenerate() {

        try {
            OpenAiService service = new OpenAiService(token, Duration.ofSeconds(60));

            String question = questionTv.getText().toString().trim();

            CompletionRequest completionRequest = new CompletionRequest("text-davinci-003", question, 1, false, "niyige");
            Log.d("oyy", "生成中");
            List<CompletionChoice> results = service.createCompletion(completionRequest).getChoices();
            Log.d("oyy", "生成结束, data=" + results.toString());
            Message message = Message.obtain();
            message.what = 1;
            message.obj = results;
            handler.sendMessage(message);

        } catch (Exception e) {
            e.printStackTrace();
            Log.e("oyy", e.getMessage());
        }

    }

    private void showContent(String result) {
        contentTv.setVisibility(View.VISIBLE);
        contentTv.setText(result);
    }

    private void hideContent() {
        contentTv.setVisibility(View.GONE);
    }
}