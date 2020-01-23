package com.example.choiceproperties.Views.Fragments;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Spinner;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.choiceproperties.R;
import com.example.choiceproperties.Views.dialog.ProgressDialogClass;
import com.example.choiceproperties.repository.LeedRepository;
import com.example.choiceproperties.repository.UserRepository;
import com.example.choiceproperties.repository.impl.LeedRepositoryImpl;
import com.example.choiceproperties.repository.impl.UserRepositoryImpl;

public class Fragment_Add_Customers extends Fragment implements View.OnClickListener {

    EditText inputName, inputMobile, inputpassword, inputAddress, inputPincode;
    Button btnAdd ;



    ProgressDialogClass progressDialogClass;

    UserRepository userRepository;
    LeedRepository leedRepository;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.layout_add_customers, container, false);

        userRepository = new UserRepositoryImpl(getActivity());
        leedRepository = new LeedRepositoryImpl();
        progressDialogClass = new ProgressDialogClass(getActivity());







        return view;

    }

    @Override
    public void onClick(View v) {

        try {

//            if (v == rateCard) {
//                pickImage();
//            }

        } catch (Exception e) {
        }

    }





//    private User fillUserModel() {
//        User user = new User();
//        user.setName(Susername);
//        user.setNumber(Smobilr);
//        user.setAddress(Saddress);
//        user.setPincode(Spincode);
//        user.setPassword(Spassword);
//        user.setRole(Srole);
//        user.setImageList(imageList1);
//        user.setStatus(Constant.USER_STATUS_ACTIVE);
//        user.setUserid(Utility.generateAgentId(AGENT_PREFIX));
//        user.setGeneratedId(Constant.SRVICE_PROVIDER_TABLE_REF.push().getKey());
//
//        return user;
//    }

    private boolean isNetworkAvailable() {
        ConnectivityManager connectivityManager
                = (ConnectivityManager) getActivity().getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
        return activeNetworkInfo != null && activeNetworkInfo.isConnected();
    }
}