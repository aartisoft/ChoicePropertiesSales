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
import android.widget.RadioButton;
import android.widget.RadioGroup;
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
import java.util.Calendar;
import java.util.Date;

public class Fragment_Sale_Plots extends Fragment implements View.OnClickListener {

    EditText inputPlotnumber, inputCustomerName, inputSalePrice, inputDepositAmount, inputRemainingAmount, inputInstallment,
            inputPaidAmount, inputAgentName;
    RadioGroup GroupInsatllment, GroupComission;
    RadioButton Rinstallment, Rcomission, radioCash, radioPaid;
    Button btnAdd;
    String Sinstallment, Scomission;

    ProgressDialogClass progressDialogClass;
    UserRepository userRepository;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        final View view = inflater.inflate(R.layout.layout_sale_plots, container, false);

        userRepository = new UserRepositoryImpl(getActivity());
        progressDialogClass = new ProgressDialogClass(getActivity());

        inputPlotnumber = (EditText) view.findViewById(R.id.plot_number);
        inputCustomerName = (EditText) view.findViewById(R.id.customer_name);
        inputSalePrice = (EditText) view.findViewById(R.id.plot_salling_price);
        inputDepositAmount = (EditText) view.findViewById(R.id.deposit_amount);
        inputRemainingAmount = (EditText) view.findViewById(R.id.remaining_amount);
        inputInstallment = (EditText) view.findViewById(R.id.installment);
        inputPaidAmount = (EditText) view.findViewById(R.id.paid);
        inputAgentName = (EditText) view.findViewById(R.id.agent_name);

        GroupInsatllment = (RadioGroup) view.findViewById(R.id.group_installment_type);
        GroupComission = (RadioGroup) view.findViewById(R.id.group_agent_comission);
        radioCash = (RadioButton) view.findViewById(R.id.cash);
        radioPaid = (RadioButton) view.findViewById(R.id.comissionpaid);

        btnAdd = (Button) view.findViewById(R.id.add_button);

        btnAdd.setOnClickListener(this);
        GroupInsatllment.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int checkedId) {
                Rinstallment = (RadioButton) view.findViewById(checkedId);
                Sinstallment = Rinstallment.getText().toString();

            }
        });
        if (GroupInsatllment.getCheckedRadioButtonId() != -1) {
            Sinstallment = ((RadioButton) GroupInsatllment.getChildAt(GroupInsatllment.indexOfChild(GroupInsatllment.findViewById(GroupInsatllment.getCheckedRadioButtonId())))).getText().toString();

        }
        GroupComission.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int checkedId) {
                Rcomission = (RadioButton) view.findViewById(checkedId);
                Scomission = Rcomission.getText().toString();

            }
        });
        if (GroupComission.getCheckedRadioButtonId() != -1) {
            Scomission = ((RadioButton) GroupComission.getChildAt(GroupComission.indexOfChild(GroupComission.findViewById(GroupComission.getCheckedRadioButtonId())))).getText().toString();
        }

        return view;

    }

    @Override
    public void onClick(View v) {

        try {

            if (v == btnAdd) {
                progressDialogClass.showDialog(this.getString(R.string.loading), this.getString(R.string.PLEASE_WAIT));
                Plots plots = fillUserModel();
                SalePlot(plots);
            }


        } catch (Exception e) {
        }

    }

    private void SalePlot(Plots plots) {
        userRepository.salePlot(plots, new CallBack() {
            @Override
            public void onSuccess(Object object) {
                Toast.makeText(getContext(), "Plot Sold Successfully", Toast.LENGTH_SHORT).show();
                inputPlotnumber.setText("");
                inputCustomerName.setText("");
                inputSalePrice.setText("");
                inputDepositAmount.setText("");
                inputRemainingAmount.setText("");
                inputInstallment.setText("");
                inputPaidAmount.setText("");
                inputAgentName.setText("");
                radioCash.setChecked(true);
                radioPaid.setChecked(true);
                progressDialogClass.dismissDialog();
            }

            @Override
            public void onError(Object object) {
                progressDialogClass.dismissDialog();
            }
        });

    }


    private Plots fillUserModel() {
        Plots plots = new Plots();
        plots.setPlotnumber(inputPlotnumber.getText().toString());
        plots.setCustomerNmae(inputCustomerName.getText().toString());
        plots.setPlotPrice(inputSalePrice.getText().toString());
        plots.setDepositAmount(inputDepositAmount.getText().toString());
        plots.setRemainingAmount(inputRemainingAmount.getText().toString());
        plots.setInstallment(inputInstallment.getText().toString());
        plots.setInstallmentType(Sinstallment);
        plots.setPayedAmount(inputPaidAmount.getText().toString());
        plots.setAgentName(inputAgentName.getText().toString());
        plots.setComissionStatus(Scomission);
        plots.setStatus(Constant.STATUS_PLOT_SOLD);

        plots.setPloteId(Constant.SOLD_PLOT_TABLE_REF.push().getKey());

        return plots;
    }


    private boolean isNetworkAvailable() {
        ConnectivityManager connectivityManager
                = (ConnectivityManager) getActivity().getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
        return activeNetworkInfo != null && activeNetworkInfo.isConnected();
    }
}