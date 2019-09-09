package service;

import model.Product;

public interface MessageService {

    public String getProductInfo(Product product);

    public String getTotalSumInfo(Double sum);

    public String getQuantityInfo(Integer quantity);


}
