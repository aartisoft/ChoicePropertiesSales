package com.example.choiceproperties.Views.Fragments;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.choiceproperties.CallBack.CallBack;
import com.example.choiceproperties.Constant.Constant;
import com.example.choiceproperties.Models.User;
import com.example.choiceproperties.R;
import com.example.choiceproperties.Views.Adapters.DeactiveSalesAdapter;
import com.example.choiceproperties.interfaces.OnFragmentInteractionListener;
import com.example.choiceproperties.repository.LeedRepository;
import com.example.choiceproperties.repository.impl.LeedRepositoryImpl;
import com.example.choiceproperties.utilities.Utility;

import java.util.ArrayList;

public class Admin_Pending_sales_requests_Fragment extends Fragment implements AdapterView.OnItemSelectedListener {
    // NOTE: Removed Some unwanted Boiler Plate Codes
    private OnFragmentInteractionListener mListener;
    private LeedRepository leedRepository;


    public Admin_Pending_sales_requests_Fragment() {
    }

    ArrayList<User> salesList;
    RecyclerView listView;
    DeactiveSalesAdapter adapter;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_admin_pending_requests, container, false);
        // NOTE : We are calling the onFragmentInteraction() declared in the MainActivity
        // ie we are sending "Fragment 1" as title parameter when fragment1 is activated
        if (mListener != null) {
            mListener.onFragmentInteraction("Approved");

            leedRepository = new LeedRepositoryImpl();
            salesList = new ArrayList<>();
            listView = (RecyclerView) view.findViewById(R.id.recycler_view_users);

            readExpences();
        }

        return view;
    }

    private void readExpences() {
        salesList.clear();
        leedRepository.readSalesPersonByStatus(Constant.STATUS_DEACTIVE,new CallBack() {
            @Override
            public void onSuccess(Object object) {
                if (object != null) {
                    salesList = (ArrayList<User>) object;


                }

                adapter = new DeactiveSalesAdapter(getActivity(), salesList,false);
                //adding adapter to recyclerview
                listView.setAdapter(adapter);
                listView.setHasFixedSize(true);
                listView.setLayoutManager(new LinearLayoutManager(getContext()));

            }

            @Override
            public void onError(Object object) {
                Utility.showLongMessage(getActivity(), getString(R.string.server_error));
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