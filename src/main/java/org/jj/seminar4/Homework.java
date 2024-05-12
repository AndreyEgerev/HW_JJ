package org.jj.seminar4;

import org.jj.seminar3.FIRST_NAME;
import org.jj.seminar3.SECOND_NAME;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;


/**
Перенести структуру дз третьего урока на JPA:
1. Описать сущности Student и Group
2. Написать запросы: Find, Persist, Remove
3. ... поупражняться с разными запросами ...
 */
public class Homework {
    public static final String URLDB = "jdbc:h2:mem:testdb";
    public static final String USERMYSQL = "test1";
    public static final String PASSMYSQL = "test1User1#123";
    public static void main(String[] args) throws SQLException {
//        try (Connection connection =
//                     DriverManager.getConnection(URLDB, USERMYSQL, PASSMYSQL)) {
            DAO_Hibernate dao = new DAO_Hibernate();
            GroupStudent group = new GroupStudent();
            Student student = new Student();
            List<GroupStudent> groups = new ArrayList<>();
            List<Student> students = new ArrayList<>();
            System.out.println("Очистка таблиц");
            //dao.truncateTable(Student.class);
            //dao.truncateTable(GroupStudent.class);
            System.out.println("Добавление данных о группах");
            //Добавление групп
            for (int i = 1; i <= 3; i++) {
                    groups.add(new GroupStudent("test" + i));
            }
            dao.insertData(groups);
            System.out.println("Записей о группах - " + dao.countAllRecords(GroupStudent.class));
            System.out.println("Список всех групп");
            System.out.println(dao.selectAllData(GroupStudent.class));
            System.out.println("-".repeat(50));
            groups = dao.selectAllData(GroupStudent.class);
//          Добавление студентов
            System.out.println("Добавление данных о студентах");
            for (int i = 0; i < 50; i++) {
                    students.add(new Student(
                        FIRST_NAME.getRandomName(),
                        SECOND_NAME.getRandomName(),
                        groups.get(ThreadLocalRandom.current().nextInt(groups.size()))));
            }
            dao.insertData(students);
            System.out.println("Список всех студентов");
            students = dao.selectAllData(Student.class);
            for (Student s:
                 students) {
                    System.out.println(s);
                    System.out.println("-".repeat(10));
            }

            System.out.println("Работа поиска по ID");
            group = dao.findByID(group.getClass(), 3L);
            System.out.println(group);
            System.out.println("-".repeat(50));
            student = dao.findByID(student, 25L);
            System.out.println(student);


            System.out.println("-".repeat(50));
            System.out.println("Поиск студентов по ID группы");
            students = dao.selectStudentByIdGroup(Student.class, "id","2");
            for (Student s:
                    students) {
                    System.out.println(s);
            }

            System.out.println("-".repeat(10));
            System.out.println("Поиск студентов по имени группы");
            students = dao.selectStudentByIdGroup(Student.class, "name", "test2");
            for (Student s:
                    students) {
                    System.out.println(s);
            }
            System.out.println("Изменение данных студента");
            long idStudent = ThreadLocalRandom.current().nextLong(50);
            student = dao.findByID(Student.class, idStudent);
            System.out.println(student);
            student.setFirstName("Andrey");
            dao.updateData(student);
            student = dao.findByID(student, idStudent);
            System.out.println(student);

            System.out.println("-".repeat(50));
            System.out.println("Изменение данных группы");
            group = dao.findByID(GroupStudent.class, 2L);
            System.out.println(group);
            group.setName("testoGroup");
            dao.updateData(group);
            group = dao.findByID(group, 2L);
            System.out.println(group);

            System.out.println("Удаление данных студента");
            idStudent = dao.countAllRecords(Student.class);
            System.out.println(idStudent);
            student = dao.findByID(Student.class,idStudent);
            dao.deleteData(student);
            System.out.println(dao.findByID(Student.class, idStudent));

            System.out.println("-=".repeat(50));
            System.out.println("Удаление данных группы");
            group = dao.findByID(group, dao.countAllRecords(GroupStudent.class));
            dao.deleteData(group);

            System.out.println(dao.selectAllData(GroupStudent.class));
            System.out.println(dao.selectAllData(Student.class));

            System.out.println("-=-".repeat(50));
            System.out.println("Поиск студентов без группы");
            students = dao.selectStudentByIdGroup(Student.class, "id", null);
            for (Student s:
                 students) {
                    System.out.println(s);
            }
            System.out.println("Очистка таблиц");
            dao.truncateTable(Student.class);
            dao.truncateTable(GroupStudent.class);

//        }



    }

}
