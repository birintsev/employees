package ua.edu.sumdu.employees.repository;

import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.transaction.annotation.Transactional;
import ua.edu.sumdu.employees.model.Employee;

import java.math.BigInteger;

@Transactional
public interface EmployeeRepository extends PagingAndSortingRepository<Employee, BigInteger> {

}
