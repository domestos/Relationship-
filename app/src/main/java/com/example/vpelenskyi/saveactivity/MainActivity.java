package com.example.vpelenskyi.saveactivity;

import android.support.v4.app.FragmentActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;

public class MainActivity extends FragmentActivity implements View.OnClickListener {
    private Button btnStart;
    private Button btnStop;
    public TextView tvUpdate;
    private ProgressBar progressBar;
    private MainFragment mainFragment;
    private String TAG_FRAGMENT = "my_fragment";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        tvUpdate = (TextView) findViewById(R.id.tvUpdate);
        btnStart = (Button) findViewById(R.id.btnStart);
        btnStop = (Button) findViewById(R.id.btnStop);
        progressBar = (ProgressBar) findViewById(R.id.progress);
        btnStop.setOnClickListener(this);
        btnStart.setOnClickListener(this);

        mainFragment = getMainFragment();
        //Передаємо об"єкту MainFragment актівіті
        mainFragment.link(this);
        // визначаємо в якому стані AsyncTask
        // mainFragment.getIsWork() - повертає true якщо виконується процес, false - якщо він в стані спокою
        showProgress(mainFragment.getIsWork());
    }


    public MainFragment getMainFragment() {
        //перевіряємо чи існує фрагмент з такою міткою TAG_FRAGMENT
        mainFragment = (MainFragment) getFragmentManager().findFragmentByTag(TAG_FRAGMENT);
        //якщо перевірка верне null, то означає,
        // що фрагмент раніше не був створений і його нобхідно створити
        if (mainFragment == null) {
            //створюємо новий екземпляр класу
            mainFragment = new MainFragment();
            //записуємо створений екземпляр MainFragment у FragmentManager  і даємо йому мітку TAG_FRAGMENT
            getFragmentManager().beginTransaction().add(mainFragment, TAG_FRAGMENT).commit();
        }
        return mainFragment;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btnStart:
                mainFragment.start();
                break;
            case R.id.btnStop:
                mainFragment.stop();
                break;
        }
    }

    // показує стан AsyncTasks
    public void showProgress(boolean show) {
        btnStart.setEnabled(!show);
        btnStop.setEnabled(show);
        progressBar.setVisibility(show ? View.VISIBLE : View.GONE);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();

        // завершує роботу AsyncTask якщо нажата кнопка Back
        if (isFinishing()) {
            mainFragment.stop();
        }
    }


}
