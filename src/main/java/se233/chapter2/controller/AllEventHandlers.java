package se233.chapter2.controller;

import javafx.scene.control.TextInputDialog;
import se233.chapter2.Launcher;
import se233.chapter2.model.Currency;
import se233.chapter2.model.CurrencyEntity;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Optional;
import java.util.concurrent.ExecutionException;

public class AllEventHandlers {
    public static void onRefresh() {
        try {
            Launcher.refreshPane();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void onDelete(String code) {
        ArrayList<Currency> currencyList = Launcher.getCurrencyList();
        int index = -1;
        for (int i = 0; i<currencyList.size(); i++) {
            if (currencyList.get(i).getShortCode().equals(code)) {
                index = i;
                break;
            }
        }
        if (index != -1) {
            currencyList.remove(index);
            Launcher.setCurrencyList(currencyList);
            Launcher.refreshPane();
        }
    }
    public static void onWatch(String code) {
        ArrayList<Currency> currencyList = Launcher.getCurrencyList();
        int index = -1;
        for (int i=0; i<currencyList.size(); i++) {
            if (currencyList.get(i).getShortCode().equals(code)) {
                index = i;
                break;
            }
        }
        if (index != -1) {
            TextInputDialog dialog = new TextInputDialog();
            dialog.setTitle("Add Watch");
            dialog.setContentText("Rate: ");
            dialog.setHeaderText(null);
            dialog.setGraphic(null);
            Optional<String> retrievedRate = dialog.showAndWait();
            if (retrievedRate.isPresent()) {
                double rate = Double.parseDouble(retrievedRate.get());
                currencyList.get(index).setWatch(true);
                currencyList.get(index).setWatchRate(rate);
                Launcher.setCurrencyList(currencyList);
                Launcher.refreshPane();
            }
            Launcher.setCurrencyList(currencyList);
            Launcher.refreshPane();
        }
    }
    //Exercise 3
    public static void onUnwatch(String code) {
        ArrayList<Currency> currencyList = Launcher.getCurrencyList();
        int index = -1;
        for (int i=0; i<currencyList.size(); i++) {
            if (currencyList.get(i).getShortCode().equals(code)) {
                index = i;
                break;
            }
        }
        if (index != -1) {
            currencyList.get(index).setWatch(false);
            currencyList.get(index).setWatchRate(0);
            Launcher.setCurrencyList(currencyList);
            Launcher.refreshPane();
        }
    }
    public static void onAdd() {
        TextInputDialog dialog = new TextInputDialog();
        dialog.setTitle("Add Currency");
        dialog.setContentText("Currency code:");
        dialog.setHeaderText(null);
        dialog.setGraphic(null);
        Optional<String> code = dialog.showAndWait();

        //Exercise 5
        if (code.isPresent() && FetchData.isCurrency(code.get())) {
            ArrayList<Currency> currencyList = Launcher.getCurrencyList();
            Currency c = new Currency(code.get());

            //Exercise 1
            ArrayList<CurrencyEntity> c_List = FetchData.fetch_range(c.getShortCode(), 14);

            c.setHistData(c_List);
            c.setCurrencyEntity(c_List.get(c_List.size() - 1));
            currencyList.add(c);
            Launcher.setCurrencyList(currencyList);
            Launcher.refreshPane();
        }
    }
}
