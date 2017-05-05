package promotoria.grocus.mx.promotoriapedidos.business.rest.Get;

import java.util.Arrays;

/**
 * Created by devmac03 on 03/08/15.
 */
public class EncuestasRutaRest {
    private int idEncuesta;
    private String descripcion;
    private PreguntasEncuestaRest[] preguntasEncuesta;

    public EncuestasRutaRest( ) {
        this.idEncuesta = 0;
        this.descripcion = "";
        this.preguntasEncuesta = new PreguntasEncuestaRest[0];
    }

    public int getIdEncuesta() {
        return idEncuesta;
    }

    public void setIdEncuesta(int idEncuesta) {
        this.idEncuesta = idEncuesta;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public PreguntasEncuestaRest[] getPreguntasEncuesta() {
        return preguntasEncuesta;
    }

    public void setPreguntasEncuesta(PreguntasEncuestaRest[] preguntasEncuesta) {
        this.preguntasEncuesta = preguntasEncuesta;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        EncuestasRutaRest that = (EncuestasRutaRest) o;

        if (idEncuesta != that.idEncuesta) return false;
        if (!descripcion.equals(that.descripcion)) return false;
        if (!Arrays.equals(preguntasEncuesta, that.preguntasEncuesta)) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = idEncuesta;
        result = 31 * result + descripcion.hashCode();
        result = 31 * result + Arrays.hashCode(preguntasEncuesta);
        return result;
    }

    @Override
    public String toString() {
        return "EncuestasRutaRest{" +
                "idEncuesta=" + idEncuesta +
                ", descripcion='" + descripcion + '\'' +
                ", preguntasEncuesta=" + Arrays.toString(preguntasEncuesta) +
                '}';
    }
}
