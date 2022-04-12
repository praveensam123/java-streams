package com.rps.employee;

import com.rps.employee.model.Employee;

import java.util.Arrays;
import java.util.List;

import static com.rps.util.file.Reader.readJsonFrom;

public class EmployeeRepository {

    public List<Employee> getEmployees() {
        return Arrays.asList(readJsonFrom("Employees.json", Employee[].class));
    }

    public List<Employee> getSubsidiaryEmployees() {
        return Arrays.asList(readJsonFrom("EmployeesInSubsidiary.json", Employee[].class));
    }
}
