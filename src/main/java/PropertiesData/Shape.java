package PropertiesData;


import javafx.beans.property.StringProperty;

import java.util.List;
import java.util.UUID;
import java.util.Vector;

public class Shape {

    private Vector<Pose> poses = new Vector<>();
    private String name = "Shape";
    private UUID id = UUID.randomUUID();

    // Vector mit Stringproperties von GUIObjekten,
    // die den Namen darstellen und bei einer Änderung synchronisiert werden sollen
    private Vector<StringProperty> connectedFields = new Vector<>();

    public Shape(){}

    // verbinde ein Feld
    public void connectField(StringProperty field){
        connectedFields.add(field);
    }

    // trenne ein Feld
    public void disconnectField(StringProperty field){
        connectedFields.remove(field);
    }

    // der Name und alle verbundenen Felder werden aktualisiert
    public void setName(String n){
        this.name = n;
        for(StringProperty field : connectedFields){
            field.setValue(name);
        }
    }

    public void clearConnectedFields(){
        connectedFields.clear();
    }

    public String getName(){
        return name;
    }

    // wenn bereits eine Pose vorhanden ist, wird diese aktualisiert, wenn nicht wird eine neue angelegt
    public void setPose(Dancer d, double x, double y, double theta){
        Pose p = getPoseOf(d.getId());
        if(p == null){
            p = new Pose(d, x, y, theta);
            poses.add(p);
        }else{
            p.define(x, y, theta);
        }
    }

    public void setPose(Pose pose){
        Pose p = getPoseOf(pose.getDancer());
        if(p == null){
            poses.add(pose);
        }else{
            //p = pose; // call by value!!!
            p.define(pose.getX(), pose.getY(), pose.getTheta());
        }
    }

    // mit indexOf() Methoden implementiert aus performance Gründen
    public void removePoseOf(UUID dancer){
        int index = indexOf(dancer);
        if(index >= 0) poses.remove(index);
    }

    public void removePoseOf(Dancer d){
        int index = indexOf(d);
        if(index >= 0) poses.remove(index);
    }

    public void removePose(Pose p){
        poses.remove(p);
    }


    public Dancer getDancer(UUID id){
        Dancer result = null;
        for(Pose p : poses){
            p.getDancer(id);
        }
        return result;
    }

    private int indexOf(UUID dancer){
        for(int i = 0; i < poses.size(); i++){
            if(poses.elementAt(i).hasDancer(dancer)) return i;
        }
        return -1;
    }

    private int indexOf(Dancer d){
        for(int i = 0; i < poses.size(); i++){
            if(poses.elementAt(i).hasDancer(d)) return i;
        }
        return -1;
    }

    public boolean hasPose(UUID d){
        return getPoseOf(d) != null;
    }

    public boolean hasPose(Dancer d){
        return getPoseOf(d) != null;
    }

    public Pose getPoseOf(UUID dancer){
        for (Pose p : poses) {
            for(Dancer d : p.getDancers()) {
                if (d.getId().equals(dancer)) {
                    return p;
                }
            }
        }
        return null;
    }

    public Pose getPoseOf(Dancer dancer){
        for (Pose p : poses) {
            for(Dancer d : p.getDancers()) {
                if (d.equals(dancer) || d.getId().equals(dancer.getId())) {
                    return p;
                }
            }
        }
        return null;
    }

    public void addDancerToPoseOf(Dancer add, Dancer to){
        if(add != null && to != null) {
            if(hasPose(to)) { // erstmal checken, ob der Tänzer überhaupt eine Pose hat zu der man hinzufügen kann
                //System.out.println("remove");
                removePoseOf(add);
                getPoseOf(to).addDancer(add);
            }
        }
    }

    public List<Pose> getPoses() {
        return poses;
    }

    public UUID getId(){
        return id;
    }

    public String print() {
        StringBuilder builder = new StringBuilder();
        for (Pose p : poses) {
            builder.append(p.print()).append("\n");
        }
        return builder.toString();
    }

    @Override
    public String toString() {
        return name;
    }
}

