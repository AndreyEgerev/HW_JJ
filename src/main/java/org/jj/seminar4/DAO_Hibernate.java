package org.jj.seminar4;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;
import org.hibernate.query.Query;

import java.util.List;

public class DAO_Hibernate {
    private Configuration configuration;
    private SessionFactory sessionFactory;

    public DAO_Hibernate() {
        configuration = new Configuration().configure();
        sessionFactory = configuration.buildSessionFactory();
    }

    public void truncateTable (Class<?> type) {
        try (Session session = sessionFactory.openSession()){
            session.beginTransaction();
            //String sqlRequest = "TRUNCATE TABLE " + type.getSimpleName() + ";";
            String sql = "DELETE FROM " + type.getSimpleName() + " u";
            session.createQuery(sql).executeUpdate();
            //session.createNativeQuery(sqlRequest).executeUpdate();
            session.getTransaction().commit();
        }
    }

    public void deleteData (Object data) {
            try (Session session = sessionFactory.openSession()){
                Transaction tx = session.beginTransaction();
                session.remove(data);
                tx.commit();
            }
    }
    public void updateData (Object data) {
        try (Session session = sessionFactory.openSession()){
            session.beginTransaction();
            session.merge(data);
            session.getTransaction().commit();
        }
    }
    public void insertData(Object dataObj) {
        try (Session session = sessionFactory.openSession()) {
            Transaction transaction = session.beginTransaction();
            session.persist(dataObj);
            transaction.commit();
        }
    }

    public void insertData(List<?> dataList) {
        try (Session session = sessionFactory.openSession()) {
            for (Object data:
                 dataList) {
                Transaction transaction = session.beginTransaction();
                session.persist(data);
                transaction.commit();
            }
//            Transaction transaction = session.beginTransaction();
//            for (Object data:
//                 dataList) {
//                session.persist(data);
//            }
//            transaction.commit();
        }
    }
    public <T> T findByID(Class<T> type, Long id) {
        try (Session session = sessionFactory.openSession()){
            return session.find(type, id);
        }
    }

    public <T> T findByID(T type, Long id) {
        try (Session session = sessionFactory.openSession()){
            type =  session.find((Class<T>) type.getClass(), id);
        }
        return type;
    }

    public Long countAllRecords(Class<?> type) {
        Long count;
        try (Session session = sessionFactory.openSession()){
            count = session
                    .createQuery("select count(u) from " + type.getSimpleName() + " u", Long.class)
                    //.setParameter(1, type.getSimpleName())// запрос не работает с установкой параметров
                    .uniqueResult();
        }
        return count;
    }
    public List selectStudentByIdGroup(Class<?> type, String request, String requestData) {
        List<?> data;
        try (Session session = sessionFactory.openSession()){
            Query<?> query = session.createQuery(
                    "SELECT s FROM "+ type.getSimpleName() + " s JOIN s.groupId g WHERE g." + request + " = ?1"
                            , type)
                    .setParameter(1, requestData);
            data = query.getResultList();
        }
        return data;
    }

    public List selectAllData (Class<?> type) {
        List rezult;
        try (Session session = sessionFactory.openSession()) {
            rezult = session.createQuery("SELECT u FROM " + type.getSimpleName() + " u",
                    type).getResultList();
        }
        return rezult;
    }


}
