package ua.edu.sumdu.employees.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import ua.edu.sumdu.employees.model.Authority;
import ua.edu.sumdu.employees.model.AuthorityID;

@Transactional
@Repository
public interface AuthorityRepository extends CrudRepository<Authority, AuthorityID> {

}
