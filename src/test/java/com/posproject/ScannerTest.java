package com.posproject;

import device.in.impl.EanCodeScannerImpl;
import model.POS;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.times;


@RunWith(MockitoJUnitRunner.class)
public class ScannerTest {

    private EanCodeScannerImpl eanCodeScanner = new EanCodeScannerImpl();


    @Mock
    POS pos;



    @Test
    public void checkListener(){

        eanCodeScanner.addObserver(pos);

        eanCodeScanner.readEanCode("4567378");

        verify(pos,times(1)).scanEanCodeAction("4567378");



    }


}
