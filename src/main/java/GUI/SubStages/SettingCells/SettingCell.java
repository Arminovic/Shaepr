package GUI.SubStages.SettingCells;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.TextField;
import javafx.scene.layout.Background;
import javafx.scene.layout.Border;
import javafx.scene.layout.Pane;

import java.lang.reflect.Field;

public class SettingCell extends Pane {

    public String getIdentifier(){
        return String.class.toString();
    }

    public Node makeNode(Field field, Object instance){
        TextField textfield = new TextField();
        textfield.setAlignment(Pos.CENTER_RIGHT);
        textfield.setMaxWidth(Double.MAX_VALUE);
        textfield.setBackground(Background.EMPTY);
        //textfield.setBorder(GUIHelper.redBorder);
        textfield.setPadding(new Insets(2,-1, 2, 0));
        textfield.setBorder(Border.EMPTY);

        try {
            textfield.setText(field.get(instance).toString());
            textfield.focusedProperty().addListener((observable, oldValue, newValue) -> {
                if(!textfield.isFocused()){
                    try {
                        field.set(instance, textfield.getText());
                        System.out.println(field.getName() + " = " + field.get(instance).toString());
                    } catch (IllegalAccessException e) {
                        e.printStackTrace();
                    }
                }else{
                    System.out.println("focused");
                }
            });
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
        return textfield;
    }

}
