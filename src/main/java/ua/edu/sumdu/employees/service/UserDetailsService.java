package ua.edu.sumdu.employees.service;

import org.springframework.stereotype.Service;
import ua.edu.sumdu.employees.model.User;

import java.io.IOException;

@Service
public interface UserDetailsService extends org.springframework.security.core.userdetails.UserDetailsService {
    User registerNewUser(User user) throws IOException;

}
