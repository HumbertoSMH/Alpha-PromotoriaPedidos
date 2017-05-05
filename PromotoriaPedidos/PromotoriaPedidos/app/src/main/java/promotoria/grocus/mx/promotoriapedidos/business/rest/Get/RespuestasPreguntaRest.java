package promotoria.grocus.mx.promotoriapedidos.business.rest.Get;

/**
 * Created by devmac03 on 03/08/15.
 */
public class RespuestasPreguntaRest {
    private int idRespuesta;
    private String descripcion;

    public RespuestasPreguntaRest( ) {
        this.idRespuesta = 0;
        this.descripcion = "";
    }

    public int getIdRespuesta() {
        return idRespuesta;
    }

    public void setIdRespuesta(int idRespuesta) {
        this.idRespuesta = idRespuesta;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        RespuestasPreguntaRest that = (RespuestasPreguntaRest) o;

        if (idRespuesta != that.idRespuesta) return false;
        if (!descripcion.equals(that.descripcion)) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = idRespuesta;
        result = 31 * result + descripcion.hashCode();
        return result;
    }

    @Override
    public String toString() {
        return "RespuestasPreguntaRest{" +
                "idRespuesta=" + idRespuesta +
                ", descripcion='" + descripcion + '\'' +
                '}';
    }
}
