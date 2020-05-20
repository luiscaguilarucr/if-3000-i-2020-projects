package edu.ucr.rp.programacion2.proyecto.domain.logic;

public class Feature {
    private String name;
    private Object value;

    /**
     * Object Feature that contains a name and quantity
     * @param name fature name
     * @param value stock quantity
     */
    public Feature(String name, Object value) {
        this.name = name;
        this.value = value;
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

    @Override
    public String toString() {
        return "Feature{" +
                "name='" + name + '\'' +
                ", value=" + value +
                '}';
    }
}
