package com.oyy.chatgtpstudy.api.edit;

import lombok.Data;

/**
 * An object containing the API usage for an edit request
 *
 * Deprecated, use {@link com.theokanning.openai.Usage} instead
 *
 * https://beta.openai.com/docs/api-reference/edits/create
 */
@Data
@Deprecated
public class EditUsage {

    /**
     * The number of prompt tokens consumed.
     */
    String promptTokens;

    /**
     * The number of completion tokens consumed.
     */
    String completionTokens;

    /**
     * The number of total tokens consumed.
     */
    String totalTokens;

    public String getPromptTokens() {
        return promptTokens;
    }

    public void setPromptTokens(String promptTokens) {
        this.promptTokens = promptTokens;
    }

    public String getCompletionTokens() {
        return completionTokens;
    }

    public void setCompletionTokens(String completionTokens) {
        this.completionTokens = completionTokens;
    }

    public String getTotalTokens() {
        return totalTokens;
    }

    public void setTotalTokens(String totalTokens) {
        this.totalTokens = totalTokens;
    }
}
