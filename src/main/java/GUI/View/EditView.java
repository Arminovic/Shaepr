package GUI.View;

import Data.*;
import GUI.MainScene;
import GUI.SubStages.DataManipulationDialogs;
import GUI.DancerShape;
import Utils.Formatter;
import javafx.geometry.Insets;
import javafx.scene.control.Label;
import javafx.scene.input.*;
import javafx.scene.layout.*;
import javafx.scene.shape.Line;

import java.util.UUID;

/**
 * Class for editing the data in a visual way
 */
public class EditView extends GridView {

    //private static Pane rootLayer;
    private static CommonValues cv;
    private Data data;

    private Label crosshairLabel = new Label();
    private Line crosshairX = new Line();
    private Line crosshairY = new Line();
    private boolean onDancer = false;

    private Pose dragPose;
    private Pose oldPose;
    private DancerShape connectCandidate;

    public EditView(Data d) {
        cv = Data.getInstance().getCommonValues();
        data = d;
        //rootLayer.setCursor(Cursor.NONE);
        rootLayer.setId("edit");

        rootLayer.setOnDragOver(event -> {
            System.out.println("root over");
            if (!cv.editView_connectDrag &&
                    event.getGestureSource() != rootLayer &&
                    event.getDragboard().hasString()) {
                event.acceptTransferModes(TransferMode.ANY);
                dragPose.setX(convertPaneToGridX(event.getX()));
                dragPose.setY(convertPaneToGridY(event.getY()));
            }
            event.consume();
        });

        rootLayer.setOnDragEntered(event -> {
            System.out.println("root enter");
            if (!cv.editView_connectDrag &&
                    event.getGestureSource() != rootLayer &&
                    event.getDragboard().hasString()) {
                Dancer draggedDancer = data.getActiveSequence().getDancer(UUID.fromString(event.getDragboard().getString()));
                dragPose = MainScene.getSelectedShape().getPoseOf(draggedDancer);
                if (dragPose == null) {
                    dragPose = new Pose(draggedDancer,
                            convertPaneToGridX(event.getX()),
                            convertPaneToGridY(event.getY()),
                            0);
                    MainScene.getSelectedShape().setPose(dragPose);
                    buildView();
                } else {
                    oldPose = new Pose(null, dragPose.getX(), dragPose.getY(), 0);
                    dragPose.setX(convertPaneToGridX(event.getX()));
                    dragPose.setY(convertPaneToGridY(event.getY()));
                }
            }
            event.consume();
        });

        rootLayer.setOnDragExited(event -> {
            System.out.println("root exited");
            if (dragPose == null) {
                oldPose = null;
            } else {
                if (oldPose == null) {
                    MainScene.getSelectedShape().removePose(dragPose);
                    buildView();
                } else {
                    dragPose.setX(oldPose.getX());
                    dragPose.setY(oldPose.getY());
                    dragPose = null;
                    oldPose = null;
                }
            }
            event.consume();
        });

        rootLayer.setOnDragDropped(event -> {
            System.out.println("root dropped");
            dragPose = null;
            event.consume();
        });

    }

    public void buildView() {
        rootLayer.getChildren().clear();

        super.buildView();

        double labelInsets = -5;
        crosshairLabel.setBackground(new Background(
                new BackgroundFill(cv.crosshairLabel_backgroundcolor,
                        new CornerRadii(10), new Insets(labelInsets))));
        crosshairLabel.getInsets();
        crosshairLabel.setTextFill(cv.crosshairLabel_fontcolor);

        crosshairX.setStroke(cv.crosshair_color);
        crosshairY.setStroke(cv.crosshair_color);
        crosshairX.setStartY(0);
        crosshairX.setEndY(paneHeight());
        crosshairY.setStartX(0);
        crosshairY.setEndX(paneWidth());

        rootLayer.setOnMouseMoved(event -> {
            crosshairX.setStartX(event.getX());
            crosshairX.setEndX(event.getX());
            crosshairY.setStartY(event.getY());
            crosshairY.setEndY(event.getY());
            if (!onDancer) {
                crosshairLabel.setText(Formatter.coordinates(convertPaneToGridX(event.getX()), convertPaneToGridY(event.getY())));

                double newX = event.getX() + 10;
                double newY = event.getY() - 25;

                if (newY + labelInsets <= 0) {
                    newY = 0 - labelInsets;
                }
                if (newX + crosshairLabel.getWidth() - labelInsets >= rootLayer.getWidth()) {
                    newX = rootLayer.getWidth() - crosshairLabel.getWidth() + labelInsets;
                }

                crosshairLabel.relocate(newX, newY);
            }
        });

        for (DancerShape dancerShape : getDancers()) {
            dancerShape.setOnMouseEntered(event -> {
                onDancer = true;

                crosshairLabel.setText(dancerShape.getPose().getDancerNamesConcat(", ") + " " + dancerShape.getPose().getPoseRepresentation());
                crosshairLabel.relocate(dancerShape.getLayoutX() + cv.dancershape_arcRadius + 5, dancerShape.getLayoutY() - cv.dancershape_arcRadius - (crosshairLabel.getHeight() / 2) - 5);
            });

            dancerShape.setOnMouseExited(event -> {
                onDancer = false;
            });

            dancerShape.setOnMousePressed(event -> {
                if (!cv.editView_connectDrag) {
                    dancerShape.orgSceneX = event.getSceneX();
                    dancerShape.orgSceneY = event.getSceneY();
                    dancerShape.orgX = dancerShape.getLayoutX();
                    dancerShape.orgY = dancerShape.getLayoutY();
                }
            });

            dancerShape.setOnMouseDragged(event -> {

                //System.out.println("dragged");
                if (!cv.editView_connectDrag) {
                    dancerShape.setDragged(true);
                    dancerShape.offsetX = event.getSceneX() - dancerShape.orgSceneX;
                    dancerShape.offsetY = event.getSceneY() - dancerShape.orgSceneY;
                    double x = dancerShape.orgX + dancerShape.offsetX;
                    double y = dancerShape.orgY + dancerShape.offsetY;

                    dancerShape.getPose().setXY(convertPaneToGridX(x), convertPaneToGridY(y));

                    x = dancerShape.getLayoutX() + cv.dancershape_arcRadius;
                    y = dancerShape.getLayoutY() + cv.dancershape_arcRadius;
                    crosshairX.setStartX(x);
                    crosshairX.setEndX(x);
                    crosshairY.setStartY(y);
                    crosshairY.setEndY(y);
                    crosshairLabel.relocate(
                            x + cv.dancershape_arcRadius + 5,
                            y - cv.dancershape_arcRadius - (crosshairLabel.getHeight() / 2) - 5);
                    crosshairLabel.setText(Formatter.coordinates(convertPaneToGridX(x), convertPaneToGridY(y)));
                }
            });

            dancerShape.setOnDragDetected(event -> {
                //System.out.println("drag detected");
                if(cv.editView_connectDrag) {
                    dancerShape.startFullDrag();
                }
            });

            dancerShape.setOnMouseDragEntered(event -> {
                //System.out.println("drag entered");
                dancerShape.setMarked(true);
                connectCandidate = dancerShape;
            });

            dancerShape.setOnMouseDragExited(event -> {
                //System.out.println("drag exited");
                dancerShape.setMarked(false);
                connectCandidate = null;
            });

            dancerShape.setOnMouseReleased(event -> {
                if (cv.editView_connectDrag) {
                    //System.out.println("connect release");
                    if(connectCandidate != null && connectCandidate != dancerShape){
                        connectCandidate.getPose().addDancer(dancerShape.getDancers().toArray(new Dancer[0]));
                        MainScene.getSelectedShape().removePose(dancerShape.getPose());
                        buildView();
                    }
                }
                connectCandidate = null;
                dancerShape.setMarked(false);
            });

            dancerShape.setOnContextMenuRequested(event -> {
                DataManipulationDialogs.showEditDancerDialog(event.getScreenX(), event.getScreenY(), dancerShape.getDancer());
            });



            dancerShape.setOnDragOver(event -> {
                if (event.getGestureSource() != rootLayer &&
                        event.getDragboard().hasString()) {
                    event.acceptTransferModes(TransferMode.ANY);
                }
                event.consume();
            });

            dancerShape.setOnDragEntered(event -> {
                if (event.getGestureSource() != rootLayer &&
                        event.getDragboard().hasString()) {
                    dancerShape.setMarked(true);
                }
                event.consume();
            });

            dancerShape.setOnDragExited(event -> {
                dancerShape.setMarked(false);
                event.consume();
            });

            dancerShape.setOnDragDropped(event -> {
                Dancer add = data.getActiveSequence().getDancer(UUID.fromString(event.getDragboard().getString()));

                //MainScene.getSelectedShape().removePoseOf(add);
                //dancerShape.getPose().addDancer(data.getActiveSequence().getDancer(UUID.fromString(event.getDragboard().getString())));

                dancerShape.setMarked(false);
                MainScene.getSelectedShape().addDancerToPoseOf(add, dancerShape.getDancer());

                buildView();
                event.consume();
            });

        }
        rebuildDancers();
        rootLayer.getChildren().addAll(getGridLayer(), crosshairX, crosshairY);
        rootLayer.getChildren().addAll(getDancers().toArray(new DancerShape[0]));
        rootLayer.getChildren().addAll(crosshairLabel);
    }

    public void rebuildView() {
        crosshairY.setEndX(paneWidth());
        crosshairX.setEndY(paneHeight());
        super.rebuildView();
    }
}
