package GUI.SubStages;

import Data.*;
import javafx.scene.Scene;
import javafx.scene.control.TextArea;
import javafx.stage.Stage;

import java.util.List;
import java.util.Vector;

public class DataValidator {

    private static final String prefix = "+---------------\n";
    private static final String suffix = "\n+---------------";
    private static final String leveling = " | ";

    public static void start(Data pData){

        TextArea text = new TextArea();
        text.setEditable(false);
        Scene scene = new Scene(text);
        Stage stage = new Stage();
        stage.setTitle("Datavalidation");
        stage.setScene(scene);
        stage.show();

        DataValidator validator = new DataValidator(pData);
        validator.checkAllSequences();
        for(logMessage s : validator.getLogs()){
            switch (s.level){
                case 1:
                    break;
                case 2:
                    text.appendText(leveling + leveling);
                    break;
                case 3:
                    text.appendText(leveling + leveling + leveling);
                    break;
                default:
                    break;
            }
            text.appendText(s.message + "\n");

        }
    }

    private Data data;
    private Vector<logMessage> logs = new Vector<>();


    private class logMessage{
        final int level;
        final String message;
        logMessage(int pLevel, String pMessage){
            level = pLevel;
            message = pMessage;
        }
    }

    private DataValidator(Data pData){
        data = pData;
        /* Validation Checklist
            every Dancer has one and only one pose per Shape
            every Pose has at least one Dancer

         */
    }

    public Vector<logMessage> getLogs(){
        return logs;
    }

    private boolean checkAllSequences(){
        boolean valid = true;
        for(Shapesequence seq : data.getSequences()){
            logs.add(new logMessage(1, prefix+leveling+"--> checking Sequence "+seq.getId()));
            if(checkSequence(seq)){
                logs.add(new logMessage(1, leveling+"Sequence "+seq.getId()+" is valid"+suffix));
            }else{
                valid = false;
                logs.add(new logMessage(1, leveling+"Sequence "+seq.getId()+" is invalid"+suffix));
            }
        }
        return valid;
    }

    private boolean checkSequence(Shapesequence sequence){
        boolean sequenceValid = true;
        for(Shape s : sequence.getShapes()){
            //logs.add("\n---> checking Shape "+s.getId());
            if(!checkShape(sequence.getDancers(), s)){
                sequenceValid = false;
                logs.add(new logMessage(2, "Shape " + s.getId() + " is invalid"));
            }
        }
        return sequenceValid;
    }

    private boolean checkShape(List<Dancer> dancers, Shape shape){
        boolean shapeValid = true;
        for(Pose p : shape.getPoses()){
            //logs.add("\n----> checking Pose "+p.getId());
            if(!checkPose(p)){
                shapeValid = false;
                logs.add(new logMessage(3, "Pose " + p.getId() + " in shape " + shape.getId() + " has no dancers"));
            }
        }
        if(!everyDancerHasOnePose(dancers, shape)){
            shapeValid = false;
        }
        return shapeValid;
    }

    private boolean checkPose(Pose p){
        return p.getDancers().size() > 0;
    }

    private boolean everyDancerHasOnePose(List<Dancer> dancers, Shape shape){
        boolean allPositioned = true;
        for(Dancer d : dancers){
            //logs.add("\n----> checking Dancer "+d.getId());
            int numberPositions = 0;
            for(Pose p : shape.getPoses()){
                for(Dancer pd : p.getDancers()) {
                    if(pd.getId().equals(d.getId())) numberPositions++;
                }
            }
            if(numberPositions == 0){
                logs.add(new logMessage(3, "Dancer "+d.getId()+" has no Pose in "+shape.getId()));
                allPositioned = false;
            } else if(numberPositions > 1){
                logs.add(new logMessage(3, "Dancer "+d.getId()+" has more than one Pose in "+shape.getId()));
                allPositioned = false;
            }
        }
        return allPositioned;
    }

}
