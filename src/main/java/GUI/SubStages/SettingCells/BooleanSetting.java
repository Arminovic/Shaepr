package GUI.SubStages.SettingCells;

import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.CheckBox;

import java.lang.reflect.Field;

public class BooleanSetting extends SettingCell{


    public String getIdentifier(){
        return boolean.class.toString();
    }

    public Node makeNode(Field field, Object instance){
        //ToggleSwitch toggleSwitch = new ToggleSwitch();
        CheckBox checkBox = new CheckBox();
        checkBox.setAlignment(Pos.CENTER_RIGHT);
        try {
            checkBox.setSelected((boolean) field.get(instance));
            checkBox.selectedProperty().addListener((observable, oldValue, newValue) -> {
                try {
                    field.set(instance, observable.getValue());
                    System.out.println(field.getName() + " = " + field.get(instance).toString());
                } catch (IllegalAccessException e) {
                    e.printStackTrace();
                }
            });
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }

        return checkBox;
    }
}
