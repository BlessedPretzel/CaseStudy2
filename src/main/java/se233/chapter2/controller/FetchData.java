package se233.chapter2.controller;

import javafx.scene.control.Alert;
import org.json.JSONObject;
import se233.chapter2.model.CurrencyEntity;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.charset.Charset;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import org.apache.commons.io.IOUtils;

public class FetchData {
    private static final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");

    public static ArrayList<CurrencyEntity> fetch_range(String src, int N) {

        ArrayList<CurrencyEntity> histList = new ArrayList<>();

        for (int i=N; i>0; i--) {
            String retrievedJson = null;
            try {
                String dateI = LocalDate.now().minusDays(i).format(formatter);
                String url_str = String.format("https://cdn.jsdelivr.net/gh/fawazahmed0/currency-api@1/%s/currencies/thb/%s.json", dateI, src.toLowerCase());
                retrievedJson = IOUtils.toString(new URL(url_str), Charset.defaultCharset());
            } catch (MalformedURLException e) {
                System.out.println("Encountered a Malformed URL exception");
            }
            catch (IOException e) {
                System.out.println("Encountered an IO exception");
            }
            JSONObject jsonObj2 = new JSONObject(retrievedJson);
            String date = jsonObj2.getString("date");

            //Exercise 4
            double rate = jsonObj2.getDouble(src.toLowerCase());

            histList.add(new CurrencyEntity(rate, date));
        }
        //print(histList);
        return histList;
    }

    //Exercise 5
    public static boolean isCurrency(String src) {
        try {
            JSONObject jsonObj1 = new JSONObject(IOUtils.toString(new URL("https://cdn.jsdelivr.net/gh/fawazahmed0/currency-api@1/latest/currencies.json"), Charset.defaultCharset()));
            jsonObj1.getString(src.toLowerCase());
        } catch (Exception e) {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setContentText("Currency not found. Please re-enter the currency code");
            alert.showAndWait();
            return false;
        }
        return true;
    }
}
