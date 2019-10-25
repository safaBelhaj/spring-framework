package guru.springframework.domain;

import guru.springframework.enums.OrderStatus;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "order_table")
public class Order extends AbstractDomainClass {
    @Embedded
    private  Address shipToAddress;
    @OneToOne
    private Customer customer;

    @OneToMany(cascade = CascadeType.ALL,mappedBy = "order",orphanRemoval = true)
    private List<OrderDetail> orderDetails=new ArrayList<>();

    private OrderStatus orderStatus;

    public List<OrderDetail> getOrderDetails() {
        return orderDetails;
    }
    public void setOrderDetails(List<OrderDetail> orderDetails) {
        this.orderDetails = orderDetails;
    }
    public void addToOrderDetails(OrderDetail orderDetail){
        orderDetail.setOrder(this);
        orderDetails.add(orderDetail);
    }
    public void removeOrderDetail(OrderDetail orderDetail){
        orderDetail.setOrder(this);
        orderDetails.remove(orderDetail);
    }
    public Customer getCustomer() {
        return customer;
    }
    public void setCustomer(Customer customer) {
        this.customer = customer;
    }
    public Address getShipToAddress() {
        return shipToAddress;
    }
    public void setShipToAddress(Address shipToAddress) {
        this.shipToAddress = shipToAddress;
    }
    public OrderStatus getOrderStatus() {
        return orderStatus;
    }
    public void setOrderStatus(OrderStatus orderStatus) {
        this.orderStatus = orderStatus;
    }
}
