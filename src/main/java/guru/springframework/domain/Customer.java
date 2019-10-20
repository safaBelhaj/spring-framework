package guru.springframework.domain;

import javax.persistence.*;

@Entity
public class Customer extends AbstractDomainClass  {

    private String firstName;
    private String lastName;
    private String email;
    private String phoneNumber;

    @Embedded
    private Address billingAddress;
    @Embedded
    private Address shippingAddress;

    @OneToOne(cascade = {CascadeType.MERGE,CascadeType.PERSIST})
    private User user;

    public User getUser(){
        return user;
    }
    public void setUser(User user){
        this.user=user;
    }
    public void setFirstName(String firstName){
        this.firstName=firstName;
    }
    public String getFirstName(){
        return firstName;
    }
    public void setLastName(String lastName){
        this.lastName=lastName;
    }
    public String getLastName(){
        return lastName;
    }
    public void setEmail(String email){
        this.email=email;
    }
    public String getEmail(){
        return email;
    }
    public void setPhoneNumber(String phoneNumber){
        this.phoneNumber=phoneNumber;
    }
    public String getPhoneNumber(){
        return phoneNumber;
    }
    public Address getBillingAddress() {
        return billingAddress;
    }
    public void setBillingAddress(Address billingAddress) {
        this.billingAddress = billingAddress;
    }
    public Address getShippingAddress() {
        return shippingAddress;
    }
    public void setShippingAddress(Address shippingAddress) {
        this.shippingAddress = shippingAddress;
    }
}
