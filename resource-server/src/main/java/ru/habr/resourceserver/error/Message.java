package ru.habr.resourceserver.error;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


@Getter
@Setter
@NoArgsConstructor

public class Message {
    private boolean status;

    @JsonInclude(JsonInclude.Include.NON_NULL)
    private String msg;

    @JsonInclude(JsonInclude.Include.NON_NULL)
    private Error error;

    public Message(boolean status, Error error) {
        this.status = status;
        this.error = error;
    }
    public Message(boolean status, String message) {
        this.status = status;
        this.msg=message;
    }
}
