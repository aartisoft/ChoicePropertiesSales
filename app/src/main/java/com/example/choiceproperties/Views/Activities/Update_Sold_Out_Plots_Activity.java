package com.example.choiceproperties.Views.Activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.TextView;

import com.example.choiceproperties.CallBack.CallBack;
import com.example.choiceproperties.Constant.Constant;
import com.example.choiceproperties.Models.Plots;
import com.example.choiceproperties.Models.User;
import com.example.choiceproperties.R;
import com.example.choiceproperties.Views.dialog.ProgressDialogClass;
import com.example.choiceproperties.repository.LeedRepository;
import com.example.choiceproperties.repository.UserRepository;
import com.example.choiceproperties.repository.impl.LeedRepositoryImpl;
import com.example.choiceproperties.utilities.Utility;

import java.util.ArrayList;
import java.util.Map;

public class Update_Sold_Out_Plots_Activity extends AppCompatActivity implements View.OnClickListener {

    Plots plots;
    EditText inputPlotNumber, inputCustomerName, inputSalePrice, inputDepositAmount, inputRemainingAmount, inputInstallment,
            inputPaidAmount, inputAgentName;
    RadioGroup GroupInsatllment, GroupComission;
    RadioButton Rinstallment, Rcomission, radioCash, radioBank, radioPaid, radioUnpaid;
    Button btnAdd;
    String Sinstallment, Scomission;
    TextView txtPAmount,txtRamount;

    ProgressDialogClass progressDialogClass;
    UserRepository userRepository;
    LeedRepository leedRepository;
    ArrayList<Plots> plotsArrayList;

    String previousRemainingAmount,previousPayedAmount;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update__sold__out__plots_);

        plots = (Plots) getIntent().getSerializableExtra(Constant.PLOTS);

        leedRepository = new LeedRepositoryImpl();

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

        txtPAmount = (TextView) findViewById(R.id.Pamount);
        txtRamount = (TextView) findViewById(R.id.Ramount);

        btnAdd = (Button) findViewById(R.id.add_button);
        btnAdd.setOnClickListener(this);
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
            txtPAmount.setText(paymentAmount);
        }
        if (plotPrice != null) {
            inputSalePrice.setText(plotPrice);
        }
        if (remainingAmount != null) {
            inputRemainingAmount.setText(remainingAmount);
            txtRamount.setText(remainingAmount);
        }

    }

    @Override
    public void onClick(View view) {
        if (view == btnAdd){
            setLeedStatus(plots);
        }
    }

    private void setLeedStatus(Plots plots) {
        plots.setPlotnumber(inputPlotNumber.getText().toString());
        plots.setCustomerNmae(inputCustomerName.getText().toString());
        plots.setPlotPrice(inputSalePrice.getText().toString());
        plots.setDepositAmount(inputDepositAmount.getText().toString());

        plots.setRemainingAmount(inputPlotNumber.getText().toString());
        plots.setInstallment(inputPlotNumber.getText().toString());

        plots.setInstallmentType(Sinstallment);

        plots.setPayedAmount(inputPlotNumber.getText().toString());

        plots.setAgentName(inputAgentName.getText().toString());
        plots.setComissionStatus(Scomission);
        updateLeed(plots.getPloteId(), plots.toMap());
    }

    private void updateLeed(String leedId, Map leedsMap) {

        leedRepository = new LeedRepositoryImpl();
        leedRepository.updatePlot(leedId, leedsMap, new CallBack() {
            @Override
            public void onSuccess(Object object) {


            }

            @Override
            public void onError(Object object) {

                Utility.showLongMessage(getApplication(), getString(R.string.server_error));

            }
        });
    }
}
