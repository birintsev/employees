package ua.edu.sumdu.employees.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ua.edu.sumdu.employees.model.Employee;

import java.math.BigInteger;

@Repository("EmployeesJpaRepository")
public interface EmployeesJpaRepository extends JpaRepository<Employee, BigInteger> {
}
