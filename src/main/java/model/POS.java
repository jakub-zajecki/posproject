package model;

import dao.ProductRepository;
import device.out.Display;
import device.out.Printer;
import listeners.EanScannerListener;
import service.MessageService;
import service.impl.MessageServiceImpl;

import java.util.Optional;

public class POS implements EanScannerListener {


    public static final String WRONG_EAN_CODE = "Invalid bar-code";
    public static final String PRODUCT_NOT_FOUND = "Product not found";
    public static final String EXIT_CODE = "exit";

    private ProductRepository productRepository;
    private Display display;
    private Printer printer;
    private MessageService messageService;
    private ProductReceipt  productReceipt = newProductReceipt();

    public POS(ProductRepository productRepository, Display display, Printer printer) {
        this.productRepository = productRepository;
        this.display = display;
        this.printer = printer;
        this.messageService = new MessageServiceImpl();
    }

    @Override
    public void scanEanCodeAction(String ean) {

        if(isEanCorrect(ean)){
            handleWrongEanCode();

        }else {
            handleCorrectEanCode(ean);

        }

    }

    private void handleCorrectEanCode(String ean) {
        if (isExitCode(ean)) {
            handleExitCode();
        } else {
            Optional<Product> product = getProductByBarcode(ean);
            handleScannedProduct(product);
        }
    }

    public void handleExitCode() {

        productReceipt.getScannedProducts().entrySet().stream().
                forEach(e-> printer.printLine( messageService.getProductInfo(e.getKey())+ messageService.getQuantityInfo(e.getValue())));


        display.showMessage(messageService.getTotalSumInfo(productReceipt.getTotalCost()));


       printer.printLine(messageService.getTotalSumInfo(productReceipt.getTotalCost()));

        productReceipt = newProductReceipt();

    }

    private void handleScannedProduct(Optional<Product> product) {
        if (product.isPresent()) {
            productFound(product.get());
        } else {
            productNotFound();
        }
    }

    private void productFound(Product product) {
        String message = messageService.getProductInfo(product);
        productReceipt.addProduct(product);
        display.showMessage(message);
    }


    public void productNotFound() {

        display.showMessage(PRODUCT_NOT_FOUND);

    }

    private void handleWrongEanCode() {
        display.showMessage(WRONG_EAN_CODE);
    }

    private Optional<Product> getProductByBarcode(String ean) {
        return Optional.ofNullable(productRepository.getProduct(ean));
    }



    private boolean isExitCode(String ean) {

        return EXIT_CODE.equals(ean);
    }

    public boolean isEanCorrect(String ean) {
        return ean == null || ean.isEmpty();
    }

    public ProductReceipt newProductReceipt(){

        return new ProductReceipt();
    }

    public MessageService getMessageService() {
        return messageService;
    }
}
