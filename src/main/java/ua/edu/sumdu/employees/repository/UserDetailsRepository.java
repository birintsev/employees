package ua.edu.sumdu.employees.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import ua.edu.sumdu.employees.model.User;

@Repository(value = "UserDetailsRepository")
@Transactional
public interface UserDetailsRepository extends JpaRepository<User, String> {
}
