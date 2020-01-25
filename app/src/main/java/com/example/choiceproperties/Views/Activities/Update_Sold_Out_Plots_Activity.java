package com.example.choiceproperties.Views.Activities;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.example.choiceproperties.Constant.Constant;
import com.example.choiceproperties.Models.Plots;
import com.example.choiceproperties.R;

public class Update_Sold_Out_Plots_Activity extends AppCompatActivity {

    Plots plots;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update__sold__out__plots_);

        plots = (Plots) getIntent().getSerializableExtra(Constant.PLOTS);
    }
}
