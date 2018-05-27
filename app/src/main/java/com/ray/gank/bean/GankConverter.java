package com.ray.gank.bean;

import com.google.gson.Gson;
import com.ray.library.utils.T;

import org.greenrobot.greendao.converter.PropertyConverter;

public  class GankConverter implements PropertyConverter<Gank, String> {
    @Override
    public Gank convertToEntityProperty(String databaseValue) {
        if (databaseValue == null) {
            return null;
        }
        return new Gson().fromJson(databaseValue, Gank.class);
    }

    @Override
    public String convertToDatabaseValue(Gank entityProperty) {
        if (entityProperty == null) {
            return null;
        }
        return new Gson().toJson(entityProperty);
    }
}