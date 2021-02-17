package com.goregoblin.atombuilder.logic;

import android.content.Intent;
import android.view.View;

import com.goregoblin.atombuilder.R;
import com.goregoblin.atombuilder.gui.AtomActivity;
import com.goregoblin.atombuilder.gui.MainActivity;

public class MainActivityListener implements View.OnClickListener {

    MainActivity mainActivity;

    public MainActivityListener(MainActivity mainActivity) {

        this.mainActivity = mainActivity;

    }


    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.btnNew:
                // hier zum AtomBuilder
                Intent intent = new Intent(mainActivity, AtomActivity.class);
                mainActivity.startActivity(intent);
                break;
        }
    }
}
