package com.example.choiceproperties_sales.Views.Fragments;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.choiceproperties_sales.R;
import com.example.choiceproperties_sales.Views.Activities.Activity_Add_Customers;

public class Fragment_Add_Customer extends Fragment {


    private ProgressDialog progressDialog;
    ImageView AddProduct;


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_add_products, container, false);


        AddProduct = (ImageView) view.findViewById(R.id.addProduct);
        AddProduct.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getContext(), Activity_Add_Customers.class);
                startActivity(intent);
                getActivity().overridePendingTransition(R.anim.down_to_up, R.anim.down_to_up);
            }
        });


        return view;
    }
}
