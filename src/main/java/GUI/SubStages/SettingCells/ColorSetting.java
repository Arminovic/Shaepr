package GUI.SubStages.SettingCells;

import javafx.scene.Node;
import javafx.scene.control.ColorPicker;
import javafx.scene.layout.Background;
import javafx.scene.paint.Color;

import java.lang.reflect.Field;

public class ColorSetting extends SettingCell {


    public String getIdentifier() {
        return Color.class.toString();
    }

    public Node makeNode(Field field, Object instance) {
        ColorPicker colorPicker = new ColorPicker();
        colorPicker.setBackground(Background.EMPTY);

        try {
            colorPicker.setValue((Color) field.get(instance));
            colorPicker.setOnAction(a -> {
                try {
                    field.set(instance, colorPicker.getValue());
                    System.out.println(field.getName() + " = " + field.get(instance).toString());
                } catch (IllegalAccessException e) {
                    e.printStackTrace();
                }
            });
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
        return colorPicker;
    }
}

