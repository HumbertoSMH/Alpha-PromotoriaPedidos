package promotoria.grocus.mx.promotoriapedidos.dao.impl;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;
import java.util.List;

import promotoria.grocus.mx.promotoriapedidos.PromotoriaPedidosApp;
import promotoria.grocus.mx.promotoriapedidos.business.Medicamento;
import promotoria.grocus.mx.promotoriapedidos.business.Visita;
import promotoria.grocus.mx.promotoriapedidos.business.persistence.TablasDeNegocio;
import promotoria.grocus.mx.promotoriapedidos.dao.EncuestaDao;
import promotoria.grocus.mx.promotoriapedidos.dao.PromotoriaPedidosDbHelper;
import promotoria.grocus.mx.promotoriapedidos.utils.LogUtil;
import promotoria.grocus.mx.promotoriapedidos.utils.PreguntaRespuesta;

/**
 * Created by devmac02 on 17/07/15.
 */
public class EncuestaDaoImpl implements EncuestaDao {

    private static String CLASSNAME = EncuestaDaoImpl.class.getSimpleName();

    private static EncuestaDao encuestaDao;
    private Context context;
    PromotoriaPedidosDbHelper mDbHelper;

    public EncuestaDaoImpl(Context context) {
        this.context = context;
        this.mDbHelper = new PromotoriaPedidosDbHelper( context );
    }

    public static EncuestaDao getSingleton( ) {
        if (encuestaDao == null) {
            encuestaDao = new EncuestaDaoImpl(PromotoriaPedidosApp.getCurrentApplication() );
        }
        return encuestaDao;
    }

    @Override
    public void insertEncuesta( PreguntaRespuesta[] preguntasRespuestas, Visita visita ) {
        int idVisita = Integer.parseInt(visita.getIdVisita());
        // Gets the data repository in write mode
        SQLiteDatabase db = mDbHelper.getWritableDatabase();

//        int idVisita = Integer.parseInt(encuesta.getIdVisita());


        int totPreguntas = preguntasRespuestas.length;
        for (int j = 0; j < totPreguntas; j++) {

            ContentValues values = this.rellenarDatosAInsertar(preguntasRespuestas[j], visita.getIdEncuesta(), idVisita);

            // Insert the new row, returning the primary key value of the new row
            long newRowId;
            newRowId = db.insert(
                    TablasDeNegocio.Encuestas.TABLE_NAME,
                    null,
                    values);
        }
        LogUtil.printLog(CLASSNAME, "insertaEncuestas: encuestaPersona:" + preguntasRespuestas);
    }

    public long deleteEncuestaByIdVisita( int idVisita){
        SQLiteDatabase db = mDbHelper.getReadableDatabase();
        return db.delete(
                TablasDeNegocio.Encuestas.TABLE_NAME ,
                TablasDeNegocio.Encuestas.COLUMN_NAME_IDVISITA.column + " = " + idVisita ,
                null);
    }

    public PreguntaRespuesta[] getEncuestasByIdVisita(Integer idVisita){
        SQLiteDatabase db = mDbHelper.getReadableDatabase();
        PreguntaRespuesta[] encuestaArray = new PreguntaRespuesta[0];

        Cursor cursor = db.query(
                TablasDeNegocio.Encuestas.TABLE_NAME,  // The table to query
                TablasDeNegocio.Encuestas.getColumns(),                                // The columns to return
                TablasDeNegocio.Encuestas.COLUMN_NAME_IDVISITA.column + " = " + idVisita,                                        // The columns for the WHERE clause
                null,                                                    // The values for the WHERE clause
                null,                                                   // don't group the rows
                null,                                                   // don't filter by row groups
                null                                                    // The sort order
        );

        int size = cursor.getCount();
        if( size  > 0){
            encuestaArray = new PreguntaRespuesta[size];
            cursor.moveToFirst();
            for (int i = 0; i < size; i++) {
                encuestaArray[i] = this.cargarObjetoPreguntaRespuesta(cursor);
                cursor.moveToNext();
            }
        }

        return encuestaArray;
    }

//    public PreguntaRespuesta[] getEncuestasByIdVisita(Integer idVisita){
//        SQLiteDatabase db = mDbHelper.getReadableDatabase();
//        PreguntaRespuesta[] encuestaArray = new PreguntaRespuesta[0];
//
//        List<String> identificadorEncuesta = this.recuperarIdentificadorEncuesta(db, idVisita);
//        int size = identificadorEncuesta.size();
//        if( size > 0  ){
//            encuestaArray = new PreguntaRespuesta[ size ];
//            for( int j = 0 ; j < size ; j++ ){
//                Cursor cursor = db.query(
//                        TablasDeNegocio.Encuestas.TABLE_NAME,  // The table to query
//                        TablasDeNegocio.Encuestas.getColumns(),                                // The columns to return
//                        TablasDeNegocio.Encuestas.COLUMN_NAME_IDENTIFICADORENCUESTA.column + " = '" + identificadorEncuesta.get(j) + "'",  // The columns for the WHERE clause
//                        null,                                                    // The values for the WHERE clause
//                        null,                                                   // don't group the rows
//                        null,                                                   // don't filter by row groups
//                        null                                                    // The sort order
//                );
//
//                int count = cursor.getCount();
//                encuestaArray = new PreguntaRespuesta[ count ];
//                cursor.moveToFirst();
//                for( int i = 0 ; i < count ; i++ ){
//                    encuestaArray[i] = this.cargarObjetoPreguntaRespuesta(cursor);
//                    cursor.moveToNext();
//                }
//            }
//
//        }
//        return encuestaArray;
//    }

//    private List<String> recuperarIdentificadorEncuesta( SQLiteDatabase db  , Integer idVisita) {
//        List<String> personas = new ArrayList<String>();
//        String[] columns = {TablasDeNegocio.Encuestas.COLUMN_NAME_IDENTIFICADORENCUESTA.column};
//        Cursor cursor = db.query(
//                TablasDeNegocio.Encuestas.TABLE_NAME,  // The table to query
//                columns,                                // The columns to return
//                TablasDeNegocio.Encuestas.COLUMN_NAME_IDVISITA.column + " = " + idVisita,  // The columns for the WHERE clause
//                null,                                                    // The values for the WHERE clause
//                TablasDeNegocio.Encuestas.COLUMN_NAME_IDENTIFICADORENCUESTA.column,  // don't group the rows
//                null,                                                   // don't filter by row groups
//                null                                                    // The sort order
//        );
//
//        int count = cursor.getCount();
//        if( count > 0 ){
//            cursor.moveToFirst();
//            for( int j = 0 ; j < count; j++){
//                String persona = cursor.getString(0);
//                personas.add(persona);
//                cursor.moveToNext();
//            }
//        }
//        return personas;
//    }


    private ContentValues rellenarDatosAInsertar( PreguntaRespuesta preguntaRespuesta, String idEncuesta, int idVisita ) {
        ContentValues values = new ContentValues();
        values.put(TablasDeNegocio.Encuestas.COLUMN_NAME_IDPREGUNTA.column, preguntaRespuesta.getPregunta().getIdPregunta());
        values.put(TablasDeNegocio.Encuestas.COLUMN_NAME_DESCRIPCIONPREGUNTA.column, preguntaRespuesta.getPregunta().getTextoPregunta() );
        values.put( TablasDeNegocio.Encuestas.COLUMN_NAME_IDRESPUESTA.column, preguntaRespuesta.getRespuestaElegida().getIdRespuesta() );
        values.put( TablasDeNegocio.Encuestas.COLUMN_NAME_DESCRIPCIONRESPUESTA.column, preguntaRespuesta.getRespuestaElegida().getTextoRespuesta() );

        values.put(TablasDeNegocio.Encuestas.COLUMN_NAME_IDENTIFICADORENCUESTA.column, idEncuesta );
        values.put(TablasDeNegocio.Encuestas.COLUMN_NAME_IDVISITA.column, idVisita);
        return values;
    }

    private PreguntaRespuesta cargarObjetoPreguntaRespuesta( Cursor cursor ) {
        PreguntaRespuesta preguntaRespuesta = new PreguntaRespuesta();
        preguntaRespuesta.getPregunta().setIdPregunta(cursor.getString(TablasDeNegocio.Encuestas.COLUMN_NAME_IDPREGUNTA.index));
        preguntaRespuesta.getPregunta().setTextoPregunta(cursor.getString(TablasDeNegocio.Encuestas.COLUMN_NAME_DESCRIPCIONPREGUNTA.index));
        preguntaRespuesta.getRespuestaElegida().setIdRespuesta(cursor.getString(TablasDeNegocio.Encuestas.COLUMN_NAME_IDRESPUESTA.index));
        preguntaRespuesta.getRespuestaElegida().setTextoRespuesta(cursor.getString(TablasDeNegocio.Encuestas.COLUMN_NAME_DESCRIPCIONRESPUESTA.index));
        return preguntaRespuesta;
    }


}
