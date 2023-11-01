package pl.jewusiak.todo_list.utils;

import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validator;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.validation.annotation.Validated;
import pl.jewusiak.todo_list.exceptions.ValidationFailedException;

import java.util.Arrays;
import java.util.Set;

@Component
@Validated
@Slf4j
@RequiredArgsConstructor
public class ValidationUtils {
    private final Validator validator;

    public <T> void validate(T object) {
        Set<ConstraintViolation<T>> violations = validator.validate(object);

        if (!violations.isEmpty()) {
            String[] array = violations.stream().map(ConstraintViolation::getMessage).toArray(String[]::new);
            log.info("Validation failed: {}", Arrays.toString(array));
            throw new ValidationFailedException(array);
        }
    }
}
