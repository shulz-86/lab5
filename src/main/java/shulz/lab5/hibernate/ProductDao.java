package shulz.lab5.hibernate;

import org.hibernate.Session;
import org.hibernate.query.Query;
import org.hibernate.resource.transaction.spi.TransactionStatus;

import java.util.List;

public class ProductDao {

    private Session session = null;

    public Product findById(Long id) {
        session = HibernateSessionFactoryUtil.getSessionFactory().openSession();
        return session.get(Product.class, id);
    }

    public List<Product> findAll() {
        session = HibernateSessionFactoryUtil.getSessionFactory().openSession();
        return session.createQuery("SELECT p FROM Product p", Product.class).getResultList();
    }

    public void saveOrUpdate(Product product) {
        try {
            session = HibernateSessionFactoryUtil.getSessionFactory().openSession();
            session.beginTransaction();

            Product existsProduct = findById(product.getId());
            if (existsProduct != null) {
                session.saveOrUpdate(product);
            } else {
                session.save(product);
            }
            if (TransactionStatus.ACTIVE.equals(session.getTransaction().getStatus())) {
                session.getTransaction().commit();
            }
        } finally {
            session.close();
        }
    }

    public void deleteById(Long id) {
        try {
            session = HibernateSessionFactoryUtil.getSessionFactory().openSession();
            Query query = session.createQuery("DELETE Product p WHERE p.id = :p");
            session.beginTransaction();
            query.setParameter("p", id).executeUpdate();
            session.getTransaction().commit();
        } finally {
            session.close();
        }
    }

}
