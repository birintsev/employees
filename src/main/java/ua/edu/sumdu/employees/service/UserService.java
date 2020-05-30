package ua.edu.sumdu.employees.service;

import org.springframework.stereotype.Service;
import ua.edu.sumdu.employees.model.user.ResetPasswordToken;
import ua.edu.sumdu.employees.model.user.User;

import java.io.IOException;

@Service
public interface UserService extends org.springframework.security.core.userdetails.UserDetailsService {
    User registerNewUser(User user) throws IOException;
    User findByEmail(String email) throws IOException;
    User resetPassword(User user, String newPassword) throws IOException;
}
