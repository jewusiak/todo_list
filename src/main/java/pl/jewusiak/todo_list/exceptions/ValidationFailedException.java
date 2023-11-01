package pl.jewusiak.todo_list.exceptions;

import lombok.Getter;
import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

import java.util.Arrays;

@Getter
public class ValidationFailedException extends ResponseStatusException {
    private final String[] messages;

    public ValidationFailedException(String[] messages) {
        super(HttpStatus.BAD_REQUEST, Arrays.toString(messages));
        this.messages = messages;
    }

}
