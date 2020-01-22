package com.example.choiceproperties.Views.Activities;

import android.app.Activity;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.view.Window;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.choiceproperties.Exception.ExceptionUtil;
import com.example.choiceproperties.Models.User;
import com.example.choiceproperties.R;
import com.example.choiceproperties.Views.dialog.ProgressDialogClass;
import com.example.choiceproperties.preferences.AppSharedPreference;
import com.example.choiceproperties.repository.UserRepository;
import com.example.choiceproperties.repository.impl.UserRepositoryImpl;
import com.example.choiceproperties.utilities.Utility;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class LoginScreen extends AppCompatActivity {
    TextView txtregister;
    Button login, Register;
    EditText etMobileNumber, etpassword;
    private AppSharedPreference appSharedPreference;
    private UserRepository userRepository;
    private ProgressDialogClass progressDialog;
    Activity activity;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        activity = this;
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_login);
        userRepository = new UserRepositoryImpl(this);
        appSharedPreference = new AppSharedPreference(this);
        checkLoginState();
        Register = (Button) findViewById(R.id.buttonRegister);
        login = (Button) findViewById(R.id.buttonlogin);
        etMobileNumber = (EditText) findViewById(R.id.edittext_mobile_number);
        etpassword = (EditText) findViewById(R.id.edittextpassword);
        Register.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent i = new Intent(LoginScreen.this, Registeractivity.class);
                startActivity(i);
                overridePendingTransition(R.anim.slide_in, R.anim.slide_out);
//                overridePendingTransition(R.anim.backslide_in, R.anim.backslide_out);
            }
        });
        login.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
//                login();
                login1();
            }
        });
        etMobileNumber.setOnTouchListener(new View.OnTouchListener() {
            public boolean onTouch(View arg0, MotionEvent arg1) {
                Animation zoomOutAnimation = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.zoomin);
                etMobileNumber.startAnimation(zoomOutAnimation);
                return false;
            }
        });
        etpassword.setOnTouchListener(new View.OnTouchListener() {
            public boolean onTouch(View arg0, MotionEvent arg1) {
                Animation zoomOutAnimation = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.zoomin);
                etpassword.startAnimation(zoomOutAnimation);
                return false;
            }
        });
    }

    private void login1() {
        progressDialog = new ProgressDialogClass(this);
        progressDialog.showDialog(this.getString(R.string.SIGNING_IN), this.getString(R.string.PLEASE_WAIT));
        String code = "91";
        String number = etMobileNumber.getText().toString().trim();
        final String username = etpassword.getText().toString().trim();

        if (number.isEmpty() || number.length() < 10) {
            etMobileNumber.setError("Valid number is required");
            etMobileNumber.requestFocus();
            return;
        }
        if (username.isEmpty()) {
            etpassword.setError("Password is required");
            etpassword.requestFocus();
            return;
        }

        final String phoneNumber = number;


        DatabaseReference Dref = FirebaseDatabase.getInstance().getReference("user");
        Dref.orderByChild("mobileNumber").equalTo(phoneNumber).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                if (dataSnapshot.getValue() != null) {
                    for (DataSnapshot postSnapshot : dataSnapshot.getChildren()) {

                        User upload = postSnapshot.getValue(User.class);

                        progressDialog.dismissDialog();
                        String userid = upload.getUserId();
                        Toast.makeText(LoginScreen.this, "Login Successfull", Toast.LENGTH_SHORT).show();
                        signInUserData(userid);

                    }


                } else {
                    progressDialog.dismissDialog();
                    Toast.makeText(LoginScreen.this, "Login failed Please Register", Toast.LENGTH_SHORT).show();

                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

    }

    public void checkLoginState() {
        AsyncTask.execute(new Runnable() {
            @Override
            public void run() {
                try {
                    if (appSharedPreference != null && appSharedPreference.getUserLoginStatus()) {
                        if (appSharedPreference.getRegId() != null && appSharedPreference.getUserId() != null) {

                            String roll = appSharedPreference.getRole();
//                            loginTotellecallerApp();
                            if (roll.equals("ADMIN")) {
                                loginToadminApp();

                            } else if (roll.equals("SALES")) {
//                                loginTosalesApp();

                            }
                        }
                    }
                } catch (Exception e) {
                    ExceptionUtil.logException(e);
                }
            }
        });
    }

    private void loginToadminApp() {
//        Intent i = new Intent(this, Home_Activity.class);
//        startActivity(i);
//        finish();
    }

    private void loginTotellecallerApp() {
//        Intent i = new Intent(this, MainActivity_telecaller.class);
//        startActivity(i);
//        finish();
    }


    public void login() {
        if (!Utility.isNetworkConnected(this)) {
            Utility.showMessage(this, this.getString(R.string.network_not_available));
            return;
        }
        final String mobileNumber = etMobileNumber.getText().toString();
        final String password = etpassword.getText().toString();
        if (validate(mobileNumber, password)) {
            if (!Utility.isNetworkConnected(this)) {
                Utility.showMessage(this, this.getString(R.string.network_not_available));
                return;
            }
            progressDialog = new ProgressDialogClass(this);
            progressDialog.showDialog(this.getString(R.string.SIGNING_IN), this.getString(R.string.PLEASE_WAIT));
//            userRepository.signIn(mobileNumber, password, new CallBack() {
//                @Override
//                public void onSuccess(Object object) {
//                    // Sign in success, update UI with the signed-in user's information
//                    FirebaseUser userFirebase = Constant.AUTH.getCurrentUser();
//                    if (userFirebase != null) {
//                        signInUserData(userFirebase.getUid());
//
//                    }
//                }
//
//                @Override
//                public void onError(Object object) {
//                    if (progressDialog != null)
//                        progressDialog.dismissDialog();
//                    Utility.showSnackBar(activity, etpassword, getMessage(R.string.login_fail_try_again));
//                }
//            });
        }
    }

    private void signInUserData(final String userId) {
//        userRepository.readUserByUserId(userId, new CallBack() {
//            @Override
//            public void onSuccess(Object object) {
//                if (object != null) {
//                    User user = (User) object;
//                    appSharedPreference.createUserLoginSession();
//                    appSharedPreference.addUserDetails(user);
//
//                    String roll = appSharedPreference.getRole();
//
//                    if (roll.equals("ADMIN")) {
//                        loginToadminApp();
//
//                    } else if (roll.equals("TELECALLER")) {
//                        loginTotellecallerApp();
//
//                    } else if (roll.equals("COORDINATOR")) {
//                        loginTocordinatorApp();
//
//                    } else if (roll.equals("SALES")) {
//                        loginTosalesApp();
//
//                    } else if (roll.equals("ACCOUNTANT")) {
//                        loginToaccountantApp();
//
//                    } else if (roll.equals("ADMIN")) {
//                        loginToadminApp();
//
//                    }
//                } else {
//                    Utility.showTimedSnackBar(activity, etpassword, getMessage(R.string.login_fail_try_again));
//                }
//                if (progressDialog != null)
//                    progressDialog.dismissDialog();
//            }
//
//            @Override
//            public void onError(Object object) {
//                if (progressDialog != null)
//                    progressDialog.dismissDialog();
//                Utility.showTimedSnackBar(activity, etpassword, getMessage(R.string.login_fail_try_again));
//            }
//        });
    }

    private String getMessage(int id) {
        return getString(id);
    }

    private boolean validate(String mobileNumber, String password) {
        String validationMessage;
        boolean isValid = true;
        try {
            if (Utility.isEmptyOrNull(mobileNumber)) {
                validationMessage = getString(R.string.MOBILE_NUMBER_SHOULD_NOT_BE_EMPTY);
                etMobileNumber.setError(validationMessage);
                isValid = false;
            } else if (!Utility.isValidMobileNumber(mobileNumber)) {
                validationMessage = getMessage(R.string.INVALID_MOBILE_NUMBER);
                etMobileNumber.setError(validationMessage);
                isValid = false;
            }
            if (Utility.isEmptyOrNull(password)) {
                validationMessage = getString(R.string.PASSWORD_SHOULD_NOT_BE_EMPTY);
                etpassword.setError(validationMessage);
                isValid = false;
            }
        } catch (Exception e) {
            isValid = false;
            ExceptionUtil.logException(e);
        }
        return isValid;
    }
}
