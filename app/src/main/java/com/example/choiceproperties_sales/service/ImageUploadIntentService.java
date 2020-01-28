package com.example.choiceproperties_sales.service;

import android.app.IntentService;
import android.content.Intent;
import android.graphics.Bitmap;


import com.example.choiceproperties_sales.CallBack.CallBack;
import com.example.choiceproperties_sales.Constant.Constant;
import com.example.choiceproperties_sales.Exception.ExceptionUtil;
import com.example.choiceproperties_sales.Models.ImagesModel;
import com.example.choiceproperties_sales.Models.ServiceRequestModel;
import com.example.choiceproperties_sales.firebasestorage.StorageService;
import com.example.choiceproperties_sales.repository.LeedRepository;
import com.example.choiceproperties_sales.repository.impl.LeedRepositoryImpl;
import com.example.choiceproperties_sales.service.impl.ImageCompressionServiceImp;
import com.example.choiceproperties_sales.singleton.AppSingleton;
import com.example.choiceproperties_sales.utilities.FileUtils;
import com.example.choiceproperties_sales.utilities.Utility;

import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;

public class ImageUploadIntentService extends IntentService {

    public ImageUploadIntentService() {
        super("ImageUploadIntentService");
    }

    @Override
    protected void onHandleIntent(Intent intent) {
        try {
            ServiceRequestModel serviceRequestModel = new ServiceRequestModel();
            serviceRequestModel.initReuestModel(intent);
            if (serviceRequestModel.getUri() != null) {
                if (serviceRequestModel.getImageCount() == 1) {
                    AppSingleton.getInstance(this).setNotificationManager();
                }
                String path = FileUtils.getPath(this, serviceRequestModel.getUri());
                serviceRequestModel.setBitmapPath(path);
                compressBitmap(serviceRequestModel);
            }
        } catch (Exception e) {
            ExceptionUtil.logException(e);
        }
    }

    private void compressBitmap(final ServiceRequestModel serviceRequestModel) {
        ImageCompressionService imageCompressionService = new ImageCompressionServiceImp();
        imageCompressionService.compressImage(serviceRequestModel.getBitmapPath(), new CallBack() {
            @Override
            public void onSuccess(Object object) {
                if (object != null) {
                    Bitmap bitmap = (Bitmap) object;
                    serviceRequestModel.setBitmap(bitmap);
                    uploadImage(serviceRequestModel);
                }
            }

            @Override
            public void onError(Object object) {
            }
        });
    }

    void uploadImage(final ServiceRequestModel serviceRequestModel) {
        try {
            InputStream imageInputStream = Utility.returnInputStreamFromBitmap(serviceRequestModel.getBitmap());
            StorageService.uploadImageStreamToFirebaseStorage(imageInputStream, serviceRequestModel.getStoragePath(), new CallBack() {
                public void onSuccess(Object object) {
                    //  final Uri downloadUrlLarge= (Uri) object;
                    String downloadUrlLarge = (String) object;
                    serviceRequestModel.setImagePath(downloadUrlLarge);
                    if (serviceRequestModel.getStoragePath().equalsIgnoreCase(Constant.DOCUMENTS_PATH))
                        updateLeedDocuments(serviceRequestModel);
                    else if (serviceRequestModel.getStoragePath().equalsIgnoreCase(Constant.CUSROMER_PROFILE_PATH))
                        updateLeed(serviceRequestModel);
                }

                public void onError(Object object) {
                }
            });
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void updateLeedDocuments(final ServiceRequestModel serviceRequestModel) {
        LeedRepository leedRepository = new LeedRepositoryImpl();
        Map<String, Object> map = new HashMap<>();
        String key = Constant.LEEDS_TABLE_REF.push().getKey();
        ImagesModel imagesModel = new ImagesModel();
        imagesModel.setLargImage(serviceRequestModel.getImagePath());
        map.put(key, imagesModel);
        leedRepository.updateLeedDocuments(serviceRequestModel.getLeedId(), map, new CallBack() {
            @Override
            public void onSuccess(Object object) {
               /* Intent broadcastIntent = new Intent();
                broadcastIntent.setAction(GenerateLeedFragment.ImageUploadReceiver.PROCESS_RESPONSE);
                broadcastIntent.putExtra(IMAGE_COUNT, imageCount);
                LocalBroadcastManager.getInstance(getApplicationContext()).sendBroadcast(broadcastIntent);
                */
                int progress = 0;
                if (serviceRequestModel.getTotalCount() > 0)
                    progress = (100 / serviceRequestModel.getTotalCount()) * serviceRequestModel.getImageCount();
                AppSingleton.getInstance(ImageUploadIntentService.this).updateProgress(serviceRequestModel.getImageCount(), serviceRequestModel.getTotalCount(), progress);
            }

            @Override
            public void onError(Object object) {
            }
        });
    }

    private void updateLeed(final ServiceRequestModel serviceRequestModel) {
        LeedRepository leedRepository = new LeedRepositoryImpl();
        Map<String, Object> map = new HashMap<>();
        map.put("customerImagelarge", serviceRequestModel.getImagePath());
        map.put("customerImageSmall", serviceRequestModel.getImagePath());
        leedRepository.updateLeed(serviceRequestModel.getLeedId(), map, new CallBack() {
            @Override
            public void onSuccess(Object object) {
               /* Intent broadcastIntent = new Intent();
                broadcastIntent.setAction(GenerateLeedFragment.ImageUploadReceiver.PROCESS_RESPONSE);
                broadcastIntent.putExtra(IMAGE_COUNT, imageCount);
                LocalBroadcastManager.getInstance(getApplicationContext()).sendBroadcast(broadcastIntent);
                */
                int progress = 0;
                if (serviceRequestModel.getTotalCount() > 0)
                    progress = (100 / serviceRequestModel.getTotalCount()) * serviceRequestModel.getImageCount();
                AppSingleton.getInstance(ImageUploadIntentService.this).updateProgress(serviceRequestModel.getImageCount(), serviceRequestModel.getTotalCount(), progress);
            }

            @Override
            public void onError(Object object) {
            }
        });
    }
}//end of service