package org.mrshoffen.tasktracker.task.desk.exception;

public class DeskNotFoundException extends RuntimeException{
    public DeskNotFoundException(String message) {
        super(message);
    }
}
