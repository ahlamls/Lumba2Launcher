package tech.krds.warnetlauncher;

public class catatanModels {

    int id;
    String packageName;
            ;
    // contrustor(empty)
    public catatanModels() {
    }

    // constructor
    public catatanModels(int id, String packageName) {
        this.id = id;
        this.packageName = packageName;
    }

    /*Setter and Getter*/

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getPackageName() {
        return packageName;
    }

    public void setPackageName(String packageName) {
        this.packageName = packageName;
    }
}