package com.example.choiceproperties.repository.impl;

import com.example.choiceproperties.CallBack.CallBack;
import com.example.choiceproperties.Constant.Constant;
import com.example.choiceproperties.Models.Requests;
import com.example.choiceproperties.Models.User;
import com.example.choiceproperties.repository.FirebaseTemplateRepository;
import com.example.choiceproperties.repository.LeedRepository;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.Query;

import java.util.ArrayList;
import java.util.Map;

public class LeedRepositoryImpl extends FirebaseTemplateRepository implements LeedRepository {

    @Override
    public void updateLeedDocuments(String leedId, Map leedMap, final CallBack callBack) {
        final DatabaseReference databaseReference = Constant.LEEDS_TABLE_REF.child(leedId).child("documentImages");
        fireBaseUpdateChildren(databaseReference, leedMap, new CallBack() {
            @Override
            public void onSuccess(Object object) {
                callBack.onSuccess(object);
            }

            @Override
            public void onError(Object object) {
                callBack.onError(object);
            }
        });
    }

    @Override
    public void updateLeed(String leedId, Map leedMap, final CallBack callBack) {
        final DatabaseReference databaseReference = Constant.LEEDS_TABLE_REF.child(leedId);
        fireBaseUpdateChildren(databaseReference, leedMap, new CallBack() {
            @Override
            public void onSuccess(Object object) {
                callBack.onSuccess(object);
            }

            @Override
            public void onError(Object object) {
                callBack.onError(object);
            }
        });
    }


    @Override
    public void readRequestsByStatus(String status, final CallBack callBack) {
        final Query query = Constant.REQUESTS_TABLE_REF.orderByChild("status").equalTo(status);
        fireBaseNotifyChange(query, new CallBack() {
            @Override
            public void onSuccess(Object object) {
                if (object != null) {
                    DataSnapshot dataSnapshot = (DataSnapshot) object;
                    if (dataSnapshot.getValue() != null & dataSnapshot.hasChildren()) {
                        ArrayList<Requests> leedsModelArrayList = new ArrayList<>();
                        for (DataSnapshot suggestionSnapshot : dataSnapshot.getChildren()) {
                            Requests leedsModel = suggestionSnapshot.getValue(Requests.class);
                            leedsModelArrayList.add(leedsModel);
                        }
                        callBack.onSuccess(leedsModelArrayList);
                    } else {
                        callBack.onSuccess(null);
                    }
                }
            }

            @Override
            public void onError(Object object) {
                callBack.onError(object);
            }
        });
    }


    @Override
    public void readSalesPersonByStatus(String status, final CallBack callBack) {
        final Query query = Constant.USER_TABLE_REF.orderByChild("status").equalTo(status);
        fireBaseNotifyChange(query, new CallBack() {
            @Override
            public void onSuccess(Object object) {
                if (object != null) {
                    DataSnapshot dataSnapshot = (DataSnapshot) object;
                    if (dataSnapshot.getValue() != null & dataSnapshot.hasChildren()) {
                        ArrayList<User> leedsModelArrayList = new ArrayList<>();
                        for (DataSnapshot suggestionSnapshot : dataSnapshot.getChildren()) {
                            User leedsModel = suggestionSnapshot.getValue(User.class);
                            leedsModelArrayList.add(leedsModel);
                        }
                        callBack.onSuccess(leedsModelArrayList);
                    } else {
                        callBack.onSuccess(null);
                    }
                }
            }

            @Override
            public void onError(Object object) {
                callBack.onError(object);
            }
        });
    }

}
