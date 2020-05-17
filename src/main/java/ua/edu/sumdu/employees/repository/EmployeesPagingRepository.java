package ua.edu.sumdu.employees.repository;

import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import ua.edu.sumdu.employees.model.Employee;

import java.math.BigInteger;

@Repository("EmployeesPagingRepository")
public interface EmployeesPagingRepository extends PagingAndSortingRepository<Employee, BigInteger> {
}
