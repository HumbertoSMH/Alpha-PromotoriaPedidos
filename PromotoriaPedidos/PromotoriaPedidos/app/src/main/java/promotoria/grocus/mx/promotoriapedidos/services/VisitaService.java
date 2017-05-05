package promotoria.grocus.mx.promotoriapedidos.services;


import promotoria.grocus.mx.promotoriapedidos.business.Encuesta;
import promotoria.grocus.mx.promotoriapedidos.business.Medicamento;
import promotoria.grocus.mx.promotoriapedidos.business.Promotor;
import promotoria.grocus.mx.promotoriapedidos.business.Ruta;
import promotoria.grocus.mx.promotoriapedidos.business.Visita;
import promotoria.grocus.mx.promotoriapedidos.business.rest.Post.CheckInFarmaciaRest;
import promotoria.grocus.mx.promotoriapedidos.business.rest.Response;
import promotoria.grocus.mx.promotoriapedidos.utils.PreguntaRespuesta;

/**
 * Created by MAMM on 19/04/15.
 */
public interface VisitaService {

    public void recuperarRuta(Promotor promotorLogged);
//    public void recuperarMotivosDeRetiro();
    public Ruta getRutaActual();


    public Response realizarCheckIn( Visita visita );
    public Response realizarCheckOut(Visita visita);
//
//    public Visita recuperarVisitaPorIdVisita(String idVisita);
//    public List<MotivoUbicado> getCatalogoMotivoRetiro();
//
    public Visita getVisitaPorPosicionEnLista(int posicion);
    public Visita getVisitaActual();

    public String getTotalDeCompraEnVisitaActual();

    public void guardarEncuesta( PreguntaRespuesta[] preguntasRespuestas );
    public void agregarMedicamentosAVisitaActual( Medicamento[] medicamentos );
//
    public void actualizarVisitaActual();
    public void recuperarRutaDesdeBase( String user , String pass );

}
