package guru.springframework.services;

import guru.springframework.domain.Product;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.List;



@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootApplication
@ActiveProfiles("jpadao")
public class ProductServiceJpaDaoImplTest {
    private ProductService productService;

    @Autowired
    public void setProductService(ProductService productService){
        this.productService=productService;
    }
    @Test
    public void testListMethod() throws Exception{
        List<Product> products = (List<Product>) productService.listAll();
        assert products.size() == 5;

    }

}
