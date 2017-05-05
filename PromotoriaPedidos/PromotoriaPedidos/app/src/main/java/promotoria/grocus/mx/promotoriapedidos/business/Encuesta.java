package promotoria.grocus.mx.promotoriapedidos.business;

import java.util.Arrays;

import promotoria.grocus.mx.promotoriapedidos.utils.Pregunta;

/**
 * Created by devmac02 on 06/07/15.
 */
public class Encuesta {
    private String idEncuesta;
    private Pregunta[] preguntas;

    public Encuesta() {
        this.idEncuesta = "";
        this.preguntas = new Pregunta[0];
    }

    public String getIdEncuesta() {
        return idEncuesta;
    }

    public void setIdEncuesta(String idEncuesta) {
        this.idEncuesta = idEncuesta;
    }

    public Pregunta[] getPreguntas() {
        return preguntas;
    }

    public void setPreguntas(Pregunta[] preguntas) {
        this.preguntas = preguntas;
    }

    @Override
    public String toString() {
        return "Encuesta{" +
                "idEncuesta='" + idEncuesta + '\'' +
                ", preguntas=" + Arrays.toString(preguntas) +
                '}';
    }
}
