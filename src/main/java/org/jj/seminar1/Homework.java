package org.jj.seminar1;

import java.util.*;
import java.util.stream.Collectors;

public class Homework {

  /**
   * Используя классы Person и Department, реализовать методы ниже:
   */

  public static class Person {
    private String name;
    private int age;
    private double salary;
    private Department department;

    public String getName() {
      return name;
    }

    public void setName(String name) {
      this.name = name;
    }

    public int getAge() {
      return age;
    }

    public void setAge(int age) {
      this.age = age;
    }

    public double getSalary() {
      return salary;
    }

    public void setSalary(double salary) {
      this.salary = salary;
    }

    public Department getDepartment() {
      return department;
    }

    public void setDepartment(Department department) {
      this.department = department;
    }

    @Override
    public String toString() {
      return "Person{" +
        "name='" + name + '\'' +
        ", age=" + age +
        ", salary=" + salary +
        ", department=" + department +
        '}';
    }
  }

  public static class Department {
    private String name;

    public Department(String name) {
      this.name = name;
    }

    public String getName() {
      return name;
    }

    @Override
    public String toString() {
      return "Department{" +
        "name='" + name + '\'' +
        '}';
    }

    @Override
    public boolean equals(Object o) {
      if (this == o) return true;
      if (o == null || getClass() != o.getClass()) return false;
      Department that = (Department) o;
      return Objects.equals(name, that.name);
    }

    @Override
    public int hashCode() {
      return Objects.hash(name);
    }
    /**
     * Найти количество сотрудников, старше x лет с зарплатой больше, чем d
     */
    public static int countPersons(List<Person> persons, int x, double d) {
      return (int) persons.stream()
              .filter(person -> person.age > x )
              .filter(person -> person.salary > d)
              .count();
    }
    public static int countPersons2(List<Person> persons, int x, double d) {
      int count = 0;
      for (Person person:
              persons) {
        if (person.getAge() > x && person.getSalary() > d){
          count++;
        }
      }
      return count;
    }

    /**
     * Найти среднюю зарплату сотрудников, которые работают в департаменте X
     */
    public static OptionalDouble averageSalary(List<Person> persons, int x) {
      return persons.stream()
              .filter(person -> person.getDepartment().getName().contains(Integer.toString(x)))
              .mapToDouble(Person::getSalary)
              .average();
    }

    public static double averageSalary2(List<Person> persons, int x) {
      double avgSalary = 0.0;
      int count = 0;
      for (Person p:
              persons) {
        if (p.getDepartment().getName().contains(Integer.toString(x))){
          avgSalary += p.getSalary();
          count++;
        }
      }
      if (count == 0) return 0d;
      return avgSalary /= count;
    }

    /**
     * Сгруппировать сотрудников по департаментам
     */
    public static Map<Department, List<Person>> groupByDepartment(List<Person> persons) {
      return persons.stream()
              .collect(Collectors.groupingBy(Person::getDepartment));
    }

    /**
     * Найти максимальные зарплаты по отделам
     */
    public static Map<Department, Double> maxSalaryByDepartment(List<Person> persons) {
      return persons.stream()
              .collect(Collectors.toMap(Person::getDepartment, Person::getSalary, Math::max));
    }

    /**
     * ** Сгруппировать имена сотрудников по департаментам
     */
    public static Map<Department, List<String>> groupPersonNamesByDepartment(List<Person> persons) {
      return persons.stream()
              .collect(Collectors.groupingBy(Person::getDepartment,
                      Collectors.mapping(Person::getName,Collectors.toList())));
    }

    /**
     * ** Найти сотрудников с минимальными зарплатами в своем отделе
     */
    public static List<Person> minSalaryPersons(List<Person> persons) {
      // В каждом департаменте ищем сотрудника с минимальной зарплатой.
      // Всех таких сотрудников собираем в список и возвращаем из метода.
      return persons.stream()
              .collect(Collectors.groupingBy(Person::getDepartment,
                      Collectors.minBy(Comparator.comparing(Person::getSalary))))
              .values()
              .stream()
              .map(Optional::get)
              .collect(Collectors.toList());
    }

  }

}
