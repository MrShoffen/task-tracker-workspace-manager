package org.mrshoffen.tasktracker.task.desk.exception;

public class DeskAlreadyExistsException extends RuntimeException{
    public DeskAlreadyExistsException(String message) {
        super(message);
    }
}
