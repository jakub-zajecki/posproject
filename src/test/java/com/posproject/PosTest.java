package com.posproject;

import dao.ProductRepository;
import dao.impl.ProductRepositoryImpl;
import device.in.impl.EanCodeScannerImpl;
import device.out.Display;
import device.out.Printer;
import model.POS;
import model.Product;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;



import static model.POS.EXIT_CODE;
import static model.POS.WRONG_EAN_CODE;
import static model.POS.PRODUCT_NOT_FOUND;
import static org.mockito.Mockito.*;


@RunWith(MockitoJUnitRunner.class)
public class PosTest {

    private static final String EANCODE_EMPTY = "";
    private static final String EANCODE_NULL = null;
    private static final String EANCODE_OF_NOT_EXISTING_PRODUCT = "747838374";
    private static final String EANCODE_PRODUCT_1 = "5353535";
    private static final String EANCODE_PRODUCT_2 = "14263856";


    @Mock
    private Display display;

    @Mock
    private Printer printer;

    private ProductRepository productRepository;

    private EanCodeScannerImpl eanCodeScannerImpl;

    private POS tested;

    @Before
    public void setup(){

        productRepository = new ProductRepositoryImpl();

        tested = new POS(productRepository, display, printer);
        eanCodeScannerImpl = new EanCodeScannerImpl();

        eanCodeScannerImpl.addObserver(tested);
    }


    @Test
    public void ifScannedEanCodeIsEmptyshouldPrintInvalidEanCodeMessageOnLcd(){
        //given
        String barcode = EANCODE_EMPTY;

        //when
        tested.scanEanCodeAction(barcode);

        //than
        verify(display).showMessage(WRONG_EAN_CODE);
        verifyNoMoreInteractions(display);
    }

    @Test
    public void IfScannedEanCodeIsNullShouldPrintInvalidBarEanCodeMessageOnLcd(){
        //given
        String barcode = EANCODE_NULL;

        //when
        tested.scanEanCodeAction(barcode);

        //than
        verify(display).showMessage(WRONG_EAN_CODE);
        verifyNoMoreInteractions(display);
    }

    @Test
    public void IfProductIsNotFoundShouldPrintProductNotFound(){
        //given
        String barcode = EANCODE_OF_NOT_EXISTING_PRODUCT;

        //when
        tested.scanEanCodeAction(barcode);

        //than
        verify(display).showMessage(PRODUCT_NOT_FOUND);
        verifyNoMoreInteractions(display);
    }

    @Test
    public void whenExitCodeReadBeforeScannedAnyProductShouldPrintSumOfZero(){
        //given
        String barcode = EXIT_CODE;

        //when
        tested.scanEanCodeAction(barcode);

        verify(display).showMessage("0.0");
        verify(printer, atMost(1)).printLine("0.0");
        verifyNoMoreInteractions(display);
        verifyNoMoreInteractions(printer);
    }

    @Test
    public void printSumOfProductPriceWhenReadExitCode(){
        //given
        String barcodeOfProduct1 = EANCODE_PRODUCT_1;
        String barcodeOfProduct2 = EANCODE_PRODUCT_2;
        String exitCode = EXIT_CODE;
        Product product1 = productRepository.getProduct(barcodeOfProduct1);
        Product product2 = productRepository.getProduct(barcodeOfProduct2);

        //when
        eanCodeScannerImpl.readEanCode(barcodeOfProduct1);
        eanCodeScannerImpl.readEanCode(barcodeOfProduct1);
        eanCodeScannerImpl.readEanCode(barcodeOfProduct2);
        tested.scanEanCodeAction(exitCode);


        verify(display,times(2)).showMessage(tested.getMessageService().getProductInfo(product1));

        verify(display).showMessage(tested.getMessageService().getProductInfo(product2));
        verify(display).showMessage(tested.getMessageService().getTotalSumInfo(product1.getPrice()+product1.getPrice()+product2.getPrice()));
        verify(printer).printLine(tested.getMessageService().getProductInfo(product1) + tested.getMessageService().getQuantityInfo(2));
        verify(printer).printLine(tested.getMessageService().getProductInfo(product2) + tested.getMessageService().getQuantityInfo(1));
        verify(printer).printLine(tested.getMessageService().getTotalSumInfo(product1.getPrice()+product1.getPrice()+product2.getPrice()));
        verifyNoMoreInteractions(display);
        verifyNoMoreInteractions(printer);
    }

}
