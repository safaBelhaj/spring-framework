package guru.springframework.controllers;


import guru.springframework.domain.Product;
import guru.springframework.services.ProductService;
import org.junit.Before;
import org.junit.Test;
import org.mockito.*;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import static org.hamcrest.Matchers.*;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
public class ProductControllerTest {
    @Mock //Mockito Mock Object
    private ProductService productService;

    @InjectMocks //Setup controller and injects Mock Objects
    private ProductController productController;

    private MockMvc mockMvc;

    @Before
    public void setup(){
        MockitoAnnotations.initMocks(this); //Initializes controller and mocks
        mockMvc= MockMvcBuilders.standaloneSetup(productController).build();
    }

    @Test
    public void testList() throws Exception{
        List<Product> products=new ArrayList<>();
        products.add(new Product());
        products.add(new Product());

        //specific Mockito interaction , tell stub to return a list of products
        when(productService.listAll()).thenReturn((List)products);
        //Now the mock object does not know anything about ProductService . So, somewhere we have to tell the mock that "whenever someone calls the listAll() method of ProductService, please return a List of products" . This is exactly what the when  method is doing.
        mockMvc.perform(get("/product/list"))
                .andExpect(status().isOk())
                .andExpect(view().name("listProduct"))
                .andExpect(model().attribute("products",hasSize(2)));
    }

    @Test
    public void testSHow() throws Exception{
        Integer id=1;
        //Tell Mockito stub to return new Product for ID 1
        when(productService.getById(id)).thenReturn(new Product());

        mockMvc.perform(get("/product/show/1"))
                .andExpect(status().isOk())
                .andExpect(view().name("showProduct"))
                .andExpect(model().attribute("product",instanceOf(Product.class)));
    }
     @Test
    public void testEdit() throws Exception{
        Integer id=1;

        when(productService.getById(id)).thenReturn(new Product());

        mockMvc.perform(get("/product/edit/1"))
                .andExpect(status().isOk())
                .andExpect(view().name("productForm"))
                .andExpect(model().attribute("product",instanceOf(Product.class)));
     }
    @Test
    public void testNew() throws Exception{
        verifyZeroInteractions(productService);//verify that the product service is not used (we didn't use productService also in the actual controller)
        mockMvc.perform(get("/product/new"))
                .andExpect(status().isOk())
                .andExpect(view().name("productForm"))
                .andExpect(model().attribute("product",instanceOf(Product.class)));
    }
    @Test
    public void testSaveOrUpdate() throws Exception{
        Integer id=1;
        String description ="Test Description";
        BigDecimal price =new BigDecimal("12.00");
        String imageUrl="example.com";

        Product returnProduct =new Product();
        returnProduct.setId(id);
        returnProduct.setDescription(description);
        returnProduct.setPrice(price);
        returnProduct.setImageUrl(imageUrl);

        when(productService.saveOrUpdate(Matchers.<Product>any())).thenReturn(returnProduct);

        mockMvc.perform(post("/product")
                .param("id","1")
                .param("description",description)
                .param("price","12.00")
                .param("imageUrl",imageUrl))
                        .andExpect(status().is3xxRedirection())
                        .andExpect(view().name("redirect:/product/show/1"))
                        .andExpect(model().attribute("product",instanceOf(Product.class)))
                        .andExpect(model().attribute("product",hasProperty("id",is(id))))
                        .andExpect(model().attribute("product",hasProperty("description",is(description))))
                        .andExpect(model().attribute("product",hasProperty("price",is(price))))
                        .andExpect(model().attribute("product",hasProperty("imageUrl",is(imageUrl))));
        //Verify properties of bound object
        ArgumentCaptor<Product> boundProduct=ArgumentCaptor.forClass(Product.class);
        verify(productService).saveOrUpdate(boundProduct.capture());
    }
    @Test
    public void testDelete() throws Exception{
        Integer id=1;
        mockMvc.perform(get("/product/delete/1"))
                .andExpect(status().is3xxRedirection()) //Status of redirection
                .andExpect(view().name("redirect:/product/list"));
        verify(productService,times(1)).delete(id);
    }

}
