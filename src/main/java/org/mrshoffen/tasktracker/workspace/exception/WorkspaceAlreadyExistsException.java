package org.mrshoffen.tasktracker.workspace.exception;

public class WorkspaceAlreadyExistsException extends RuntimeException{
    public WorkspaceAlreadyExistsException(String message) {
        super(message);
    }
}
