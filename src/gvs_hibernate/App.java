package gvs_hibernate;

import java.util.List;
import org.hibernate.Session;
import org.hibernate.criterion.Restrictions;
import pojos.PhonebookRecord;

public class App {

    public static void main(String[] args) {
        Session session = connection.Controller.getSessionFactory().openSession();

        // Выводим все строки в БД
        session.beginTransaction();
        System.out.println("Изначальное состояние БД:");
        printAll((List<PhonebookRecord>) session.createCriteria(PhonebookRecord.class).list());
        session.getTransaction().commit();

        // Удаляем запись по фамилии
        session.beginTransaction();
        System.out.println("Удаляем запись с фамилией 'Федоров'...");
        PhonebookRecord r1 = (PhonebookRecord) session.createCriteria(PhonebookRecord.class)
                .add(Restrictions.eq("lastName", "Федоров"))
                .uniqueResult();
        session.delete(r1);
        session.getTransaction().commit();

        // Создание новой записи в БД
        session.beginTransaction();
        System.out.println("Добавление записи...");
        session.save(new PhonebookRecord("Игорь", "Федор", "77052457654"));
        session.getTransaction().commit();

        // Выводим все строки в БД
        session.beginTransaction();
        System.out.println("Состояние после:");
        printAll((List<PhonebookRecord>) session.createCriteria(PhonebookRecord.class).list());
        session.getTransaction().commit();

        // Изменение записи
        session.beginTransaction();
        System.out.println("Изменяем фамилию...");
        PhonebookRecord r2 = (PhonebookRecord) session.createCriteria(PhonebookRecord.class)
                .add(Restrictions.eq("lastName", "Федор"))
                .uniqueResult();
        r2.setLastName("Федоров");
        session.update(r2);
        session.getTransaction().commit();

        // Выводим все строки в БД
        session.beginTransaction();
        System.out.println("Состояние после:");
        printAll((List<PhonebookRecord>) session.createCriteria(PhonebookRecord.class).list());
        session.getTransaction().commit();

        session.close();
        System.exit(0);
    }

    private static void printAll(List<PhonebookRecord> records) {
        records.forEach((record) -> {
            System.out.println(record);
        });
        System.out.println("");
    }
}
