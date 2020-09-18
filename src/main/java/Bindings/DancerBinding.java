package Bindings;

import Data.Dancer;
import Data.Sex;
import ExternalProperties.ExternalObjectProperty;
import GUI.DancerShape;
import javafx.beans.property.SimpleStringProperty;

public class DancerBinding {

    Dancer dancer;
    ExternalObjectProperty<String> name;
    SimpleStringProperty testname;

    public DancerBinding(DancerShape shape, Dancer dancer) {

    }

    public DancerBinding() {
        this.dancer = new Dancer("dummy", Sex.Male);
        this.name = new ExternalObjectProperty<String>() {
            @Override
            public void setExternalValue(String newValue) {
                dancer.setName(newValue);
            }

            @Override
            public String getExternalValue() {
                return dancer.getName();
            }
        };
    }

    public void testConcept(){

        testname = new SimpleStringProperty("test 1");

        System.out.println("pre binding");
        System.out.println("testname.get() = " + testname.get());
        System.out.println("name.get() = " + name.get());
        System.out.println("----------------------------------");

        //name.bind(testname);
        //testname.bindBidirectional(name);
        name.bindBidirectional(testname);

        System.out.println("post binding/pre set");
        System.out.println("testname.get() = " + testname.get());
        System.out.println("name.get() = " + name.get());
        System.out.println("----------------------------------");

        name.set("dummy 2");

        System.out.println("post set 1");
        System.out.println("testname.get() = " + testname.get());
        System.out.println("name.get() = " + name.get());
        System.out.println("----------------------------------");

        testname.set("test 3");

        System.out.println("post set 2");
        System.out.println("testname.get() = " + testname.get());
        System.out.println("name.get() = " + name.get());
        System.out.println("----------------------------------");
    }

}
