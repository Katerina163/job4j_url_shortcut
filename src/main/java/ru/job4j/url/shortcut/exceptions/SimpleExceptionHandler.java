package ru.job4j.url.shortcut.exceptions;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.sql.SQLException;
import java.util.Map;
import java.util.stream.Collectors;

@Slf4j
@RestControllerAdvice
public class SimpleExceptionHandler {

    @ExceptionHandler(value = {NullPointerException.class, IllegalArgumentException.class})
    public ResponseEntity<?> handleException(Exception e) {
        log.error(e.getMessage());
        return ResponseEntity.badRequest().body(
                Map.of("message", "Some fields are incorrect",
                        "details", e.getLocalizedMessage())
        );
    }

    @ExceptionHandler(SQLException.class)
    public ResponseEntity<?> sqlException(Exception e) {
        log.error(e.getMessage());
        String s = e.getCause().getCause().getMessage();
        return ResponseEntity.badRequest().body(
                Map.of("message", "Этот url уже зарегистрирован",
                        "details", s.substring(0, s.indexOf("\"")))
        );
    }

    @ExceptionHandler({MethodArgumentNotValidException.class})
    public ResponseEntity<?> validationException(MethodArgumentNotValidException e) {
        log.error(e.getMessage());
        return ResponseEntity.badRequest().body(
                e.getFieldErrors().stream()
                        .map(f -> Map.of(
                                "message", f.getField() + " are incorrect",
                                "details", String.format("%s. Текущее значение: %s", f.getDefaultMessage(), f.getRejectedValue())
                        ))
                        .collect(Collectors.toList())
        );
    }
}
