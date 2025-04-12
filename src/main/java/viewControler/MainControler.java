package viewControler;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class MainControler extends Application {
    private static Stage primaryStage;

    @Override
    public void start(Stage primaryStage) throws IOException {
//        Manager.initManager();
        this.primaryStage = primaryStage;
        loadFXML();
        primaryStage.setTitle("Quản lý bán hàng");
        primaryStage.show();
    }

    private void loadFXML() throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/view/khachHangView.fxml"));
        Parent root = fxmlLoader.load();
        Scene scene = new Scene(root, 800, 600);
        scene.getStylesheets().add(this.getClass().getResource("/css/generalCss.css").toExternalForm());
        primaryStage.setScene(scene);
    }

    public static Stage getMainStage(){
        return primaryStage;
    }
    public static void startProgram(String[] args) {
        launch(args);
    }
}