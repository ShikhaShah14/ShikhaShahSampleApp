package com.sample;

import android.app.Application;

import com.activeandroid.ActiveAndroid;
import com.activeandroid.Configuration;

import io.objectbox.BoxStore;

/**
 * Created by cisner-1 on 9/3/18.
 */

public class MyApplication extends Application {

    public static MyApplication instance;
  //  public BoxStore boxStore;

    public static synchronized MyApplication getInstance() {
        return instance;
    }

    @Override
    public void onCreate() {
        super.onCreate();

        instance = this;
        //boxStore = MyObjectBox.builder().androidContext(this).build();

        Configuration dbConfiguration = new Configuration.Builder(this)
                .setDatabaseName("upstack.db")
                .addModelClass(VideoData.class)
                .create();

        ActiveAndroid.initialize(dbConfiguration);
    }

   /* public BoxStore getBoxStore() {
        return boxStore;
    }*/
}