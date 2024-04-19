package org.jj.seminar1;

import org.jj.seminar1.Homework.*;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

public class Sem1 {
    static final int NUMBER_DEPARTMENT = 10;
    static final int NUMBER_PERSON = 100;
    static final int MAX_SALARY = 100_000;
    static final int MIN_SALARY = 20_000;
    static final int MIN_AGE = 20;
    static final int MAX_AGE = 65;
    public static void main(String[] args) {

        List<Department> departments = new ArrayList<>();
        for (int i = 1; i <= NUMBER_DEPARTMENT; i++) {
            departments.add(new Department("Department #" + i));
        }

        List<Person> persons = new ArrayList<>();
        for (int i = 1; i <= NUMBER_PERSON; i++) {
            int randomDepartmentIndex = ThreadLocalRandom.current().nextInt(departments.size());
            Homework.Department department = departments.get(randomDepartmentIndex);

            Person person = new Person();
            person.setName("Person #" + i);
            person.setAge(ThreadLocalRandom.current().nextInt(MIN_AGE, MAX_AGE));
            person.setSalary(ThreadLocalRandom.current().nextInt(MIN_SALARY, MAX_SALARY) * 1.0);
            person.setDepartment(department);

            persons.add(person);
        }

    }
}
