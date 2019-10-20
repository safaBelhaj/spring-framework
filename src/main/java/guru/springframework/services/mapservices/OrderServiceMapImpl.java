package guru.springframework.services.mapservices;

import guru.springframework.domain.DomainObject;
import guru.springframework.domain.Order;
import guru.springframework.services.OrderService;

import java.util.List;

public class OrderServiceMapImpl extends AbstractMapService implements OrderService {
    @Override
    public List<DomainObject> listAll() {
        return super.listAll() ;
    }

    @Override
    public Order getById(Integer id) {
        return (Order) super.getById(id);
    }

    @Override
    public Order saveOrUpdate(Order domainObject) {
        return (Order) super.saveOrUpdate(domainObject);
    }

    @Override
    public void delete(Integer id) {
        super.delete(id);
    }
}
