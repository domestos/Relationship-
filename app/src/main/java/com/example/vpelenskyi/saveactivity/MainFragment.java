package com.example.vpelenskyi.saveactivity;

import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;

/**
 * Created by v.pelenskyi on 01.03.2016.
 */
public class MainFragment extends android.app.Fragment {
    private boolean isWork = false; //інформує актівіті про стан AsyncTask
    private FragmentTask fragmentTask;
    private MainActivity activity;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRetainInstance(true);//заборона перестворювати фрагмент при повороті екрану
    }

    /**
     * Даний метод викликається в методі onCreate() класу MainActivity,
     * тому ми завжди будемо мати актуальний activity, навіть при повороті екрану.
     */
    public MainActivity link(MainActivity activity) {
        return this.activity = activity;
    }

    /**
     * цей метод демонструє звязок між класом MainFragment та MainActivity
     * але замість ньоги ми будемо використовувати метод link(...).
     */
    public MainActivity getMainActvity() {
        activity = (MainActivity) this.getActivity();
        return activity;
    }

    public void start() {

        fragmentTask = new FragmentTask();
        fragmentTask.execute();
    }

    public void stop() {
        if (fragmentTask != null) {
            fragmentTask.cancel(true);
        }
    }

    public boolean getIsWork() {
        return isWork;
    }

    /**
     *   Довгограючий клас :)
     */
    public class FragmentTask extends AsyncTask<Void, Integer, Boolean> {
        @Override
        protected void onPreExecute() {
            isWork = true;
            activity.showProgress(isWork);
        }

        @Override
        protected Boolean doInBackground(Void... params) {
            try {
                for (int i = 1000; i >= 0; i--) {
                    publishProgress(i);
                    Thread.sleep(10);
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            return false;
        }

        @Override
        protected void onProgressUpdate(Integer... values) {
            super.onProgressUpdate(values);
            // activity = getMainActvity();
            activity.tvUpdate.setText(values[0].toString());
        }

        @Override
        protected void onPostExecute(Boolean aBoolean) {
            //   activity = getMainActvity();
            activity.showProgress(false);
            isWork = false;
            fragmentTask = null;
        }

        @Override
        protected void onCancelled() {
            Log.i("log", "cancelled");
            isWork = false;
            activity.showProgress(isWork);
            super.onCancelled();
        }
    }

}
