package GUI.SubStages.SettingCells;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.Node;
import javafx.scene.control.ComboBox;

import java.lang.reflect.Field;
import java.text.DecimalFormat;
import java.util.Vector;

public class DecimalFormatSetting extends SettingCell {

    public String getIdentifier() {
        return DecimalFormat.class.toString();
    }

    public Node makeNode(Field field, Object instance) {

        ObservableList<String> availableFormats = FXCollections.observableList(new Vector<>());
        availableFormats.addAll("0.00", "0.0", "0");
        ComboBox<String> comboBox = new ComboBox<>(availableFormats);

        try {
            comboBox.getSelectionModel().select(((DecimalFormat)field.get(instance)).toPattern());
            comboBox.setOnAction(a -> {
                try {
                    field.set(instance, new DecimalFormat(comboBox.getValue()));
                    System.out.println(field.getName() + " = " + field.get(instance).toString());
                } catch (IllegalAccessException e) {
                    e.printStackTrace();
                }

            });
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
        return comboBox;
    }
}