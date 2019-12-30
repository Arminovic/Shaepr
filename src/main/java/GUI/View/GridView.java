package GUI.View;

import Data.*;
import GUI.DancerShape;
import GUI.MainScene;
import javafx.beans.value.ChangeListener;
import javafx.scene.SnapshotParameters;
import javafx.scene.image.Image;
import javafx.scene.layout.Pane;
import javafx.scene.shape.Line;

import java.util.Vector;

/**
 * Class for visualizing the data
 * there are no direct functionalities for interacting with the data implemented here
 */
public class GridView {

    protected Pane rootLayer;
    private CommonValues cv;

    private Pane gridLayer = new Pane();
    protected Vector<DancerShape> dancers = new Vector<>();
    final protected String maleID = "male";
    final protected String femaleID = "female";
    final protected String connectedID = "connected";

    public GridView() {
        cv = Data.getInstance().getCommonValues();

        rootLayer = new Pane();

        //rootLayer.setBackground(new Background(new BackgroundFill(Color.GREEN, CornerRadii.EMPTY, new Insets(0))));

        rootLayer.resize(cv.gridview_canvasWidth, cv.gridview_canvasHeight);
        ChangeListener<Number> handler = (a, b, c) -> rebuildView();
        rootLayer.widthProperty().addListener(handler);
        rootLayer.heightProperty().addListener(handler);
    }

    public Pane getRoot() {
        return rootLayer;
    }

    public double fieldWidth() {
        return paneWidth() / cv.gridview_xFields;
    }

    public double fieldHeight() {
        return paneHeight() / cv.gridview_yFields;
    }

    double paneWidth() {
        return rootLayer.getWidth();
    }

    double paneHeight() {
        return rootLayer.getHeight();
    }

    public double convertGridToPaneX(double x) {
        return (fieldWidth() * x) + (paneWidth() / 2) - cv.dancershape_arcRadius;
    }

    public double convertGridToPaneY(double y) {
        return (fieldHeight() * y) + (paneHeight() / 2) - cv.dancershape_arcRadius;
    }

    double convertPaneToGridX(double sceneX) {
        return (sceneX - (paneWidth() / 2)) / fieldWidth();
    }

    double convertPaneToGridY(double sceneY) {
        return (sceneY - (paneHeight() / 2)) / fieldHeight();
    }

    public void setMaleVisibility(boolean visible) {
        cv.gridview_maleVisible = visible;
        rebuildDancers();
    }

    public void setFemaleVisibility(boolean visible) {
        cv.gridview_femaleVisible = visible;
        rebuildDancers();
    }

    public void setGridVisibility(boolean visible) {
        cv.gridview_gridVisible = visible;
        buildGrid();
    }

    public void buildView() {
        buildView(MainScene.getSelectedShape());
    }

    public void buildView(Shape shape) {
        if (shape == null) {
            return;
        }
        dancers.clear();

        buildGrid();

        for (Pose p : shape.getPoses()) {
            DancerShape dancerShape = new DancerShape(p, this);
            if (p.isConnected()) {
                dancerShape.setId(connectedID);
            } else if (p.getDancer().getSex() == Data.sex.male) {
                dancerShape.setId(maleID);
            } else {
                dancerShape.setId(femaleID);
            }
            dancerShape.setOnMouseClicked(event -> {
                if (!dancerShape.isDragged()) {
                    MainScene.setSelectedDancer(dancerShape.getDancer());
                } else {
                    dancerShape.setDragged(false);
                }
            });
            dancers.add(dancerShape);
        }
        rebuildDancers();
    }

    private void buildGrid() {
        gridLayer.getChildren().clear();
        Double[] strokeDashArray = {3d, 3d};
        if (cv.gridview_gridVisible) {
            for (int i = 0; i <= cv.gridview_xFields; i++) {
                Line line = new Line(i * fieldWidth(), 0, i * fieldWidth(), paneHeight());
                if (i == cv.gridview_yFields / 2) {
                    line.setStrokeWidth(2.3d);
                } else {
                    line.getStrokeDashArray().addAll(strokeDashArray);
                }
                line.setId("line");
                gridLayer.getChildren().add(line);
            }
            for (int i = 0; i <= cv.gridview_yFields; i++) {
                Line line = new Line(0, i * fieldHeight(), paneWidth(), i * fieldHeight());
                if (i == cv.gridview_yFields / 2) {
                    line.setStrokeWidth(2.3d);
                } else {
                    line.getStrokeDashArray().addAll(strokeDashArray);
                }
                line.setId("line");
                gridLayer.getChildren().add(line);
            }
        }
    }

    public void rebuildView() {
        buildGrid();
        rebuildDancers();
    }

    public void rebuildDancers() {
        paintDancers();
        moveDancers();
    }

    public void paintDancers() {
        for (DancerShape n : dancers) {

            n.repaint();

            /*switch (n.getId()){
                case connectedID:
                    int femaleNumber = 0;
                    int maleNumber = 0;
                    for(Dancer d : n.getPose().getDancers()){
                        if (d.getSex().equals(Data.sex.female)){
                            femaleNumber++;
                        } else {
                            maleNumber++;
                        }
                    }
                    Color connectedColor = Color.color(
                            (cv.maledancer_color.getRed()*maleNumber + cv.femaledancer_color.getRed()*femaleNumber)/(maleNumber+femaleNumber),
                            (cv.maledancer_color.getGreen()*maleNumber + cv.femaledancer_color.getGreen()*femaleNumber)/(maleNumber+femaleNumber),
                            (cv.maledancer_color.getBlue()*maleNumber + cv.femaledancer_color.getBlue()*femaleNumber)/(maleNumber+femaleNumber)
                    );
                    n.setFill(connectedColor);
                    boolean male = false;
                    boolean female = false;
                    for(Dancer d : n.getDancers()){
                        if(d.getSex().equals(Data.sex.male)) male = true;
                        if(d.getSex().equals(Data.sex.female)) female = true;
                    }
                    male = Boolean.logicalAnd(male, cv.gridview_maleVisible);
                    female = Boolean.logicalAnd(female, cv.gridview_femaleVisible);
                    n.setVisible(Boolean.logicalOr(male, female));
                    break;
                case maleID:
                    n.setFill(cv.maledancer_color);
                    n.setVisible(cv.gridview_maleVisible);
                    break;
                case femaleID:
                    n.setFill(cv.femaledancer_color);
                    n.setVisible(cv.gridview_femaleVisible);
                    break;
            }
            if(MainScene.getSelectedDancer() != null) {
                if (n.hasDancer(MainScene.getSelectedDancer().getId())) {
                    n.setFill(cv.selecteddancer_color);
                }
            }*/
        }
    }

    public void moveDancers() {
        for (DancerShape n : dancers) {
            n.relocate();
        }
    }

    public Vector<DancerShape> getDancers() {
        return dancers;
    }

    public DancerShape getShapeOf(Dancer dancer) {
        //if(dancer == null) return null;
        for (DancerShape shape : dancers) {
            if (shape.hasDancer(dancer.getId())) return shape;
        }
        return null;
    }

    public Pane getGridLayer() {
        return gridLayer;
    }

    public Image takeSnapshot() {
        return rootLayer.snapshot(new SnapshotParameters(), null);
        /* write Image to file
        File file = new File(data.getSavePath()+"snapshot.png");
        try {
            ImageIO.write(SwingFXUtils.fromFXImage(image, null), "png", file);
        } catch (IOException e) {
            System.out.println("error printing snapshot");
        }
        */
    }
}
