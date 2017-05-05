package promotoria.grocus.mx.promotoriapedidos;

import android.app.Application;
import android.content.res.Configuration;
import android.database.sqlite.SQLiteDatabase;

import java.util.Locale;

import promotoria.grocus.mx.promotoriapedidos.dao.PromotoriaPedidosDbHelper;
import promotoria.grocus.mx.promotoriapedidos.utils.Const;
import promotoria.grocus.mx.promotoriapedidos.utils.LogUtil;


/**
 * Created by devmac03 on 06/07/15.
 */
public class PromotoriaPedidosApp extends Application {
    private static PromotoriaPedidosApp singleton;
    private PromotoriaPedidosDbHelper promotoriaPedidosDbHelper;
    private static final String CLASSNAME = PromotoriaPedidosApp.class.getSimpleName();
    private static String cveIsoLanguaje = "es";

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        LogUtil.printLog(CLASSNAME, "onConfigurationChanged()  newConfig=" + newConfig);
    }

    @Override
    public void onCreate() {
        super.onCreate();
        singleton = this;

        setCveIsoLanguaje();

        this.prepareDatabase();
        LogUtil.printLog( CLASSNAME , "onCreate() OK " );
    }

    private void prepareDatabase() {
        LogUtil.logInfo( CLASSNAME , "prepareDatabase.. start" );
        this.promotoriaPedidosDbHelper = new PromotoriaPedidosDbHelper( singleton );
//        if( this.promotorMovilDbHelper.checkDataBaseExists() == false ){
        this.promotoriaPedidosDbHelper = new PromotoriaPedidosDbHelper( singleton );
        SQLiteDatabase db = this.promotoriaPedidosDbHelper.getWritableDatabase();
        db.openOrCreateDatabase(Const.PATH + Const.DATABASE_NAME, null);
        //this.promotorMovilDbHelper.onCreate( db );
//        }else{
//            //No se ejecuta acción
//        }

    }

    @Override
    public void onLowMemory() {
        super.onLowMemory();
        LogUtil.logWarn( CLASSNAME , "onLowMemory()" );
    }

    @Override
    public void onTerminate() {
        super.onTerminate();
        LogUtil.logInfo( CLASSNAME , "onTerminate()" );
    }


//    @SuppressLint("NewApi")
    @Override
    public void onTrimMemory(int level) {
        super.onTrimMemory(level);

        if (level == Application.TRIM_MEMORY_BACKGROUND) {
            LogUtil.logInfo(CLASSNAME, "onTrimMemory() level= TRIM_MEMORY_BACKGROUND ");
        } else if (level == Application.TRIM_MEMORY_COMPLETE) {
            LogUtil.logInfo(CLASSNAME, "onTrimMemory() level= TRIM_MEMORY_COMPLETE ");
        } else if (level == Application.TRIM_MEMORY_MODERATE) {
            LogUtil.logInfo(CLASSNAME, "onTrimMemory() level= TRIM_MEMORY_MODERATE ");
        } else if (level == Application.TRIM_MEMORY_RUNNING_CRITICAL) {
            LogUtil.logInfo(CLASSNAME,
                    "onTrimMemory() level= TRIM_MEMORY_RUNNING_CRITICAL ");
        } else if (level == Application.TRIM_MEMORY_RUNNING_LOW) {
            LogUtil.logInfo(CLASSNAME, "onTrimMemory() level= TRIM_MEMORY_RUNNING_LOW ");
        } else if (level == Application.TRIM_MEMORY_RUNNING_MODERATE) {
            LogUtil.logInfo(CLASSNAME,
                    "onTrimMemory() level= TRIM_MEMORY_RUNNING_MODERATE ");
        } else if (level == Application.TRIM_MEMORY_UI_HIDDEN) {
            LogUtil.logInfo(CLASSNAME, "onTrimMemory() level= TRIM_MEMORY_UI_HIDDEN ");
        }
    }

    public static PromotoriaPedidosApp getCurrentApplication() {
        return singleton;
    }


    private void setCveIsoLanguaje() {
        cveIsoLanguaje = Locale.getDefault().getLanguage();
        if (cveIsoLanguaje == null || cveIsoLanguaje.length() == 0) {
            cveIsoLanguaje = "es";
        }
    }

    public synchronized void waitForIdle() {
        LogUtil.logInfo( CLASSNAME , "waitForIdle () ENTRO");
    }

    public static String getCveIsoLanguaje() {
        return cveIsoLanguaje;
    }
}
