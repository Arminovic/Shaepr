package Data;

import GUI.DancerNode;
import GUI.DancerShape;
import Utils.Formatter;

import java.util.UUID;
import java.util.Vector;

public class Pose {

    private double x;
    private double y;
    private double theta;
    private Vector<Dancer> dancers = new Vector<>();
    private Vector<DancerNode> connectedShapes = new Vector<>();
    private UUID id = UUID.randomUUID();

    public Pose(Dancer d){
        new Pose(d, 0, 0, 0);
    }

    public Pose(Dancer d, double x, double y, double theta){
        this.dancers.add(d);
        define(x, y, theta, false);
    }

    void define(double x, double y, double theta){
        define(x, y, theta, true);
    }

    void define(double x, double y, double theta, boolean snapping){
        double newX = x, newY = y;
        if (Data.getInstance().getCommonValues().dancershape_snapping && snapping) {
            double delta = Data.getInstance().getCommonValues().dancershape_snappingprecision;
            newX = ((int)((newX / delta) + (1 + (newX / Math.abs(newX)) * 0.5))) * delta;
            newY = ((int)((newY / delta) + (1 + (newY / Math.abs(newY)) * 0.5))) * delta;
        }

        this.x = newX;
        this.y = newY;

        double normalizer = theta;
        while(normalizer < 0){
            normalizer += 360;
        }
        while(normalizer >= 360){
            normalizer -= 360;
        }

        this.theta = normalizer;

        for(DancerNode s : connectedShapes){
            s.relocate();
            s.setOrientation();
        }
    }

    public boolean isConnected(){
        return dancers.size() > 1;
    }


    //--Dancer--
    public void addDancer(Dancer... d){
        for(Dancer dancer : d) {
            if (!dancers.contains(dancer))
                dancers.add(dancer);
        }
    }

    public void removeDancer(Dancer d){
        dancers.remove(d);
    }

    public Dancer getDancer() {
        return dancers.firstElement();
    }

    public Dancer getDancer(String name){
        for(Dancer d : dancers){
            if(d.getName().equals(name)) return d;
        }
        return null;
    }

    public Dancer getDancer(UUID id){
        for(Dancer d : dancers){
            if(d.getId().equals(id)) return d;
        }
        return null;
    }

    public Vector<Dancer> getDancers(){
        return dancers;
    }

    public boolean hasDancer(Dancer dancer){
        for(Dancer d : dancers){
            if(d.getId().equals(dancer.getId())) return true;
        }
        return false;
    }

    public boolean hasDancer(UUID dancer){
        for(Dancer d : dancers){
            if(d.getId().equals(dancer)) return true;
        }
        return false;
    }

    public String getDancerNamesConcat(String delimiter){
        StringBuilder sb = new StringBuilder();
        String[] names = getDancerNames();
        sb.append(names[0]);
        for(int i = 1; i < names.length; i++){
            sb.append(delimiter).append(names[i]);
        }
        return sb.toString();
    }

    public String[] getDancerNames(){
        Vector<String> names = new Vector<>();
        for(Dancer d : dancers){
            names.add(d.getName());
        }
        return names.toArray(new String[0]);
    }

    public Vector<Dancer> disconnectDancers(){
        Dancer first = dancers.firstElement();
        Vector<Dancer> disconnectedDancers = new Vector<>();
        for(int pointer = 1; pointer < dancers.size(); pointer++){
            disconnectedDancers.add(dancers.get(pointer));
        }
        dancers.removeAllElements();
        dancers.add(first);
        return disconnectedDancers;
    }


    //--X--
    public double getX() {
        return x;
    }

    public void setX(double x, boolean snapping) {
        define(x, y, theta, snapping);
    }

    public void setX(double x) {
        setX(x, true);
    }

    public void moveX(double x, boolean snapping) {
        setX(this.x+x, snapping);
    }
    public void moveX(double x) {
        moveX(x, true);
    }


    //--Y--
    public double getY() {
        return y;
    }

    public void setY(double y, boolean snapping) {
        define(x, y, theta, snapping);
    }

    public void setY(double y) {
        setY(y, true);
    }

    public void moveY(double y, boolean snapping) {
        setY(this.y+y, snapping);
    }

    public void moveY(double y) {
        moveY(y, true);
    }


    //--XY--
    public void setXY(double x, double y, boolean snapping){
        define(x, y, theta, snapping);
    }

    public void setXY(double x, double y){
        setXY(x, y, true);
    }

    public void moveXY(double x, double y, boolean snapping){
        setXY(this.x+x, this.y+y, snapping);
    }

    public void moveXY(double x, double y){
        moveXY(x, y, true);
    }


    //--Theta--
    public double getTheta() {
        return theta;
    }

    public void setTheta(double theta, boolean snapping) {
        define(x, y, theta, snapping);
    }

    public void setTheta(double theta) {
        setTheta(theta, false);
    }



    public UUID getId(){
        return id;
    }

    public String getPoseRepresentation(){
        return Formatter.coordinates(x, y, theta);
    }

    public void clearConnectedShapes(){
        connectedShapes.clear();
    }

    public void connectShape(DancerShape shape){
        //System.out.println("connectedShapes = " + connectedShapes);
        connectedShapes.add(shape);
    }

    public String print(){
        StringBuilder sb = new StringBuilder();
        for (Dancer d : dancers){
            if(d.equals(dancers.firstElement())){
                sb.append(d.print());
            }else{
                sb.append(" and " + d.print());
            }
        }
        sb.append(" has pose " + getPoseRepresentation());
        return sb.toString();
    }
}
