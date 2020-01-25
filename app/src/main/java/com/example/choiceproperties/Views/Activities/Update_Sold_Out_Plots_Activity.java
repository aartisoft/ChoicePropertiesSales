package com.example.choiceproperties.Views.Activities;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;

import com.example.choiceproperties.Constant.Constant;
import com.example.choiceproperties.Models.Plots;
import com.example.choiceproperties.R;
import com.example.choiceproperties.Views.dialog.ProgressDialogClass;
import com.example.choiceproperties.repository.UserRepository;
import com.example.choiceproperties.utilities.Utility;

import java.util.ArrayList;

public class Update_Sold_Out_Plots_Activity extends AppCompatActivity {

    Plots plots;
    EditText inputPlotNumber, inputCustomerName, inputSalePrice, inputDepositAmount, inputRemainingAmount, inputInstallment,
            inputPaidAmount, inputAgentName;
    RadioGroup GroupInsatllment, GroupComission;
    RadioButton Rinstallment, Rcomission, radioCash, radioBank, radioPaid, radioUnpaid;
    Button btnAdd;
    String Sinstallment, Scomission;

    ProgressDialogClass progressDialogClass;
    UserRepository userRepository;
    ArrayList<Plots> plotsArrayList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update__sold__out__plots_);

        plots = (Plots) getIntent().getSerializableExtra(Constant.PLOTS);

        inputPlotNumber = (EditText) findViewById(R.id.plot_number);
        inputCustomerName = (EditText) findViewById(R.id.customer_name);
        inputSalePrice = (EditText) findViewById(R.id.plot_salling_price);
        inputDepositAmount = (EditText) findViewById(R.id.deposit_amount);
        inputRemainingAmount = (EditText) findViewById(R.id.remaining_amount);
        inputInstallment = (EditText) findViewById(R.id.installment);
        inputPaidAmount = (EditText) findViewById(R.id.paid);
        inputAgentName = (EditText) findViewById(R.id.agent_name);

        GroupInsatllment = (RadioGroup) findViewById(R.id.group_installment_type);
        GroupComission = (RadioGroup) findViewById(R.id.group_agent_comission);
        radioCash = (RadioButton) findViewById(R.id.cash);
        radioPaid = (RadioButton) findViewById(R.id.comissionpaid);
        radioBank = (RadioButton) findViewById(R.id.bank);
        radioUnpaid = (RadioButton) findViewById(R.id.comissionunpaid);

        btnAdd = (Button) findViewById(R.id.add_button);
        GroupInsatllment.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int checkedId) {
                Rinstallment = (RadioButton) findViewById(checkedId);
                Sinstallment = Rinstallment.getText().toString();

            }
        });
        if (GroupInsatllment.getCheckedRadioButtonId() != -1) {
            Sinstallment = ((RadioButton) GroupInsatllment.getChildAt(GroupInsatllment.indexOfChild(GroupInsatllment.findViewById(GroupInsatllment.getCheckedRadioButtonId())))).getText().toString();

        }
        GroupComission.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int checkedId) {
                Rcomission = (RadioButton) findViewById(checkedId);
                Scomission = Rcomission.getText().toString();

            }
        });
        if (GroupComission.getCheckedRadioButtonId() != -1) {
            Scomission = ((RadioButton) GroupComission.getChildAt(GroupComission.indexOfChild(GroupComission.findViewById(GroupComission.getCheckedRadioButtonId())))).getText().toString();
        }

        getData();
    }

    private void getData() {

        String plotNumber = plots.getPlotnumber();
        String agentname = plots.getAgentName();
        String ComissionStatus = plots.getComissionStatus();
        String customerName = plots.getCustomerNmae();
        String depositAmount = plots.getDepositAmount();
        String installment = plots.getInstallment();
        String installmentType = plots.getInstallmentType();
        String paymentAmount = plots.getPayedAmount();
        String plotPrice = plots.getPlotPrice();
        String remainingAmount = plots.getRemainingAmount();

//                .setText(": "+ Utility.convertMilliSecondsToFormatedDate(plots.getCreatedDateTimeLong(), Constant.GLOBAL_DATE_FORMATE));

        if (plotNumber != null) {
            inputPlotNumber.setText(plotNumber);
        }
        if (agentname != null) {
            inputAgentName.setText(agentname);
        }
        if (ComissionStatus != null) {
            if (ComissionStatus.equalsIgnoreCase("Paid")) {
                radioPaid.setChecked(true);
            } else if (ComissionStatus.equalsIgnoreCase("Unpaid")) {
                radioUnpaid.setChecked(true);
            }
        }
        if (customerName != null) {
            inputCustomerName.setText(customerName);
        }
        if (depositAmount != null) {
            inputDepositAmount.setText(depositAmount);
        }
        if (installment != null) {
            inputInstallment.setText(installment);
        }
        if (installmentType != null) {
            if (installmentType.equalsIgnoreCase("Cash")) {
                radioCash.setChecked(true);
            } else if (installmentType.equalsIgnoreCase("Bank")) {
                radioBank.setChecked(true);
            }
        }
        if (paymentAmount != null) {
            inputPaidAmount.setText(paymentAmount);
        }
        if (plotPrice != null) {
            inputSalePrice.setText(plotPrice);
        }
        if (remainingAmount != null) {
            inputRemainingAmount.setText(remainingAmount);
        }

    }
}
