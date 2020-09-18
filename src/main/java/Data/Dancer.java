package Data;

import javafx.beans.property.StringProperty;

import java.util.UUID;
import java.util.Vector;

public class Dancer{

	private Sex sex;
	private String name;
	private int number;
	private UUID id;
    private Vector<StringProperty> connectedFields = new Vector<>();

	public Dancer(String name, Sex sex){
		new Dancer(name, sex, -1);
	}

	public Dancer(String name, Sex sex, int number){
        this.name = name;
        this.sex = sex;
	    this.number = number;
        id = UUID.randomUUID();
    }

    public void connectField(StringProperty field){
        connectedFields.add(field);
    }

    public void clearConnectedFields(){
        connectedFields.clear();
    }

    public void disconnectField(StringProperty field){
        connectedFields.remove(field);
    }

    public String print() {
        return "Dancer "+ getName();
    }

    public void setSex(Sex sex){
	    this.sex = sex;
    }

	public Sex getSex() {
		return sex;
	}

    public void setName(String n){
        this.name = n;
        for(StringProperty field : connectedFields){
            field.setValue(name);
        }
    }

    public int getNumber(){
	    return number;
    }

    public UUID getId(){
	    return id;
    }

	public String getName() {
		return name;
	}

	@Override
	public String toString() {
		return getName();
	}
}