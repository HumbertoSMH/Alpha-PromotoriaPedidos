package promotoria.grocus.mx.promotoriapedidos.dao;

import promotoria.grocus.mx.promotoriapedidos.business.Medicamento;

/**
 * Created by devmac02 on 17/07/15.
 */
public interface MedicamentoDao {

    public long insertMedicamento( Medicamento medicamento, int idVisita);
//    public Medicamento getMedicamentoById(String idMedicamento);
    public Medicamento[] getMedicamentosByIdVisita( Integer idVisita);
    public long updateMedicamento( Medicamento medicamento, int idVisita);
    public long deleteMedicamentoById( String codigoMedicaemntos, int idVisita);
    public long deleteMedicamentoByIdVisita( int idVisita);
}
