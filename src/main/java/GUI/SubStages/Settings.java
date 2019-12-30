package GUI.SubStages;

import Data.CommonValues;
import Data.Data;
import GUI.SubStages.SettingCells.*;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.Region;
import javafx.stage.Stage;
import javafx.util.Callback;

import java.lang.reflect.Field;
import java.util.Collections;
import java.util.Vector;

public class Settings {

    private Stage stage;
    private CommonValues cv = Data.getInstance().getCommonValues();
    //private double maxCellWidth = 0;
    private static Vector<SettingCell> availableCellz;
    private SettingCell[] availableCells;
    private SettingCell defaultCell;

    //public static void registerCellType()

    public Settings() {
        stage = new Stage();
        stage.setTitle("Settings");
        ListView list = makeSettingsTable();
        list.setPrefWidth(450);
        //stage.setWidth(list.getPrefWidth());
        stage.setScene(new Scene(list));

        // List of available Settingtypes
        availableCells = new SettingCell[]{
                //new SettingCell(),
                new BooleanSetting(),
                new IntegerSetting(),
                new DoubleSetting(),
                new ColorSetting(),
                new DecimalFormatSetting()
        };
        defaultCell = new SettingCell();
    }

    public void openSettings() {
        stage.show();
    }



    private ListView makeSettingsTable() {
        ObservableList<Field> fields = FXCollections.observableList(new Vector<>());
        Collections.addAll(fields, cv.getClass().getFields());
        ListView<Field> settingsList = new ListView<>(fields);
        settingsList.setMaxWidth(Double.MAX_VALUE);

        settingsList.setCellFactory(new Callback<>() {
            @Override
            public ListCell<Field> call(ListView<Field> list) {

                return new ListCell<>() {
                    @Override
                    public void updateItem(Field item, boolean empty) {
                        super.updateItem(item, empty);

                        if (empty || item == null) {
                            setText(null);
                            setGraphic(null);
                        } else {
                            Label settingName = new Label(item.getName());

                            Node settingValue = null;
                            for (SettingCell cell : availableCells) {
                                //System.out.println("celltype = "+cell.getIdentifier()+", itemtype = "+item.getType().toString());
                                if (item.getType().toString().equals(cell.getIdentifier())) {
                                    settingValue = cell.makeNode(item, cv);
                                }
                            }
                            if (settingValue == null) {
                                settingValue = defaultCell.makeNode(item, cv);
                            }

                            Region spacer = new Region();
                            HBox.setHgrow(spacer, Priority.ALWAYS);
                            HBox box = new HBox(settingName, spacer, settingValue);
                            box.setPadding(new Insets(3));

                            //styling
                            box.setAlignment(Pos.CENTER_LEFT);
                            settingName.setAlignment(Pos.CENTER_LEFT);
                            settingName.setMaxWidth(Double.MAX_VALUE);
                            HBox.setHgrow(settingValue, Priority.ALWAYS);
                            //box.setBorder(GUIHelper.redBorder);

                            setGraphic(box);
                        }
                    }
                };
            }
        });
        return settingsList;
    }
}
