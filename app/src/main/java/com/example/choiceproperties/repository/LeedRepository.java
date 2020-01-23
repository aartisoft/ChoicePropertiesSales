package com.example.choiceproperties.repository;

import com.example.choiceproperties.CallBack.CallBack;

import java.util.Map;

public interface LeedRepository {


    void updateLeedDocuments(final String leedId, final Map leedMap, final CallBack callback);

    void updateLeed(final String leedId, final Map leedMap, final CallBack callback);

    void readRequestsByStatus(final String status, final CallBack callBack);

    void readSalesPersonByStatus(final String status, final CallBack callBack);

}
