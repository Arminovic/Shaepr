package GUI;

import Data.CommonValues;
import Data.Dancer;
import Data.Data;
import Data.Pose;
import Data.Sex;
import GUI.View.GridView;
import javafx.beans.property.DoubleProperty;
import javafx.scene.control.Label;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Arc;
import javafx.scene.shape.ArcType;
import javafx.scene.text.Font;

import java.util.UUID;
import java.util.Vector;

public class DancerShape extends StackPane implements DancerNode {

    public double orgSceneX, orgSceneY, orgX, orgY, offsetX, offsetY;

    private Pose pose;
    private CommonValues cv;
    private GridView parent;
    private DancerArc arcShape;
    private double strokeWidth = 5;
    private boolean dragged = false;

    final protected String maleID = "male";
    final protected String femaleID = "female";
    final protected String connectedID = "connected";

    private class DancerArc extends Arc {
        public void relocate(double x, double y) {
            setCenterX(cv.dancershape_arcRadius + 1 + (strokeWidth / 4));
            setCenterY(cv.dancershape_arcRadius + 1 + (strokeWidth / 4));
        }
    }

    public DancerShape(Pose p, GridView v) {
        cv = Data.getInstance().getCommonValues();
        this.setPrefSize(cv.dancershape_arcRadius * 2 + strokeWidth + 2, cv.dancershape_arcRadius * 2 + strokeWidth + 2);
        //this.setAlignment(Pos.CENTER);

        arcShape = new DancerArc();
        arcShape.setRadiusX(cv.dancershape_arcRadius);
        arcShape.setRadiusY(cv.dancershape_arcRadius);
        arcShape.setLength(240.0f);
        arcShape.setType(ArcType.CHORD);
        arcShape.setStrokeWidth(strokeWidth);
        pose = p;
        parent = v;
        p.connectShape(this);
        setOrientation();
        Label numberLabel = new Label(String.valueOf(p.getDancer().getNumber()));
        numberLabel.setFont(Font.font("Verdana", 20));
        numberLabel.setTextFill(Color.WHITE);
        this.getChildren().addAll(arcShape, numberLabel);
        //this.setBackground(new Background(new BackgroundFill(Color.RED, CornerRadii.EMPTY, Insets.EMPTY)));
    }

    public boolean isDragged() {
        return dragged;
    }

    public void setDragged(boolean value){
        dragged = value;
    }

    public void setMarked(boolean marked) {
        if (marked) {
            //System.out.println("mark");
            arcShape.setStroke(Color.DARKGRAY);

        } else {
            //System.out.println("unmark");
            arcShape.setStroke(Color.TRANSPARENT);
        }
    }

    /*public void setFill(Color c){
        arcShape.setFill(c);
    }*/

    public DoubleProperty startAngleProperty() {
        return arcShape.startAngleProperty();
    }

    public Pose getPose() {
        return pose;
    }

    public Dancer getDancer() {
        return pose.getDancer();
    }

    public Vector<Dancer> getDancers() {
        return pose.getDancers();
    }

    public boolean hasDancer(UUID id) {
        if (id == null) return false;
        for (Dancer d : pose.getDancers()) {
            if (d.getId().equals(id)) return true;
        }
        return false;
    }

    public boolean hasDancer(Dancer dancer) {
        if (dancer == null) return false;
        return hasDancer(dancer.getId());
    }

    public void setOrientation() {
        double r = (360.0 - 240.0) / 2;
        arcShape.setStartAngle(-90 + r + pose.getTheta());
        arcShape.setCenterX(this.getWidth() / 2);
        arcShape.setCenterY(this.getHeight() / 2);
    }

    public void relocate(double x, double y) {
        setLayoutX(parent.convertGridToPaneX(x));
        setLayoutY(parent.convertGridToPaneY(y));
    }

    public void relocate() {
        relocate(pose.getX(), pose.getY());
        arcShape.setLayoutX(0);
        arcShape.setLayoutY(0);
    }

    public void setPosition(double x, double y) {
        pose.setX(x);
        pose.setY(y);
    }

    public void repaint() {
        //System.out.println("repaint " + pose.getDancer().getName());
        if(getId() == null) {
            System.out.println("Dancershape.repaint.getID() is null");
            return;
        }
        switch (getId()) {
            case connectedID:
                int femaleNumber = 0;
                int maleNumber = 0;
                for (Dancer d : pose.getDancers()) {
                    if (d.getSex().equals(Sex.Female)) {
                        femaleNumber++;
                    } else if (d.getSex().equals(Sex.Male)) {
                        maleNumber++;
                    }
                }
                Color connectedColor = Color.color(
                        (cv.maledancer_color.getRed() * maleNumber + cv.femaledancer_color.getRed() * femaleNumber) / (maleNumber + femaleNumber),
                        (cv.maledancer_color.getGreen() * maleNumber + cv.femaledancer_color.getGreen() * femaleNumber) / (maleNumber + femaleNumber),
                        (cv.maledancer_color.getBlue() * maleNumber + cv.femaledancer_color.getBlue() * femaleNumber) / (maleNumber + femaleNumber)
                );
                arcShape.setFill(connectedColor);
                boolean male = false;
                boolean female = false;
                for (Dancer d : getDancers()) {
                    if (d.getSex().equals(Sex.Male)) male = true;
                    if (d.getSex().equals(Sex.Female)) female = true;
                }
                male = Boolean.logicalAnd(male, cv.gridview_maleVisible);
                female = Boolean.logicalAnd(female, cv.gridview_femaleVisible);
                setVisible(Boolean.logicalOr(male, female));
                break;
            case maleID:
                arcShape.setFill(cv.maledancer_color);
                setVisible(cv.gridview_maleVisible);
                break;
            case femaleID:
                arcShape.setFill(cv.femaledancer_color);
                setVisible(cv.gridview_femaleVisible);
                break;
        }
        if (hasDancer(MainScene.getSelectedDancer())) {
            arcShape.setFill(cv.selecteddancer_color);
        }
    }
}
