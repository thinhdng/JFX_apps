module com.todo {
    requires transitive javafx.controls;
    requires transitive javafx.fxml;

    requires org.mongodb.driver.sync.client;
    requires org.mongodb.bson;
    requires org.mongodb.driver.core;

    opens com.todo to javafx.fxml;
    exports com.todo;
}
