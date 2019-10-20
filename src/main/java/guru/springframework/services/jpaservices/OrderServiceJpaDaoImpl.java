package guru.springframework.services.jpaservices;

import guru.springframework.domain.Order;
import guru.springframework.services.OrderService;

import javax.persistence.EntityManager;
import java.util.List;

public class OrderServiceJpaDaoImpl extends AbstractJpaDaoService implements OrderService {
    @Override
    public List<Order> listAll() {
        EntityManager em=emf.createEntityManager();
        return em.createQuery("from Order",Order.class).getResultList();
    }

    @Override
    public Order getById(Integer id) {
        EntityManager em=emf.createEntityManager();
        return em.find(Order.class,id);
    }

    @Override
    public Order saveOrUpdate(Order domainObject) {
        EntityManager em=emf.createEntityManager();
        em.getTransaction().begin();
        Order savedOrder=em.merge(domainObject);
        em.getTransaction().commit();
        return savedOrder;
    }

    @Override
    public void delete(Integer id) {
        EntityManager em=emf.createEntityManager();
        em.getTransaction().begin();
        em.remove(em.find(Order.class,id));
        em.getTransaction().commit();
    }
}
