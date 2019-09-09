package com.posproject;
import dao.impl.ProductRepositoryImpl;
import model.Product;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.runners.MockitoJUnitRunner;

import java.util.Random;


@RunWith(MockitoJUnitRunner.class)
public class DaoTest {



    @Test
    public void saveProduct(){

        ProductRepositoryImpl productRepository = new ProductRepositoryImpl();

        Random random = new Random();
        String tempEan = String.valueOf(random.nextInt(100000000));
        Product testProduct = new Product(tempEan,"testProduct",11.0);
        productRepository.saveProduct(testProduct);

        Assert.assertEquals(productRepository.getProduct(tempEan),testProduct);

    }

    @Test
    public void loadProduct(){

        ProductRepositoryImpl productRepository = new ProductRepositoryImpl();

        Product testProduct = new Product("14263856","testProduct",15.0);

        productRepository.saveProduct(testProduct);

        Assert.assertEquals(productRepository.getProduct("14263856"),testProduct);

    }
}
