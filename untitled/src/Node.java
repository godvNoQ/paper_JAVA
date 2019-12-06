public class Node {
   private int id;
   private String label;
   private int external;
   private int entrypoint;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getLabel() {
        return label;
    }

    public void setLabel(String label) {
        this.label = label;
    }

    public int getExternal() {
        return external;
    }

    public void setExternal(int external) {
        this.external = external;
    }

    public int getEntrypoint() {
        return entrypoint;
    }

    public void setEntrypoint(int entrypoint) {
        this.entrypoint = entrypoint;
    }
}
