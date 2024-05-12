package org.jj.seminar3;

import java.sql.*;

public class Homework {

  /**
   * Повторить все, что было на семинаре на таблице Student с полями
   * 1. id - bigint
   * 2. first_name - varchar(256)
   * 3. second_name - varchar(256)
   * 4. group - varchar(128)
   *
   * Написать запросы:
   * 1. Создать таблицу
   * 2. Наполнить таблицу данными (insert)
   * 3. Поиск всех студентов
   * 4. Поиск всех студентов по имени группы
   *
   * Доп. задания:
   * 1. ** Создать таблицу group(id, name); в таблице student сделать внешний ключ на group
   * 2. ** Все идентификаторы превратить в UUID
   *
   * Замечание: можно использовать ЛЮБУЮ базу данных: h2, postgres, mysql, ...
   */

  public static final String URLDB = "jdbc:h2:mem:testdb";
  public static final String USER = "root";
  public static final String PASS = "root";

  public static void main(String[] args) {

      try (Connection connection = DriverManager.getConnection(URLDB, USER, PASS)){
          DAO_JDBC.createTable(connection);
          for (int i = 0; i < 4; i++) {
              DAO_JDBC.insertData(connection, "A" + i + "-24");
          }

          DAO_JDBC.insertDataStudent(connection);
          System.out.println("-".repeat(20));
          DAO_JDBC.getDataStudent(connection);
          System.out.println("-".repeat(20));
          DAO_JDBC.getDataStudent(connection, "A1-24");
          System.out.println("-".repeat(20));
          DAO_JDBC.getDataStudent(connection, "A10-24");

      } catch (SQLException e) {
          System.out.println("Ошибка в работе с БД: " + e.getMessage());
      }

  }
}
