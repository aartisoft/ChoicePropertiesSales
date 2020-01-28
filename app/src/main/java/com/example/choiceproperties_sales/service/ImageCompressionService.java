package com.example.choiceproperties_sales.service;


import com.example.choiceproperties_sales.CallBack.CallBack;

public interface ImageCompressionService {
    void compressImage(String ImagePath, CallBack callBack);
}
