package com.oyy.chatgtpstudy.api.finetune;

import lombok.Data;

/**
 * An object representing an event in the lifecycle of a fine-tuning job
 *
 * https://beta.openai.com/docs/api-reference/fine-tunes
 */
@Data
public class FineTuneEvent {
    /**
     * The type of object returned, should be "fine-tune-event".
     */
    String object;

    /**
     * The creation time in epoch seconds.
     */
    Long createdAt;

    /**
     * The log level of this message.
     */
    String level;

    /**
     * The event message.
     */
    String message;

    public String getObject() {
        return object;
    }

    public void setObject(String object) {
        this.object = object;
    }

    public Long getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Long createdAt) {
        this.createdAt = createdAt;
    }

    public String getLevel() {
        return level;
    }

    public void setLevel(String level) {
        this.level = level;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
