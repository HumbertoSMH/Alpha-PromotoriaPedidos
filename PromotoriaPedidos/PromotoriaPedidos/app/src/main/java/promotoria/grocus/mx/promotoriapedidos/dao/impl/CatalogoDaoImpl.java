package promotoria.grocus.mx.promotoriapedidos.dao.impl;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;
import java.util.List;

import promotoria.grocus.mx.promotoriapedidos.PromotoriaPedidosApp;
import promotoria.grocus.mx.promotoriapedidos.business.Medicamento;
import promotoria.grocus.mx.promotoriapedidos.business.persistence.TablasDeCatalogos;
import promotoria.grocus.mx.promotoriapedidos.dao.PromotoriaPedidosDbHelper;
import promotoria.grocus.mx.promotoriapedidos.utils.Pregunta;
import promotoria.grocus.mx.promotoriapedidos.utils.Respuesta;

/**
 * Created by devmac03 on 04/08/15.
 */
public class CatalogoDaoImpl implements CatalogoDao{
    private static String CLASSNAME = CatalogoDaoImpl.class.getSimpleName();

    private static CatalogoDao catalogoDao;
    private Context context;
    PromotoriaPedidosDbHelper mDbHelper;


    public CatalogoDaoImpl(Context context) {
        this.context = context;
        this.mDbHelper = new PromotoriaPedidosDbHelper( context );
    }

    public static CatalogoDao getSingleton( ) {
        if (catalogoDao == null) {
            catalogoDao = new CatalogoDaoImpl(PromotoriaPedidosApp.getCurrentApplication() );
        }
        return catalogoDao;
    }

    @Override
    public long insertarCatalogoMedicamentos(Medicamento medicamento, Integer idFarmacia) {
        SQLiteDatabase db = mDbHelper.getWritableDatabase();
        ContentValues valoresAinsertar = this.rellenarDatosAinsertarEnCatalogoMedicamento(medicamento, idFarmacia);

        long newRowId;
        newRowId = db.insert(
                TablasDeCatalogos.CatalogoMedicamentos.TABLE_NAME,
                null,
                valoresAinsertar);

        return newRowId;
    }


    @Override
    public long eliminarCatalogoMedicamentos() {
        SQLiteDatabase db = mDbHelper.getWritableDatabase();
        return db.delete(  TablasDeCatalogos.CatalogoMedicamentos.TABLE_NAME , null , null);
    }

    @Override
    public List<Medicamento> recuperarCatalogoMedicamentosPorIdFarmacia(Integer idFarmacia) {
        SQLiteDatabase db = mDbHelper.getReadableDatabase();
        List<Medicamento> medicamentos = new ArrayList<Medicamento>();

        Cursor cursor = null;
        try{
                cursor = db.query(
                TablasDeCatalogos.CatalogoMedicamentos.TABLE_NAME,  // The table to query
                TablasDeCatalogos.CatalogoMedicamentos.getColumns(),                                // The columns to return
                TablasDeCatalogos.CatalogoMedicamentos.COLUMN_NAME_IDFARMACIA.column + " = " + idFarmacia,                                        // The columns for the WHERE clause
                null,                                                    // The values for the WHERE clause
                null,                                                   // don't group the rows
                null,                                                   // don't filter by row groups
                null                                                    // The sort order
            );

            int size = cursor.getCount();
            if( size  > 0){
                cursor.moveToFirst();
                for (int i = 0; i < size; i++) {
                    medicamentos.add(this.cargarObjetoMedicamentoDesdeCursor(cursor));
                    cursor.moveToNext();
                }
            }
        }finally{
            if( cursor != null ){
                cursor.close();
            }
        }
        return medicamentos;
    }


    @Override
    public List<Integer> recuperarListaDeIdFarmaciaEnCatalogo() {
        SQLiteDatabase db = mDbHelper.getReadableDatabase();
        List<Integer> idFarmaciasList = new ArrayList<Integer>();

        String[] nombreColumnaIdFarmacia = {TablasDeCatalogos.CatalogoMedicamentos.COLUMN_NAME_IDFARMACIA.column };
        Cursor cursor = null;
        try {
            cursor = db.query(
                    TablasDeCatalogos.CatalogoMedicamentos.TABLE_NAME,       // The table to query
                    nombreColumnaIdFarmacia,                                 // The columns to return
                    null,                                                    // The columns for the WHERE clause
                    null,                                                    // The values for the WHERE clause
                    TablasDeCatalogos.CatalogoMedicamentos.COLUMN_NAME_IDFARMACIA.column  ,     // don't group the rows
                    null,                                                   // don't filter by row groups
                    null                                                    // The sort order
            );


            int size = cursor.getCount();
            if( size  > 0){
                cursor.moveToFirst();
                for (int i = 0; i < size; i++) {
                    Integer idFarmacia = cursor.getInt(0);
                    idFarmaciasList.add( idFarmacia );
                    cursor.moveToNext();
                }
            }

        }finally {
            if( cursor != null ){
                cursor.close();
            }
        }

        return idFarmaciasList;

    }

    @Override
    public long eliminarCatalogoPreguntasRespuestas() {
        SQLiteDatabase db = mDbHelper.getWritableDatabase();
        db.delete(  TablasDeCatalogos.CatalogoRespuestas.TABLE_NAME , null , null);
        db.delete(  TablasDeCatalogos.CatalogoPreguntas.TABLE_NAME , null , null);
        return 0;
    }

    @Override
    public long insertarCatalogoPreguntas(Pregunta pregunta, int idEncuesta) {
        SQLiteDatabase db = mDbHelper.getWritableDatabase();
        ContentValues valoresAinsertar = this.rellenarDatosAinsertarEnCatalogoPreguntas(pregunta, idEncuesta);

        long newRowId;
        newRowId = db.insert(
                TablasDeCatalogos.CatalogoPreguntas.TABLE_NAME,
                null,
                valoresAinsertar);

        return newRowId;
    }

    @Override
    public List<Pregunta> recuperarCatalogoPreguntasPorIdEncuesta(int idEncuesta) {
        SQLiteDatabase db = mDbHelper.getReadableDatabase();
        List<Pregunta> preguntas = new ArrayList<Pregunta>();

        Cursor cursor = null;
        try{
            cursor = db.query(
                    TablasDeCatalogos.CatalogoPreguntas.TABLE_NAME,  // The table to query
                    TablasDeCatalogos.CatalogoPreguntas.getColumns(),                                // The columns to return
                    TablasDeCatalogos.CatalogoPreguntas.COLUMN_NAME_IDENCUESTA.column + " = " + idEncuesta,                                        // The columns for the WHERE clause
                    null,                                                    // The values for the WHERE clause
                    null,                                                   // don't group the rows
                    null,                                                   // don't filter by row groups
                    null                                                    // The sort order
            );

            int size = cursor.getCount();
            if( size  > 0){
                cursor.moveToFirst();
                for (int i = 0; i < size; i++) {
                    preguntas.add(this.cargarObjetoPreguntasDesdeCursor(cursor));
                    cursor.moveToNext();
                }
            }
        }finally{
            if( cursor != null ){
                cursor.close();
            }
        }
        return preguntas;
    }



    @Override
    public List<Integer> recuperarListaIdEncuestaEnCatalogo( ) {
        SQLiteDatabase db = mDbHelper.getReadableDatabase();
        List<Integer> idEncuestaList = new ArrayList<Integer>();

        String[] nombreColumnaIdEncuesta = {TablasDeCatalogos.CatalogoPreguntas.COLUMN_NAME_IDENCUESTA.column };
        Cursor cursor = null;
        try {
            cursor = db.query(
                    TablasDeCatalogos.CatalogoPreguntas.TABLE_NAME,       // The table to query
                    nombreColumnaIdEncuesta,                                 // The columns to return
                    null,                                                    // The columns for the WHERE clause
                    null,                                                    // The values for the WHERE clause
                    TablasDeCatalogos.CatalogoPreguntas.COLUMN_NAME_IDENCUESTA.column  ,     // don't group the rows
                    null,                                                   // don't filter by row groups
                    null                                                    // The sort order
            );


            int size = cursor.getCount();
            if( size  > 0){
                cursor.moveToFirst();
                for (int i = 0; i < size; i++) {
                    Integer idEncuesta = cursor.getInt(0);
                    idEncuestaList.add( idEncuesta );
                    cursor.moveToNext();
                }
            }

        }finally {
            if( cursor != null ){
                cursor.close();
            }
        }

        return idEncuestaList;
    }

    @Override
    public long insertarCatalogoRespuesta(Respuesta respuesta, int idPregunta) {
        SQLiteDatabase db = mDbHelper.getWritableDatabase();
        ContentValues valoresAinsertar = this.rellenarDatosAinsertarEnCatalogoRespuesta(respuesta, idPregunta);

        long newRowId;
        newRowId = db.insert(
                TablasDeCatalogos.CatalogoRespuestas.TABLE_NAME,
                null,
                valoresAinsertar);

        return newRowId;
    }



    @Override
    public List<Respuesta> recuperarCatalogoRespuestaPorIdPregunta(int idPregunta) {
        SQLiteDatabase db = mDbHelper.getReadableDatabase();
        List<Respuesta> respuestas = new ArrayList<Respuesta>();

        Cursor cursor = null;
        try{
            cursor = db.query(
                    TablasDeCatalogos.CatalogoRespuestas.TABLE_NAME,  // The table to query
                    TablasDeCatalogos.CatalogoRespuestas.getColumns(),                                // The columns to return
                    TablasDeCatalogos.CatalogoRespuestas.COLUMN_NAME_IDPREGUNTA.column + " = " + idPregunta,                                        // The columns for the WHERE clause
                    null,                                                    // The values for the WHERE clause
                    null,                                                   // don't group the rows
                    null,                                                   // don't filter by row groups
                    null                                                    // The sort order
            );

            int size = cursor.getCount();
            if( size  > 0){
                cursor.moveToFirst();
                for (int i = 0; i < size; i++) {
                    respuestas.add(this.cargarObjetoRespuestaDesdeCursor(cursor));
                    cursor.moveToNext();
                }
            }
        }finally{
            if( cursor != null ){
                cursor.close();
            }
        }
        return respuestas;
    }




    /**
     *
     * BLOQUE PARA PREPARAR LOS DATOS
     *
     * ***/
    private ContentValues rellenarDatosAinsertarEnCatalogoMedicamento(Medicamento medicamento, Integer idFarmacia) {
        ContentValues values = new ContentValues();
        values.put(TablasDeCatalogos.CatalogoMedicamentos.COLUMN_NAME_IDMEDICAMENTO.column , medicamento.getIdMedicamento() );
        values.put(TablasDeCatalogos.CatalogoMedicamentos.COLUMN_NAME_NOMBREMEDICAMENTO.column , medicamento.getNombreMedicamento() );
        values.put(TablasDeCatalogos.CatalogoMedicamentos.COLUMN_NAME_MAXIMOPERMITIDO.column , medicamento.getMaximoPermitido() );
        values.put(TablasDeCatalogos.CatalogoMedicamentos.COLUMN_NAME_PRECIO.column , medicamento.getPrecio() );
        values.put(TablasDeCatalogos.CatalogoMedicamentos.COLUMN_NAME_CANTIDAD.column , medicamento.getCantidad() );
        values.put(TablasDeCatalogos.CatalogoMedicamentos.COLUMN_NAME_IDFARMACIA.column , idFarmacia );
        return values;
    }


    private Medicamento cargarObjetoMedicamentoDesdeCursor(Cursor cursor) {
        Medicamento medicamento = new Medicamento();
        medicamento.setIdMedicamento( cursor.getString(TablasDeCatalogos.CatalogoMedicamentos.COLUMN_NAME_IDMEDICAMENTO.index) );
        medicamento.setNombreMedicamento(cursor.getString(TablasDeCatalogos.CatalogoMedicamentos.COLUMN_NAME_NOMBREMEDICAMENTO.index));
        medicamento.setMaximoPermitido(cursor.getInt(TablasDeCatalogos.CatalogoMedicamentos.COLUMN_NAME_MAXIMOPERMITIDO.index));
        medicamento.setCantidad(cursor.getInt(TablasDeCatalogos.CatalogoMedicamentos.COLUMN_NAME_CANTIDAD.index));
        medicamento.setPrecio(cursor.getInt(TablasDeCatalogos.CatalogoMedicamentos.COLUMN_NAME_PRECIO.index));
        return medicamento;
    }

    private ContentValues rellenarDatosAinsertarEnCatalogoPreguntas(Pregunta pregunta , int idEncuesta) {
        ContentValues values = new ContentValues();
        values.put(TablasDeCatalogos.CatalogoPreguntas.COLUMN_NAME_IDPREGUNTA.column , pregunta.getIdPregunta() );
        values.put(TablasDeCatalogos.CatalogoPreguntas.COLUMN_NAME_DESCRIPCIONPREGUNTA.column , pregunta.getTextoPregunta() );
        values.put(TablasDeCatalogos.CatalogoPreguntas.COLUMN_NAME_IDENCUESTA.column , idEncuesta );
        return values;
    }

    private Pregunta cargarObjetoPreguntasDesdeCursor(Cursor cursor) {
        Pregunta pregunta = new Pregunta();
        pregunta.setIdPregunta(cursor.getString(TablasDeCatalogos.CatalogoPreguntas.COLUMN_NAME_IDPREGUNTA.index));
        pregunta.setTextoPregunta(cursor.getString(TablasDeCatalogos.CatalogoPreguntas.COLUMN_NAME_DESCRIPCIONPREGUNTA.index));
        return pregunta;
    }

    private ContentValues rellenarDatosAinsertarEnCatalogoRespuesta(Respuesta respuesta, int idPregunta) {
        ContentValues values = new ContentValues();
        values.put(TablasDeCatalogos.CatalogoRespuestas.COLUMN_NAME_IDRESPUESTA.column , respuesta.getIdRespuesta() );
        values.put(TablasDeCatalogos.CatalogoRespuestas.COLUMN_NAME_DESCRIPCIONRESPUESTA.column , respuesta.getTextoRespuesta() );
        values.put(TablasDeCatalogos.CatalogoRespuestas.COLUMN_NAME_IDPREGUNTA.column , idPregunta );
        return values;
    }

    private Respuesta cargarObjetoRespuestaDesdeCursor(Cursor cursor) {
        Respuesta respuesta = new Respuesta();
        respuesta.setIdRespuesta(cursor.getString(TablasDeCatalogos.CatalogoRespuestas.COLUMN_NAME_IDRESPUESTA.index));
        respuesta.setTextoRespuesta(cursor.getString(TablasDeCatalogos.CatalogoRespuestas.COLUMN_NAME_DESCRIPCIONRESPUESTA.index));
        return respuesta;
    }


}
