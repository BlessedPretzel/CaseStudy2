module com.example.casestudy2 {
    requires javafx.controls;
    requires javafx.fxml;
    requires org.json;
    requires org.apache.commons.io;
    requires org.apache.logging.log4j;
    requires org.slf4j;


    opens se233.chapter2 to javafx.fxml;
    exports se233.chapter2;
}