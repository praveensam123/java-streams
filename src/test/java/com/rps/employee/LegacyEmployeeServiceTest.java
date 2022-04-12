package com.rps.employee;

import com.rps.employee.model.Employee;
import com.rps.employee.model.Gender;
import com.rps.employee.model.Name;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Map;

import static com.rps.employee.model.Gender.FEMALE;
import static com.rps.employee.model.Gender.MALE;
import static java.util.stream.Collectors.toList;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;

public class LegacyEmployeeServiceTest {

    private LegacyEmployeeService service;

    @BeforeEach
    public void setUp() {
        service = new LegacyEmployeeService();
    }

    @Test
    public void total_strength_of_employees() {
        List<Employee> employees = service.getEmployees();
        assertThat(employees.size(), equalTo(11));
    }

    @Test
    public void total_strength_of_subsidiary_employees() {
        List<Employee> employees = service.getSubsidiaryEmployees();
        assertThat(employees.size(), equalTo(4));
    }

    @Test
    public void entire_organisation_employees() {
        List<List<Employee>> employees = service.getAllEmployees();
        assertThat(employees.size(), equalTo(2));
    }

    @Test
    public void find_the_first_employee() {
        Employee employee = service.firstEmployee();
        assertThat(employee.toString(), equalTo("Bob Martin R"));
    }

    @Test
    public void find_the_first_three_employee_names() {
        List<Name> employees = service.firstThreeEmployeeNames();
        assertThat(employees.size(), equalTo(3));
        assertThat(employees.get(0).toString(), equalTo("Bob Martin R"));
        assertThat(employees.get(1).toString(), equalTo("James Matthew S"));
        assertThat(employees.get(2).toString(), equalTo("Sharmila  D"));
    }

    @Test
    public void count_female_employees() {
        long count = service.getFemaleEmployeesCount();
        assertThat(count, equalTo(5L));
    }

    @Test
    public void list_leadership_team() {
        List<Employee> leaders = service.getLeaders();
        assertThat(leaders.size(), equalTo(3));
    }

    @Test
    public void list_employees_who_earn_above_one_lakh() {
        List<Employee> employees = service.getEmployeesEarnAbove(1_00_000);
        assertThat(employees.size(), equalTo(4));
    }

    @Test
    public void list_SG_employees_who_earn_above_one_lakh() {
        List<Employee> employees = service.getSalaryGradeEmployeesEarnAbove("SG", 100000);
        assertThat(employees.size(), equalTo(1));
        assertThat(employees.stream().findFirst().orElse(new Employee()).getFirstName(), equalTo("Bob"));
    }

    @Test
    public void list_female_employees_of_SG_grade_who_earn_less_than_one_lakh() {
        List<Name> employees = service.getEmployeesEarnBelow(1_00_000, "SG", FEMALE);
        assertThat(employees.size(), equalTo(4));

        List<String> employeeFullNames = employees.stream()
                .map(Name::toString)
                .collect(toList());
        assertThat(employeeFullNames, hasItems("Sharmila  D", "Christine Sophia G", "Anitha Meenakshi D", "Anitha  S"));
    }

    @Test
    public void list_entire_organization_employees_who_earn_above_one_lakh() {
        int salary = 1_00_000;
        List<Employee> employees = service.getOrganizationEmployeesEarnAbove(salary);
        assertThat(employees.size(), equalTo(5));
    }

    @Test
    public void list_distinct_firstNames() {
        List<String> names = service.getDistinctFirstNames();
        assertThat(names.size(), equalTo(9));
        assertThat(names, containsInAnyOrder("Bob", "James", "Sharmila",
                "Christine", "Martin", "Sofia", "Mustafa", "Ganesh", "Anitha"));
    }

    @Test
    public void list_distinct_firstNames_sorted() {
        List<String> names = service.getDistinctFirstNamesSorted();
        assertThat(names.size(), equalTo(9));
        assertThat(names, containsInRelativeOrder("Anitha", "Bob", "Christine",
                "Ganesh", "James", "Martin", "Mustafa", "Sharmila", "Sofia"));
    }

    @Test
    public void list_employees_sorted_by_date_of_joining() {
        List<Employee> employees = service.getEmployeesSortedByExperience();
        List<String> employeeFullNames = employees.stream()
                .map(Employee::toString)
                .collect(toList());
        assertThat(employeeFullNames, containsInRelativeOrder("Bob Martin D", "Sofia Blessy B",
                "Martin Luther A", "Bob Martin R", "James Matthew S", "Ganesh Moorthy S", "Anitha Meenakshi D",
                "Christine Sophia G", "Mustafa Mohammed H", "Sharmila  D", "Anitha  S"));
    }

    @Test
    public void list_SG_grade_employees_sorted_by_grade_and_salary() {
        List<Employee> employees = service.getSGGradeEmployeesSortedByGradeAndSalary();
        List<String> employeeFullNames = employees.stream()
                .map(Employee::toString)
                .collect(toList());
        assertThat(employees.size(), equalTo(8));
        assertThat(employeeFullNames, containsInRelativeOrder("Bob Martin R", "Ganesh Moorthy S",
                "James Matthew S", "Christine Sophia G", "Anitha Meenakshi D",
                "Sharmila  D", "Anitha  S", "Mustafa Mohammed H"));
    }

    @Test
    public void find_most_experienced_employee() {
        Name seniorEmployee = service.getMostExperiencedEmployee();
        assertThat(seniorEmployee.toString(), equalTo("Bob Martin D"));
    }

    @Test
    public void find_least_experienced_employee() {
        Name juniorEmployee = service.getLeastExperiencedEmployee();
        assertThat(juniorEmployee.toString(), equalTo("Anitha  S"));
    }

    @Test
    public void list_employees_groupBy_gender() {
        Map<Gender, List<Employee>> employeesByGender = service.employeesGroupingByGender();
        assertThat(employeesByGender.size(), equalTo(2));
        assertThat(employeesByGender.get(MALE).size(), equalTo(6));
        assertThat(employeesByGender.get(FEMALE).size(), equalTo(5));
    }

    @Test
    public void total_salary_of_employees() {
        int totalSalary = service.getTotalSalaryOfEmployees();
        assertThat(totalSalary, equalTo(13_00_000));
    }

}
