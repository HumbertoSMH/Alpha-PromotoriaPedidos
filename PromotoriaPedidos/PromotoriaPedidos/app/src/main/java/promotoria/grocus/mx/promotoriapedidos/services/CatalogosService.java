package promotoria.grocus.mx.promotoriapedidos.services;

import java.util.List;

import promotoria.grocus.mx.promotoriapedidos.business.Encuesta;
import promotoria.grocus.mx.promotoriapedidos.business.Medicamento;
import promotoria.grocus.mx.promotoriapedidos.business.rest.Get.EncuestaVisitaResponse;
import promotoria.grocus.mx.promotoriapedidos.business.rest.Get.ProductosPorFarmaciaResponse;

/**
 * Created by devmac02 on 16/07/15.
 */
public interface CatalogosService {
    public ProductosPorFarmaciaResponse cargarTodosLosProductosDesdeWeb( List<Integer> idFarmaciasEnRutaActual);
    public EncuestaVisitaResponse cargarTodasLasEncuestasDesdeWeb( int idRuta);
    public List<Medicamento> getCatalogoMedicamentos( int idFarmacia);

    public Encuesta  recuperarEncuestaPorId( String idEncuesta );
//    public void actualizarMapaEncuesta(String idRuta);

    public void recuperarCatalogosDesdeBase();
    public void actualizarCatalogosEnBase();

}
