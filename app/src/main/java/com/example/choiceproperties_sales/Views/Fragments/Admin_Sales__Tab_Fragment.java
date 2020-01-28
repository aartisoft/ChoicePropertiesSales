package com.example.choiceproperties_sales.Views.Fragments;


import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;

import com.example.choiceproperties_sales.R;
import com.example.choiceproperties_sales.Views.Adapters.ViewPagerAdapter;
import com.example.choiceproperties_sales.interfaces.OnFragmentInteractionListener;
import com.google.android.material.tabs.TabLayout;

public class Admin_Sales__Tab_Fragment extends Fragment {
    TabLayout tabLayout;
    ViewPager viewPager;
    ViewPagerAdapter viewPagerAdapter;
    public OnFragmentInteractionListener mListener;

    public Admin_Sales__Tab_Fragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        if (mListener != null) {
            mListener.onFragmentInteraction("Bills");
        }
        View view = inflater.inflate(R.layout.fragment_sales_tab, container, false);
        viewPager = (ViewPager) view.findViewById(R.id.viewPager);
        viewPagerAdapter = new ViewPagerAdapter(getChildFragmentManager());
        viewPagerAdapter.addFragement(new Admin_Pending_sales_requests_Fragment(), "Deactive");
        viewPagerAdapter.addFragement(new Admin_Active_sales_Persons_Fragment(), "Active");
        
        viewPager.setAdapter(viewPagerAdapter);
        tabLayout = (TabLayout) view.findViewById(R.id.tabs);
//        tabLayout.setTabMode(1);
        tabLayout.setupWithViewPager(viewPager);
        return view;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnFragmentInteractionListener) {
            mListener = (OnFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString() + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    
}
