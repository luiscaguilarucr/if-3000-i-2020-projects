package edu.ucr.rp.programacion2.proyecto.util.inventorycontrol;

public class Configuration {
    //  Variables  \\
    private int id;
    //  Constructor  \\

    public Configuration() {
    }

    public Configuration(int id) {
        this.id = id;
    }
    //  Methods  \\
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Configuration that = (Configuration) o;
        return id == that.id;
    }

    @Override
    public String toString() {
        return "Configuration{" +
                "id=" + id +
                '}';
    }
}
