package ua.edu.sumdu.employees.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import ua.edu.sumdu.employees.model.User;

@Repository(value = "UserDetailsService")
@Transactional
public interface UserDetailsRepository
        extends org.springframework.security.core.userdetails.UserDetailsService, CrudRepository<User, String> {
    @Override
    default UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return findById(username).orElseThrow(() -> new UsernameNotFoundException("User " + username + " not found"));
    }
}
