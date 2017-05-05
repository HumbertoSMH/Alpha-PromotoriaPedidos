package promotoria.grocus.mx.promotoriapedidos.business.rest.Post;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by devmac02 on 03/08/15.
 */
public class EncuestaRespuesta {
    int idEncuesta;
    List<DetalleRespuestasRest> detalleRespuestas;

    public EncuestaRespuesta() {
        this.idEncuesta = 0;
        this.detalleRespuestas = new ArrayList<DetalleRespuestasRest>(0);
    }

    public int getIdEncuesta() {
        return idEncuesta;
    }

    public void setIdEncuesta(int idEncuesta) {
        this.idEncuesta = idEncuesta;
    }

    public List<DetalleRespuestasRest> getDetalleRespuestas() {
        return detalleRespuestas;
    }

    public void setDetalleRespuestas(List<DetalleRespuestasRest> detalleRespuestas) {
        this.detalleRespuestas = detalleRespuestas;
    }

    @Override
    public String toString() {
        return "EncuestaRespuestaRest{" +
                "idEncuesta=" + idEncuesta +
                ", detalleRespuestas=" + detalleRespuestas +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        EncuestaRespuesta that = (EncuestaRespuesta) o;

        if (idEncuesta != that.idEncuesta) return false;
        return !(detalleRespuestas != null ? !detalleRespuestas.equals(that.detalleRespuestas) : that.detalleRespuestas != null);

    }

    @Override
    public int hashCode() {
        int result = idEncuesta;
        result = 31 * result + (detalleRespuestas != null ? detalleRespuestas.hashCode() : 0);
        return result;
    }
}
