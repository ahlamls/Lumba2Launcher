package tech.krds.warnetlauncher;

import android.os.AsyncTask;

import java.util.List;

public class ESLThread extends AsyncTask<Void, Void, List> {


    private ESLThreadListener mListener;

    public ESLThread() {

        if (mListener != null) {

            mListener.onBackground();
        }
    }

    @Override
    protected List doInBackground(Void... params) {

        if (mListener != null) {

            List list = mListener.onBackground();

            return list;
        }

        return null;
    }

    @Override
    protected void onPostExecute(List t) {
        if (mListener != null) {

            if (t != null) {
                mListener.onPostExecute(t);
            }
        }

    }

    public void setListener(ESLThreadListener mListener){

        this.mListener = mListener;
    }
}