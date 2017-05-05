package promotoria.grocus.mx.promotoriapedidos.dao;

import promotoria.grocus.mx.promotoriapedidos.business.Visita;
import promotoria.grocus.mx.promotoriapedidos.utils.PreguntaRespuesta;

/**
 * Created by devmac02 on 17/07/15.
 */
public interface EncuestaDao {

    public void insertEncuesta( PreguntaRespuesta[] preguntasRespuestas, Visita visita );
    public PreguntaRespuesta[] getEncuestasByIdVisita(Integer idVisita);
    public long deleteEncuestaByIdVisita( int idVisita);
}
