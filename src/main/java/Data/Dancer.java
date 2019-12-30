package Data;

import javafx.beans.property.StringProperty;

import java.util.UUID;
import java.util.Vector;

public class Dancer{

	private Data.sex sex;
	private String name;
	private int number;
	private UUID id = UUID.randomUUID();
    private Vector<StringProperty> connectedFields = new Vector<>();

	public Dancer(String name, Data.sex sex){
		new Dancer(name, sex, -1);
	}

	public Dancer(String name, Data.sex sex, int number){
        this.name = name;
        this.sex = sex;
	    this.number = number;
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

    public void setSex(Data.sex sex){
	    this.sex = sex;
    }

	public Data.sex getSex() {
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