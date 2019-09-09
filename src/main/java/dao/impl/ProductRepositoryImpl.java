package dao.impl;

import dao.ProductRepository;
import model.Product;

import java.io.*;
import java.util.List;

public class ProductRepositoryImpl implements ProductRepository {



    public Product getProduct(String ean)  {

        try {
            BufferedReader bufferedReader = new BufferedReader(new FileReader("src/main/resources/products"));

            String temporaryProduct;

            while ((temporaryProduct = bufferedReader.readLine()) != null){
                String[] tokens = temporaryProduct.split("\\|");
                if(tokens[0].equals(ean)){
                    return new Product(tokens[0],tokens[1],Double.parseDouble(tokens[2]));
                }
            }
        } catch (FileNotFoundException e1) {
            e1.printStackTrace();
        } catch (IOException e1) {
            e1.printStackTrace();
        }

        return null;
    }






    public List<Product> getAllProducts() {
        return null;
    }


    public boolean saveProduct(Product product) {

        try {

            BufferedWriter bw = new BufferedWriter(new FileWriter("src/main/resources/products", true));

            bw.write(product.getEan() + "|" + product.getName() + "|" + product.getPrice());
            bw.newLine();
            bw.close();


        } catch (IOException e) {
            e.printStackTrace();
        }


        return true;

    }
}
