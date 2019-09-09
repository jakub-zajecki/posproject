package device.in.impl;

import device.in.EanCodeScanner;
import model.POS;

import java.util.ArrayList;
import java.util.List;

public class EanCodeScannerImpl implements device.in.EanCodeScanner {

    private List<POS> observers;

    public EanCodeScannerImpl(){
        observers = new ArrayList<>();
    }

    public boolean addObserver(POS pos){
        observers.add(pos);
        return true;
    }

    public void updateObservers(String ean){
        for (POS pos : observers){
            pos.scanEanCodeAction(ean);
        }
    }

    @Override
    public void readEanCode(String ean) {
       updateObservers(ean);
    }
}
