package com.example.MovieInABox.data.model.viewmodels.authen;

import com.example.MovieInABox.data.model.User;

import java.util.List;

public class AuthResult {
    private final boolean success;
    private final User    user;
    private final List<String> errors;

    private AuthResult(boolean success, User user, List<String> errors) {
        this.success = success;
        this.user    = user;
        this.errors  = errors;
    }

    public static AuthResult success(User u)   { return new AuthResult(true, u, null); }
    public static AuthResult fail(String msg)  { return new AuthResult(false, null, List.of(msg)); }
    public static AuthResult fail(List<String> errs) { return new AuthResult(false, null, errs); }

    public boolean isSuccess() {
        return success;
    }

    public User getUser() {
        return user;
    }

    public List<String> getErrors() {
        return errors;
    }
}
