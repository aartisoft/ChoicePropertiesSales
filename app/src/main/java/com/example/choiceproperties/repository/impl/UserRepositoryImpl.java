package com.example.choiceproperties.repository.impl;

import android.app.Activity;

import androidx.annotation.NonNull;

import com.example.choiceproperties.CallBack.CallBack;
import com.example.choiceproperties.Constant.Constant;
import com.example.choiceproperties.Exception.ExceptionUtil;
import com.example.choiceproperties.Models.Customer;
import com.example.choiceproperties.Models.User;
import com.example.choiceproperties.repository.FirebaseTemplateRepository;
import com.example.choiceproperties.repository.UserRepository;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.Map;

import static com.example.choiceproperties.Constant.Constant.EMAIL_POSTFIX;


public class UserRepositoryImpl extends FirebaseTemplateRepository implements UserRepository {
    private Activity _activity;

    public UserRepositoryImpl(final Activity activity) {
        _activity = activity;
    }

    public UserRepositoryImpl() {

    }


    @Override
    public void createUserData(User user, final CallBack callBack) {
        DatabaseReference databaseReference = Constant.USER_TABLE_REF.child(user.getUserId());
        fireBaseCreate(databaseReference, user, new CallBack() {
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
    public void readUserByUserId(String userId, final CallBack callBack) {
        final Query query = Constant.USER_TABLE_REF.child(userId);
        query.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if (dataSnapshot != null) {
                    if (dataSnapshot.getValue() != null) {
                        try {
                            if (dataSnapshot.hasChildren()) {
                                callBack.onSuccess(dataSnapshot.getValue(User.class));
                            } else {
                                callBack.onError(null);
                            }
                        } catch (Exception e) {
                            ExceptionUtil.logException(e);
                        }
                    } else
                        callBack.onError(null);
                } else
                    callBack.onError(null);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                callBack.onError(databaseError);
            }
        });
    }

    @Override
    public void createCustomer(Customer customer, final CallBack callBack) {
        DatabaseReference databaseReference = Constant.CUSTOMERS_TABLE_REF.child(customer.getCustomerId());
        fireBaseCreate(databaseReference, customer, new CallBack() {
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

}