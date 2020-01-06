package tech.krds.warnetlauncher;

import java.util.List;

public interface ESLThreadListener {

    public List onBackground();

    public void onPostExecute(List list);

}