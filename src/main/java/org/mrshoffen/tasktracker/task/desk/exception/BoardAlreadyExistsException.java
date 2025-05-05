package org.mrshoffen.tasktracker.task.desk.exception;

public class BoardAlreadyExistsException extends RuntimeException{
    public BoardAlreadyExistsException(String message) {
        super(message);
    }
}
