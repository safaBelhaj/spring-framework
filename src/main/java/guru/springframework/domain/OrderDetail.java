package guru.springframework.domain;

import javax.persistence.*;

@Entity
public class OrderDetail extends AbstractDomainClass{

    @ManyToOne
    private Order order;
    @OneToOne
    private Product product;

    private Integer quantity;

    public Product getProduct() {
        return product;
    }
    public void setProduct(Product product) { this.product = product; }
    public Integer getQuantity(){  return quantity;  }
    public void setQuantity(Integer quantity){ this.quantity=quantity; }
    public Order getOrder() {
        return order;
    }
    public void setOrder(Order order) {
        this.order = order;
    }
}
