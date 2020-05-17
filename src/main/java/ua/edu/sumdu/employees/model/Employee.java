package ua.edu.sumdu.employees.model;

import lombok.Getter;
import lombok.Setter;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.ToString;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.*;
import java.math.BigInteger;
import java.sql.Date;

@Entity
@Table(name = "EMP")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
@ToString
public class Employee {
    @Id
    private BigInteger empno;
    private String ename;
    private String job;
    @JoinColumn(name = "MGR", referencedColumnName = "EMPNO")
    @OneToOne
    private Employee mgr;
    private Date hiredate;
    @Column(columnDefinition = "NUMBER(10,2)")
    private Float sal;
    @Column(columnDefinition = "NUMBER(10,2)")
    private Float comm;
    @JoinColumn(name = "DEPTNO", referencedColumnName = "DEPTNO")
    @ManyToOne
    private Department dept;
}
