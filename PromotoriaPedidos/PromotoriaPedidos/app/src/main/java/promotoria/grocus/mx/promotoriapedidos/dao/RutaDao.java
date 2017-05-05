package promotoria.grocus.mx.promotoriapedidos.dao;

import promotoria.grocus.mx.promotoriapedidos.business.Ruta;

/**
 * Created by devmac02 on 17/07/15.
 */
public interface RutaDao {
    public void insertRuta( Ruta ruta );
    public Ruta getRutaById( int idRuta);
    public long updateRuta( Ruta ruta);
    public long deleteRutaById( int idRuta);
    public long deleteRutaAnterior( int idRutaActual );
    public Ruta getRutaPorClaveYPasswordDePromotor( String clavePromotor, String passwordPromotor );
}
