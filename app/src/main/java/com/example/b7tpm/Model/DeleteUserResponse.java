package com.example.b7tpm.Model;

public class DeleteUserResponse {
    private boolean error;
    private String message;

    public DeleteUserResponse() {
    }

    public boolean isError() {
        return error;
    }

    public void setError(boolean error) {
        this.error = error;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
