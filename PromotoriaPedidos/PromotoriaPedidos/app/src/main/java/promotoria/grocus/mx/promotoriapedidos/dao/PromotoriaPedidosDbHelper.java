package promotoria.grocus.mx.promotoriapedidos.dao;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import promotoria.grocus.mx.promotoriapedidos.business.persistence.TablasDeCatalogos;
import promotoria.grocus.mx.promotoriapedidos.business.persistence.TablasDeNegocio;
import promotoria.grocus.mx.promotoriapedidos.utils.Const;
import promotoria.grocus.mx.promotoriapedidos.utils.LogUtil;

/**
 * Created by devmac02 on 17/07/15.
 */
public class PromotoriaPedidosDbHelper extends SQLiteOpenHelper {
    private static final String CLASSNAME = PromotoriaPedidosDbHelper.class.getSimpleName();
    private Context context;


    public PromotoriaPedidosDbHelper(Context context) {
        super(context, Const.DATABASE_NAME, null, Const.DATABASE_VERSION);
        this.context = context;
    }
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(TablasDeNegocio.SQL_CREATE_TABLE_RUTAS);
        db.execSQL(TablasDeNegocio.SQL_CREATE_TABLE_VISITAS);
        db.execSQL(TablasDeNegocio.SQL_CREATE_TABLE_MEDICAMENTOS);
        db.execSQL(TablasDeNegocio.SQL_CREATE_TABLE_ENCUESTA);
        db.execSQL(TablasDeCatalogos.SQL_CREATE_TABLE__CATALOGO_MEDICAMENTOS);
        db.execSQL(TablasDeCatalogos.SQL_CREATE_TABLE_CATALOGO_PREGUNTAS);
        db.execSQL(TablasDeCatalogos.SQL_CREATE_TABLE_CATALOGO_RESPUESTAS);
        LogUtil.logInfo(CLASSNAME, "onCreate(SQLiteDatabase db)");
    }
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // This database is only a cache for online data, so its upgrade policy is
        // to simply to discard the data and start over
        db.execSQL( TablasDeNegocio.SQL_DELETE_ENCUESTAS );
        db.execSQL( TablasDeNegocio.SQL_CREATE_TABLE_MEDICAMENTOS );
        db.execSQL( TablasDeNegocio.SQL_DELETE_VISITAS );
        db.execSQL( TablasDeNegocio.SQL_DELETE_RUTAS );

        onCreate(db);
    }
    public void onDowngrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        onUpgrade(db, oldVersion, newVersion);
    }


    /*Public boolean checkDataBaseExists() {
        SQLiteDatabase checkDB = null;
        try {
            checkDB = SQLiteDatabase.openDatabase( Const.PATH + Const.DATABASE_NAME , null,
                    SQLiteDatabase.OPEN_READONLY);
            checkDB.close();
        } catch (SQLiteException e) {
            // La base de datos no existe aun
            LogUtil.logInfo( CLASSNAME , "La base de datos no existe error:" + e.getMessage() ) ;
        }
        LogUtil.logInfo( CLASSNAME , "checkDataBaseExists...La base de datos existe?: " + (checkDB != null) );
        return checkDB != null;
    }*/

}
