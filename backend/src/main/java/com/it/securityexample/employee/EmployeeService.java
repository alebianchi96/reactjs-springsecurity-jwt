package com.it.securityexample.employee;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class EmployeeService {

    @Autowired
    private EmployeeDao repository;

    public List<EmployeeEntity> findAll() {
        return repository.findAll();
    }

    public Optional<EmployeeEntity> findById(Integer id) {
        return repository.findById(id);
    }

    public EmployeeEntity save(EmployeeEntity employee) {
        return repository.save(employee);
    }

    public void deleteById(Integer id) {
        repository.deleteById(id);
    }

    public Optional<EmployeeEntity> updateEmployee(
            Integer id, EmployeeEntity employeeDetails) {
        return repository.findById(id).map(employee -> {
            employee.setName(employeeDetails.getName());
            employee.setEmail(employeeDetails.getEmail());
            employee.setDepartment(employeeDetails.getDepartment());
            employee.setCompany(employeeDetails.getCompany());
            return Optional.of(repository.save(employee));
        }).orElse(Optional.empty());
    }

}
