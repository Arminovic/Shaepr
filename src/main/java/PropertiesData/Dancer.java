package PropertiesData;

import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

import java.util.UUID;
import java.util.Vector;

public class Dancer{

	private SimpleObjectProperty<Sex> sex;
	private SimpleStringProperty name;
	private SimpleIntegerProperty number;
	private final UUID id;

	public Dancer(String name, Sex sex){
        this(name,sex,-1);
	}

	public Dancer(String name, Sex sex, int number){
        this.name = new SimpleStringProperty(name);
        this.sex = new SimpleObjectProperty<Sex>(sex);
	    this.number = new SimpleIntegerProperty(number);
        id = UUID.randomUUID();
    }

    public String print() {
        return "Dancer "+ getName();
    }

    public void setName(String pName){
        this.name.set(pName);
    }

    public String getName() {
        return name.getName();
    }

    public void setSex(Sex pSex){
	    this.sex.set(pSex);
    }

	public Sex getSex() {
		return sex.get();
	}

	public void setNumber(int pNumber){
	    this.number.set(pNumber);
    }

    public int getNumber(){
	    return number.get();
    }

    public UUID getId(){
	    return id;
    }

    public SimpleStringProperty getNameProperty(){
	    return name;
    }

    public SimpleIntegerProperty getNumberProperty(){
	    return number;
    }

    public SimpleObjectProperty<Sex> getSexProperty(){
	    return sex;
    }

	@Override
	public String toString() {
		return getName();
	}
}