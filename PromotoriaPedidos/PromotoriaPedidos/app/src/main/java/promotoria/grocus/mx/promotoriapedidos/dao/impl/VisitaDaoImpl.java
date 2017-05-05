package promotoria.grocus.mx.promotoriapedidos.dao.impl;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import promotoria.grocus.mx.promotoriapedidos.PromotoriaPedidosApp;
import promotoria.grocus.mx.promotoriapedidos.business.Visita;
import promotoria.grocus.mx.promotoriapedidos.business.persistence.TablasDeNegocio;
import promotoria.grocus.mx.promotoriapedidos.business.utils.EstatusVisita;
import promotoria.grocus.mx.promotoriapedidos.business.utils.RespuestaBinaria;
import promotoria.grocus.mx.promotoriapedidos.dao.PromotoriaPedidosDbHelper;
import promotoria.grocus.mx.promotoriapedidos.dao.VisitaDao;
import promotoria.grocus.mx.promotoriapedidos.utils.LogUtil;

/**
 * Created by devmac02 on 17/07/15.
 */
public class VisitaDaoImpl implements VisitaDao{

    private static String CLASSNAME = VisitaDaoImpl.class.getSimpleName();

    private static VisitaDao visitaDao;
    private Context context;
    PromotoriaPedidosDbHelper mDbHelper;

    public VisitaDaoImpl(Context context) {
        this.context = context;
        this.mDbHelper = new PromotoriaPedidosDbHelper( context );
    }

    public static VisitaDao getSingleton( ) {
        if (visitaDao == null) {
            visitaDao = new VisitaDaoImpl(PromotoriaPedidosApp.getCurrentApplication() );
        }
        return visitaDao;
    }

    @Override
    public void insertVisita( Visita visita , int idRuta) {
        // Gets the data repository in write mode
        SQLiteDatabase db = mDbHelper.getWritableDatabase();

        // Create a new map of values, where column names are the keys
        ContentValues values = this.rellenarDatosAInsertar( visita , idRuta );

        // Insert the new row, returning the primary key value of the new row
        long newRowId;
        newRowId = db.insert(
                TablasDeNegocio.Visitas.TABLE_NAME,
                null,
                values);
        LogUtil.printLog(CLASSNAME, "insertVisita: visita:" + visita);
    }

    public Visita getVisitaById( Integer idVisita){
        SQLiteDatabase db = mDbHelper.getReadableDatabase();
        Visita visita = null;

        Cursor cursor = db.query(
                TablasDeNegocio.Visitas.TABLE_NAME,  // The table to query
                TablasDeNegocio.Visitas.getColumns(),                                // The columns to return
                TablasDeNegocio.Visitas.COLUMN_NAME_IDVISITA.column + " = " + idVisita,  // The columns for the WHERE clause
                null,                                                    // The values for the WHERE clause
                null,                                                   // don't group the rows
                null,                                                   // don't filter by row groups
                null                                                    // The sort order
        );

        if( cursor.getCount() > 0){
            cursor.moveToFirst();
            visita = this.cargarObjetoVisita( cursor);
        }

        return visita;
    }

    public Visita[] getVisitasByIdRuta(  Integer idRuta){
        SQLiteDatabase db = mDbHelper.getReadableDatabase();
        Visita[] visitasArray = new Visita[0];

        Cursor cursor = db.query(
                TablasDeNegocio.Visitas.TABLE_NAME,  // The table to query
                TablasDeNegocio.Visitas.getColumns(),                                // The columns to return
                TablasDeNegocio.Visitas.COLUMN_NAME_IDRUTA.column + " = " + idRuta,                                        // The columns for the WHERE clause
                null,                                                    // The values for the WHERE clause
                null,                                                   // don't group the rows
                null,                                                   // don't filter by row groups
                null                                                    // The sort order
        );

        int size = cursor.getCount();
        if( size  > 0){
            visitasArray = new Visita[size];
            cursor.moveToFirst();
            for (int i = 0; i < size; i++) {
                visitasArray[i] = this.cargarObjetoVisita(cursor);
                cursor.moveToNext();
            }
        }

        return visitasArray;

    }

    public Integer[] getIdVisitasQueNoSonDeRuta(  Integer idRuta){
        SQLiteDatabase db = mDbHelper.getReadableDatabase();
        Integer[] idVisitaSinRutaArray = new Integer[0];

        String[] columnsIdVisita = {TablasDeNegocio.Visitas.COLUMN_NAME_IDVISITA.column};
        Cursor cursor = db.query(
                TablasDeNegocio.Visitas.TABLE_NAME,  // The table to query
                columnsIdVisita,                                // The columns to return
                TablasDeNegocio.Visitas.COLUMN_NAME_IDRUTA.column + " <> " + idRuta,                                        // The columns for the WHERE clause
                null,                                                    // The values for the WHERE clause
                null,                                                   // don't group the rows
                null,                                                   // don't filter by row groups
                null                                                    // The sort order
        );

        int size = cursor.getCount();
        if( size  > 0){
            idVisitaSinRutaArray = new Integer[size];
            cursor.moveToFirst();
            for (int i = 0; i < size; i++) {
                idVisitaSinRutaArray[i] = cursor.getInt(0);
                cursor.moveToNext();
            }
        }
        return idVisitaSinRutaArray;

    }

    public long updateVisita( Visita visita , int idRuta){
        SQLiteDatabase db = mDbHelper.getWritableDatabase();

        // New value for one column
        ContentValues values = this.rellenarDatosAActualizar(visita, idRuta);

        return db.update( TablasDeNegocio.Visitas.TABLE_NAME , values, TablasDeNegocio.Visitas.COLUMN_NAME_IDVISITA.column + " = "
                + visita.getIdVisita() , null);

    }

    public long updateIdRutaEnVisita( Visita visita , int idRuta){
        SQLiteDatabase db = mDbHelper.getWritableDatabase();

        // New value for one column
        ContentValues values = this.rellenarDatosAActualizarSoloIdRuta(visita, idRuta);
        return db.update( TablasDeNegocio.Visitas.TABLE_NAME , values, TablasDeNegocio.Visitas.COLUMN_NAME_IDVISITA.column + " = "
                + visita.getIdVisita() , null);
    }

    public long deleteVisitaById( int idVisita){
        SQLiteDatabase db = mDbHelper.getWritableDatabase();
        return db.delete(TablasDeNegocio.Visitas.TABLE_NAME, TablasDeNegocio.Visitas.COLUMN_NAME_IDVISITA.column + " = "
                + idVisita, null);
    }

    private ContentValues rellenarDatosAInsertar( Visita visita , int idRuta ) {
        ContentValues values = new ContentValues();
        values.put( TablasDeNegocio.Visitas.COLUMN_NAME_IDVISITA.column, visita.getIdVisita() );
        values.put( TablasDeNegocio.Visitas.COLUMN_NAME_ESTATUSVISITA.column, visita.getEstatusVisita().name() );
        values.put( TablasDeNegocio.Visitas.COLUMN_NAME_FECHACHECKIN.column, visita.getFechaCheckIn() );
        values.put( TablasDeNegocio.Visitas.COLUMN_NAME_FECHACHECKOUT.column, visita.getFechaCheckOut() );

        values.put( TablasDeNegocio.Visitas.COLUMN_NAME_GERENTE.column, visita.getGerente().getNombre() );
        values.put( TablasDeNegocio.Visitas.COLUMN_NAME_EMAILGERENTE.column, visita.getEmail() );
        values.put( TablasDeNegocio.Visitas.COLUMN_NAME_FIRMAGERENTE.column, visita.getFirmaGerente() );
        values.put( TablasDeNegocio.Visitas.COLUMN_NAME_COMENTARIOS.column, visita.getComentarios() );

        values.put( TablasDeNegocio.Visitas.COLUMN_NAME_APLICARENCUESTA.column, visita.getAplicarEncuesta().name() );
        values.put( TablasDeNegocio.Visitas.COLUMN_NAME_IDENCUESTA.column, visita.getIdEncuesta() );
        values.put( TablasDeNegocio.Visitas.COLUMN_NAME_ENCUESTACAPTURADA.column, visita.getEncuestaCapturada().name() );
        values.put( TablasDeNegocio.Visitas.COLUMN_NAME_PEDIDOCAPTURADO.column, visita.getEncuestaCapturada().name() );


        values.put( TablasDeNegocio.Visitas.COLUMN_NAME_IDFARMACIA.column, visita.getIdFarmacia() );
        values.put( TablasDeNegocio.Visitas.COLUMN_NAME_NOMBREFARMACIA.column, visita.getNombreFarmacia() );
        values.put( TablasDeNegocio.Visitas.COLUMN_NAME_DIRECCIONFARMACIA.column, visita.getDireccionFarmacia() );

        values.put( TablasDeNegocio.Visitas.COLUMN_NAME_LATITUD.column, visita.getLocation().getLatitud() );
        values.put( TablasDeNegocio.Visitas.COLUMN_NAME_LONGITUD.column, visita.getLocation().getLongitud() );

        values.put( TablasDeNegocio.Visitas.COLUMN_NAME_CHECKINNOTIFICADO.column, visita.getCheckInNotificado().name() );

        values.put( TablasDeNegocio.Visitas.COLUMN_NAME_IDRUTA.column, idRuta );
        return values;
    }

    private Visita cargarObjetoVisita( Cursor cursor ){
        Visita visita = new Visita();
        visita.setIdVisita("" + cursor.getInt(TablasDeNegocio.Visitas.COLUMN_NAME_IDVISITA.index));

        String estatusNombre = cursor.getString( TablasDeNegocio.Visitas.COLUMN_NAME_ESTATUSVISITA.index );
        visita.setEstatusVisita(EstatusVisita.valueOf(estatusNombre)) ;

        visita.setFechaCheckIn(cursor.getString(TablasDeNegocio.Visitas.COLUMN_NAME_FECHACHECKIN.index)) ;
        visita.setFechaCheckOut(cursor.getString(TablasDeNegocio.Visitas.COLUMN_NAME_FECHACHECKOUT.index));

        visita.getGerente().setNombre(cursor.getString(TablasDeNegocio.Visitas.COLUMN_NAME_GERENTE.index));
        visita.setEmail(cursor.getString(TablasDeNegocio.Visitas.COLUMN_NAME_EMAILGERENTE.index));
        visita.setFirmaGerente(cursor.getBlob(TablasDeNegocio.Visitas.COLUMN_NAME_FIRMAGERENTE.index)) ;
        visita.setComentarios(cursor.getString(TablasDeNegocio.Visitas.COLUMN_NAME_COMENTARIOS.index));

        String aplicarEncuestaName = cursor.getString(TablasDeNegocio.Visitas.COLUMN_NAME_APLICARENCUESTA.index);
        visita.setAplicarEncuesta(RespuestaBinaria.valueOf(aplicarEncuestaName));

        visita.setIdEncuesta(cursor.getString(TablasDeNegocio.Visitas.COLUMN_NAME_IDENCUESTA.index));

        String encuestaCapturadaName = cursor.getString(TablasDeNegocio.Visitas.COLUMN_NAME_ENCUESTACAPTURADA.index);
        visita.setEncuestaCapturada(RespuestaBinaria.valueOf(encuestaCapturadaName));

        String pedidoCapturadoName = cursor.getString(TablasDeNegocio.Visitas.COLUMN_NAME_PEDIDOCAPTURADO.index);
        visita.setPedidoCapturado(RespuestaBinaria.valueOf(pedidoCapturadoName));


        visita.setIdFarmacia( cursor.getString(TablasDeNegocio.Visitas.COLUMN_NAME_IDFARMACIA.index) );
        visita.setNombreFarmacia(cursor.getString(TablasDeNegocio.Visitas.COLUMN_NAME_NOMBREFARMACIA.index)); ;
        visita.setDireccionFarmacia(cursor.getString(TablasDeNegocio.Visitas.COLUMN_NAME_DIRECCIONFARMACIA.index));

        visita.getLocation().setLatitud(cursor.getString(TablasDeNegocio.Visitas.COLUMN_NAME_LATITUD.index));
        visita.getLocation().setLongitud(cursor.getString(TablasDeNegocio.Visitas.COLUMN_NAME_LONGITUD.index));

        String checkInNotificadoName = cursor.getString(TablasDeNegocio.Visitas.COLUMN_NAME_CHECKINNOTIFICADO.index);
        visita.setCheckInNotificado(RespuestaBinaria.valueOf(checkInNotificadoName));

        return visita;
    }

    private ContentValues rellenarDatosAActualizar( Visita visita , int idRuta ) {
        ContentValues values = new ContentValues();
        //values.put( Table.Visitas.COLUMN_NAME_IDVISITA.column, visita.getIdVisita() );
        values.put(TablasDeNegocio.Visitas.COLUMN_NAME_ESTATUSVISITA.column, visita.getEstatusVisita().name());
        values.put(TablasDeNegocio.Visitas.COLUMN_NAME_FECHACHECKIN.column, visita.getFechaCheckIn());
        values.put(TablasDeNegocio.Visitas.COLUMN_NAME_FECHACHECKOUT.column, visita.getFechaCheckOut());

        values.put(TablasDeNegocio.Visitas.COLUMN_NAME_GERENTE.column, visita.getGerente().getNombre());
        values.put(TablasDeNegocio.Visitas.COLUMN_NAME_EMAILGERENTE.column, visita.getEmail());
        values.put(TablasDeNegocio.Visitas.COLUMN_NAME_FIRMAGERENTE.column, visita.getFirmaGerente());
        values.put(TablasDeNegocio.Visitas.COLUMN_NAME_COMENTARIOS.column, visita.getComentarios());

        values.put(TablasDeNegocio.Visitas.COLUMN_NAME_APLICARENCUESTA.column, visita.getAplicarEncuesta().name());
        values.put(TablasDeNegocio.Visitas.COLUMN_NAME_IDENCUESTA.column, visita.getIdEncuesta());
        values.put(TablasDeNegocio.Visitas.COLUMN_NAME_ENCUESTACAPTURADA.column, visita.getEncuestaCapturada().name());
        values.put(TablasDeNegocio.Visitas.COLUMN_NAME_PEDIDOCAPTURADO.column, visita.getPedidoCapturado().name());

        values.put(TablasDeNegocio.Visitas.COLUMN_NAME_IDFARMACIA.column, visita.getIdFarmacia());
        values.put(TablasDeNegocio.Visitas.COLUMN_NAME_NOMBREFARMACIA.column, visita.getNombreFarmacia());
        values.put(TablasDeNegocio.Visitas.COLUMN_NAME_DIRECCIONFARMACIA.column, visita.getDireccionFarmacia());

        values.put(TablasDeNegocio.Visitas.COLUMN_NAME_LATITUD.column, visita.getLocation().getLatitud());
        values.put(TablasDeNegocio.Visitas.COLUMN_NAME_LONGITUD.column, visita.getLocation().getLongitud());

        values.put(TablasDeNegocio.Visitas.COLUMN_NAME_CHECKINNOTIFICADO.column, visita.getCheckInNotificado().name());

        values.put(TablasDeNegocio.Visitas.COLUMN_NAME_IDRUTA.column, idRuta);
        return values;
    }

    private ContentValues rellenarDatosAActualizarSoloIdRuta( Visita visita , int idRuta ) {
        ContentValues values = new ContentValues();
        //values.put( Table.Visitas.COLUMN_NAME_IDVISITA.column, visita.getIdVisita() );
        values.put( TablasDeNegocio.Visitas.COLUMN_NAME_IDRUTA.column, idRuta );
        return values;
    }
}
