package dao;

import model.Product;

import java.util.List;

public interface ProductRepository {

    public Product getProduct(String ean);
    public List<Product> getAllProducts();
    public boolean saveProduct(Product product);
}
