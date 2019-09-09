package model;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.Map;

public class ProductReceipt {

    private static Long idCounter = 0L;
    private final Long ID = idCounter;
    private Map<Product,Integer> scannedProducts;
    private LocalDate purchaseDate;
    private double totalCost;

    public ProductReceipt(){
        this.scannedProducts = new HashMap<>();
        this.purchaseDate = LocalDate.now();
        this.totalCost = 0;
        ++idCounter;
    };

    public void addProduct(Product product){
        if(!scannedProducts.containsKey(product)){
            scannedProducts.put(product,1);
            totalCost = totalCost + product.getPrice();
        }else {
            Integer tempQuantity = scannedProducts.get(product);
            scannedProducts.put(product,++tempQuantity);
            totalCost = totalCost + product.getPrice();

        }

    }

    public Long getId() {
        return ID;
    }


    public Map<Product, Integer> getScannedProducts() {
        return scannedProducts;
    }


    public LocalDate getPurchaseDate() {
        return purchaseDate;
    }

    public void setPurchaseDate(LocalDate purchaseDate) {
        this.purchaseDate = purchaseDate;
    }

    public double getTotalCost() {
        return totalCost;
    }

    public void setTotalCost(double totalCost) {
        this.totalCost = totalCost;
    }
}
