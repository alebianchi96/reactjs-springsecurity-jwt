package com.it.securityexample.employee;

import org.springframework.data.jpa.repository.JpaRepository;

public interface EmployeeDao
        extends JpaRepository<EmployeeEntity, Integer> {

}
