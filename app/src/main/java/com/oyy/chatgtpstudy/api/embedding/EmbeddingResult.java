package com.oyy.chatgtpstudy.api.embedding;

import com.oyy.chatgtpstudy.api.Usage;

import java.util.List;

import lombok.Data;

/**
 * An object containing a response from the answer api
 *
 * https://beta.openai.com/docs/api-reference/embeddings/create
 */
@Data
public class EmbeddingResult {

    /**
     * The GPT-3 model used for generating embeddings
     */
    String model;

    /**
     * The type of object returned, should be "list"
     */
    String object;

    /**
     * A list of the calculated embeddings
     */
    List<Embedding> data;

    /**
     * The API usage for this request
     */
    Usage usage;

    public String getModel() {
        return model;
    }

    public void setModel(String model) {
        this.model = model;
    }

    public String getObject() {
        return object;
    }

    public void setObject(String object) {
        this.object = object;
    }

    public List<Embedding> getData() {
        return data;
    }

    public void setData(List<Embedding> data) {
        this.data = data;
    }

    public Usage getUsage() {
        return usage;
    }

    public void setUsage(Usage usage) {
        this.usage = usage;
    }
}
