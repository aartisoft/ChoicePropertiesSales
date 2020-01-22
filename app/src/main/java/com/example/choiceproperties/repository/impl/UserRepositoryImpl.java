package com.example.choiceproperties.repository.impl;

import android.app.Activity;

import androidx.annotation.NonNull;

import com.example.choiceproperties.CallBack.CallBack;
import com.example.choiceproperties.Constant.Constant;
import com.example.choiceproperties.Models.User;
import com.example.choiceproperties.repository.FirebaseTemplateRepository;
import com.example.choiceproperties.repository.UserRepository;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;

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

}