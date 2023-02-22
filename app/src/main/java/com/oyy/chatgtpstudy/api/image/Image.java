package com.oyy.chatgtpstudy.api.image;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Data;

/**
 * An object containing either a URL or a base 64 encoded image.
 *
 * https://beta.openai.com/docs/api-reference/images
 */
@Data
public class Image {
    /**
     * The URL where the image can be accessed.
     */
    String url;

    String message;


    /**
     * Base64 encoded image string.
     */
    @JsonProperty("b64_json")
    String b64Json;

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getB64Json() {
        return b64Json;
    }

    public void setB64Json(String b64Json) {
        this.b64Json = b64Json;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Image(String message) {
        this.message = message;
    }

    public Image(String url, String message, String b64Json) {
        this.url = url;
        this.message = message;
        this.b64Json = b64Json;
    }
    public Image() {
    }

    @Override
    public String toString() {
        return "Image{" +
                "url='" + url + '\'' +
                ", message='" + message + '\'' +
                ", b64Json='" + b64Json + '\'' +
                '}';
    }
}
