package Utils;

import javafx.scene.control.Label;
import javafx.scene.paint.Color;

public class Notifier {

    private static Label notificationcenter = new Label("");
    private static Color normaleColor = Color.BLACK;
    private static Color warningColor = Color.RED;

    public static void notify(String notification){
        //notificationcenter.setText(notification);
        Toast.makeText(notification);
    }

    public static void warn(String warning){
        notificationcenter.setTextFill(warningColor);
        notificationcenter.setText(warning);
        notificationcenter.setTextFill(normaleColor);
    }

    public static Label getLabel(){
        return notificationcenter;
    }
}
