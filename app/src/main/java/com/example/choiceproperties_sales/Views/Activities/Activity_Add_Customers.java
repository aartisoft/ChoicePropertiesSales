package com.example.choiceproperties_sales.Views.Activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.DatePickerDialog;
import android.app.ProgressDialog;
import android.app.TimePickerDialog;
import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.view.View;
import android.webkit.MimeTypeMap;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TimePicker;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.choiceproperties_sales.CallBack.CallBack;
import com.example.choiceproperties_sales.Constant.Constant;
import com.example.choiceproperties_sales.Models.Customer;
import com.example.choiceproperties_sales.R;
import com.example.choiceproperties_sales.Views.Adapters.CustomerDocumentAdapter;
import com.example.choiceproperties_sales.Views.dialog.ProgressDialogClass;
import com.example.choiceproperties_sales.repository.UserRepository;
import com.example.choiceproperties_sales.repository.impl.UserRepositoryImpl;
import com.google.android.gms.common.internal.Constants;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class Activity_Add_Customers extends AppCompatActivity implements View.OnClickListener {

    EditText inputName, inputMobile, inputNote, inputAddress, inputDateTime, inputDiscussion;
    Button btnAdd;
    ImageView imgCustomer, imgAttachment;
    private DatePickerDialog mDatePickerDialog;
    String fdate;
    int mHour;
    int mMinute;
    String image;
    private List<Uri> fileDoneList;
    private Uri filePath;
    String Sdownloadurl;
    CustomerDocumentAdapter customerDocumentAdapter;
    RecyclerView recycleDocuments;

    private static final int REQUEST_PICK_IMAGE = 1002;
    private static final int REQUEST_CROP_IMAGE = 2342;
    private static final int RESULT_LOAD_IMAGE = 1;
    private StorageReference storageReference;

    ProgressDialogClass progressDialogClass;
    UserRepository userRepository;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity__add__customers);

        userRepository = new UserRepositoryImpl();
//        leedRepository = new LeedRepositoryImpl();
        progressDialogClass = new ProgressDialogClass(this);
        storageReference = FirebaseStorage.getInstance().getReference();

        fileDoneList = new ArrayList<>();

        inputName = (EditText) findViewById(R.id.username);
        inputMobile = (EditText) findViewById(R.id.mobilenumber);
        inputNote = (EditText) findViewById(R.id.note);
        inputAddress = (EditText) findViewById(R.id.address);
        inputDateTime = (EditText) findViewById(R.id.date_time);
        inputDiscussion = (EditText) findViewById(R.id.discussion);
        btnAdd = (Button) findViewById(R.id.add_button);

        imgCustomer = (ImageView) findViewById(R.id.iv_customerImage);
        imgAttachment = (ImageView) findViewById(R.id.attachment);
        recycleDocuments = (RecyclerView) findViewById(R.id.recycler_view_users);

        btnAdd.setOnClickListener(this);
        imgCustomer.setOnClickListener(this);
        imgAttachment.setOnClickListener(this);
        setDateTimeField();
        inputDateTime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mDatePickerDialog.show();
            }
        });

    }

    @Override
    public void onClick(View v) {

        try {

            if (v == btnAdd) {

                Upload();

            } else if (v == imgCustomer) {

                pickImage();
            } else if (v == imgAttachment) {

                Intent intent = new Intent();
                intent.setType("image/*");
                intent.putExtra(Intent.EXTRA_ALLOW_MULTIPLE, true);
                intent.setAction(Intent.ACTION_GET_CONTENT);
                startActivityForResult(Intent.createChooser(intent, "Select Picture"), RESULT_LOAD_IMAGE);
            }


        } catch (Exception e) {
        }

    }

    public void pickImage() {
        startActivityForResult(new Intent(this, ImagePickerActivity.class), REQUEST_PICK_IMAGE);

    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (resultCode == RESULT_OK) {
            if (data != null) {
                switch (requestCode) {
                    case REQUEST_PICK_IMAGE:

                        Uri imagePath = Uri.parse(data.getStringExtra("image_path"));

                        String str = imagePath.toString();
                        String whatyouaresearching = str.substring(0, str.lastIndexOf("/"));
                        image = whatyouaresearching.substring(whatyouaresearching.lastIndexOf("/") + 1, whatyouaresearching.length());
                        String root = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS).getAbsolutePath();
                        File file = new File(root, image);

                        filePath = Uri.fromFile(file);
                        setImage(filePath);
                        break;

                    case RESULT_LOAD_IMAGE:
                        if (data.getClipData() != null) {

                            int totalItemsSelected = data.getClipData().getItemCount();

                            for (int i = 0; i < totalItemsSelected; i++) {

                                Uri fileUri = data.getClipData().getItemAt(i).getUri();
                                fileDoneList.add(data.getClipData().getItemAt(i).getUri());

                                //String fileName = getFileName(fileUri);
                            }
                            customerDocumentAdapter = new CustomerDocumentAdapter(Activity_Add_Customers.this, fileDoneList);
                            recycleDocuments.setLayoutManager(new LinearLayoutManager(Activity_Add_Customers.this, LinearLayoutManager.HORIZONTAL, true));
                            recycleDocuments.setHasFixedSize(true);
                            recycleDocuments.setAdapter(customerDocumentAdapter);

                        } else if (data.getData() != null) {

                        }
                }
            }
        } else {

            System.out.println("Failed to load image");
        }

    }

    private void Upload() {
        final ProgressDialog progressDialog = new ProgressDialog(Activity_Add_Customers.this);
        progressDialog.setTitle("Uploading");
        progressDialog.show();

        final StorageReference sRef = storageReference.child(Constant.STORAGE_PATH_UPLOADS + System.currentTimeMillis() + "." + getFileExtension(filePath));
        sRef.putFile(filePath).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                progressDialog.dismiss();

                sRef.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                    @Override
                    public void onSuccess(Uri uri) {
                        Sdownloadurl = uri.toString();

                        Customer customer = fillUserModel();
                        CreateCustomer(customer);

                    }
                });
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception exception) {
                progressDialog.dismiss();
                Toast.makeText(Activity_Add_Customers.this, exception.getMessage(), Toast.LENGTH_LONG).show();
            }
        }).addOnProgressListener(new OnProgressListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onProgress(UploadTask.TaskSnapshot taskSnapshot) {
                //displaying the upload progress
                double progress = (100.0 * taskSnapshot.getBytesTransferred()) / taskSnapshot.getTotalByteCount();
                progressDialog.setMessage("Uploaded " + ((int) progress) + "%...");
            }
        });
    }

    public String getFileExtension(Uri uri) {
        ContentResolver cR = getContentResolver();
        MimeTypeMap mime = MimeTypeMap.getSingleton();
        return mime.getExtensionFromMimeType(cR.getType(uri));
    }



    private void setImage(Uri imagePath) {

//        Glide.with(getApplicationContext()).load(imagePath).placeholder(R.drawable.loading).into(imgCustomer);
        imgCustomer.setImageURI(imagePath);

    }

    private void CreateCustomer(Customer customer) {
        userRepository.createCustomer(customer, new CallBack() {
            @Override
            public void onSuccess(Object object) {
                Toast.makeText(getApplicationContext(), "Customer Added Successfully", Toast.LENGTH_SHORT).show();
                inputName.setText("");
                inputMobile.setText("");
                inputAddress.setText("");
                inputNote.setText("");
                inputDateTime.setText("");
                inputDiscussion.setText("");
                imgCustomer.setImageResource(0);
                progressDialogClass.dismissDialog();
            }

            @Override
            public void onError(Object object) {

            }
        });

    }


    private Customer fillUserModel() {
        Customer customer = new Customer();
        customer.setName(inputName.getText().toString());
        customer.setAddress(inputAddress.getText().toString());
        customer.setMobile(inputMobile.getText().toString());
        customer.setAttendedBy(inputNote.getText().toString());
        customer.setDateTime(inputDateTime.getText().toString());
        customer.setDiscussion(inputDiscussion.getText().toString());
        customer.setCustomerImage(String.valueOf(Sdownloadurl));
        customer.setStatus(Constant.STATUS_REQUEST_VISITED);
        customer.setCustomerId(Constant.CUSTOMERS_TABLE_REF.push().getKey());

        return customer;
    }


    private void setDateTimeField() {
        Calendar newCalendar = Calendar.getInstance();
        mDatePickerDialog = new DatePickerDialog(Activity_Add_Customers.this, new DatePickerDialog.OnDateSetListener() {

            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                Calendar newDate = Calendar.getInstance();
                newDate.set(year, monthOfYear, dayOfMonth);
                SimpleDateFormat sd = new SimpleDateFormat("dd-MM-yyyy");
                final Date startDate = newDate.getTime();
                fdate = sd.format(startDate);

                timePicker();
            }

            private void timePicker() {
                // Get Current Time
                final Calendar c = Calendar.getInstance();
                mHour = c.get(Calendar.HOUR_OF_DAY);
                mMinute = c.get(Calendar.MINUTE);

                // Launch Time Picker Dialog
                TimePickerDialog timePickerDialog = new TimePickerDialog(Activity_Add_Customers.this,
                        new TimePickerDialog.OnTimeSetListener() {

                            @Override
                            public void onTimeSet(TimePicker view, int hourOfDay, int minute) {

                                mHour = hourOfDay;
                                mMinute = minute;

                                inputDateTime.setText(fdate + " " + hourOfDay + ":" + minute);
                            }
                        }, mHour, mMinute, false);
                timePickerDialog.show();
            }
        }, newCalendar.get(Calendar.YEAR), newCalendar.get(Calendar.MONTH), newCalendar.get(Calendar.DAY_OF_MONTH));

    }

    private boolean isNetworkAvailable() {
        ConnectivityManager connectivityManager
                = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
        return activeNetworkInfo != null && activeNetworkInfo.isConnected();
    }
}
