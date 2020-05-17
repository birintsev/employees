package ua.edu.sumdu.employees.model;

import lombok.*;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.*;
import java.math.BigInteger;
import java.util.Set;

@Entity
@Table(name = "DEPT")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
@ToString
public class Department {
    @Id
    @Column(columnDefinition = "NUMBER")
    private BigInteger deptno;
    private String dname;
    private String loc;
    @OneToMany(mappedBy = "dept")
    private Set<Employee> employees;
}
