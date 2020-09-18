package PropertiesData;

import java.util.Arrays;
import java.util.UUID;
import java.util.Vector;

public class Shapesequence {

    private Vector<Shape> sequence = new Vector<>();
    private Vector<Dancer> dancers = new Vector<>();
    private UUID id = UUID.randomUUID();

    public Shapesequence(){
    }

    public Shape makeNewShape(){
        Shape s = new Shape();
        sequence.add(s);
        s.setName("Shape Nr." + (sequence.size()));
        return s;
    }

    public UUID getId(){
        return id;
    }

    public Shape getShape(int index){
        return sequence.get(index);
    }

    public void addDancer(Dancer... dancer) {
        dancers.addAll(Arrays.asList(dancer));
    }

    public void addShape(Shape... shape){
        sequence.addAll(Arrays.asList(shape));
    }

    public void addNewDancer(String name, Sex sex){
        Dancer d = new Dancer(name, sex);
        addDancer(d);
    }

    public void removeDancer(UUID id){
        dancers.remove(indexOfShape(id));
    }

    public void removeShape(UUID id){
        sequence.remove(indexOfDancer(id));
    }

    public Dancer getDancer(UUID id){
        int index = indexOfDancer(id);
        if (index >= 0) {
            //return dancers.get(indexOfDancer(name));
            return dancers.elementAt(index);
        } else{
            return null;
        }
    }

    @Deprecated
    public Dancer getDancer(String name){
        int index = indexOfDancer(name);
        if (index >= 0) {
            //return dancers.get(indexOfDancer(name));
            return dancers.elementAt(index);
        } else{
            return null;
        }
    }

    private int indexOfShape(UUID id){
        for(int i = 0; i < sequence.size(); i++){
            if (sequence.elementAt(i).getId().equals(id))
                return i;
        }
        return -1;
    }


    private int indexOfDancer(UUID id){
        for(int i = 0; i < dancers.size(); i++){
            if (dancers.elementAt(i).getId().equals(id))
                return i;
        }
        return -1;
    }

    @Deprecated
    private int indexOfDancer(String dancerName){
        for(Dancer d : dancers){
            if (d.getName().equals(dancerName))
                return dancers.indexOf(d);
        }
        return -1;
    }

    public Dancer[] getDancersAsArray(){
        return dancers.toArray(new Dancer[0]);
    }

    public Vector<Dancer> getDancers(){
        return dancers;
    }

    public Shape[] getShapesAsArray(){
        return sequence.toArray(new Shape[0]);
    }

    public Vector<Shape> getShapes(){
        return sequence;
    }

    public Pose[] getPosesOf(UUID id){
        Vector<Pose> poses = new Vector<Pose>();
        for (Shape s : sequence){
            poses.add(s.getPoseOf(id));
        }
        return poses.toArray(new Pose[0]);
    }

    public Pose[] getPosesOf(Dancer d){
        Vector<Pose> poses = new Vector<Pose>();
        for (Shape s : sequence){
            poses.add(s.getPoseOf(d));
        }
        return poses.toArray(new Pose[0]);
    }

    public String printDancerList(){
        StringBuilder builder = new StringBuilder();
        for(Dancer d : dancers){
            builder.append(d.getName() + "\n");
        }
        return builder.toString();
    }

    public String print(){
        StringBuilder builder = new StringBuilder();
        int i = 1;
        for (Shape p : sequence) {
            builder.append(p.toString()+"\n"+p.print()+"---------------------------------\n");
            i++;
        }
        return builder.toString();
    }
}
