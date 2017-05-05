package promotoria.grocus.mx.promotoriapedidos.business.rest.Get;

import java.util.Arrays;

/**
 * Created by devmac03 on 03/08/15.
 */
public class PreguntasEncuestaRest {
    private int idPregunta;
    private String descripcion;
    private RespuestasPreguntaRest[] respuestasPregunta;

    public PreguntasEncuestaRest( ) {
        this.idPregunta = 0;
        this.descripcion = "";
        this.respuestasPregunta = new RespuestasPreguntaRest[0];
    }

    public int getIdPregunta() {
        return idPregunta;
    }

    public void setIdPregunta(int idPregunta) {
        this.idPregunta = idPregunta;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public RespuestasPreguntaRest[] getRespuestasPregunta() {
        return respuestasPregunta;
    }

    public void setRespuestasPregunta(RespuestasPreguntaRest[] respuestasPregunta) {
        this.respuestasPregunta = respuestasPregunta;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        PreguntasEncuestaRest that = (PreguntasEncuestaRest) o;

        if (idPregunta != that.idPregunta) return false;
        if (!descripcion.equals(that.descripcion)) return false;
        if (!Arrays.equals(respuestasPregunta, that.respuestasPregunta)) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = idPregunta;
        result = 31 * result + descripcion.hashCode();
        result = 31 * result + Arrays.hashCode(respuestasPregunta);
        return result;
    }

    @Override
    public String toString() {
        return "PreguntasEncuestaRest{" +
                "idPregunta=" + idPregunta +
                ", descripcion='" + descripcion + '\'' +
                ", respuestasPregunta=" + Arrays.toString(respuestasPregunta) +
                '}';
    }
}
