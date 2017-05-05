package promotoria.grocus.mx.promotoriapedidos.dao.impl;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import promotoria.grocus.mx.promotoriapedidos.PromotoriaPedidosApp;
import promotoria.grocus.mx.promotoriapedidos.business.Medicamento;
import promotoria.grocus.mx.promotoriapedidos.business.persistence.TablasDeNegocio;
import promotoria.grocus.mx.promotoriapedidos.dao.MedicamentoDao;
import promotoria.grocus.mx.promotoriapedidos.dao.PromotoriaPedidosDbHelper;
import promotoria.grocus.mx.promotoriapedidos.utils.LogUtil;

/**
 * Created by devmac02 on 17/07/15.
 */
public class MedicamentoDaoImpl implements MedicamentoDao{
    private static String CLASSNAME = MedicamentoDaoImpl.class.getSimpleName();

    private static MedicamentoDao medicamentoDao;
    private Context context;
    PromotoriaPedidosDbHelper mDbHelper;

    public MedicamentoDaoImpl(Context context) {
        this.context = context;
        this.mDbHelper = new PromotoriaPedidosDbHelper( context );
    }

    public static MedicamentoDao getSingleton( ) {
        if (medicamentoDao == null) {
            medicamentoDao = new MedicamentoDaoImpl(PromotoriaPedidosApp.getCurrentApplication() );
        }
        return medicamentoDao;
    }

    @Override
    public long insertMedicamento( Medicamento medicamento , int idVisita){
        // Gets the data repository in write mode
        SQLiteDatabase db = mDbHelper.getWritableDatabase();

        // Create a new map of values, where column names are the keys
        ContentValues values = this.rellenarDatosAInsertar( medicamento , idVisita );

        // Insert the new row, returning the primary key value of the new row
        long newRowId;
        newRowId = db.insert(
                TablasDeNegocio.Medicamentos.TABLE_NAME,
                null,
                values);
        LogUtil.printLog(CLASSNAME, "insertProducto: producto:" + medicamento);
        return newRowId; //Noi es necesario recuperar un id de la tabla
    }

//    public Medicamento getMedicamentoById(String idMedicamento){
//        SQLiteDatabase db = mDbHelper.getReadableDatabase();
//        Medicamento medicamento = null;
//
//        Cursor cursor = db.query(
//                TablasDeNegocio.Medicamentos.TABLE_NAME,  // The table to query
//                TablasDeNegocio.Medicamentos.getColumns(),                                // The columns to return
//                TablasDeNegocio.Medicamentos.COLUMN_NAME_IDMEDICAMENTO.column + " = " + idMedicamento,  // The columns for the WHERE clause
//                null,                                                    // The values for the WHERE clause
//                null,                                                   // don't group the rows
//                null,                                                   // don't filter by row groups
//                null                                                    // The sort order
//        );
//
//        if( cursor.getCount() > 0){
//            cursor.moveToFirst();
//            medicamento = this.cargarObjetoMedicamento(cursor);
//        }
//
//        return medicamento;
//    }

    public Medicamento[] getMedicamentosByIdVisita( Integer idVisita){
        SQLiteDatabase db = mDbHelper.getReadableDatabase();
        Medicamento[] medicamentosArray = new Medicamento[0];

        Cursor cursor = db.query(
                TablasDeNegocio.Medicamentos.TABLE_NAME,  // The table to query
                TablasDeNegocio.Medicamentos.getColumns(),                                // The columns to return
                TablasDeNegocio.Medicamentos.COLUMN_NAME_IDVISITA.column + " = " + idVisita,                                        // The columns for the WHERE clause
                null,                                                    // The values for the WHERE clause
                null,                                                   // don't group the rows
                null,                                                   // don't filter by row groups
                null                                                    // The sort order
        );

        int size = cursor.getCount();
        if( size  > 0){
            medicamentosArray = new Medicamento[size];
            cursor.moveToFirst();
            for (int i = 0; i < size; i++) {
                medicamentosArray[i] = this.cargarObjetoMedicamento(cursor);
                cursor.moveToNext();
            }
        }

        return medicamentosArray;
    }

    public long updateMedicamento( Medicamento medicamento, int idVisita){
        SQLiteDatabase db = mDbHelper.getWritableDatabase();

        // New value for one column
        ContentValues values = this.rellenarDatosAActualizar(medicamento, idVisita);

        return db.update( TablasDeNegocio.Medicamentos.TABLE_NAME , values, TablasDeNegocio.Medicamentos.COLUMN_NAME_IDMEDICAMENTO.column + " = "
                + medicamento.getIdMedicamento() , null);
    }

    public long deleteMedicamentoById( String codigoMedicaemntos, int idVisita){
        SQLiteDatabase db = mDbHelper.getWritableDatabase();
        return db.delete(  TablasDeNegocio.Medicamentos.TABLE_NAME , TablasDeNegocio.Medicamentos.COLUMN_NAME_IDMEDICAMENTO.column + " = '"
                + codigoMedicaemntos + "'" , null);
    }

    public long deleteMedicamentoByIdVisita( int idVisita){
        SQLiteDatabase db = mDbHelper.getWritableDatabase();
        return db.delete(TablasDeNegocio.Medicamentos.TABLE_NAME, TablasDeNegocio.Medicamentos.COLUMN_NAME_IDVISITA.column + " = "
                + idVisita, null);
    }

    private ContentValues rellenarDatosAInsertar( Medicamento medicamento , int idVisita ) {
        ContentValues values = new ContentValues();
        values.put( TablasDeNegocio.Medicamentos.COLUMN_NAME_IDMEDICAMENTO.column, medicamento.getIdMedicamento() );
        values.put( TablasDeNegocio.Medicamentos.COLUMN_NAME_NOMBREMEDICAMENTO.column, medicamento.getNombreMedicamento() );
        values.put( TablasDeNegocio.Medicamentos.COLUMN_NAME_MAXIMOPERMITIDO.column, medicamento.getMaximoPermitido() );
        values.put( TablasDeNegocio.Medicamentos.COLUMN_NAME_PRECIO.column, medicamento.getPrecio() );
        values.put( TablasDeNegocio.Medicamentos.COLUMN_NAME_CANTIDAD.column, medicamento.getCantidad() );

        values.put(TablasDeNegocio.Medicamentos.COLUMN_NAME_IDVISITA.column, idVisita);
        return values;
    }

    private Medicamento cargarObjetoMedicamento( Cursor cursor ){
        Medicamento medicamento = new Medicamento();
        medicamento.setIdMedicamento(cursor.getString(TablasDeNegocio.Medicamentos.COLUMN_NAME_IDMEDICAMENTO.index));
        medicamento.setNombreMedicamento(cursor.getString(TablasDeNegocio.Medicamentos.COLUMN_NAME_NOMBREMEDICAMENTO.index));
        medicamento.setMaximoPermitido(cursor.getInt(TablasDeNegocio.Medicamentos.COLUMN_NAME_MAXIMOPERMITIDO.index));
        medicamento.setPrecio(cursor.getInt(TablasDeNegocio.Medicamentos.COLUMN_NAME_PRECIO.index));
        medicamento.setCantidad(cursor.getInt(TablasDeNegocio.Medicamentos.COLUMN_NAME_CANTIDAD.index));

        return medicamento;
    }

    private ContentValues rellenarDatosAActualizar( Medicamento medicamento , int idVisita  ) {
        ContentValues values = new ContentValues();
        //values.put( Table.Productos.COLUMN_NAME_CODIGO.column, revisionProducto.getProducto().getCodigo() );
        values.put( TablasDeNegocio.Medicamentos.COLUMN_NAME_IDMEDICAMENTO.column, medicamento.getIdMedicamento() );
        values.put( TablasDeNegocio.Medicamentos.COLUMN_NAME_NOMBREMEDICAMENTO.column, medicamento.getNombreMedicamento() );
        values.put( TablasDeNegocio.Medicamentos.COLUMN_NAME_MAXIMOPERMITIDO.column, medicamento.getMaximoPermitido() );
        values.put( TablasDeNegocio.Medicamentos.COLUMN_NAME_PRECIO.column, medicamento.getPrecio() );
        values.put( TablasDeNegocio.Medicamentos.COLUMN_NAME_CANTIDAD.column, medicamento.getCantidad() );

        values.put( TablasDeNegocio.Medicamentos.COLUMN_NAME_IDVISITA.column, idVisita );
        return values;
    }
}
