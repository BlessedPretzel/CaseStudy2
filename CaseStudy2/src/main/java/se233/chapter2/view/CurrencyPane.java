package se233.chapter2.view;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import se233.chapter2.controller.AllEventHandlers;
import se233.chapter2.controller.draw.DrawGraphTask;
import se233.chapter2.model.Currency;

import java.util.concurrent.*;

public class CurrencyPane extends BorderPane {
    private Currency currency;
    private Button watch;
    private Button unwatch;
    private Button delete;

    public CurrencyPane(Currency currency) throws InterruptedException {
        this.watch = new Button("Watch");
        this.watch.setOnAction(actionEvent -> {
            AllEventHandlers.onWatch(currency.getShortCode());
        });

        //Exercise 3
        this.unwatch = new Button("Unwatch");
        this.unwatch.setOnAction(actionEvent -> {
            AllEventHandlers.onUnwatch(currency.getShortCode());
        });

        this.delete = new Button("Delete");
        this.delete.setOnAction(actionEvent -> {
            AllEventHandlers.onDelete(currency.getShortCode());
        });
        this.setPadding(new Insets(0));
        this.setPrefSize(640,300);
        this.setStyle("-fx-border-color: black");
        try {
            this.refreshPane(currency);
        } catch (ExecutionException e) {
            System.out.println(e);
        } catch (InterruptedException e) {
            System.out.println(e);
        }
    }


    public void refreshPane(Currency currency) throws ExecutionException, InterruptedException {
        this.currency = currency;
        //Exercise 2
        FutureTask graphTask = new FutureTask<>(new DrawGraphTask((currency)));
        FutureTask infoTask = new FutureTask<>(new genInfoPane(currency));
        FutureTask topTask = new FutureTask(new genTopArea());
        ExecutorService executorService = Executors.newCachedThreadPool();
        executorService.execute(graphTask);
        executorService.execute(infoTask);
        executorService.execute(topTask);
        VBox currencyGraph = (VBox) graphTask.get();
        this.setTop((Node) topTask.get());
        this.setLeft((Node) infoTask.get());
        this.setCenter(currencyGraph);
    }

    //Exercise 2
    public class genInfoPane implements Callable<Pane> {
        Currency currency;
        public genInfoPane(Currency currency) {
            this.currency = currency;
        }

        @Override
        public Pane call() {
            VBox currencyInfoPane = new VBox(10);
            currencyInfoPane.setPadding(new Insets(5, 25, 5, 25));
            currencyInfoPane.setAlignment(Pos.CENTER);
            Label exchangeString = new Label("");
            Label watchString = new Label("");
            exchangeString.setStyle("-fx-font-size: 20;");
            watchString.setStyle("-fx-font-size: 14;");
            if (this.currency!= null) {
                exchangeString.setText(String.format("%s: %.4f", this.currency.getShortCode(), this.currency.getCurrencyEntity().getRate()));
                if (this.currency.getWatch() == true) {
                    watchString.setText(String.format("(Watch @%.4f)", this.currency.getWatchRate()));
                }
            }
            currencyInfoPane.getChildren().addAll(exchangeString, watchString);
            return currencyInfoPane;
        }
    }

    public class genTopArea implements Callable<HBox> {
        @Override
        public HBox call() {
            HBox topArea = new HBox(10);
            topArea.setPadding(new Insets(5));
            topArea.getChildren().addAll(watch, unwatch, delete);
            topArea.setAlignment(Pos.CENTER_RIGHT);
            return topArea;
        }
    }
}
