import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.boot.Metadata;
import org.hibernate.boot.MetadataSources;
import org.hibernate.boot.registry.StandardServiceRegistry;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.cfg.Configuration;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import java.util.List;

public class Main {
    public static void main(String[] args) {
        StandardServiceRegistry registry = new StandardServiceRegistryBuilder().configure("hibernate.cfg.xml").build();
        Metadata metadata = new MetadataSources(registry).getMetadataBuilder().build();
        SessionFactory sessionFactory = metadata.getSessionFactoryBuilder().build();
        //или так
//      SessionFactory sessionFactory = new Configuration().configure("hibernate.cfg.xml").buildSessionFactory();

        Session session = sessionFactory.openSession();
        Transaction transaction = session.beginTransaction(); //открываем транзакцию

        CriteriaBuilder builder = session.getCriteriaBuilder(); //строит объекты запросов

        CriteriaQuery<PurchaseList> query = builder.createQuery(PurchaseList.class); //возвращает запрос, указывает тип данных
        Root<PurchaseList> root = query.from(PurchaseList.class); //корневой объект, от которого производится обход дерева
        query.select(root);
        List<PurchaseList> purchaselist = session.createQuery(query).getResultList();

        for (PurchaseList p : purchaselist) {
            LinkedPurchaseList linkedPurchaseList = new LinkedPurchaseList();

            int studentId = p.getStudent().getId();
            int courseId = p.getCourse().getId();

            linkedPurchaseList.setId(new Key());
            linkedPurchaseList.setStudentId(studentId);
            linkedPurchaseList.setCourseId(courseId);
            session.saveOrUpdate(linkedPurchaseList);
        }

        transaction.commit();
        session.close();
        sessionFactory.close();
    }
}
