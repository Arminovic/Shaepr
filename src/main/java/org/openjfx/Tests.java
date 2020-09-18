package org.openjfx;

import Data.*;
import GUI.SubStages.DataValidator;
import com.google.gson.Gson;

import java.util.Random;
import java.util.Vector;

public class Tests {

    private static String json = "{\"series\":[{\"list\":[{\"p\":{\"x\":-1.28608257E9,\"y\":-1.343278692E9},\"DancersOnPosition\":[{\"sex\":\"male\",\"name\":\"m1\"},{\"sex\":\"female\",\"name\":\"f1\"}]},{\"p\":{\"x\":-3.90706926E8,\"y\":-4.88269753E8},\"DancersOnPosition\":[{\"sex\":\"male\",\"name\":\"m2\"},{\"sex\":\"female\",\"name\":\"f2\"}]},{\"p\":{\"x\":1.35599469E9,\"y\":-1.870576674E9},\"DancersOnPosition\":[{\"sex\":\"male\",\"name\":\"m3\"},{\"sex\":\"female\",\"name\":\"f3\"}]},{\"p\":{\"x\":8.93439232E8,\"y\":1.22435737E9},\"DancersOnPosition\":[{\"sex\":\"male\",\"name\":\"m4\"},{\"sex\":\"female\",\"name\":\"f4\"}]},{\"p\":{\"x\":-1.000844826E9,\"y\":-4.10467215E8},\"DancersOnPosition\":[{\"sex\":\"male\",\"name\":\"m5\"},{\"sex\":\"female\",\"name\":\"f5\"}]},{\"p\":{\"x\":-7.69891236E8,\"y\":-1.643004735E9},\"DancersOnPosition\":[{\"sex\":\"male\",\"name\":\"m6\"},{\"sex\":\"female\",\"name\":\"f6\"}]},{\"p\":{\"x\":1.245262285E9,\"y\":-1.930500567E9},\"DancersOnPosition\":[{\"sex\":\"male\",\"name\":\"m7\"},{\"sex\":\"female\",\"name\":\"f7\"}]},{\"p\":{\"x\":5.7707875E7,\"y\":1.491976541E9},\"DancersOnPosition\":[{\"sex\":\"male\",\"name\":\"m8\"},{\"sex\":\"female\",\"name\":\"f8\"}]}]},{\"list\":[{\"p\":{\"x\":-1.744824489E9,\"y\":-1.17724219E9},\"DancersOnPosition\":[{\"sex\":\"male\",\"name\":\"m1\"},{\"sex\":\"female\",\"name\":\"f1\"}]},{\"p\":{\"x\":4.30749522E8,\"y\":1.899147052E9},\"DancersOnPosition\":[{\"sex\":\"male\",\"name\":\"m2\"},{\"sex\":\"female\",\"name\":\"f2\"}]},{\"p\":{\"x\":-9.18847619E8,\"y\":6.7065317E7},\"DancersOnPosition\":[{\"sex\":\"male\",\"name\":\"m3\"},{\"sex\":\"female\",\"name\":\"f3\"}]},{\"p\":{\"x\":8.43147817E8,\"y\":1.755827864E9},\"DancersOnPosition\":[{\"sex\":\"male\",\"name\":\"m4\"},{\"sex\":\"female\",\"name\":\"f4\"}]},{\"p\":{\"x\":1.618786872E9,\"y\":-1.529139876E9},\"DancersOnPosition\":[{\"sex\":\"male\",\"name\":\"m5\"},{\"sex\":\"female\",\"name\":\"f5\"}]},{\"p\":{\"x\":-1.419925839E9,\"y\":-1.295204656E9},\"DancersOnPosition\":[{\"sex\":\"male\",\"name\":\"m6\"},{\"sex\":\"female\",\"name\":\"f6\"}]},{\"p\":{\"x\":-1.46508765E8,\"y\":776520.0},\"DancersOnPosition\":[{\"sex\":\"male\",\"name\":\"m7\"},{\"sex\":\"female\",\"name\":\"f7\"}]},{\"p\":{\"x\":1.99358944E9,\"y\":-1.528801606E9},\"DancersOnPosition\":[{\"sex\":\"male\",\"name\":\"m8\"},{\"sex\":\"female\",\"name\":\"f8\"}]}]},{\"list\":[{\"p\":{\"x\":-1.745111458E9,\"y\":1.183316353E9},\"DancersOnPosition\":[{\"sex\":\"male\",\"name\":\"m1\"},{\"sex\":\"female\",\"name\":\"f1\"}]},{\"p\":{\"x\":1.2142011E7,\"y\":1.63586344E8},\"DancersOnPosition\":[{\"sex\":\"male\",\"name\":\"m2\"},{\"sex\":\"female\",\"name\":\"f2\"}]},{\"p\":{\"x\":3.62971318E8,\"y\":-7.358621E7},\"DancersOnPosition\":[{\"sex\":\"male\",\"name\":\"m3\"},{\"sex\":\"female\",\"name\":\"f3\"}]},{\"p\":{\"x\":3.61335133E8,\"y\":1.741088841E9},\"DancersOnPosition\":[{\"sex\":\"male\",\"name\":\"m4\"},{\"sex\":\"female\",\"name\":\"f4\"}]},{\"p\":{\"x\":-4.73210366E8,\"y\":7.07378374E8},\"DancersOnPosition\":[{\"sex\":\"male\",\"name\":\"m5\"},{\"sex\":\"female\",\"name\":\"f5\"}]},{\"p\":{\"x\":-1.043651914E9,\"y\":5.07774049E8},\"DancersOnPosition\":[{\"sex\":\"male\",\"name\":\"m6\"},{\"sex\":\"female\",\"name\":\"f6\"}]},{\"p\":{\"x\":2.030940879E9,\"y\":9.24781319E8},\"DancersOnPosition\":[{\"sex\":\"male\",\"name\":\"m7\"},{\"sex\":\"female\",\"name\":\"f7\"}]},{\"p\":{\"x\":1.991530357E9,\"y\":-7.4577466E8},\"DancersOnPosition\":[{\"sex\":\"male\",\"name\":\"m8\"},{\"sex\":\"female\",\"name\":\"f8\"}]}]},{\"list\":[{\"p\":{\"x\":-1.936828865E9,\"y\":2.084072678E9},\"DancersOnPosition\":[{\"sex\":\"male\",\"name\":\"m1\"},{\"sex\":\"female\",\"name\":\"f1\"}]},{\"p\":{\"x\":1.145774953E9,\"y\":-2.025754144E9},\"DancersOnPosition\":[{\"sex\":\"male\",\"name\":\"m2\"},{\"sex\":\"female\",\"name\":\"f2\"}]},{\"p\":{\"x\":2.49111853E8,\"y\":-1.879035786E9},\"DancersOnPosition\":[{\"sex\":\"male\",\"name\":\"m3\"},{\"sex\":\"female\",\"name\":\"f3\"}]},{\"p\":{\"x\":-4.31910332E8,\"y\":-1.116111871E9},\"DancersOnPosition\":[{\"sex\":\"male\",\"name\":\"m4\"},{\"sex\":\"female\",\"name\":\"f4\"}]},{\"p\":{\"x\":-2.059180139E9,\"y\":1.207771437E9},\"DancersOnPosition\":[{\"sex\":\"male\",\"name\":\"m5\"},{\"sex\":\"female\",\"name\":\"f5\"}]},{\"p\":{\"x\":-3.08842078E8,\"y\":3.05660089E8},\"DancersOnPosition\":[{\"sex\":\"male\",\"name\":\"m6\"},{\"sex\":\"female\",\"name\":\"f6\"}]},{\"p\":{\"x\":-5111757.0,\"y\":-5.96085581E8},\"DancersOnPosition\":[{\"sex\":\"male\",\"name\":\"m7\"},{\"sex\":\"female\",\"name\":\"f7\"}]},{\"p\":{\"x\":-9.9180727E7,\"y\":-2.141444872E9},\"DancersOnPosition\":[{\"sex\":\"male\",\"name\":\"m8\"},{\"sex\":\"female\",\"name\":\"f8\"}]}]},{\"list\":[{\"p\":{\"x\":-1.323028887E9,\"y\":-6.7817219E7},\"DancersOnPosition\":[{\"sex\":\"male\",\"name\":\"m1\"},{\"sex\":\"female\",\"name\":\"f1\"}]},{\"p\":{\"x\":-1.135642702E9,\"y\":1.595299896E9},\"DancersOnPosition\":[{\"sex\":\"male\",\"name\":\"m2\"},{\"sex\":\"female\",\"name\":\"f2\"}]},{\"p\":{\"x\":-5.69505179E8,\"y\":1.01393193E9},\"DancersOnPosition\":[{\"sex\":\"male\",\"name\":\"m3\"},{\"sex\":\"female\",\"name\":\"f3\"}]},{\"p\":{\"x\":5.21087176E8,\"y\":-1.672107531E9},\"DancersOnPosition\":[{\"sex\":\"male\",\"name\":\"m4\"},{\"sex\":\"female\",\"name\":\"f4\"}]},{\"p\":{\"x\":1.453072259E9,\"y\":-2.174069E8},\"DancersOnPosition\":[{\"sex\":\"male\",\"name\":\"m5\"},{\"sex\":\"female\",\"name\":\"f5\"}]},{\"p\":{\"x\":1.18501707E9,\"y\":-8.60847524E8},\"DancersOnPosition\":[{\"sex\":\"male\",\"name\":\"m6\"},{\"sex\":\"female\",\"name\":\"f6\"}]},{\"p\":{\"x\":-1.330919185E9,\"y\":-1.522729766E9},\"DancersOnPosition\":[{\"sex\":\"male\",\"name\":\"m7\"},{\"sex\":\"female\",\"name\":\"f7\"}]},{\"p\":{\"x\":4.16248391E8,\"y\":2.2262338E7},\"DancersOnPosition\":[{\"sex\":\"male\",\"name\":\"m8\"},{\"sex\":\"female\",\"name\":\"f8\"}]}]},{\"list\":[{\"p\":{\"x\":1.943680142E9,\"y\":-1.443108828E9},\"DancersOnPosition\":[{\"sex\":\"male\",\"name\":\"m1\"},{\"sex\":\"female\",\"name\":\"f1\"}]},{\"p\":{\"x\":1.251996208E9,\"y\":-5.74747049E8},\"DancersOnPosition\":[{\"sex\":\"male\",\"name\":\"m2\"},{\"sex\":\"female\",\"name\":\"f2\"}]},{\"p\":{\"x\":3.3175555E8,\"y\":7.25549688E8},\"DancersOnPosition\":[{\"sex\":\"male\",\"name\":\"m3\"},{\"sex\":\"female\",\"name\":\"f3\"}]},{\"p\":{\"x\":-7.23731183E8,\"y\":-2.135029349E9},\"DancersOnPosition\":[{\"sex\":\"male\",\"name\":\"m4\"},{\"sex\":\"female\",\"name\":\"f4\"}]},{\"p\":{\"x\":-1.38468197E8,\"y\":4.28667171E8},\"DancersOnPosition\":[{\"sex\":\"male\",\"name\":\"m5\"},{\"sex\":\"female\",\"name\":\"f5\"}]},{\"p\":{\"x\":-2.044248491E9,\"y\":-1.853226225E9},\"DancersOnPosition\":[{\"sex\":\"male\",\"name\":\"m6\"},{\"sex\":\"female\",\"name\":\"f6\"}]},{\"p\":{\"x\":-1.646402588E9,\"y\":8.30228297E8},\"DancersOnPosition\":[{\"sex\":\"male\",\"name\":\"m7\"},{\"sex\":\"female\",\"name\":\"f7\"}]},{\"p\":{\"x\":-8.44251978E8,\"y\":7.06951856E8},\"DancersOnPosition\":[{\"sex\":\"male\",\"name\":\"m8\"},{\"sex\":\"female\",\"name\":\"f8\"}]}]}]}";
    private static String json2= "{\"series\":[{\"list\":[{\"p\":{\"x\":-1.28608257E9,\"y\":-1.343278692E9},\"DancersOnPosition\":[{\"sex\":\"male\",\"name\":\"m1\"}]},{\"p\":{\"x\":-3.90706926E8,\"y\":-4.88269753E8},\"DancersOnPosition\":[{\"sex\":\"male\",\"name\":\"m2\"}]},{\"p\":{\"x\":1.35599469E9,\"y\":-1.870576674E9},\"DancersOnPosition\":[{\"sex\":\"male\",\"name\":\"m3\"}]},{\"p\":{\"x\":8.93439232E8,\"y\":1.22435737E9},\"DancersOnPosition\":[{\"sex\":\"male\",\"name\":\"m4\"}]},{\"p\":{\"x\":-1.000844826E9,\"y\":-4.10467215E8},\"DancersOnPosition\":[{\"sex\":\"male\",\"name\":\"m5\"}]},{\"p\":{\"x\":-7.69891236E8,\"y\":-1.643004735E9},\"DancersOnPosition\":[{\"sex\":\"male\",\"name\":\"m6\"}]},{\"p\":{\"x\":1.245262285E9,\"y\":-1.930500567E9},\"DancersOnPosition\":[{\"sex\":\"male\",\"name\":\"m7\"}]},{\"p\":{\"x\":5.7707875E7,\"y\":1.491976541E9},\"DancersOnPosition\":[{\"sex\":\"male\",\"name\":\"m8\"}]}]},{\"list\":[{\"p\":{\"x\":-1.744824489E9,\"y\":-1.17724219E9},\"DancersOnPosition\":[{\"sex\":\"male\",\"name\":\"m1\"}]},{\"p\":{\"x\":4.30749522E8,\"y\":1.899147052E9},\"DancersOnPosition\":[{\"sex\":\"male\",\"name\":\"m2\"}]},{\"p\":{\"x\":-9.18847619E8,\"y\":6.7065317E7},\"DancersOnPosition\":[{\"sex\":\"male\",\"name\":\"m3\"}]},{\"p\":{\"x\":8.43147817E8,\"y\":1.755827864E9},\"DancersOnPosition\":[{\"sex\":\"male\",\"name\":\"m4\"}]},{\"p\":{\"x\":1.618786872E9,\"y\":-1.529139876E9},\"DancersOnPosition\":[{\"sex\":\"male\",\"name\":\"m5\"}]},{\"p\":{\"x\":-1.419925839E9,\"y\":-1.295204656E9},\"DancersOnPosition\":[{\"sex\":\"male\",\"name\":\"m6\"}]},{\"p\":{\"x\":-1.46508765E8,\"y\":776520.0},\"DancersOnPosition\":[{\"sex\":\"male\",\"name\":\"m7\"}]},{\"p\":{\"x\":1.99358944E9,\"y\":-1.528801606E9},\"DancersOnPosition\":[{\"sex\":\"male\",\"name\":\"m8\"}]}]},{\"list\":[{\"p\":{\"x\":-1.745111458E9,\"y\":1.183316353E9},\"DancersOnPosition\":[{\"sex\":\"male\",\"name\":\"m1\"}]},{\"p\":{\"x\":1.2142011E7,\"y\":1.63586344E8},\"DancersOnPosition\":[{\"sex\":\"male\",\"name\":\"m2\"}]},{\"p\":{\"x\":3.62971318E8,\"y\":-7.358621E7},\"DancersOnPosition\":[{\"sex\":\"male\",\"name\":\"m3\"}]},{\"p\":{\"x\":3.61335133E8,\"y\":1.741088841E9},\"DancersOnPosition\":[{\"sex\":\"male\",\"name\":\"m4\"}]},{\"p\":{\"x\":-4.73210366E8,\"y\":7.07378374E8},\"DancersOnPosition\":[{\"sex\":\"male\",\"name\":\"m5\"}]},{\"p\":{\"x\":-1.043651914E9,\"y\":5.07774049E8},\"DancersOnPosition\":[{\"sex\":\"male\",\"name\":\"m6\"}]},{\"p\":{\"x\":2.030940879E9,\"y\":9.24781319E8},\"DancersOnPosition\":[{\"sex\":\"male\",\"name\":\"m7\"}]},{\"p\":{\"x\":1.991530357E9,\"y\":-7.4577466E8},\"DancersOnPosition\":[{\"sex\":\"male\",\"name\":\"m8\"}]}]},{\"list\":[{\"p\":{\"x\":-1.936828865E9,\"y\":2.084072678E9},\"DancersOnPosition\":[{\"sex\":\"male\",\"name\":\"m1\"}]},{\"p\":{\"x\":1.145774953E9,\"y\":-2.025754144E9},\"DancersOnPosition\":[{\"sex\":\"male\",\"name\":\"m2\"}]},{\"p\":{\"x\":2.49111853E8,\"y\":-1.879035786E9},\"DancersOnPosition\":[{\"sex\":\"male\",\"name\":\"m3\"}]},{\"p\":{\"x\":-4.31910332E8,\"y\":-1.116111871E9},\"DancersOnPosition\":[{\"sex\":\"male\",\"name\":\"m4\"}]},{\"p\":{\"x\":-2.059180139E9,\"y\":1.207771437E9},\"DancersOnPosition\":[{\"sex\":\"male\",\"name\":\"m5\"}]},{\"p\":{\"x\":-3.08842078E8,\"y\":3.05660089E8},\"DancersOnPosition\":[{\"sex\":\"male\",\"name\":\"m6\"}]},{\"p\":{\"x\":-5111757.0,\"y\":-5.96085581E8},\"DancersOnPosition\":[{\"sex\":\"male\",\"name\":\"m7\"}]},{\"p\":{\"x\":-9.9180727E7,\"y\":-2.141444872E9},\"DancersOnPosition\":[{\"sex\":\"male\",\"name\":\"m8\"}]}]},{\"list\":[{\"p\":{\"x\":-1.323028887E9,\"y\":-6.7817219E7},\"DancersOnPosition\":[{\"sex\":\"male\",\"name\":\"m1\"}]},{\"p\":{\"x\":-1.135642702E9,\"y\":1.595299896E9},\"DancersOnPosition\":[{\"sex\":\"male\",\"name\":\"m2\"}]},{\"p\":{\"x\":-5.69505179E8,\"y\":1.01393193E9},\"DancersOnPosition\":[{\"sex\":\"male\",\"name\":\"m3\"}]},{\"p\":{\"x\":5.21087176E8,\"y\":-1.672107531E9},\"DancersOnPosition\":[{\"sex\":\"male\",\"name\":\"m4\"}]},{\"p\":{\"x\":1.453072259E9,\"y\":-2.174069E8},\"DancersOnPosition\":[{\"sex\":\"male\",\"name\":\"m5\"}]},{\"p\":{\"x\":1.18501707E9,\"y\":-8.60847524E8},\"DancersOnPosition\":[{\"sex\":\"male\",\"name\":\"m6\"}]},{\"p\":{\"x\":-1.330919185E9,\"y\":-1.522729766E9},\"DancersOnPosition\":[{\"sex\":\"male\",\"name\":\"m7\"}]},{\"p\":{\"x\":4.16248391E8,\"y\":2.2262338E7},\"DancersOnPosition\":[{\"sex\":\"male\",\"name\":\"m8\"}]}]},{\"list\":[{\"p\":{\"x\":1.943680142E9,\"y\":-1.443108828E9},\"DancersOnPosition\":[{\"sex\":\"male\",\"name\":\"m1\"}]},{\"p\":{\"x\":1.251996208E9,\"y\":-5.74747049E8},\"DancersOnPosition\":[{\"sex\":\"male\",\"name\":\"m2\"}]},{\"p\":{\"x\":3.3175555E8,\"y\":7.25549688E8},\"DancersOnPosition\":[{\"sex\":\"male\",\"name\":\"m3\"}]},{\"p\":{\"x\":-7.23731183E8,\"y\":-2.135029349E9},\"DancersOnPosition\":[{\"sex\":\"male\",\"name\":\"m4\"}]},{\"p\":{\"x\":-1.38468197E8,\"y\":4.28667171E8},\"DancersOnPosition\":[{\"sex\":\"male\",\"name\":\"m5\"}]},{\"p\":{\"x\":-2.044248491E9,\"y\":-1.853226225E9},\"DancersOnPosition\":[{\"sex\":\"male\",\"name\":\"m6\"}]},{\"p\":{\"x\":-1.646402588E9,\"y\":8.30228297E8},\"DancersOnPosition\":[{\"sex\":\"male\",\"name\":\"m7\"}]},{\"p\":{\"x\":-8.44251978E8,\"y\":7.06951856E8},\"DancersOnPosition\":[{\"sex\":\"male\",\"name\":\"m8\"}]}]}]}";
    private static Random rand = new Random(12345678);
    private Data data = Data.getInstance();

    public void testLoad(){
        data.loadSaveData();
    }

    public void testJSON(){
        Gson gson = new Gson();
        String json = gson.toJson(data.getSequence(0));
        System.out.println(json);
    }

    public void testData(){
        Vector<Dancer> males = new Vector<>();
        for(int i = 8; i>0; i--){
            males.add(new Dancer("m"+i, Sex.Male, i));
        }
        Vector<Dancer> females = new Vector<>();
        for(int i = 1; i<=8; i++){
            females.add(new Dancer("f"+i, Sex.Female, i));
        }

        Shapesequence shapesequence = new Shapesequence();
        shapesequence.addDancer(males.toArray(new Dancer[0]));
        shapesequence.addDancer(females.toArray(new Dancer[0]));

        Shape s1 = shapesequence.makeNewShape();
        Shape s2 = shapesequence.makeNewShape();
        Shape s3 = shapesequence.makeNewShape();
        Shape s4 = shapesequence.makeNewShape();

        //shapesequence.addShape(s1, s2, s3, s4);

        s1.setPose(males.get(0), -2, -2, 45);
        s1.setPose(males.get(1), -4, -4, 45);
        s1.setPose(males.get(2), 2, 2, 45);
        s1.setPose(males.get(3), 4, 4, 45);
        s1.setPose(males.get(4), 0, 0, 45);
        s1.setPose(males.get(5), -2, 2, 45);
        s1.setPose(males.get(6), -6, -6, 45);
        s1.setPose(males.get(7), 2, -2, 45);

        s1.setPose(females.get(0), -1, -1 , -45);
        s1.setPose(females.get(1), -3, -3 , -45);
        s1.setPose(females.get(2), -5, -5 , -45);
        s1.setPose(females.get(3), 3, 3 , -45);
        s1.setPose(females.get(4), 5, 5 , -45);
        s1.setPose(females.get(5), 1, 1 , -45);
        s1.setPose(females.get(6), -1, 1 , -45);
        s1.setPose(females.get(7), 1, -1 , -45);

        s2.setPose(males.get(0), -5, -5, 0);
        s2.setPose(males.get(1), -5, -4, 0);
        s2.setPose(males.get(2), -5, -3, 0);
        s2.setPose(males.get(3), -5, -2, 0);
        s2.setPose(males.get(4), -5, -1, 0);
        s2.setPose(males.get(5), -5, 0, 0);
        s2.setPose(males.get(6), -5, 1, 0);
        s2.setPose(males.get(7), -5, 2, 0);

        s2.setPose(females.get(0), -4, -5 , 0);
        s2.setPose(females.get(1), -4, -4 , 0);
        s2.setPose(females.get(2), -4, -3 , 0);
        s2.setPose(females.get(3), -4, -2 , 0);
        s2.setPose(females.get(4), -4, -1 , 0);
        s2.setPose(females.get(5), -4, 0 , 0);
        s2.setPose(females.get(6), -4, 1 , 0);
        s2.setPose(females.get(7), -4, 2 , 0);

        s3.setPose(males.get(0), 5, -5, 0);
        s3.setPose(males.get(1), 5, -4, 0);
        s3.setPose(males.get(2), 5, -3, 0);
        s3.setPose(males.get(3), 5, -2, 0);
        s3.setPose(males.get(4), 5, -1, 0);
        s3.setPose(males.get(5), 5, 0, 0);
        s3.setPose(males.get(6), 5, 1, 0);
        s3.setPose(males.get(7), 5, 2, 0);

        s3.setPose(females.get(0), 4, -5 , 0);
        s3.setPose(females.get(1), 4, -4 , 0);
        s3.setPose(females.get(2), 4, -3 , 0);
        s3.setPose(females.get(3), 4, -2 , 0);
        s3.setPose(females.get(4), 4, -1 , 0);
        s3.setPose(females.get(5), 4, 0 , 0);
        s3.setPose(females.get(6), 4, 1 , 0);
        s3.setPose(females.get(7), 4, 2 , 0);

        s4.setPose(males.get(0), 0, -5, 45);
        s4.setPose(males.get(1), 0, -4, -45);
        s4.setPose(males.get(2), 0, -3, 45);
        s4.setPose(males.get(3), 0, -2, -45);
        s4.setPose(males.get(4), 0, -1, 45);
        s4.setPose(males.get(5), 0, 0, -45);
        s4.setPose(males.get(6), 0, 1, 45);
        s4.setPose(males.get(7), 0, 2, -45);

        s4.addDancerToPoseOf(females.get(0), males.get(0));
        s4.addDancerToPoseOf(females.get(1), males.get(1));
        s4.addDancerToPoseOf(females.get(2), males.get(2));
        s4.addDancerToPoseOf(females.get(3), males.get(3));
        s4.addDancerToPoseOf(females.get(4), males.get(4));
        s4.addDancerToPoseOf(females.get(5), males.get(5));
        s4.addDancerToPoseOf(females.get(6), males.get(6));
        s4.addDancerToPoseOf(females.get(7), males.get(7));

        data.clearSequences();
        data.addSequence(shapesequence);
        //data.addSequence(shapesequence);
        //System.out.println(data.print());

    }

    public void testValidator(){

        Data d = Data.getInstance();
        d.clearSequences();

        Vector<Dancer> males = new Vector<>();
        for(int i = 8; i>0; i--){
            males.add(new Dancer("m"+i, Sex.Male, i));
        }
        Vector<Dancer> females = new Vector<>();
        for(int i = 1; i<=8; i++){
            females.add(new Dancer("f"+i, Sex.Female, i));
        }

        Shapesequence shapesequence = new Shapesequence();
        shapesequence.addDancer(males.toArray(new Dancer[0]));
        shapesequence.addDancer(females.toArray(new Dancer[0]));

        Shape s1 = shapesequence.makeNewShape();
        Shape s2 = shapesequence.makeNewShape();
        Shape s3 = shapesequence.makeNewShape();
        Shape s4 = shapesequence.makeNewShape();

        //shapesequence.addShape(s1, s2, s3, s4);

        s1.setPose(males.get(0), -2, -2, 45);
        s1.setPose(males.get(1), -4, -4, 45);
        s1.setPose(males.get(2), 2, 2, 45);
        s1.setPose(males.get(3), 4, 4, 45);
        s1.setPose(males.get(4), 0, 0, 45);
        s1.setPose(males.get(5), -2, 2, 45);
        s1.setPose(males.get(6), -6, -6, 45);
        s1.setPose(males.get(7), 2, -2, 45);

        s1.setPose(females.get(0), -1, -1 , -45);
        s1.setPose(females.get(1), -3, -3 , -45);
        //s1.setPose(females.get(2), -5, -5 , -45);
        s1.setPose(females.get(3), 3, 3 , -45);
        s1.setPose(females.get(4), 5, 5 , -45);
        s1.setPose(females.get(5), 1, 1 , -45);
        s1.setPose(females.get(6), -1, 1 , -45);
        s1.setPose(females.get(7), 1, -1 , -45);

        s2.setPose(males.get(0), -5, -5, 0);
        s2.setPose(males.get(1), -5, -4, 0);
        s2.setPose(males.get(2), -5, -3, 0);
        s2.setPose(males.get(3), -5, -2, 0);
        s2.setPose(males.get(4), -5, -1, 0);
        s2.setPose(males.get(5), -5, 0, 0);
        //s2.setPose(males.get(6), -5, 1, 0);
        s2.setPose(males.get(7), -5, 2, 0);

        s2.setPose(females.get(0), -4, -5 , 0);
        s2.setPose(females.get(1), -4, -4 , 0);
        s2.setPose(females.get(2), -4, -3 , 0);
        s2.setPose(females.get(3), -4, -2 , 0);
        s2.setPose(females.get(4), -4, -1 , 0);
        s2.setPose(females.get(5), -4, 0 , 0);
        s2.setPose(females.get(6), -4, 1 , 0);
        s2.setPose(females.get(7), -4, 2 , 0);

        s3.setPose(males.get(0), 5, -5, 0);
        s3.setPose(males.get(1), 5, -4, 0);
        //s3.setPose(males.get(2), 5, -3, 0);
        //s3.setPose(males.get(3), 5, -2, 0);
        s3.setPose(males.get(4), 5, -1, 0);
        s3.setPose(males.get(5), 5, 0, 0);
        s3.setPose(males.get(6), 5, 1, 0);
        s3.setPose(males.get(7), 5, 2, 0);

        s3.setPose(females.get(0), 4, -5 , 0);
        s3.setPose(females.get(1), 4, -4 , 0);
        s3.setPose(females.get(2), 4, -3 , 0);
        s3.setPose(females.get(3), 4, -2 , 0);
        s3.setPose(females.get(4), 4, -1 , 0);
        s3.setPose(females.get(5), 4, 0 , 0);
        s3.setPose(females.get(6), 4, 1 , 0);
        s3.setPose(females.get(7), 4, 2 , 0);

        s4.setPose(males.get(0), 0, -5, 45);
        s4.setPose(males.get(1), 0, -4, -45);
        s4.setPose(males.get(2), 0, -3, 45);
        //s4.setPose(males.get(3), 0, -2, -45);
        s4.setPose(males.get(4), 0, -1, 45);
        s4.setPose(males.get(5), 0, 0, -45);
        s4.setPose(males.get(6), 0, 1, 45);
        s4.setPose(males.get(7), 0, 2, -45);

        s4.addDancerToPoseOf(females.get(0), males.get(0));
        s4.addDancerToPoseOf(females.get(1), males.get(1));
        //s4.addDancerToPoseOf(females.get(2), males.get(2));
        s4.addDancerToPoseOf(females.get(3), males.get(3));
        s4.addDancerToPoseOf(females.get(4), males.get(4));
        s4.addDancerToPoseOf(females.get(5), males.get(5));
        s4.addDancerToPoseOf(females.get(6), males.get(6));
        s4.addDancerToPoseOf(females.get(7), males.get(7));

        s4.getPoseOf(males.get(2)).removeDancer(males.get(2));

        d.addSequence(shapesequence);

        DataValidator.start(d);
    }

    public void printData(){
        System.out.println(data.print());
    }


    public void testSave(){
        data.saveData();
    }
}
