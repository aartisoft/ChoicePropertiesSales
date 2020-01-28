package com.example.choiceproperties_sales.Views.Activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.choiceproperties_sales.CallBack.CallBack;
import com.example.choiceproperties_sales.Constant.Constant;
import com.example.choiceproperties_sales.Models.Plots;
import com.example.choiceproperties_sales.Models.User;
import com.example.choiceproperties_sales.R;
import com.example.choiceproperties_sales.Views.dialog.ProgressDialogClass;
import com.example.choiceproperties_sales.repository.LeedRepository;
import com.example.choiceproperties_sales.repository.UserRepository;
import com.example.choiceproperties_sales.repository.impl.LeedRepositoryImpl;
import com.example.choiceproperties_sales.utilities.Utility;

import java.util.ArrayList;
import java.util.Map;

public class Update_Sold_Out_Plots_Activity extends AppCompatActivity implements View.OnClickListener {

    Plots plots;
    EditText inputPlotNumber, inputCustomerName, inputSalePrice, inputDepositAmount, inputInstallment,
            inputAgentName;
    RadioGroup GroupInsatllment, GroupComission;
    RadioButton Rinstallment, Rcomission, radioCash, radioBank, radioPaid, radioUnpaid;
    Button btnAdd;
    String Sinstallment, Scomission;
    TextView txtPAmount, txtRamount,txtTamount;

    ProgressDialogClass progressDialogClass;
    UserRepository userRepository;
    LeedRepository leedRepository;
    ArrayList<Plots> plotsArrayList;
    int previousRemainingAmount, previousPayedAmount;

    @Override
    public boolean onSupportNavigateUp() {
        finish();
        return true;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update__sold__out__plots_);

        assert getSupportActionBar() != null;   //null check
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        progressDialogClass = new ProgressDialogClass(this);
        leedRepository = new LeedRepositoryImpl();

        plots = (Plots) getIntent().getSerializableExtra(Constant.PLOTS);

        previousPayedAmount = Integer.parseInt(plots.getPayedAmount());
        previousRemainingAmount = Integer.parseInt(plots.getRemainingAmount());

        inputPlotNumber = (EditText) findViewById(R.id.plot_number);
        inputCustomerName = (EditText) findViewById(R.id.customer_name);
        inputSalePrice = (EditText) findViewById(R.id.plot_salling_price);
        inputDepositAmount = (EditText) findViewById(R.id.deposit_amount);
        inputInstallment = (EditText) findViewById(R.id.installment);
        inputAgentName = (EditText) findViewById(R.id.agent_name);

        GroupInsatllment = (RadioGroup) findViewById(R.id.group_installment_type);
        GroupComission = (RadioGroup) findViewById(R.id.group_agent_comission);
        radioCash = (RadioButton) findViewById(R.id.cash);
        radioPaid = (RadioButton) findViewById(R.id.comissionpaid);
        radioBank = (RadioButton) findViewById(R.id.bank);
        radioUnpaid = (RadioButton) findViewById(R.id.comissionunpaid);

        txtPAmount = (TextView) findViewById(R.id.Pamount);
        txtRamount = (TextView) findViewById(R.id.Ramount);
        txtTamount = (TextView) findViewById(R.id.Tamount);

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

//        Animation anim = new AlphaAnimation(0.0f, 1.0f);
//        anim.setDuration(250); //You can manage the blinking time with this parameter
//        anim.setStartOffset(20);
//        anim.setRepeatMode(Animation.REVERSE);
//        anim.setRepeatCount(Animation.INFINITE);
//        txtRamount.startAnimation(anim);

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
        String plotPrice = plots.getPlotPrice();
        String remainingAmount = plots.getRemainingAmount();
        String payedAmount = plots.getPayedAmount();
//                .setText(": "+ Utility.convertMilliSecondsToFormatedDate(plots.getCreatedDateTimeLong(), Constant.GLOBAL_DATE_FORMATE));


        if (remainingAmount != null) {
            txtRamount.setText(remainingAmount);
        }
        if (payedAmount != null) {
            txtPAmount.setText(payedAmount);
        }

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
        if (plotPrice != null) {
            inputSalePrice.setText(plotPrice);
            txtTamount.setText(plotPrice);
        }

    }

    @Override
    public void onClick(View view) {
        if (view == btnAdd) {
            setLeedStatus(plots);
        }
    }

    private void setLeedStatus(Plots plots) {

        int afterpayed = previousPayedAmount + Integer.parseInt(inputInstallment.getText().toString());
        int afterremained = previousRemainingAmount - Integer.parseInt(inputInstallment.getText().toString());

        plots.setPlotnumber(inputPlotNumber.getText().toString());
        plots.setCustomerNmae(inputCustomerName.getText().toString());
        plots.setPlotPrice(inputSalePrice.getText().toString());
        plots.setDepositAmount(inputDepositAmount.getText().toString());
        plots.setRemainingAmount(String.valueOf(afterremained));
        plots.setInstallment(inputInstallment.getText().toString());
        plots.setInstallmentType(Sinstallment);
        plots.setPayedAmount(String.valueOf(afterpayed));
        plots.setAgentName(inputAgentName.getText().toString());
        plots.setComissionStatus(Scomission);
        plots.setDepositType(plots.getDepositType());
        plots.setCreatedDateTime(plots.getCreatedDateTimeLong());
        updateLeed(plots.getPloteId(), plots.toMap());
    }

    private void updateLeed(String leedId, Map leedsMap) {
        progressDialogClass.showDialog(String.valueOf(R.string.loading), String.valueOf(R.string.PLEASE_WAIT));
        leedRepository = new LeedRepositoryImpl();
        leedRepository.updatePlot(leedId, leedsMap, new CallBack() {
            @Override
            public void onSuccess(Object object) {
                Toast.makeText(Update_Sold_Out_Plots_Activity.this, "Plot Updated SuccessFully", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(Update_Sold_Out_Plots_Activity.this, Main2Activity.class);
                startActivity(intent);
                progressDialogClass.dismissDialog();
            }

            @Override
            public void onError(Object object) {

                Utility.showLongMessage(getApplication(), getString(R.string.server_error));
                progressDialogClass.dismissDialog();

            }
        });
    }
}
