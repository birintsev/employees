package ua.edu.sumdu.employees.service;

import lombok.AllArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import ua.edu.sumdu.employees.model.User;
import ua.edu.sumdu.employees.repository.UserDetailsRepository;

import java.io.IOException;

@Service
@AllArgsConstructor
public class UserDetailsServiceImpl implements UserDetailsService {
    private UserDetailsRepository userDetailsRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return userDetailsRepository.findById(username).orElseThrow(
                () -> new UsernameNotFoundException("User " + username + " not found"));
    }

    @Override
    public User registerNewUser(User user) throws IOException {
        if (userDetailsRepository.findById(user.getUsername()).isPresent()) {
            throw new IOException("A user with username " + user.getUsername() + " is already registered");
        }
        return userDetailsRepository.saveAndFlush(user);
    }
}
