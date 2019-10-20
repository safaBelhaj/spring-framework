package guru.springframework.controllers;


import guru.springframework.domain.Address;
import guru.springframework.domain.Customer;
import guru.springframework.services.CustomerService;
import org.junit.Before;
import org.junit.Test;
import org.mockito.*;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.ArrayList;
import java.util.List;

import static org.hamcrest.Matchers.*;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

public class CustomerControllerTest {
    @Mock
    private CustomerService customerService;

    private MockMvc mockMvc;
    @InjectMocks
    private CustomerController customerController;
    @Before
    public void setup(){
        MockitoAnnotations.initMocks(this);
        mockMvc=MockMvcBuilders.standaloneSetup(customerController).build();
    }

    @Test
    public void TestList() throws Exception{
        List<Customer> customers=new ArrayList<>();
        customers.add(new Customer());
        customers.add(new Customer());

        when(customerService.listAll()).thenReturn((List) customers);
        mockMvc.perform(get("/customer/list"))
                .andExpect(status().isOk())
                .andExpect(view().name("list"))
                .andExpect(model().attribute("customers",hasSize(2)));
    }

    @Test
    public void testShow() throws Exception{
        Integer id=1;
        when(customerService.getById(id)).thenReturn(new Customer());
        mockMvc.perform(get("/customer/show/1"))
                .andExpect(status().isOk())
                .andExpect(view().name("show"))
                .andExpect(model().attribute("customer",instanceOf(Customer.class)));
    }

    @Test
    public void testEdit() throws Exception{
        Integer id=1;
        when(customerService.getById(id)).thenReturn(new Customer());
        mockMvc.perform(get("/customer/edit/1"))
                .andExpect(status().isOk())
                .andExpect(view().name("customerForm"))
                .andExpect(model().attribute("customer",instanceOf(Customer.class)));
    }

    @Test
    public void testNew() throws Exception{
        verifyZeroInteractions(customerService);
        mockMvc.perform(get("/customer/new"))
                .andExpect(status().isOk())
                .andExpect(view().name("customerForm"))
                .andExpect(model().attribute("customer",instanceOf(Customer.class)));
    }

    @Test
    public void testSaveOrUpdate() throws Exception{
        Integer id=1;
        String firstName="my first name";
        String lastName="my last name ";
        String email="my email";
        String phoneNumber="my phone number";
        String addressLine1="my addressLine1";
        String addressLine2="my addressLine2";
        String state="my state ";
        String city="my city";
        String zipCode="my zip code";

        Customer returnCustomer=new Customer();
        returnCustomer.setId(id);
        returnCustomer.setFirstName(firstName);
        returnCustomer.setLastName(lastName);
        returnCustomer.setEmail(email);
        returnCustomer.setPhoneNumber(phoneNumber);
        returnCustomer.setBillingAddress(new Address());
        returnCustomer.getBillingAddress().setCity(city);
        returnCustomer.getBillingAddress().setZipCode(zipCode);
        when(customerService.saveOrUpdate(Matchers.<Customer>any())).thenReturn(returnCustomer);
        mockMvc.perform(post("/customer")
                .param("id","1")
                .param("firstName",firstName)
                .param("lastName",lastName)
                .param("email",email)
                .param("city",city)
                .param("phoneNumber",phoneNumber)
                .param("zipCode",zipCode))
                    .andExpect(status().is3xxRedirection())
                    .andExpect(view().name("redirect:/customer/show/1"))
                    .andExpect(model().attribute("customer",instanceOf(Customer.class)))
                    .andExpect(model().attribute("customer",hasProperty("id",is(id))))
                    .andExpect(model().attribute("customer",hasProperty("firstName",is(firstName))))
                    .andExpect(model().attribute("customer",hasProperty("lastName",is(lastName))))
                    .andExpect(model().attribute("customer",hasProperty("email",is(email))))
                    .andExpect(model().attribute("customer",hasProperty("phoneNumber",is(phoneNumber))))
                    .andExpect(model().attribute("customer",hasProperty("shippingAddress",hasProperty("addressLine1",is(addressLine1)))))
                    .andExpect(model().attribute("customer",hasProperty("shippingAddress",hasProperty("addressLine2",is(addressLine2)))))
                    .andExpect(model().attribute("customer",hasProperty("shippingAddress",hasProperty("city",is(city)))))
                    .andExpect(model().attribute("customer",hasProperty("shippingAddress",hasProperty("state",is(state)))))
                    .andExpect(model().attribute("customer",hasProperty("shippingAddress",hasProperty("zipCode",is(zipCode)))));

        ArgumentCaptor<Customer> customerCaptor=ArgumentCaptor.forClass(Customer.class);
        verify(customerService).saveOrUpdate(customerCaptor.capture());

        Customer boundCustomer=customerCaptor.getValue();

        assertEquals(id,boundCustomer.getId());
        assertEquals(firstName,boundCustomer.getFirstName());
        assertEquals(lastName,boundCustomer.getLastName());
        assertEquals(addressLine1,boundCustomer.getShippingAddress().getAddressLine1());
        assertEquals(addressLine2,boundCustomer.getShippingAddress().getAddressLine2());
        assertEquals(city,boundCustomer.getShippingAddress().getCity());
        assertEquals(state,boundCustomer.getShippingAddress().getState());
        assertEquals(zipCode,boundCustomer.getShippingAddress().getZipCode());
        assertEquals(email,boundCustomer.getEmail());
        assertEquals(phoneNumber,boundCustomer.getPhoneNumber());

    }
    @Test
    public void testDelete() throws Exception{
        Integer id=1;
        mockMvc.perform(get("/customer/delete/1"))
                .andExpect(status().is3xxRedirection())
                .andExpect(view().name("redirect:/customer/list"));
        verify(customerService,times(1)).delete(id);

    }

}
