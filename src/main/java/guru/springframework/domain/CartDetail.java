package guru.springframework.domain;

import javax.persistence.*;

@Entity
public class CartDetail extends AbstractDomainClass {
    @ManyToOne
    private Cart cart;
    @OneToOne
    private Product product;

    private Integer quantity;

    public Integer getQuantity() {
        return quantity;
    }
    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }
    public Cart getCart() {
        return cart;
    }
    public void setCart(Cart cart) {
        this.cart = cart;
    }
    public Product getProduct() {
        return product;
    }
    public void setProduct(Product product) {
        this.product = product;
    }
}
