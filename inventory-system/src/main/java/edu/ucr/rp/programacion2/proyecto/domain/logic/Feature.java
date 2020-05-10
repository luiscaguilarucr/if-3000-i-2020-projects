package edu.ucr.rp.programacion2.proyecto.domain.logic;
/**
 * @author Luis Carlos Aguilar Morales
 * @author Jeison David Araya Mena
 * This class generates a new feature object and gives it characteristics (value and mandatory)
 * */
public class Feature {
    private String name;
    private Object value;
    private boolean mandatory;

    public Feature(String name, Object value, boolean mandatory) {
        this.name = name;
        this.value = value;
        this.mandatory = mandatory;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Object getValue() {
        return value;
    }

    public void setValue(Object value) {
        this.value = value;
    }

    public boolean isMandatory() {
        return mandatory;
    }

    public void setMandatory(boolean mandatory) {
        this.mandatory = mandatory;
    }

    @Override
    public String toString() {
        return "Feature{" +
                "name='" + name + '\'' +
                ", value=" + value +
                ", mandatory=" + mandatory +
                '}';
    }
}
