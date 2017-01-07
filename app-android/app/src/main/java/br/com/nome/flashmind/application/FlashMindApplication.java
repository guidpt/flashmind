package br.com.nome.flashmind.application;

import android.app.Application;

import net.jokubasdargis.rxbus.AndroidRxBus;

/**
 * Created by Alessandro Pryds on 03/01/2017.
 */
public class FlashMindApplication extends Application {

    private static FlashMindApplication instance;
    private static AndroidRxBus rxBus;

    @Override
    public void onCreate() {
        super.onCreate();
        rxBus = AndroidRxBus.create();
        instance = new FlashMindApplication();
    }

    public static FlashMindApplication getInstance() {
        return instance;
    }

    public static AndroidRxBus getRxBus() {
        return rxBus;
    }
}
