package com.example.choiceproperties_sales.Views.Fragments;

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

import com.example.choiceproperties_sales.CallBack.CallBack;
import com.example.choiceproperties_sales.Constant.Constant;
import com.example.choiceproperties_sales.Models.Customer;
import com.example.choiceproperties_sales.R;
import com.example.choiceproperties_sales.Views.dialog.ProgressDialogClass;
import com.example.choiceproperties_sales.repository.UserRepository;
import com.example.choiceproperties_sales.repository.impl.UserRepositoryImpl;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;


public class Fragment_Add_Customers extends Fragment implements View.OnClickListener {

    EditText inputName, inputMobile, inputNote, inputAddress, inputDateTime, inputDiscussion;
    Button btnAdd;
    private DatePickerDialog mDatePickerDialog;
    String fdate;
    int mHour;
    int mMinute;

    ProgressDialogClass progressDialogClass;
    UserRepository userRepository;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.layout_add_customers, container, false);

        userRepository = new UserRepositoryImpl(getActivity());
//        leedRepository = new LeedRepositoryImpl();
        progressDialogClass = new ProgressDialogClass(getActivity());

        inputName = (EditText) view.findViewById(R.id.username);
        inputMobile = (EditText) view.findViewById(R.id.mobilenumber);
        inputNote = (EditText) view.findViewById(R.id.note);
        inputAddress = (EditText) view.findViewById(R.id.address);
        inputDateTime = (EditText) view.findViewById(R.id.date_time);
        inputDiscussion = (EditText) view.findViewById(R.id.discussion);
        btnAdd = (Button) view.findViewById(R.id.add_button);

        btnAdd.setOnClickListener(this);
        setDateTimeField();
        inputDateTime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mDatePickerDialog.show();
            }
        });
        return view;

    }

    @Override
    public void onClick(View v) {

        try {

            if (v == btnAdd) {
                progressDialogClass.showDialog(this.getString(R.string.loading),this.getString(R.string.PLEASE_WAIT));
                Customer customer = fillUserModel();
                CreateCustomer(customer);
            }


        } catch (Exception e) {
        }

    }

    private void CreateCustomer(Customer customer) {
        userRepository.createCustomer(customer, new CallBack() {
            @Override
            public void onSuccess(Object object) {
                Toast.makeText(getContext(), "Customer Added Successfully", Toast.LENGTH_SHORT).show();
                inputName.setText("");
                inputMobile.setText("");
                inputAddress.setText("");
                inputNote.setText("");
                inputDateTime.setText("");
                inputDiscussion.setText("");
                progressDialogClass.dismissDialog();
            }

            @Override
            public void onError(Object object) {

            }
        });

    }


    private Customer fillUserModel() {
        Customer customer = new Customer();
        customer.setName(inputName.getText().toString());
        customer.setAddress(inputAddress.getText().toString());
        customer.setMobile(inputMobile.getText().toString());
        customer.setAttendedBy(inputNote.getText().toString());
        customer.setDateTime(inputDateTime.getText().toString());
        customer.setDiscussion(inputDiscussion.getText().toString());
        customer.setStatus(Constant.STATUS_REQUEST_VISITED);
        customer.setCustomerId(Constant.CUSTOMERS_TABLE_REF.push().getKey());

        return customer;
    }


    private void setDateTimeField() {
        Calendar newCalendar = Calendar.getInstance();
        mDatePickerDialog = new DatePickerDialog(getContext(), new DatePickerDialog.OnDateSetListener() {

            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                Calendar newDate = Calendar.getInstance();
                newDate.set(year, monthOfYear, dayOfMonth);
                SimpleDateFormat sd = new SimpleDateFormat("dd-MM-yyyy");
                final Date startDate = newDate.getTime();
                fdate = sd.format(startDate);

                timePicker();
            }

            private void timePicker() {
                // Get Current Time
                final Calendar c = Calendar.getInstance();
                mHour = c.get(Calendar.HOUR_OF_DAY);
                mMinute = c.get(Calendar.MINUTE);

                // Launch Time Picker Dialog
                TimePickerDialog timePickerDialog = new TimePickerDialog(getContext(),
                        new TimePickerDialog.OnTimeSetListener() {

                            @Override
                            public void onTimeSet(TimePicker view, int hourOfDay, int minute) {

                                mHour = hourOfDay;
                                mMinute = minute;

                                inputDateTime.setText(fdate + " " + hourOfDay + ":" + minute);
                            }
                        }, mHour, mMinute, false);
                timePickerDialog.show();
            }
        }, newCalendar.get(Calendar.YEAR), newCalendar.get(Calendar.MONTH), newCalendar.get(Calendar.DAY_OF_MONTH));

    }

    private boolean isNetworkAvailable() {
        ConnectivityManager connectivityManager
                = (ConnectivityManager) getActivity().getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
        return activeNetworkInfo != null && activeNetworkInfo.isConnected();
    }
}