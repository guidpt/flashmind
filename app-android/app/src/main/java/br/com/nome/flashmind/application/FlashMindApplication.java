package br.com.nome.flashmind.application;

import android.app.Application;

import net.jokubasdargis.rxbus.Bus;
import net.jokubasdargis.rxbus.RxBus;

import org.greenrobot.greendao.database.Database;

import br.com.nome.flashmind.logic.model.DaoMaster;
import br.com.nome.flashmind.logic.model.DaoSession;

import static br.com.nome.flashmind.logic.model.DaoMaster.DevOpenHelper;

/**
 * Created by Alessandro Pryds on 03/01/2017.
 */
public class FlashMindApplication extends Application {
    /** A flag to show how easily you can switch from standard SQLite to the encrypted SQLCipher. */
    public static final boolean ENCRYPTED = false;
    private static FlashMindApplication instance;
    private static Bus rxBus;
    private DaoSession daoSession;


    @Override
    public void onCreate() {
        super.onCreate();
        rxBus = RxBus.create();
        instance = new FlashMindApplication();
        createDb();
    }

    private void createDb() {
        DevOpenHelper helper = new DevOpenHelper(this, ENCRYPTED ? "flashmind-db-encrypted" : "flashmind-db");
        Database db = ENCRYPTED ? helper.getEncryptedWritableDb("ag-f145hm1nd") : helper.getWritableDb();
        daoSession = new DaoMaster(db).newSession();
    }

    public static FlashMindApplication getInstance() {
        return instance;
    }

    public static Bus getRxBus() {
        return rxBus;
    }

    public DaoSession getDaoSession() {
        return daoSession;
    }
}
