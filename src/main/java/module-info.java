module com.example.demo7 {
    requires javafx.controls;
    requires javafx.fxml;
    requires org.apache.logging.log4j;
    requires java.sql;


    opens com.example.demo7 to javafx.fxml;
    exports com.example.demo7;
    exports domain;
    opens domain to javafx.base, javafx.fxml;
    exports service;
    opens service to javafx.base, javafx.fxml;
    exports repo;
    opens repo to javafx.base, javafx.fxml;
}