package promotoria.grocus.mx.promotoriapedidos.services.impl;

import android.content.Context;

import promotoria.grocus.mx.promotoriapedidos.PromotoriaPedidosApp;
import promotoria.grocus.mx.promotoriapedidos.business.Promotor;
import promotoria.grocus.mx.promotoriapedidos.business.Ruta;
import promotoria.grocus.mx.promotoriapedidos.business.Visita;
import promotoria.grocus.mx.promotoriapedidos.business.utils.EstatusVisita;
import promotoria.grocus.mx.promotoriapedidos.dao.RutaDao;
import promotoria.grocus.mx.promotoriapedidos.dao.VisitaDao;
import promotoria.grocus.mx.promotoriapedidos.dao.impl.RutaDaoImpl;
import promotoria.grocus.mx.promotoriapedidos.dao.impl.VisitaDaoImpl;
import promotoria.grocus.mx.promotoriapedidos.services.RutaService;

/**
 * Created by devmac03 on 03/08/15.
 */
public class RutaServiceImpl implements RutaService{

    private static RutaService rutaService;
    private RutaDao rutaDao;
    private VisitaDao visitaDao;
    private Context context;

    public RutaServiceImpl(Context context){
        this.context = context;
        this.rutaDao = RutaDaoImpl.getSingleton();
        this.visitaDao = VisitaDaoImpl.getSingleton();
    }

    public static RutaService getSingleton(){
        if( rutaService == null ){
            rutaService = new RutaServiceImpl( PromotoriaPedidosApp.getCurrentApplication() );
        }
        return rutaService;
    }

    @Override
    public Ruta cargarRuta(Ruta ruta) {
        int idRutaNueva = Integer.parseInt( ruta.getIdRuta() );
        Ruta rutaEnBase = this.rutaDao.getRutaById( idRutaNueva );
        if( rutaEnBase == null ){
            //Se carga la nueva ruta
            this.rutaDao.insertRuta( ruta );
        }else{
            //se actualiza la ruta
            this.rutaDao.updateRuta( ruta );
        }
        //Se cargan las visitas de la ruta
        Visita[] visitas = ruta.getVisitas();
        if( visitas != null ){
            for( Visita itemVisita : visitas ){
                Visita visitaEnBase = this.visitaDao.getVisitaById( Integer.parseInt( itemVisita.getIdVisita() ) );
                if( visitaEnBase == null ){
                    this.visitaDao.insertVisita( itemVisita , idRutaNueva );
                }else{
                    if( itemVisita.getEstatusVisita() == EstatusVisita.EN_RUTA ){  //si esta en RUTA solo se actualiza el id de ruta de la visita
                        this.visitaDao.updateIdRutaEnVisita(itemVisita, idRutaNueva);
                    }else if( itemVisita.getEstatusVisita() != EstatusVisita.CHECK_IN  ){
                        this.visitaDao.updateVisita( itemVisita, idRutaNueva );
                    }
                }
            }
        }
        //Se eliminan las visitas que no tienen ruta asociada y todas sus dependencias
        Integer[] visitasAEliminar = this.visitaDao.getIdVisitasQueNoSonDeRuta( idRutaNueva);
        for( Integer idVisita: visitasAEliminar ){
            this.visitaDao.deleteVisitaById( idVisita );
        }

        this.rutaDao.deleteRutaAnterior( idRutaNueva );
        return this.armarRutaDeBase( idRutaNueva , ruta.getPromotor());
    }

    private Ruta armarRutaDeBase(int idRutaNueva , Promotor promotor) {
        Ruta ruta = this.rutaDao.getRutaById( idRutaNueva );
        Visita[] visitas = this.visitaDao.getVisitasByIdRuta(idRutaNueva);
        ruta.setVisitas( visitas );
        ruta.setPromotor( promotor );
        return ruta;
    }

    public Ruta refrescarRutaDesdeBase( Ruta rutaReferencia ){
        int idRuta = Integer.parseInt( rutaReferencia.getIdRuta() );
        Ruta ruta = this.rutaDao.getRutaById( idRuta );
        Visita[] visitas = this.visitaDao.getVisitasByIdRuta(idRuta );
        ruta.setVisitas( visitas );
        ruta.setPromotor( rutaReferencia.getPromotor()  );
        return ruta;
    }

    public Ruta getRutaPorClaveYPasswordDePromotor( String clavePromotor, String passwordPromotor ){
       return this.rutaDao.getRutaPorClaveYPasswordDePromotor( clavePromotor , passwordPromotor );
    }
}
