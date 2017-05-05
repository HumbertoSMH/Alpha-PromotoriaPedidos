package promotoria.grocus.mx.promotoriapedidos.business.rest.Post;

/**
 * Created by devmac02 on 03/08/15.
 */
public class DetalleRespuestasRest {
    int idPregunta;
    int idRespuestaSeleccionada;

    public DetalleRespuestasRest() {
        this.idPregunta = 0;
        this.idRespuestaSeleccionada = 0;
    }

    public int getIdPregunta() {
        return idPregunta;
    }

    public void setIdPregunta(int idPregunta) {
        this.idPregunta = idPregunta;
    }

    public int getIdRespuestaSeleccionada() {
        return idRespuestaSeleccionada;
    }

    public void setIdRespuestaSeleccionada(int idRespuestaSeleccionada) {
        this.idRespuestaSeleccionada = idRespuestaSeleccionada;
    }

    @Override
    public String toString() {
        return "DetalleRespuestasRest{" +
                "idPregunta=" + idPregunta +
                ", idRespuestaSeleccionada=" + idRespuestaSeleccionada +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        DetalleRespuestasRest that = (DetalleRespuestasRest) o;

        if (idPregunta != that.idPregunta) return false;
        return idRespuestaSeleccionada == that.idRespuestaSeleccionada;

    }

    @Override
    public int hashCode() {
        int result = idPregunta;
        result = 31 * result + idRespuestaSeleccionada;
        return result;
    }
}
