package pl.jewusiak.todo_list.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

public class NotFoundException extends ResponseStatusException {

    public NotFoundException(String entityName, String id) {
        super(HttpStatus.NOT_FOUND, entityName + " with id " + id + " not found");
    }
}
