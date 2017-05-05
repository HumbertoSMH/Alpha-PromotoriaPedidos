package promotoria.grocus.mx.promotoriapedidos.dao.impl;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import promotoria.grocus.mx.promotoriapedidos.PromotoriaPedidosApp;
import promotoria.grocus.mx.promotoriapedidos.business.Ruta;
import promotoria.grocus.mx.promotoriapedidos.business.persistence.TablasDeNegocio;
import promotoria.grocus.mx.promotoriapedidos.dao.PromotoriaPedidosDbHelper;
import promotoria.grocus.mx.promotoriapedidos.dao.RutaDao;
import promotoria.grocus.mx.promotoriapedidos.utils.LogUtil;

/**
 * Created by devmac02 on 17/07/15.
 */
public class RutaDaoImpl implements RutaDao {
    private static String CLASSNAME = RutaDaoImpl.class.getSimpleName();

    private static RutaDao rutaDao;
    private Context context;
    PromotoriaPedidosDbHelper mDbHelper;

    public  RutaDaoImpl( Context context) {
        this.context = context;
        this.mDbHelper = new PromotoriaPedidosDbHelper( context );
    }

    public static RutaDao getSingleton( ) {
        if (rutaDao == null) {
            rutaDao = new RutaDaoImpl(PromotoriaPedidosApp.getCurrentApplication() );
        }
        return rutaDao;
    }

    @Override
    public void insertRuta(Ruta ruta) {
        // Gets the data repository in write mode
        SQLiteDatabase db = mDbHelper.getWritableDatabase();

        // Create a new map of values, where column names are the keys
        ContentValues values = this.rellenarDatosAInsertar( ruta );

        // Insert the new row, returning the primary key value of the new row
        long newRowId;
        newRowId = db.insert(
                TablasDeNegocio.Rutas.TABLE_NAME,
                null,
                values);
        LogUtil.printLog(CLASSNAME, "insertRuta: ruta:" + ruta);

    }

    public Ruta getRutaById(int idRuta){
        SQLiteDatabase db = mDbHelper.getReadableDatabase();
        Ruta ruta = null;

        Cursor cursor = db.query(
                TablasDeNegocio.Rutas.TABLE_NAME,  // The table to query
                TablasDeNegocio.Rutas.getColumns(),                                // The columns to return
                TablasDeNegocio.Rutas.COLUMN_NAME_IDRUTA.column + " = " + idRuta,  // The columns for the WHERE clause
                null,                                                    // The values for the WHERE clause
                null,                                                   // don't group the rows
                null,                                                   // don't filter by row groups
                null                                                    // The sort order
        );

        if( cursor.getCount() > 0){
            cursor.moveToFirst();
            ruta = this.cargarObjetoRuta( cursor );
        }

        return ruta;
    }

    public Ruta getRutaPorClaveYPasswordDePromotor( String clavePromotor, String passwordPromotor ){
        SQLiteDatabase db = mDbHelper.getReadableDatabase();
        Ruta ruta = null;

        StringBuilder seccionWhereEnQuery = new StringBuilder();
        seccionWhereEnQuery.append( TablasDeNegocio.Rutas.COLUMN_NAME_CLAVEPROMOTOR.column + " = '" + clavePromotor + "' ");
        seccionWhereEnQuery.append( " AND " + TablasDeNegocio.Rutas.COLUMN_NAME_PASSWORDPROMOTOR.column + " = '" + passwordPromotor + "' ");

        Cursor cursor = null;
        try {
            cursor = db.query(
                    TablasDeNegocio.Rutas.TABLE_NAME,  // The table to query
                    TablasDeNegocio.Rutas.getColumns(),                                // The columns to return
                    seccionWhereEnQuery.toString(),  // The columns for the WHERE clause
                    null,                                                    // The values for the WHERE clause
                    null,                                                   // don't group the rows
                    null,                                                   // don't filter by row groups
                    null                                                    // The sort order
            );

            if( cursor.getCount() > 0){
                cursor.moveToFirst();
                ruta = this.cargarObjetoRuta( cursor );
            }

        }finally {
            if( cursor != null ){
                cursor.close();
            }
        }
        return ruta;
    }

    public long updateRuta( Ruta ruta){
        SQLiteDatabase db = mDbHelper.getWritableDatabase();

        // New value for one column
        ContentValues values = this.rellenarDatosAActualizar( ruta );

        return db.update( TablasDeNegocio.Rutas.TABLE_NAME , values, TablasDeNegocio.Rutas.COLUMN_NAME_IDRUTA.column + " = "
                + ruta.getIdRuta() , null);

    }

    public long deleteRutaById( int idRuta){
        SQLiteDatabase db = mDbHelper.getWritableDatabase();
        return db.delete(  TablasDeNegocio.Rutas.TABLE_NAME , TablasDeNegocio.Rutas.COLUMN_NAME_IDRUTA.column + " = "
                + idRuta , null);
    }

    public long deleteRutaAnterior( int idRutaActual ){
        SQLiteDatabase db = mDbHelper.getWritableDatabase();
        return db.delete(  TablasDeNegocio.Rutas.TABLE_NAME , TablasDeNegocio.Rutas.COLUMN_NAME_IDRUTA.column + " <> "
                + idRutaActual , null);

    }


    private ContentValues rellenarDatosAInsertar( Ruta ruta ) {
        ContentValues values = new ContentValues();
        values.put( TablasDeNegocio.Rutas.COLUMN_NAME_IDRUTA.column, ruta.getIdRuta() );
        values.put( TablasDeNegocio.Rutas.COLUMN_NAME_FECHAINICIO.column, ruta.getFechaInicio() );
        values.put( TablasDeNegocio.Rutas.COLUMN_NAME_FECHAFIN.column, ruta.getFechaFin() );
        values.put( TablasDeNegocio.Rutas.COLUMN_NAME_FECHAPROGRAMADASTRING.column, ruta.getFechaProgramadaString() );
        values.put( TablasDeNegocio.Rutas.COLUMN_NAME_FECHACREACIONSTRING.column, ruta.getFechaCreacionString() );
        values.put( TablasDeNegocio.Rutas.COLUMN_NAME_FECHAULTIMAMODIFICACION.column, ruta.getFechaUltimaModificacion() );
        values.put( TablasDeNegocio.Rutas.COLUMN_NAME_CLAVEPROMOTOR.column, ruta.getPromotor().getClavePromotor() );
        values.put( TablasDeNegocio.Rutas.COLUMN_NAME_PASSWORDPROMOTOR.column, ruta.getPromotor().getPassword() );
        return values;
    }

    private ContentValues rellenarDatosAActualizar( Ruta ruta ) {
        ContentValues values = new ContentValues();
        //values.put( Table.Rutas.COLUMN_NAME_IDRUTA.column ,  ruta.getIdRuta());
        values.put( TablasDeNegocio.Rutas.COLUMN_NAME_FECHACREACIONSTRING.column ,  ruta.getFechaCreacionString());
        values.put( TablasDeNegocio.Rutas.COLUMN_NAME_FECHAFIN.column ,  ruta.getFechaFin() );
        values.put( TablasDeNegocio.Rutas.COLUMN_NAME_FECHAINICIO.column ,  ruta.getFechaInicio() );
        values.put( TablasDeNegocio.Rutas.COLUMN_NAME_FECHAPROGRAMADASTRING.column ,  ruta.getFechaProgramadaString() );
        values.put( TablasDeNegocio.Rutas.COLUMN_NAME_FECHAULTIMAMODIFICACION.column, ruta.getFechaUltimaModificacion());
        values.put( TablasDeNegocio.Rutas.COLUMN_NAME_CLAVEPROMOTOR.column, ruta.getPromotor().getClavePromotor() );
        values.put( TablasDeNegocio.Rutas.COLUMN_NAME_PASSWORDPROMOTOR.column, ruta.getPromotor().getPassword() );
        return values;
    }

    private Ruta cargarObjetoRuta( Cursor cursor ){
        Ruta ruta = new Ruta();
        ruta.setIdRuta( "" + cursor.getInt( TablasDeNegocio.Rutas.COLUMN_NAME_IDRUTA.index ) ) ;
        ruta.setFechaInicio(cursor.getString( TablasDeNegocio.Rutas.COLUMN_NAME_FECHAINICIO.index ) ) ;
        ruta.setFechaFin(cursor.getString( TablasDeNegocio.Rutas.COLUMN_NAME_FECHAFIN.index) ) ;
        ruta.setFechaProgramadaString( cursor.getString( TablasDeNegocio.Rutas.COLUMN_NAME_FECHAPROGRAMADASTRING.index ) ) ;
        ruta.setFechaCreacionString( cursor.getString( TablasDeNegocio.Rutas.COLUMN_NAME_FECHACREACIONSTRING.index ) ) ;
        ruta.setFechaUltimaModificacion(cursor.getString( TablasDeNegocio.Rutas.COLUMN_NAME_FECHAULTIMAMODIFICACION.index ) );
        ruta.getPromotor().setUsuario(cursor.getString( TablasDeNegocio.Rutas.COLUMN_NAME_CLAVEPROMOTOR.index));
        ruta.getPromotor().setPassword(cursor.getString( TablasDeNegocio.Rutas.COLUMN_NAME_PASSWORDPROMOTOR.index));
        return ruta;
    }
}
