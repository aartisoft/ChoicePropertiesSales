package com.example.choiceproperties_sales.Views.Fragments;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.choiceproperties_sales.CallBack.CallBack;
import com.example.choiceproperties_sales.Constant.Constant;
import com.example.choiceproperties_sales.Models.Plots;
import com.example.choiceproperties_sales.Models.Requests;
import com.example.choiceproperties_sales.R;
import com.example.choiceproperties_sales.Views.Adapters.Sales_Customer_Requests_Adapter;
import com.example.choiceproperties_sales.Views.Adapters.Sold_Out_Plots_Adapter;
import com.example.choiceproperties_sales.Views.dialog.ProgressDialogClass;
import com.example.choiceproperties_sales.interfaces.OnFragmentInteractionListener;
import com.example.choiceproperties_sales.repository.LeedRepository;
import com.example.choiceproperties_sales.repository.UserRepository;
import com.example.choiceproperties_sales.repository.impl.LeedRepositoryImpl;
import com.example.choiceproperties_sales.repository.impl.UserRepositoryImpl;
import com.example.choiceproperties_sales.utilities.Utility;

import java.util.ArrayList;
import java.util.List;

public class Fragment_Sold_Out_Plots extends Fragment implements AdapterView.OnItemSelectedListener {
    // NOTE: Removed Some unwanted Boiler Plate Codes
    private OnFragmentInteractionListener mListener;
    private LeedRepository leedRepository;
    private UserRepository userRepository;
    RecyclerView listView;
    Sold_Out_Plots_Adapter adapter;
    private List<Plots> SoldOutList;
    ProgressDialogClass progressDialogClass;

    public Fragment_Sold_Out_Plots() {
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_sold_out_plots, container, false);
        // NOTE : We are calling the onFragmentInteraction() declared in the MainActivity
        // ie we are sending "Fragment 1" as title parameter when fragment1 is activated
        if (mListener != null) {
            mListener.onFragmentInteraction("Sold Out");

            leedRepository = new LeedRepositoryImpl();
            userRepository = new UserRepositoryImpl();
            progressDialogClass = new ProgressDialogClass(getActivity());
            SoldOutList = new ArrayList<>();
            listView = (RecyclerView) view.findViewById(R.id.recycler_view_users);

            readRequests();
        }

        return view;
    }

    private void readRequests() {
        SoldOutList.clear();
        progressDialogClass.showDialog(this.getString(R.string.loading),this.getString(R.string.PLEASE_WAIT));
        userRepository.readSoldOutPlots( new CallBack() {
            @Override
            public void onSuccess(Object object) {
                if (object != null) {
                    SoldOutList = (ArrayList<Plots>) object;

                }

                adapter = new Sold_Out_Plots_Adapter(getActivity(), SoldOutList, false);
                //adding adapter to recyclerview
                listView.setAdapter(adapter);
                listView.setHasFixedSize(true);
                listView.setLayoutManager(new LinearLayoutManager(getContext()));
                adapter.notifyDataSetChanged();
                progressDialogClass.dismissDialog();
            }

            @Override
            public void onError(Object object) {
                Utility.showLongMessage(getActivity(), getString(R.string.server_error));
                progressDialogClass.dismissDialog();
            }
        });
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnFragmentInteractionListener) {
            mListener = (OnFragmentInteractionListener) context;
        } else {
            // NOTE: This is the part that usually gives you the error
            throw new RuntimeException(context.toString() + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {

    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {

    }

}