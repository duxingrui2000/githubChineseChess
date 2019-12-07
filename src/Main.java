import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import java.io.FileInputStream;


public class Main extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception
    {
        FXMLLoader loader = new FXMLLoader();
        FileInputStream fxmlStream = new FileInputStream("src\\checker_board.fxml");
        Parent root = loader.load(fxmlStream);

        primaryStage.setScene(new Scene(root,493.0,607.0));
        primaryStage.show();

    }
    public static void main(String[] args)
    {
        launch(args);
    }
}
