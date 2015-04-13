package unifiedshoppingexperience;

import java.util.Map;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

/**
 *
 * @author Gruppe12
 */
public class UnifiedShoppingExperience extends Application
{

    private String email;
    private String phoneNumber;
    private Map customerMap;

    public Map<Product, Integer> findProduct(String[] descriptionTags, String[] typeTags)
    {
        return null;

    }

    public void addProduct(String CustomerID, Product product)
    {

    }

    public void viewCart(String id, int cartID)
    {

    }

    @Override
    public void start(Stage stage) throws Exception
    {
        Parent root = FXMLLoader.load(getClass().getResource("FXMLDocument.fxml"));

        Scene scene = new Scene(root);

        stage.setScene(scene);
        stage.show();
    }

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args)
    {
        launch(args);
    }

}
