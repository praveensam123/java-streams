package com.rps.employee;

import com.rps.employee.model.Employee;
import com.rps.employee.model.Gender;
import com.rps.employee.model.Name;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import static java.util.Comparator.comparing;
import static java.util.stream.Collectors.toList;
import static org.apache.commons.lang3.StringUtils.isNotBlank;

public class EmployeeService {

    private static final String NO_EMPLOYEE_EXIST = "No employee exist!";
    private final EmployeeRepository employeeRepository = new EmployeeRepository();

    public List<Employee> getEmployees() {
        return employeeRepository.getEmployees();
    }

    public List<Employee> getSubsidiaryEmployees() {
        return employeeRepository.getSubsidiaryEmployees();
    }

    public List<List<Employee>> getAllEmployees() {
        List<List<Employee>> employees = new ArrayList<>();
        employees.add(getEmployees());
        employees.add(getSubsidiaryEmployees());
        return employees;
    }

    public Employee firstEmployee() {
        return getEmployees().stream()
                .findFirst()
                .orElseThrow(() -> new RuntimeException(NO_EMPLOYEE_EXIST));
    }

    public Employee findAnyEmployee() {
        return getEmployees().stream()
                .findAny()
                .orElseThrow(() -> new RuntimeException(NO_EMPLOYEE_EXIST));
    }

    public List<Name> firstThreeEmployeeNames() {
        return getEmployees().stream()
                .limit(3)
                .map(Employee::toName)
                .collect(toList());
    }

    public List<Name> skipFirstEmployeeAndGetFirstThreeEmployeeNames() {
        return getEmployees().stream()
                .limit(4)
                .skip(1)
                .map(Employee::toName)
                .collect(toList());
    }

    public long getFemaleEmployeesCount() {
        return getEmployees().stream()
                .filter(employee -> Gender.FEMALE == employee.getGender())
                .count();
    }

    public List<Employee> getLeaders() {
        return getEmployees().stream()
                .filter(employee -> employee.getGrade().startsWith("LL"))
                .collect(toList());
    }

    public List<Employee> getEmployeesEarnAbove(int salary) {
        return getEmployees().stream()
                .filter(employee -> employee.getSalary() > salary)
                .collect(toList());
    }

    public List<Employee> getSalaryGradeEmployeesEarnAbove(String gradePrefix, int salary) {
        return getEmployees().stream()
                .filter(employee -> employee.getGrade().startsWith(gradePrefix))
                .filter(employee -> employee.getSalary() > salary)
                .collect(toList());
    }

    public List<Name> getEmployeesEarnBelow(int salary, String gradePrefix, Gender gender) {
        return getEmployees().stream()
                .filter(employee -> gender == employee.getGender())
                .filter(employee -> employee.getGrade().startsWith(gradePrefix))
                .filter(employee -> employee.getSalary() < salary)
                .map(Employee::toName)
                .collect(toList());
    }

    public boolean matchEmployeeFirstName(String firstName) {
        return getEmployees().stream()
                .anyMatch(employee -> firstName.equalsIgnoreCase(employee.getFirstName()));
    }

    public boolean doesNotMatchEmployeeLastName(String lastName) {
        return getEmployees().stream()
                .noneMatch(employee -> lastName.equalsIgnoreCase(employee.getLastName()));
    }

    public boolean checkAllEmployeesHaveFirstName() {
        return getEmployees().stream()
                .allMatch(employee -> isNotBlank(employee.getFirstName()));
    }

    public List<Employee> getOrganizationEmployeesEarnAbove(int salary) {
        return getAllEmployees().stream()
                .flatMap(Collection::stream)
                .filter(employee -> employee.getSalary() > salary)
                .collect(toList());
    }

    public List<String> getDistinctFirstNames() {
        return getEmployees().stream()
                .map(Employee::getFirstName)
                .distinct()
                .collect(toList());
    }

    public List<String> getDistinctFirstNamesSorted() {
        return getEmployees().stream()
                .map(Employee::getFirstName)
                .distinct()
                .sorted()
                .collect(toList());
    }

    public List<Employee> getEmployeesSortedByExperience() {
        return getEmployees().stream()
                .sorted(comparing(Employee::getDateOfJoining))
                .collect(toList());
    }

    public List<Employee> getSGGradeEmployeesSortedByGradeAndSalary() {
        return getEmployees().stream()
                .filter(employee -> employee.getGrade().startsWith("SG"))
                .sorted(comparing(Employee::getGrade)
                        .thenComparing(Employee::getSalary).reversed())
                .collect(toList());
    }

    public Name getMostExperiencedEmployee() {
        return getEmployees().stream()
                .min(comparing(Employee::getDateOfJoining))
                .map(Employee::toName)
                .orElseThrow(() -> new RuntimeException("Recruit at least a CEO"));
    }

    public Name getLeastExperiencedEmployee() {
        return getEmployees().stream()
                .max(comparing(Employee::getDateOfJoining))
                .map(Employee::toName)
                .orElseThrow(() -> new RuntimeException("Recruit at least a CEO"));
    }

    public int getTotalSalaryOfEmployees() {
        return getEmployees().stream()
                .map(Employee::getSalary)
                .reduce(0, Integer::sum);
    }

    public Map<Gender, List<Employee>> employeesGroupingByGender() {
        return getEmployees().stream()
                .collect(Collectors.groupingBy(Employee::getGender));
    }
}
