package org.mrshoffen.tasktracker.workspace.exception;

public class WorkspaceNotFoundException extends RuntimeException{
    public WorkspaceNotFoundException(String message) {
        super(message);
    }
}
