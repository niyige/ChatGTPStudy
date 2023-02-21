package com.oyy.chatgtpstudy.api.moderation;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;

/**
 * A request for OpenAi to detect if text violates OpenAi's content policy.
 *
 * https://beta.openai.com/docs/api-reference/moderations/create
 */
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Data
public class ModerationRequest {

    /**
     * The input text to classify.
     */
    @NonNull
    String input;

    /**
     * The name of the model to use, defaults to text-moderation-stable.
     */
    String model;

    public String getInput() {
        return input;
    }

    public void setInput(String input) {
        this.input = input;
    }

    public String getModel() {
        return model;
    }

    public void setModel(String model) {
        this.model = model;
    }
}
