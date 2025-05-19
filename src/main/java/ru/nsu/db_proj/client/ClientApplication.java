package ru.nsu.db_proj.client;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class ClientApplication extends Application {
    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(ClientApplication.class.getResource("client-view.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 800, 600);

        ClientController controller = fxmlLoader.getController();
        controller.updateDepartmentTypes();
        controller.updateFlightStatuses();
        controller.updateAirportTypes();
        controller.updateBrigadeTypes();
        controller.updateFlightTypes();
        controller.updateRoutes();
        controller.updateModels();
        controller.updateDelays();
        controller.updateTypes();

        controller.setStage(stage);

        stage.setMinWidth(640);
        stage.setMinHeight(480);
        stage.setTitle("Forms application!");
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {
        launch();
    }
}