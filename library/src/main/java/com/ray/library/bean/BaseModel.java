package com.ray.library.bean;

import java.io.Serializable;

/**
 * Created by ray on 17-5-10
 * Description:
 */
public class BaseModel<T> implements Serializable {
    public boolean error;
    public String msg;
    public T results;


    public boolean success(){
        return !error;
    }
}
