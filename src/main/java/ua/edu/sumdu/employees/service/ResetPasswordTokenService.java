package ua.edu.sumdu.employees.service;

import ua.edu.sumdu.employees.model.user.ResetPasswordToken;
import ua.edu.sumdu.employees.model.user.User;

import java.io.IOException;

public interface ResetPasswordTokenService {
    ResetPasswordToken createResetPasswordTokenFor(User user)
        throws IOException, UnsupportedOperationException;
    default boolean existsFor(User user) {
        return getFor(user) != null;
    }
    ResetPasswordToken getFor(User user);
}
