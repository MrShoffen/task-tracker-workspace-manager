package org.mrshoffen.tasktracker.workspace.advice;


import lombok.extern.slf4j.Slf4j;
import org.mrshoffen.tasktracker.workspace.exception.WorkspaceAlreadyExistsException;
import org.mrshoffen.tasktracker.workspace.exception.WorkspaceNotFoundException;
import org.springframework.beans.TypeMismatchException;
import org.springframework.http.*;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.lang.Nullable;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.bind.support.WebExchangeBindException;
import org.springframework.web.context.request.WebRequest;
import reactor.core.publisher.Mono;

import java.util.stream.Collectors;

import static org.springframework.http.HttpStatus.*;

@Slf4j
@RestControllerAdvice
public class WorkspaceControllerAdvice {



    protected ResponseEntity<Object> handleHttpMessageNotReadable(HttpMessageNotReadableException ex, HttpHeaders headers, HttpStatusCode status, WebRequest request) {
        var problemDetail = ProblemDetail.forStatusAndDetail(BAD_REQUEST, "Некорректный формат UUID для задачи-родителя");
        problemDetail.setTitle("Bad Request");
        return ResponseEntity
                .status(BAD_REQUEST)
                .body(problemDetail);
    }

    @Nullable
    protected ResponseEntity<Object> handleTypeMismatch(
            TypeMismatchException ex, HttpHeaders headers, HttpStatusCode status, WebRequest request) {
        var problemDetail = ProblemDetail.forStatusAndDetail(BAD_REQUEST, "Некорректный id задачи");
        problemDetail.setTitle("Bad Request");
        return ResponseEntity
                .status(BAD_REQUEST)
                .body(problemDetail);
    }

    @ExceptionHandler(WebExchangeBindException.class)
    public Mono<ResponseEntity<ProblemDetail>> handleValidationErrors(WebExchangeBindException e) {
        String errors = e.getFieldErrors()
                .stream()
                .map(FieldError::getDefaultMessage)
                .collect(Collectors.joining(" | "));
        var problemDetail = ProblemDetail.forStatusAndDetail(HttpStatus.BAD_REQUEST, errors);
        problemDetail.setTitle("Validation error");
        return Mono.just(ResponseEntity.badRequest().body(problemDetail));
    }

    @ExceptionHandler(WorkspaceAlreadyExistsException.class)
    public Mono<ResponseEntity<ProblemDetail>> handleDeskAlreadyExists(WorkspaceAlreadyExistsException e) {
        ProblemDetail problem = generateProblemDetail(CONFLICT, e);
        return Mono.just(ResponseEntity.status(CONFLICT).body(problem));
    }

    @ExceptionHandler(WorkspaceNotFoundException.class)
    public Mono<ResponseEntity<ProblemDetail>> handleDeskNotFound(WorkspaceNotFoundException e) {
        ProblemDetail problem = generateProblemDetail(NOT_FOUND, e);
        return Mono.just(ResponseEntity.status(NOT_FOUND).body(problem));
    }

    private ProblemDetail generateProblemDetail(HttpStatus status, Exception ex) {
        log.warn("Error occured: {}", ex.getMessage());

        var problemDetail = ProblemDetail.forStatusAndDetail(status, ex.getMessage());
        problemDetail.setTitle(status.getReasonPhrase());
        return problemDetail;
    }

}
