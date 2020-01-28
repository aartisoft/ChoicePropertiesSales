package com.example.choiceproperties_sales.repository;

import com.example.choiceproperties_sales.CallBack.CallBack;
import com.example.choiceproperties_sales.Models.Customer;
import com.example.choiceproperties_sales.Models.Plots;
import com.example.choiceproperties_sales.Models.User;

public interface UserRepository {

    void createUserData(final User userModel, final CallBack callback);

    void readUserByUserId(final String regId, final CallBack callBack);

    void createCustomer(final Customer customer, final CallBack callback);

    void createPlot(final Plots plots, final CallBack callback);

    void readPlots(final CallBack callback);

    void readPlotsByPlotNumber(final String plotNumber, final CallBack callBack);

    void salePlot(final Plots plots, final CallBack callback);

    void readSoldOutPlots(final CallBack callback);

    void readVisitedCustomers(final CallBack callback);
}