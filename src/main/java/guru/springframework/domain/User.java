package guru.springframework.domain;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.*;

import guru.springframework.domain.security.Role;

@Entity
public class User  extends AbstractDomainClass {
    private String username;

    @Transient //isn't stored in the DB
    private String password;

    private String encryptedPassword;
    private Boolean enabled=true;

    @OneToOne(cascade={CascadeType.MERGE,CascadeType.PERSIST})
    private Customer customer;

    @OneToOne(cascade = CascadeType.ALL,orphanRemoval = true)
    private Cart cart;

    @ManyToMany
    @JoinTable
    private List<Role> roles=new ArrayList<>();
    
    public Customer getCustomer(){
        return customer;
    }
    public void setCustomer(Customer customer){
        this.customer=customer;
        customer.setUser(this);
    }
    public Cart getCart(){
        return cart;
    }
    public void setCart(Cart cart) {
        this.cart = cart;
        cart.setUser(this);
    }
    public String getUsername(){
        return username;
    }
    public void setUsername(String username){
        this.username=username;
    }
    public String getPassword(){
        return password;
    }
    public  void setPassword(String password){
        this.password=password;
    }
    public String getEncryptedPassword(){
        return encryptedPassword;
    }
    public void setEncryptedPassword(String encryptedPassword){
        this.encryptedPassword=encryptedPassword;
    }
    public Boolean getEnabled(){
    return enabled;
    }
    public void setEnabled(Boolean enabled) {
        this.enabled = enabled;
    }
    public List<Role> getRoles(){
    	return roles;
    }
    public void setRoles(List<Role> roles) {
    	this.roles=roles;
    }
    public void addRole(Role role) {
    	if(!this.roles.contains(role)) {
    		this.roles.add(role);
    	}
    	if(!role.getUsers().contains(this)){
    		role.getUsers().add(this); 
    	}
    }
    public void removeRole(Role role) {
    	this.roles.remove(role);
    	role.getUsers().remove(this);
    }
}
