package service.impl;

import service.MessageService;
import model.Product;

public class MessageServiceImpl implements MessageService {


    @Override
    public String getProductInfo(Product product) {
        return new StringBuilder()
                .append(product.getName())
                .append(" ")
                .append(product.getPrice())
                .toString();
    }

    @Override
    public String getTotalSumInfo(Double sum) {
        return sum.toString();
    }

    @Override
    public String getQuantityInfo(Integer quantity) {
        return new StringBuilder().
                append(" ").
                append("x").
                append(quantity).
                toString();
    }
}
