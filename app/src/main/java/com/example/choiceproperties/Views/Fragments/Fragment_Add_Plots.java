package com.example.choiceproperties.Views.Fragments;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TimePicker;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.choiceproperties.CallBack.CallBack;
import com.example.choiceproperties.Constant.Constant;
import com.example.choiceproperties.Models.Customer;
import com.example.choiceproperties.Models.Plots;
import com.example.choiceproperties.R;
import com.example.choiceproperties.Views.dialog.ProgressDialogClass;
import com.example.choiceproperties.repository.UserRepository;
import com.example.choiceproperties.repository.impl.UserRepositoryImpl;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

public class Fragment_Add_Plots extends Fragment implements View.OnClickListener {

    EditText inputPlotNumber, inputPlotArea;
    Button btnAdd;
    ArrayList<Plots> plotList;

    ProgressDialogClass progressDialogClass;
    UserRepository userRepository;


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.layout_add_plots, container, false);

        userRepository = new UserRepositoryImpl(getActivity());
        progressDialogClass = new ProgressDialogClass(getActivity());

        plotList = new ArrayList<>();

        inputPlotNumber = (EditText) view.findViewById(R.id.plot_no);
        inputPlotArea = (EditText) view.findViewById(R.id.plot_area);

        btnAdd = (Button) view.findViewById(R.id.add_button);

        btnAdd.setOnClickListener(this);

        readPlots();

        return view;

    }

    private void readPlots() {
        userRepository.readPlots(new CallBack() {
            @Override
            public void onSuccess(Object object) {
                if (object != null) {
                    plotList = (ArrayList<Plots>) object;
                }
            }

            @Override
            public void onError(Object object) {

            }
        });
    }

    @Override
    public void onClick(View v) {

        try {

            if (v == btnAdd) {

                String plotNumber = inputPlotNumber.getText().toString();
                boolean productPresent = false;

                try {

                    for (int i = 0; i <= plotList.size(); i++) {
                        String mainpro = (plotList.get(i).getPlotnumber());


                        if (plotNumber.equals(mainpro)) {
                            productPresent = true;
                            break;
                        }

                    }
                } catch (Exception e) {
                }

                if (!productPresent) {
                    progressDialogClass.showDialog(this.getString(R.string.loading), this.getString(R.string.PLEASE_WAIT));
                    Plots plots = fillUserModel();
                    CreateCustomer(plots);
                } else {

                    Toast.makeText(getContext(), "Plot Number Allready Exist!", Toast.LENGTH_SHORT).show();
                }
            }


        } catch (Exception e) {
        }

    }

    private void CreateCustomer(Plots plots) {
        userRepository.createPlot(plots, new CallBack() {
            @Override
            public void onSuccess(Object object) {
                Toast.makeText(getContext(), "Plot Added Successfully", Toast.LENGTH_SHORT).show();
                inputPlotNumber.setText("");
                inputPlotArea.setText("");
                progressDialogClass.dismissDialog();
            }

            @Override
            public void onError(Object object) {

            }
        });

    }


    private Plots fillUserModel() {
        Plots plots = new Plots();
        plots.setPlotnumber(inputPlotNumber.getText().toString());
        plots.setPlotarea(inputPlotArea.getText().toString());
        plots.setStatus(Constant.STATUS_PLOT_AVAILABLE);
        plots.setPloteId(Constant.CUSTOMERS_TABLE_REF.push().getKey());

        return plots;
    }


    private boolean isNetworkAvailable() {
        ConnectivityManager connectivityManager
                = (ConnectivityManager) getActivity().getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
        return activeNetworkInfo != null && activeNetworkInfo.isConnected();
    }
}