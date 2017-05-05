package promotoria.grocus.mx.promotoriapedidos.services;

import promotoria.grocus.mx.promotoriapedidos.business.Ruta;

/**
 * Created by devmac03 on 03/08/15.
 */
public interface RutaService {
    public Ruta cargarRuta(Ruta ruta);
    public Ruta refrescarRutaDesdeBase(Ruta rutaReferencia);
    public Ruta getRutaPorClaveYPasswordDePromotor( String clavePromotor, String passwordPromotor );
}
