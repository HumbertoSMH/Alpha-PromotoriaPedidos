package promotoria.grocus.mx.promotoriapedidos.dao.impl;

import java.util.List;

import promotoria.grocus.mx.promotoriapedidos.business.Medicamento;
import promotoria.grocus.mx.promotoriapedidos.utils.Pregunta;
import promotoria.grocus.mx.promotoriapedidos.utils.Respuesta;

/**
 * Created by devmac03 on 04/08/15.
 */
public interface CatalogoDao {


    //Sentencias para el catalogo de Medicamentos
    public long insertarCatalogoMedicamentos(Medicamento medicamento , Integer idFarmacia);
    public long eliminarCatalogoMedicamentos( );
    public List<Medicamento> recuperarCatalogoMedicamentosPorIdFarmacia( Integer idFarmacia );
    public List<Integer> recuperarListaDeIdFarmaciaEnCatalogo(  );


    //Sentencias para el catalogo de Encuestas
    public long eliminarCatalogoPreguntasRespuestas( );
    public long insertarCatalogoPreguntas( Pregunta pregunta , int idEncuesta );
    public List<Pregunta> recuperarCatalogoPreguntasPorIdEncuesta( int idEncuesta );
    public List<Integer> recuperarListaIdEncuestaEnCatalogo( ) ;

    public long insertarCatalogoRespuesta( Respuesta respuesta , int idPregunta );
    public List<Respuesta> recuperarCatalogoRespuestaPorIdPregunta(int idPregunta);

}
