package org.jj.seminar3;

import java.sql.*;
import java.util.*;
import java.util.concurrent.ThreadLocalRandom;

public class DAO_JDBC {
    private static final String DROP = "DROP TABLE IF EXISTS %s";
    private static final String CREATE_GROUP = """
          CREATE TABLE Groups(
          id UUID DEFAULT random_uuid(),
          name VARCHAR(128) PRIMARY KEY
          )
          """;
    private static final String CREATE_STUDENT = """
          CREATE TABLE Student(
          id UUID DEFAULT random_uuid() PRIMARY KEY,
          first_name VARCHAR(256),
          second_name varchar(256),
          group_id varchar(128),
          FOREIGN KEY (group_id) REFERENCES `Groups` (`name`)
          )
          """;
    private static final String INSERT_DATA_GROUP = """
          INSERT INTO Groups(name)
          VALUES
          (?)
          """;

    private static final String INSERT_DATA_STUDENT = """
          INSERT INTO Student(id, first_name, second_name, group_id)
          VALUES
          (?1, ?2, ?3, ?4)
          """;
    public static void createTable(Connection connection) throws SQLException {
        try (Statement statement = connection.createStatement()){
            statement.execute(CREATE_GROUP);
        } catch (SQLException e) {
            System.err.println("Ошибка при создании БД groups: " + e.getMessage());
            throw e;
        }
        try (Statement statement = connection.createStatement()){
            statement.execute(CREATE_STUDENT);
        } catch (SQLException e) {
            System.err.println("Ошибка при создании БД student: " + e.getMessage());
            throw e;
        }
    }

    public static void insertData(Connection connection, String name) throws SQLException {
        try (PreparedStatement preparedStatement = connection.
                prepareStatement(INSERT_DATA_GROUP)) {
            preparedStatement.setString(1, name);
            preparedStatement.execute();
        } catch (SQLException e) {
            System.err.println("Ошибка при добавлении данных в БД groups: "
                    + e.getMessage());
            throw e;
        }
    }

    public static void insertDataStudent(Connection connection) throws SQLException {
        for (int i = 0; i < 10; i++) {
            try (PreparedStatement preparedStatement = connection.prepareStatement(INSERT_DATA_STUDENT)) {
                preparedStatement.setObject(1, UUID.randomUUID());
                preparedStatement.setString(2, FIRST_NAME.getRandomName());
                preparedStatement.setString(3, SECOND_NAME.getRandomName());
                preparedStatement.setString(4, getRandomGroupFromDB(connection).getName());
                preparedStatement.execute();
            } catch (SQLException e) {
                System.err.println("Ошибка при добавлении данных в БД student: "
                        + e.getMessage());
                throw e;
            }
        }
    }

    public static Group getRandomGroupFromDB(Connection connection) throws SQLException {
        List<Group> groups = new ArrayList<>();
        try (Statement statement = connection.createStatement()) {
            ResultSet resultSet = statement.executeQuery("""
                    SELECT
                    id, name
                    FROM Groups""");
            while (resultSet.next()) {
                groups.add(new Group(resultSet.getObject("id", UUID.class),
                        resultSet.getString("name")));
            }
        } catch (SQLException e) {
            System.err.println("Ошибка при чтении данных из БД groups: "
                    + e.getMessage());
            throw e;
        }
        return groups.get(ThreadLocalRandom.current().nextInt(groups.size()));
    }

    public static void getDataStudent(Connection connection) throws SQLException {
        try (Statement statement = connection.createStatement()){
            ResultSet resultSet = statement.executeQuery("""
                    SELECT
                    id, first_name, second_name, group_id
                    FROM Student""");
            while (resultSet.next()) {
                UUID id = resultSet.getObject("id", UUID.class);
                String firstName = resultSet.getString("first_name");
                String secondName = resultSet.getString("second_name");
                String groupName = resultSet.getString("group_id");
                System.out.println("ID " + id);
                System.out.println(firstName + " " + secondName + " группа " + groupName);
                if (resultSet.isLast()) {
                    System.out.println("\nПрочитано строк - " + resultSet.getRow());
                    System.out.println();
                }
            }
        } catch (SQLException e) {
            System.err.println("Ошибка при чтении данных из БД student: "
                    + e.getMessage());
            throw e;
        }
    }

    public static void getDataStudent(Connection connection, String nameGroup) throws SQLException {
        try (PreparedStatement pS = connection.prepareStatement("""
                SELECT
                COUNT(*)
                FROM student
                WHERE group_id = ?""")){
            pS.setString(1, nameGroup);
            ResultSet count = pS.executeQuery();
            if (count.next()) {
                System.out.println("Найдено записей - " + count.getInt(1));
            } else {
                System.out.println("В базе данных нет записей, удовлетворяющих условию");
            }
        } catch (SQLException e) {
            System.err.println("Ошибка при чтении данных из БД student: "
                    + e.getMessage());
            throw e;
        }
        try (PreparedStatement pS = connection.prepareStatement("""
                SELECT
                id, first_name, second_name, group_id
                FROM student
                WHERE group_id = ?""")){
            pS.setString(1,nameGroup);
            ResultSet resultSet = pS.executeQuery();
            while (resultSet.next()) {
                UUID id = resultSet.getObject("id", UUID.class);
                String firstName = resultSet.getString("first_name");
                String secondName = resultSet.getString("second_name");
                String groupName = resultSet.getString("group_id");
                System.out.println("ID " + id);
                System.out.println(firstName + " " + secondName + " группа " + groupName);

            }
        } catch (SQLException e) {
            System.err.println("Ошибка при чтении данных из БД student: "
                    + e.getMessage());
            throw e;
        }
    }
}
