package tech.krds.warnetlauncher;

import android.graphics.drawable.Drawable;

public class FavoriteModel {
    String id;
    String label;
    String packageName;
    Drawable icon;


    public String getLabel() {
        return label;
    }

    public void setLabel(String label) {
        this.label = label;
    }

    public String getPackageName() {
        return packageName;
    }

    public void setPackageName(String packageName) {
        this.packageName = packageName;
    }

    public Drawable getIcon() {
        return icon;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public void setIcon(Drawable icon) {
        this.icon = icon;
    }

    public FavoriteModel(String id,String label, String packageName, Drawable icon) {
this.id = id;
        this.label = label;
        this.packageName = packageName;
        this.icon = icon;

    }



}