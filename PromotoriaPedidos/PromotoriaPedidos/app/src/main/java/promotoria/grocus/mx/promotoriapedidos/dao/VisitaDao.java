package promotoria.grocus.mx.promotoriapedidos.dao;

import promotoria.grocus.mx.promotoriapedidos.business.Visita;

/**
 * Created by devmac02 on 17/07/15.
 */
public interface VisitaDao {

    public void insertVisita( Visita visita , int idRuta);
    public Visita getVisitaById(  Integer idVisita);
    public Visita[] getVisitasByIdRuta(  Integer idRuta );
    public Integer[] getIdVisitasQueNoSonDeRuta(  Integer idRuta);
    public long updateVisita( Visita visita , int idRuta);
    public long updateIdRutaEnVisita( Visita visita , int idRuta);
    public long deleteVisitaById(int idVisita);
}
