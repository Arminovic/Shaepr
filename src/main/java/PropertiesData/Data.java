package PropertiesData;

import com.google.gson.Gson;

import java.io.*;
import java.util.Iterator;
import java.util.Properties;
import java.util.Vector;

public class Data {

    private static Data instance = null;
    private static CommonValues commonValues = null;

    //public enum sex {male, female}

    private Gson gson = new Gson();
    private Vector<Shapesequence> sequences = new Vector<>();
    private String sep = File.separator;
    private String homePath = System.getProperty("user.home");
    private String savePath = getHomePath() + getSep() + ".shaepr" + getSep();
    private String configName = "Config.properties";
    private File saveDir = new File(getSavePath());
    private int activeSequence = 0; // die momentan aktive Sequence, die in der GUI dargestellt wird

    private Data() {
        if (!getSaveDir().mkdir()) { //if !mkdir returns true, there probably are datafiles already in the saveDir.
            loadSaveData();
        }
    }

    public static Data getInstance(){
        if(instance == null) instance = new Data();
        return instance;
    }

    public void setActiveSequence(int index){
        if(index >= 0 && sequences.size() > index) {
            activeSequence = index;
        } else {
            System.err.println("Sequence not found");
        }
    }

    public Shapesequence getActiveSequence(){
        if(sequences.size() <= activeSequence){
            return null;
        }
        return sequences.elementAt(activeSequence);
    }

    public Vector<Shapesequence> getSequences(){
        return sequences;
    }

    public String getSep() {
        return sep;
    }

    public String getHomePath() {
        return homePath;
    }

    public String getSavePath() {
        return savePath;
    }

    public File getSaveDir() {
        return saveDir;
    }

    public void newSequence(){
        Shapesequence newSeq = new Shapesequence();
        sequences.add(newSeq);
        setActiveSequence(sequences.size()-1);
        Shape plainShape = new Shape();
        newSeq.addShape(plainShape);
        //MainScene.setSelectedShape(plainShape);
    }

    public void addSequence(Shapesequence seq){
        sequences.add(seq);
    }

    public Shapesequence getSequence(int index){
        return sequences.get(index);
    }

    public void clearSequences(){
        sequences.clear();
    }

    public void saveData() {
        File saveFile = new File(getSavePath() + "saveData.txt");
        saveTo(saveFile);
    }

    public boolean saveTo(File file){
        try {
            file.createNewFile();
            BufferedWriter bw = new BufferedWriter(new FileWriter(file));

            //StringProperties can't be serialized by Gson, but we don't need to save them, so we just kick them out of our saveData
            //The Stringproperties will be rebuild by our GUI when loading the String Data from the Savefile
            Shapesequence[] saveSequences = sequences.toArray(new Shapesequence[0]);
            for(Shapesequence seq : saveSequences){

                for(Shape shape : seq.getShapes()){
                    shape.clearConnectedFields();
                    for(Pose p : shape.getPoses()){
                        //p.clearConnectedShapes(); todo
                    }
                }
                for(Dancer dancer : seq.getDancers()){
                    //dancer.clearConnectedFields(); todo
                }
            }

            for (Shapesequence seq : saveSequences) {
                //System.out.println(gson.toJson(seq));
                bw.write(gson.toJson(seq));
                bw.newLine();
            }
            bw.close();
        } catch (IOException e) {
            System.out.println("IOException when saving to saveFile");
            return false;
        }
        return true;
    }

    public void loadSaveData() {
        if(!sequences.isEmpty()) sequences.clear();
        File[] files = getSaveDir().listFiles();
        if (files == null) {
            System.out.println("saveDir is not a valid directory");
            return;
        }
        if (files.length == 0) {
            System.out.println("saveDir contains no saveFiles");
        }
        for(File f : files){
            if(!f.getName().equals(configName))
                loadFromFile(f);
        }
    }

    public boolean loadConfigTo(Properties configProperties){
        try {
            BufferedInputStream stream = new BufferedInputStream(new FileInputStream(getSavePath() + configName));
            configProperties.load(stream);
        }catch(Exception e){
            System.err.println("Error loading Config file: "+e);
            return false;
        }
        return true;
    }

    public Properties loadConfig(){
        Properties configProperties = new Properties();
        loadConfigTo(configProperties);
        return configProperties;
    }

    public void saveConfig(){
        Properties properties = commonValues.getProperties();
        try{
            BufferedWriter bw = new BufferedWriter(new FileWriter(saveDir + sep + configName));
            Iterator<Object> keys = properties.keys().asIterator();
            while(keys.hasNext()){
                Object key = keys.next();
                bw.write(key.toString() + "=" + properties.get(key).toString());
                bw.newLine();
            }
            bw.close();
        }catch(IOException e){
            System.err.println(e);
        }
    }

    public boolean loadFromFile(File f) {
        try {
            BufferedReader br = new BufferedReader(new FileReader(f));
            String jsonString;
            while(true){
                jsonString = br.readLine();
                if(jsonString == null) break;
                loadFromJson(jsonString);
            }
            br.close();
            return true;
        }catch (FileNotFoundException e){
            System.out.println("file not found when loading from File");
        }catch (IOException e){
            System.out.println("IOException when loading from File");
        }
        return false;
    }

    public void loadFromJson(String json) throws IOException{
        try{
            sequences.add(gson.fromJson(json, Shapesequence.class));
        }catch(Exception e) {
            throw new IOException("invalid JSON format");
        }
    }

    public String printNr(int nr) {
        if (sequences.size() <= nr) {
            return "";
        } else {
            return sequences.elementAt(nr).print();
        }
    }

    public String print() {
        StringBuilder sb = new StringBuilder();
        for (Shapesequence s : sequences) {
            sb.append(s.print());
        }
        return sb.toString();
    }

    public CommonValues getCommonValues(){
        if(commonValues == null){
            commonValues = CommonValues.getInstance(loadConfig());
        }
        return commonValues;
    }
}