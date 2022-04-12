package com.rps.employee;

import com.rps.employee.model.Employee;

import java.util.Arrays;
import java.util.Comparator;
import java.util.List;

public class EmployeeChainedComparator implements Comparator<Employee> {

    private final List<Comparator<Employee>> listComparators;

    @SafeVarargs
    public EmployeeChainedComparator(Comparator<Employee>... comparators) {
        this.listComparators = Arrays.asList(comparators);
    }

    @Override
    public int compare(Employee employee1, Employee employee2) {
        for (Comparator<Employee> comparator : listComparators) {
            int result = comparator.compare(employee1, employee2);
            if (result != 0) {
                return result;
            }
        }
        return 0;
    }
}
