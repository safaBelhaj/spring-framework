package guru.springframework.bootstrap;

import guru.springframework.domain.*;
import guru.springframework.domain.security.Role;
import guru.springframework.enums.OrderStatus;
import guru.springframework.services.CustomerService;
import guru.springframework.services.OrderService;
import guru.springframework.services.ProductService;
import guru.springframework.services.RoleService;
import guru.springframework.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.liquibase.LiquibaseAutoConfiguration;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.util.List;


@Component
class SpringJPABootstrap implements ApplicationListener<ContextRefreshedEvent>{

    private ProductService productService;
    private UserService userService;
    private RoleService roleService;

    @Autowired
    public void setUserService(UserService userService){
        this.userService=userService;
    }

    @Autowired
    public void setProductService(ProductService productService) {
        this.productService = productService;
    }
    
    @Autowired
    public void setRoleService(RoleService roleService) {
    	this.roleService=roleService;
    }

    @Override
    public void onApplicationEvent(ContextRefreshedEvent contextRefreshedEvent) {
        loadProducts();
        loadUsersAndCustomers();
        loadCarts();
        loadOrderHistory();
        loadRoles();
        assignUsersToDefaultRole();

    }
    public void loadRoles() {
    	Role role=new Role();
    	role.setRole("CUSTOMER");
    	roleService.saveOrUpdate(role);
    }
    public void assignUsersToDefaultRole() {
    	List<Role> roles =(List<Role>) roleService.listAll();
    	List<User> users =(List<User>) userService.listAll();
    	roles.forEach(role ->{
    			if(role.getRole().equalsIgnoreCase("CUSTOMER")) {
    				users.forEach(user->{
    					user.addRole(role);
    					userService.saveOrUpdate(user);
    				});
    			}}
    			);
    }
    public void loadCarts(){
        List<User> users= (List<User>) userService.listAll();
        List<Product> products= (List<Product>) productService.listAll();

        users.forEach(user -> {
            user.setCart(new Cart());
            CartDetail cartDetail=new CartDetail();
            cartDetail.setProduct(products.get(0));
            cartDetail.setQuantity(2);
            user.getCart().addCartDetail(cartDetail);
            userService.saveOrUpdate(user);
        });
    }
    public void loadOrderHistory(){
        List<User> users= (List<User>) userService.listAll();
        List<Product> products= (List<Product>) productService.listAll();

        users.forEach(user ->{
            Order order=new Order();
            order.setCustomer(user.getCustomer());
            order.setOrderStatus(OrderStatus.SHIPPED);

            products.forEach(product -> {
                OrderDetail orderDetail=new OrderDetail();
                orderDetail.setProduct(product);
                orderDetail.setQuantity(1);
                order.addToOrderDetails(orderDetail);
            });
        });
    }
    public void loadUsersAndCustomers() {
        User user1=new User();
        user1.setUsername("username1");
        user1.setPassword("password1");

        Customer customer1 = new Customer();
        customer1.setFirstName("Micheal");
        customer1.setLastName("Weston");
        customer1.setBillingAddress(new Address());
        customer1.getBillingAddress().setAddressLine1("1 Main St");
        customer1.getBillingAddress().setCity("Miami");
        customer1.getBillingAddress().setState("Florida");
        customer1.getBillingAddress().setZipCode("33101");
        customer1.setEmail("micheal@burnnotice.com");
        customer1.setPhoneNumber("305.333.0101");

        user1.setCustomer(customer1);
        userService.saveOrUpdate(user1);

        User user2=new User();
        user2.setUsername("username2");
        user2.setPassword("password2");

        Customer customer2 = new Customer();
        customer2.setFirstName("Micheal");
        customer2.setLastName("Weston");
        customer2.setBillingAddress(new Address());
        customer2.getBillingAddress().setAddressLine1("1 Main St");
        customer2.getBillingAddress().setCity("Miami");
        customer2.getBillingAddress().setState("Florida");
        customer2.getBillingAddress().setZipCode("33101");
        customer2.setEmail("micheal@burnnotice.com");
        customer2.setPhoneNumber("305.333.0101");

        user2.setCustomer(customer2);
        userService.saveOrUpdate(user2);

        User user3=new User();
        user3.setUsername("username1");
        user3.setPassword("password1");

        Customer customer3= new Customer();
        customer3.setFirstName("Micheal");
        customer3.setLastName("Weston");
        customer3.setBillingAddress(new Address());
        customer3.getBillingAddress().setAddressLine1("1 Main St");
        customer3.getBillingAddress().setCity("Miami");
        customer3.getBillingAddress().setState("Florida");
        customer3.getBillingAddress().setZipCode("33101");
        customer3.setEmail("micheal@burnnotice.com");
        customer3.setPhoneNumber("305.333.0101");

        user3.setCustomer(customer3);
        userService.saveOrUpdate(user3);
    }

    public void loadProducts(){

        Product product1 = new Product();
        product1.setDescription("Product 1");
        product1.setPrice(new BigDecimal("12.99"));
        product1.setImageUrl("http://example.com/product1");
        productService.saveOrUpdate(product1);

        Product product2 = new Product();
        product2.setDescription("Product 2");
        product2.setPrice(new BigDecimal("14.99"));
        product2.setImageUrl("http://example.com/product2");
        productService.saveOrUpdate(product2);

        Product product3 = new Product();
        product3.setDescription("Product 3");
        product3.setPrice(new BigDecimal("34.99"));
        product3.setImageUrl("http://example.com/product3");
        productService.saveOrUpdate(product3);

        Product product4 = new Product();
        product4.setDescription("Product 4");
        product4.setPrice(new BigDecimal("44.99"));
        product4.setImageUrl("http://example.com/product4");
        productService.saveOrUpdate(product4);

        Product product5 = new Product();
        product5.setDescription("Product 5");
        product5.setPrice(new BigDecimal("25.99"));
        product5.setImageUrl("http://example.com/product5");
        productService.saveOrUpdate(product5);

    }
}
