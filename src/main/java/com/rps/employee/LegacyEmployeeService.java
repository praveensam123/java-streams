package com.rps.employee;

import com.rps.employee.model.Employee;
import com.rps.employee.model.Gender;
import com.rps.employee.model.Name;

import java.util.*;

public class LegacyEmployeeService {

    public static final String NO_EMPLOYEE_EXIST = "No employee exist!";

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
        List<Employee> employees = getEmployees();
        if(employees.isEmpty()) {
            throw new RuntimeException(NO_EMPLOYEE_EXIST);
        }
        return employees.get(0);
    }

    public List<Name> firstThreeEmployeeNames() {
        List<Employee> employees = getEmployees();
        List<Employee> firstThreeEmployees = employees.subList(0, 3);
        List<Name> firstThreeEmployeeNames = new ArrayList<>(firstThreeEmployees.size());
        for(Employee employee : firstThreeEmployees) {
            Name employeeName = new Name(employee.getFirstName(), employee.getLastName(), employee.getInitial());
            firstThreeEmployeeNames.add(employeeName);
        }
        return firstThreeEmployeeNames;
    }

    public long getFemaleEmployeesCount() {
        List<Employee> employees = getEmployees();
        int femaleEmployeesCount = 0;
        for(Employee employee : employees) {
            if(Gender.FEMALE == employee.getGender()) {
                femaleEmployeesCount++;
            }
        }
        return femaleEmployeesCount;
    }

    public List<Employee> getLeaders() {
        List<Employee> employees = getEmployees();
        List<Employee> leaders = new ArrayList<>();
        for(Employee employee : employees) {
            if(employee.getGrade().startsWith("LL")) {
                leaders.add(employee);
            }
        }
        return leaders;
    }

    public List<Employee> getEmployeesEarnAbove(int salary) {
        List<Employee> employees = getEmployees();
        List<Employee> employeesEarnAbove = new ArrayList<>();
        for(Employee employee : employees) {
            if(employee.getSalary() > salary) {
                employeesEarnAbove.add(employee);
            }
        }
        return employeesEarnAbove;
    }

    public List<Employee> getSalaryGradeEmployeesEarnAbove(String gradePrefix, int salary) {
        List<Employee> employees = getEmployees();
        List<Employee> salaryGradeEmployeesEarnAbove = new ArrayList<>();
        for(Employee employee : employees) {
            if(employee.getSalary() > salary
                && employee.getGrade().startsWith(gradePrefix)) {
                    salaryGradeEmployeesEarnAbove.add(employee);
            }
        }
        return salaryGradeEmployeesEarnAbove;
    }

    public List<Name> getEmployeesEarnBelow(int salary, String gradePrefix, Gender gender) {
        List<Employee> employees = getEmployees();
        List<Name> employeesEarnBelow = new ArrayList<>();
        for(Employee employee : employees) {
            if(employee.getSalary() < salary
                    && employee.getGrade().startsWith(gradePrefix)
                    && gender == employee.getGender()) {
                Name employeeName = new Name(employee.getFirstName(), employee.getLastName(), employee.getInitial());
                employeesEarnBelow.add(employeeName);
            }
        }
        return employeesEarnBelow;
    }

    public List<Employee> getOrganizationEmployeesEarnAbove(int salary) {
        List<Employee> employees = getEmployees();
        List<Employee> subsidiaryEmployees = getSubsidiaryEmployees();

        List<List<Employee>> organizationEmployees = new ArrayList<>();
        organizationEmployees.add(employees);
        organizationEmployees.add(subsidiaryEmployees);

        List<Employee> organizationEmployeesEarnAbove = new ArrayList<>();
        for(List<Employee> subOrganizationEmployees : organizationEmployees) {
            for(Employee employee : subOrganizationEmployees) {
                if (employee.getSalary() > salary) {
                    organizationEmployeesEarnAbove.add(employee);
                }
            }
        }
        return organizationEmployeesEarnAbove;
    }

    public List<String> getDistinctFirstNames() {
        List<Employee> employees = getEmployees();
        List<String> distinctFirstNames = new ArrayList<>();
        for(Employee employee : employees) {
            String firstName = employee.getFirstName();
            if(!distinctFirstNames.contains(firstName)) {
                distinctFirstNames.add(firstName);
            }
        }
        return distinctFirstNames;
    }

    public List<String> getDistinctFirstNamesSorted() {
        List<Employee> employees = getEmployees();

        List<String> distinctFirstNames = new ArrayList<>();
        for(Employee employee : employees) {
            String firstName = employee.getFirstName();
            if(!distinctFirstNames.contains(firstName)) {
                distinctFirstNames.add(firstName);
            }
        }

        distinctFirstNames.sort(new Comparator<String>() {
            @Override
            public int compare(String name1, String name2) {
                return name1.compareTo(name2);
            }
        });

        return distinctFirstNames;
    }

    public List<Employee> getEmployeesSortedByExperience() {
        List<Employee> employees = getEmployees();

        employees.sort(new Comparator<Employee>() {
            @Override
            public int compare(Employee employee1, Employee employee2) {
                return employee1.getDateOfJoining().compareTo(
                        employee2.getDateOfJoining());
            }
        });

        return employees;
    }

    public List<Employee> getSGGradeEmployeesSortedByGradeAndSalary() {
        List<Employee> employees = getEmployees();

        List<Employee> sgGradeEmployees = new ArrayList<>();
        for(Employee employee : employees) {
            if(employee.getGrade().startsWith("SG")) {
                sgGradeEmployees.add(employee);
            }
        }

        sgGradeEmployees.sort(new EmployeeChainedComparator(
                new Comparator<Employee>() {
                    @Override
                    public int compare(Employee employee1, Employee employee2) {
                        return employee2.getGrade().compareTo(employee1.getGrade());
                    }
                }, new Comparator<Employee>() {
                    @Override
                    public int compare(Employee employee1, Employee employee2) {
                        return employee2.getSalary() - employee1.getSalary();
                    }
                }
        ));
        return sgGradeEmployees;
    }

    public Name getMostExperiencedEmployee() {
        List<Employee> employees = getEmployees();

        employees.sort(new Comparator<Employee>() {
            @Override
            public int compare(Employee employee1, Employee employee2) {
                return employee1.getDateOfJoining().compareTo(
                        employee2.getDateOfJoining());
            }
        });

        if(employees.isEmpty()) {
            throw new RuntimeException("Recruit at least a CEO");
        }

        Employee firstEmployee = employees.get(0);
        Name firstEmployeeName = new Name(firstEmployee().getFirstName(), firstEmployee().getLastName(), firstEmployee.getInitial());
        return firstEmployeeName;
    }

    public Name getLeastExperiencedEmployee() {
        List<Employee> employees = getEmployees();

        employees.sort(new Comparator<Employee>() {
            @Override
            public int compare(Employee employee1, Employee employee2) {
                return employee2.getDateOfJoining().compareTo(
                        employee1.getDateOfJoining());
            }
        });

        if(employees.isEmpty()) {
            throw new RuntimeException("Recruit at least a CEO");
        }

        Employee firstEmployee = employees.get(0);
        Name firstEmployeeName = new Name(firstEmployee.getFirstName(), firstEmployee.getLastName(), firstEmployee.getInitial());
        return firstEmployeeName;
    }

    public Map<Gender, List<Employee>> employeesGroupingByGender() {
        List<Employee> employees = getEmployees();
        Map<Gender, List<Employee>> employeesByGender = new HashMap<>();
        for(Employee employee : employees) {
            Gender gender = employee.getGender();
            if(employeesByGender.containsKey(gender)) {
                employeesByGender.get(gender).add(employee);
            } else {
                List<Employee> newEmployees = new ArrayList<>();
                newEmployees.add(employee);
                employeesByGender.put(gender, newEmployees);
            }
        }
        return employeesByGender;
    }

    public int getTotalSalaryOfEmployees() {
        List<Employee> employees = getEmployees();
        int totalSalary = 0;
        for(Employee employee : employees) {
            totalSalary += employee.getSalary();
        }
        return totalSalary;
    }

}
