package GUI;

import Data.*;
import GUI.SubStages.DataManipulationDialogs;
import GUI.SubStages.DataValidator;
import GUI.SubStages.Settings;
import GUI.View.AnimateView;
import GUI.View.EditView;
import GUI.View.GridView;
import GUI.View.PainterView;
import Utils.Notifier;
import Utils.ToggleButtonTab;
import javafx.collections.FXCollections;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;
import javafx.embed.swing.SwingFXUtils;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.SnapshotParameters;
import javafx.scene.control.*;
import javafx.scene.image.ImageView;
import javafx.scene.image.WritableImage;
import javafx.scene.input.ClipboardContent;
import javafx.scene.input.Dragboard;
import javafx.scene.input.TransferMode;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.stage.FileChooser;
import javafx.util.Callback;

import javax.imageio.ImageIO;
import java.io.File;
import java.io.IOException;
import java.util.Vector;

public class MainScene {

    private static Shape selectedShape;
    private static Dancer selectedDancer;
    private static GridView activeView;

    public static Shape getSelectedShape() {
        return selectedShape;
    }

    public static void setSelectedShape(Shape value){
        selectedShape = value;
        if(activeView != null) activeView.buildView();
    }

    public static Dancer getSelectedDancer() {
        return selectedDancer;
    }

    public static void setSelectedDancer(Dancer value){
        Dancer oldSelect = selectedDancer;
        selectedDancer = value;
        if(oldSelect != null
                && activeView.getShapeOf(oldSelect) != null) activeView.getShapeOf(oldSelect).repaint();
        if(selectedDancer != null
                && activeView.getShapeOf(selectedDancer) != null) activeView.getShapeOf(selectedDancer).repaint();
    }

    public static GridView getActiveView() {
        return activeView;
    }

    // data objects
    private Data data;
    private CommonValues cv;

    // scene objects
    private Scene scene;
    private BorderPane border;
    private ObservableList<Dancer> dancers;
    private ObservableList<Shape> shapes;
    private ListView<Dancer> dancerList;
    private ListView<Shape> shapeList;
    private EditView editView;
    private AnimateView animateView;

    public MainScene(){
        data = Data.getInstance();
        cv = data.getCommonValues();
        //initComponents();
        //buildComponents();
    }

    private void initComponents(){
        editView = new EditView(data);
        animateView = new AnimateView();

        // checking and fixing the absence of data, to avoid Nullpointer Exceptions
        if (data.getActiveSequence() == null) {
            data.addSequence(new Shapesequence());
        }

        // Observable Lists wrapping around our Data, to make it accessible to our GUI and interactive
        dancers = FXCollections.observableList(data.getActiveSequence().getDancers());
        shapes = FXCollections.observableList(data.getActiveSequence().getShapes());

        // sort the dancers for their sex
        dancers.sort((o1, o2) -> {
            if (o1.getSex() == o2.getSex()) {
                if (o1.getNumber() < o2.getNumber()) {
                    return -1;
                } else if (o1.getNumber() > o2.getNumber()) {
                    return 1;
                }
                return 0;
            } else if (o1.getSex() == Data.sex.male) {
                return -1;
            } else {
                return 1;
            }
        });

        dancerList = new ListView<>(dancers);
        shapeList = new ListView<>(shapes);

        // the CellFactories define the behavior of the Listcells. Here we define, that on a contextclick, the
        // data manipulation dialog should pop up and the drag'n'drop feature is implemented
        dancerList.setCellFactory(new Callback<>() {
            @Override
            public ListCell<Dancer> call(ListView<Dancer> dancerList) {

                return new ListCell<Dancer>() {
                    @Override
                    public void updateItem(Dancer item, boolean empty) {
                        super.updateItem(item, empty);

                        if (empty || item == null) {
                            setText(null);
                            setGraphic(null);
                        } else {

                            Label label = new Label(item.getName() + " Nr:" + item.getNumber());
                            label.setId("dancerLabel");
                            //label.setFont(Font.font(20));
                            label.setPadding(new Insets(10));
                            label.setMaxWidth(Double.MAX_VALUE);
                            item.connectField(label.textProperty());
                            //label.setBorder(GUIHelper.blueBorder);
                            label.setOnContextMenuRequested(event -> {
                                DataManipulationDialogs.showEditDancerDialog(event.getScreenX(), event.getScreenY(), item);
                            });

                            this.setOnDragDetected(event -> {
                                /* drag was detected, start drag-and-drop gesture*/
                                //System.out.println("onDragDetected");

                                /* allow any transfer mode */
                                Dragboard db = label.getScene().startDragAndDrop(TransferMode.ANY);

                                /* put a string on dragboard */
                                ClipboardContent content = new ClipboardContent();
                                //content.putString(item.getName());
                                content.putString(item.getId().toString());
                                db.setContent(content);

                                //label.startFullDrag();
                                event.consume();
                            });

                            this.setOnMouseEntered(event -> {
                                //selectedDancer = item;
                                if(activeView.getShapeOf(item) != null) activeView.getShapeOf(item).setMarked(true);
                                //activeView.paintDancers();
                            });

                            this.setOnMouseExited(event -> {
                                //selectedDancer = null;
                                if(activeView.getShapeOf(item) != null) activeView.getShapeOf(item).setMarked(false);
                            });

                            setGraphic(label);
                        }
                    }
                };
            }
        });
        shapeList.setCellFactory(new Callback<>() {
            PainterView sample = new PainterView();

            @Override
            public ListCell<Shape> call(ListView<Shape> shapeList) {

                return new ListCell<Shape>() {
                    @Override
                    public void updateItem(Shape item, boolean empty) {
                        super.updateItem(item, empty);

                        if (empty || item == null) {
                            setText(null);
                            setGraphic(null);
                        } else {
                            sample.buildView(item);
                            ImageView imageView = new ImageView(sample.takeSnapshot());
                            imageView.setFitWidth(80);
                            imageView.setFitHeight(80);

                            Label label = new Label(item.getName());
                            label.setFont(Font.font(20));
                            label.setPadding(new Insets(10));
                            item.connectField(label.textProperty());

                            HBox box = new HBox(imageView, label);
                            box.setAlignment(Pos.CENTER_LEFT);
                            box.setOnContextMenuRequested(event -> {
                                DataManipulationDialogs.showEditShapeDialog(event.getScreenX(), event.getScreenY(), item);
                            });
                            setGraphic(box);
                        }
                    }
                };
            }
        });

        // add Listeners to the selectedItemProperties, to update the static variables everytime the selection changes
        dancerList.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {

            setSelectedDancer(newValue);
            //System.out.println("newValue = " + newValue.getName());
        });
        shapeList.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            setSelectedShape(newValue);
            //System.out.println("newValue = " + newValue.getName());
        });

        // select the first elements by default, so there are no problems with other parts of the code
        //dancerList.getSelectionModel().select(0);
        shapeList.getSelectionModel().select(0);
        activeView = editView;

        // add a changeListener to the lists to automatically select newly added shapes
        shapes.addListener((ListChangeListener<Shape>) c -> shapeList.getSelectionModel().selectLast());
        dancers.addListener((ListChangeListener<Dancer>) c -> dancerList.getSelectionModel().selectLast());
    }

    private void buildComponents(){
        border = new BorderPane();

        border.setLeft(makeLeftView());

        //border.setRight(makeRightView());

        border.setTop(makeTopBar());

        border.setCenter(makeCenterView());

        //border.setBottom(makeBottomView());
    }

    public Scene makeScene(){
        initComponents();
        buildComponents();

        scene = new Scene(border);

        return scene;
    }

    private Node makeTopBar() {

        // View
        CheckMenuItem gridCheck = new CheckMenuItem("grid");
        gridCheck.setSelected(cv.gridview_gridVisible);
        gridCheck.setOnAction(event -> {
            if (gridCheck.isSelected()) {
                activeView.setGridVisibility(true);
            } else {
                activeView.setGridVisibility(false);
            }
        });

        CheckMenuItem femaleCheck = new CheckMenuItem("female");
        femaleCheck.setSelected(cv.gridview_femaleVisible);
        femaleCheck.setOnAction(event -> {
            if (femaleCheck.isSelected()) {
                activeView.setFemaleVisibility(true);
            } else {
                activeView.setFemaleVisibility(false);
            }
        });

        CheckMenuItem maleCheck = new CheckMenuItem("male");
        maleCheck.setSelected(cv.gridview_maleVisible);
        maleCheck.setOnAction(event -> {
            if (maleCheck.isSelected()) {
                activeView.setMaleVisibility(true);
            } else {
                activeView.setMaleVisibility(false);
            }
        });
        Menu view = new Menu("View");
        view.getItems().addAll(gridCheck, femaleCheck, maleCheck);

        // file
        MenuItem newSequence = new MenuItem("new Sequence");
        newSequence.setOnAction(event -> {
            System.out.println(data.getActiveSequence().getId());
            data.newSequence();
            System.out.println(data.getActiveSequence().getId());
            //dancers.clear();
            System.out.println("data.getActiveSequence().getDancers().size() = " + data.getActiveSequence().getDancers().size());
            System.out.println("data.getActiveSequence().getShapes().size() = " + data.getActiveSequence().getShapes().size());

            //dancers.clear();
            System.out.println("dancers.size() = " + dancers.size());
            System.out.println("dancerList.getItems().size() = " + dancerList.getItems().size());
            dancers = FXCollections.observableList(data.getActiveSequence().getDancers());
            System.out.println("dancers.size() = " + dancers.size());
            System.out.println("dancerList.getItems().size() = " + dancerList.getItems().size());

            dancerList = new ListView<Dancer>(dancers);
            dancerList.refresh();
            System.out.println("dancerList.getItems().size() = " + dancerList.getItems().size());


            //dancers.addAll(data.getActiveSequence().getDancers());
            //dancerList.refresh();
            //shapes.clear();
            shapes = FXCollections.observableList(data.getActiveSequence().getShapes());
            //shapes.addAll(data.getActiveSequence().getShapes());
            //shapeList.refresh();
            activeView.buildView();
        });

        MenuItem saveData = new MenuItem("save");
        saveData.setOnAction(event -> {
            data.saveData();
            Notifier.notify("Data saved");
        });

        MenuItem saveTo = new MenuItem("save to File");
        saveTo.setOnAction(event -> {
            FileChooser fileChooser = new FileChooser();
            fileChooser.setInitialDirectory(data.getSaveDir());
            File saveFile = fileChooser.showSaveDialog(scene.getWindow());
            if (saveFile != null) {
                if (data.saveTo(saveFile)) {
                    Notifier.notify("saved successfully");
                } else {
                    Notifier.warn("save failed");
                }
            }
        });

        MenuItem loadData = new MenuItem("load");
        loadData.setOnAction(event -> {
            data.loadSaveData();
            Notifier.notify("Data loaded");
        });

        MenuItem loadFrom = new MenuItem("load from");
        loadFrom.setOnAction(event -> {
            FileChooser fileChooser = new FileChooser();
            fileChooser.setInitialDirectory(data.getSaveDir());
            File saveFile = fileChooser.showOpenDialog(scene.getWindow());
            if (saveFile != null) {
                if (data.loadFromFile(saveFile)) {
                    Notifier.notify("loaded successfully");
                } else {
                    Notifier.warn("load failed");
                }
            }
        });

        MenuItem snaphshot = new MenuItem("take snapshot");
        snaphshot.setOnAction(event -> {
            WritableImage image = activeView.getRoot().snapshot(new SnapshotParameters(), null);
            File file = new File(data.getSavePath() + "snapshot.png");
            try {
                ImageIO.write(SwingFXUtils.fromFXImage(image, null), "png", file);
            } catch (IOException e) {
                System.out.println("error printing snapshot");
            }
        });
        Menu file = new Menu("File");

        MenuItem dataValidation = new MenuItem("validate data");
        dataValidation.setOnAction(event -> DataValidator.start(data));

        file.getItems().addAll(newSequence, saveData, saveTo, loadData, loadFrom, snaphshot, dataValidation);

        // settings
        MenuItem openSettings = new MenuItem("open");
        openSettings.setOnAction(event -> new Settings().openSettings());
        Menu settings = new Menu("Settings");
        settings.getItems().add(openSettings);

        // controls
        HBox animationControlBox = new HBox();
        animationControlBox.setAlignment(Pos.CENTER_LEFT);
        animationControlBox.setVisible(false);
        HBox.setHgrow(animationControlBox, Priority.ALWAYS);
        animationControlBox.setMaxWidth(Double.MAX_VALUE);
        animationControlBox.setSpacing(5);
        animationControlBox.setFillHeight(true);

        Button startButton = new Button("start");
        Button stopButton = new Button("stop/resume");
        Slider speedSlider = new Slider();
        speedSlider.setMin(-4);
        speedSlider.setMax(4);
        speedSlider.setValue(1);
        speedSlider.setShowTickLabels(true);
        speedSlider.setMajorTickUnit(0.5);
        speedSlider.setOnMouseDragged(event -> animateView.setAnimationRate(speedSlider.getValue()));
        HBox.setHgrow(speedSlider, Priority.ALWAYS);
        shapeList.setMaxWidth(Double.MAX_VALUE);

        animationControlBox.getChildren().addAll(startButton, stopButton, speedSlider);

        startButton.setOnAction(event -> {
            //SubList mit den Shapes erstellen, beginnend ab der momentan selektierten Shape, bis zum Schluss
            if (!shapes.isEmpty()) {
                Vector<Shape> shapeSubList = new Vector<Shape>(shapes.subList(shapes.indexOf(shapeList.getSelectionModel().getSelectedItem()), shapes.size()));
                //System.out.println(shapeSubList.toString());
                animateView.startAnimate(shapeSubList);
            }
        });
        stopButton.setOnAction(event -> animateView.toggleAnimate());

        Button buttonAnimate = new Button("animate");
        buttonAnimate.setOnAction(event -> {
            if (border.getCenter().getId().equals("edit")) {
                buttonAnimate.setText("edit");
                animationControlBox.setVisible(true);
                activeView = animateView;
            } else if (border.getCenter().getId().equals("animate")) {
                buttonAnimate.setText("animate");
                animationControlBox.setVisible(false);
                activeView = editView;
            }
            border.setCenter(activeView.getRoot());
            activeView.buildView();
        });

        ToggleButton toggleConnectButton = new ToggleButton("connect Dancers");
        toggleConnectButton.setSelected(cv.editView_connectDrag);
        toggleConnectButton.setOnAction(event -> {
            cv.editView_connectDrag = toggleConnectButton.isSelected();
            //System.out.println(cv.editView_connectDrag);
        });

        MenuBar bar = new MenuBar(file, view, settings);

        HBox box = new HBox(bar, toggleConnectButton, buttonAnimate, animationControlBox);
        box.setSpacing(5);
        box.setFillHeight(true);
        box.setAlignment(Pos.CENTER_LEFT);
        return box;
    }

    private Node makeRightView() {
        return new VBox();
    }

    private Node makeLeftView() {
        ToggleGroup group = new ToggleGroup();
        VBox leftBox = new VBox();

        ToggleButtonTab dancerTab = new ToggleButtonTab("dancers");
        dancerTab.setMaxWidth(Double.MAX_VALUE);
        dancerTab.setToggleGroup(group);
        dancerTab.setSelected(true);

        ToggleButtonTab shapeTab = new ToggleButtonTab("Shapes");
        shapeTab.setMaxWidth(Double.MAX_VALUE);
        shapeTab.setToggleGroup(group);

        HBox ToggleBox = new HBox();
        ToggleBox.setAlignment(Pos.CENTER);
        ToggleBox.getChildren().addAll(dancerTab, shapeTab);

        HBox.setHgrow(dancerTab, Priority.ALWAYS);
        HBox.setHgrow(shapeTab, Priority.ALWAYS);

        dancerList.setEditable(true);
        dancerList.setMaxHeight(Double.MAX_VALUE);
        VBox.setVgrow(dancerList, Priority.ALWAYS);
        dancerList.setPadding(new Insets(10));
        /*dancerList.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            //System.out.println("repaint"+ oldValue + ", "+newValue);
            //selectedDancer = newValue;
            //if(oldValue != null) activeView.getShapeOf(oldValue).repaint();
            //if(newValue != null) activeView.getShapeOf(newValue).repaint();
            setSelectedDancer(newValue);
        });*/

        shapeList.setEditable(true);
        shapeList.setMaxHeight(Double.MAX_VALUE);
        VBox.setVgrow(shapeList, Priority.ALWAYS);
        shapeList.setPadding(new Insets(10));
        /*shapeList.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            activeView.buildView();
            //data.getActiveSequence().setActiveShape(shapeList.getSelectionModel().getSelectedItem());
            //data.getActiveSequence().setActiveShape(newValue);
        });*/

        dancerTab.setOnAction(e -> leftBox.getChildren().set(1, dancerList));
        shapeTab.setOnAction(e -> leftBox.getChildren().set(1, shapeList));

        Button addButton = new Button("add");
        addButton.setOnAction(event -> {
            if (dancerTab.isSelected()) {
                DataManipulationDialogs.showAddDancerDialog(dancers);
            } else if (shapeTab.isSelected()) {
                DataManipulationDialogs.showAddShapeDialog(shapes);
            }
        });
        addButton.setMaxWidth(Double.MAX_VALUE);

        Button removeButton = new Button("remove");
        removeButton.setOnAction(event -> {
            if (dancerTab.isSelected()) {
                dancers.remove(selectedDancer);
            } else if (shapeTab.isSelected()) {
                shapes.remove(getSelectedShape());
            }
        });
        removeButton.setMaxWidth(Double.MAX_VALUE);

        HBox.setHgrow(addButton, Priority.ALWAYS);
        HBox.setHgrow(removeButton, Priority.ALWAYS);
        HBox addNremoveBox = new HBox(addButton, removeButton);

        leftBox.getChildren().addAll(ToggleBox, dancerList, addNremoveBox);
        return leftBox;
    }

    private Node makeCenterView() {
        activeView.buildView();
        return activeView.getRoot();
    }

    private Node makeBottomView() {
        Label l = Notifier.getLabel();
        l.setFont(Font.font(null, FontWeight.BOLD, 16.0));
        l.setAlignment(Pos.CENTER);
        l.prefWidthProperty().bind(border.widthProperty());
        return l;
    }
}
