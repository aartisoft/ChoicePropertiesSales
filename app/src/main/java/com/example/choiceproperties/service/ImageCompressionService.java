package com.example.choiceproperties.service;


import com.example.choiceproperties.CallBack.CallBack;

public interface ImageCompressionService {
    void compressImage(String ImagePath, CallBack callBack);
}
