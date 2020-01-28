package com.example.choiceproperties_sales.Views.Fragments;

import android.app.DatePickerDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.choiceproperties_sales.CallBack.CallBack;
import com.example.choiceproperties_sales.Exception.ExceptionUtil;
import com.example.choiceproperties_sales.Models.Plots;
import com.example.choiceproperties_sales.R;
import com.example.choiceproperties_sales.Views.Adapters.Reports_Adapter;
import com.example.choiceproperties_sales.interfaces.OnFragmentInteractionListener;
import com.example.choiceproperties_sales.repository.LeedRepository;
import com.example.choiceproperties_sales.repository.UserRepository;
import com.example.choiceproperties_sales.repository.impl.LeedRepositoryImpl;
import com.example.choiceproperties_sales.repository.impl.UserRepositoryImpl;
import com.example.choiceproperties_sales.utilities.Utility;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Locale;

import static com.example.choiceproperties_sales.Constant.Constant.CALANDER_DATE_FORMATE;


public class Fragment_Reports extends Fragment {
    int fromYear, fromMonth, fromDay;
    int toYear, toMonth, toDay;
    long fromDate, toDate;
    private RecyclerView catalogRecycler;
    private ArrayList<Plots> SoldOutPlotList;
    private ArrayList<Plots> AvailablePlotList;
    Reports_Adapter adapter;

    private OnFragmentInteractionListener mListener;
    private EditText edittextfromdate, edittexttodate;
    TextView TotalPlots, SoldoutPlots, RemainingPlots;
    LeedRepository leedRepository;
    UserRepository userRepository;
    private ProgressDialog progressDialog;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        if (mListener != null) {
            mListener.onFragmentInteraction("Reports");
        }
        View view = inflater.inflate(R.layout.fragment_report, container, false);

        if (isNetworkAvailable()) {

        } else {
            Toast.makeText(getContext(), "No Internet Connection", Toast.LENGTH_SHORT).show();
        }

        leedRepository = new LeedRepositoryImpl();
        userRepository = new UserRepositoryImpl();
        progressDialog = new ProgressDialog(getContext());

        SoldOutPlotList = new ArrayList<>();
        AvailablePlotList = new ArrayList<>();

        catalogRecycler = (RecyclerView) view.findViewById(R.id.catalog_recycle);
        catalogRecycler.setHasFixedSize(true);
        catalogRecycler.setLayoutManager(new LinearLayoutManager(getContext()));

        edittextfromdate = (EditText) view.findViewById(R.id.edittextfromdate);
        edittexttodate = (EditText) view.findViewById(R.id.edittexttodate);
        TotalPlots = (TextView) view.findViewById(R.id.text_view_total_plots_count);
        SoldoutPlots = (TextView) view.findViewById(R.id.text_view_sold_plots_count);
        RemainingPlots = (TextView) view.findViewById(R.id.text_view_remaining_plots_count);

        getAllSoldPlots();

        setFromDateClickListner();
        setToDateClickListner();

        return view;
    }

    private void getAllSoldPlots() {
        progressDialog.setMessage("Please wait...");
        progressDialog.show();
        userRepository.readSoldOutPlots(new CallBack() {
            @Override
            public void onSuccess(Object object) {
                if (object != null) {
                    SoldOutPlotList = (ArrayList<Plots>) object;
                    getAvailablePlots();

                    progressDialog.dismiss();
                }
            }

            @Override
            public void onError(Object object) {
                progressDialog.dismiss();
            }
        });

    }

    private void getAvailablePlots() {
        progressDialog.setMessage("Please wait...");
        progressDialog.show();
        userRepository.readPlots(new CallBack() {
            @Override
            public void onSuccess(Object object) {
                if (object != null) {
                    AvailablePlotList = (ArrayList<Plots>) object;
                    serAdapter(SoldOutPlotList);
                    progressDialog.dismiss();
                }
            }

            @Override
            public void onError(Object object) {
                progressDialog.dismiss();
            }
        });

    }

    private void serAdapter(ArrayList<Plots> leedsModels) {
        setReports(leedsModels);
        if (leedsModels != null) {
            if (adapter == null) {
                adapter = new Reports_Adapter(getActivity(), leedsModels);
                catalogRecycler.setAdapter(adapter);
            } else {
                ArrayList<Plots> leedsModelArrayList = new ArrayList<>();
                leedsModelArrayList.addAll(leedsModels);
                adapter.reload(leedsModelArrayList);
            }
        }
    }

    private void filterByDate(ArrayList<Plots> leedsModelArrayList) {
        try {
            ArrayList<Plots> filterArrayList = new ArrayList<>();
            if (leedsModelArrayList != null) {
                if (fromDate > 0) {
                    for (Plots leedsModel : leedsModelArrayList) {
                        if (leedsModel.getCreatedDateTimeLong() >= fromDate && leedsModel.getCreatedDateTimeLong() <= toDate) {
                            filterArrayList.add(leedsModel);
                        }
                    }
                } else {
                    for (Plots leedsModel : leedsModelArrayList) {
                        if (leedsModel.getCreatedDateTimeLong() <= toDate) {
                            filterArrayList.add(leedsModel);
                        }
                    }
                }
            }
            serAdapter(filterArrayList);
        } catch (Exception e) {
            ExceptionUtil.logException(e);
        }
    }

    private void setReports(ArrayList<Plots> leedsModelArrayList) {
        int soldoutCount = 0, availableCount = 0;
        int totalPayout = 0;
        if (leedsModelArrayList != null) {
           soldoutCount = SoldOutPlotList.size();
           availableCount = AvailablePlotList.size();
           totalPayout = soldoutCount + availableCount;

            TotalPlots.setText(String.valueOf(totalPayout));
            SoldoutPlots.setText(String.valueOf(soldoutCount));
            RemainingPlots.setText(String.valueOf(availableCount));
        } else {
            TotalPlots.setText("0");
            SoldoutPlots.setText("0");
            RemainingPlots.setText("0");
        }
    }


    private void setFromCurrentDate() {
        Calendar mcurrentDate = Calendar.getInstance();
        fromYear = mcurrentDate.get(Calendar.YEAR);
        fromMonth = mcurrentDate.get(Calendar.MONTH);
        fromDay = mcurrentDate.get(Calendar.DAY_OF_MONTH);
    }

    private void setFromDateClickListner() {
        setFromCurrentDate();
        edittextfromdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DatePickerDialog mDatePicker = new DatePickerDialog(getActivity(), new DatePickerDialog.OnDateSetListener() {
                    public void onDateSet(DatePicker datepicker, int selectedyear, int selectedmonth, int selectedday) {
                        Calendar myCalendar = Calendar.getInstance();
                        myCalendar.set(Calendar.YEAR, selectedyear);
                        myCalendar.set(Calendar.MONTH, selectedmonth);
                        myCalendar.set(Calendar.DAY_OF_MONTH, selectedday);
                        SimpleDateFormat sdf = new SimpleDateFormat(CALANDER_DATE_FORMATE, Locale.FRANCE);
                        String formatedDate = sdf.format(myCalendar.getTime());
                        edittextfromdate.setText(formatedDate);
                        fromDay = selectedday;
                        fromMonth = selectedmonth;
                        fromYear = selectedyear;
                        fromDate = Utility.convertFormatedDateToMilliSeconds(formatedDate, CALANDER_DATE_FORMATE);
                        filterByDate(SoldOutPlotList);
                    }
                }, fromYear, fromMonth, fromDay);
                mDatePicker.show();
            }
        });
    }

    private void setToCurrentDate() {
        toDate = System.currentTimeMillis();
        Calendar mcurrentDate = Calendar.getInstance();
        toYear = mcurrentDate.get(Calendar.YEAR);
        toMonth = mcurrentDate.get(Calendar.MONTH);
        toDay = mcurrentDate.get(Calendar.DAY_OF_MONTH);
    }

    private void setToDateClickListner() {
        setToCurrentDate();
        edittexttodate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DatePickerDialog mDatePicker = new DatePickerDialog(getActivity(), new DatePickerDialog.OnDateSetListener() {
                    public void onDateSet(DatePicker datepicker, int selectedyear, int selectedmonth, int selectedday) {
                        Calendar myCalendar = Calendar.getInstance();
                        myCalendar.set(Calendar.YEAR, selectedyear);
                        myCalendar.set(Calendar.MONTH, selectedmonth);
                        myCalendar.set(Calendar.DAY_OF_MONTH, selectedday);
                        SimpleDateFormat sdf = new SimpleDateFormat(CALANDER_DATE_FORMATE, Locale.FRANCE);
                        edittexttodate.setText(sdf.format(myCalendar.getTime()));
                        toDay = selectedday;
                        toMonth = selectedmonth;
                        toYear = selectedyear;
                        toDate = myCalendar.getTimeInMillis();
                        filterByDate(SoldOutPlotList);
                    }
                }, toYear, toMonth, toDay);
                mDatePicker.show();
            }
        });
    }


    private boolean isNetworkAvailable() {
        ConnectivityManager connectivityManager
                = (ConnectivityManager) getActivity().getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
        return activeNetworkInfo != null && activeNetworkInfo.isConnected();
    }
}
