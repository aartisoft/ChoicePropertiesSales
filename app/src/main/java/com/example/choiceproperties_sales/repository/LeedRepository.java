package com.example.choiceproperties_sales.repository;

import com.example.choiceproperties_sales.CallBack.CallBack;

import java.util.Map;

public interface LeedRepository {


    void updateLeedDocuments(final String leedId, final Map leedMap, final CallBack callback);

    void updateLeed(final String leedId, final Map leedMap, final CallBack callback);

    void readRequestsByStatus(final String status, final CallBack callBack);

    void readSalesPersonByStatus(final String status, final CallBack callBack);

    void updateUser(final String leedId, final Map leedMap, final CallBack callback);


    void updatePlot(final String leedId, final Map leedMap, final CallBack callback);

}
