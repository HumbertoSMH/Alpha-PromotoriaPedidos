package promotoria.grocus.mx.promotoriapedidos.utils;

import java.util.Arrays;

/**
 * Created by devmac02 on 06/07/15.
 */
public class Pregunta {
    private String idPregunta;
    private String textoPregunta;
    private Respuesta[] respuestas;

    public Pregunta() {
        this.idPregunta = "";
        this.textoPregunta = "";
        this.respuestas = new Respuesta[0];
    }

    public String getIdPregunta() {
        return idPregunta;
    }

    public void setIdPregunta(String idPregunta) {
        this.idPregunta = idPregunta;
    }

    public String getTextoPregunta() {
        return textoPregunta;
    }

    public void setTextoPregunta(String textoPregunta) {
        this.textoPregunta = textoPregunta;
    }

    public Respuesta[] getRespuestas() {
        return respuestas;
    }

    public void setRespuestas(Respuesta[] respuestas) {
        this.respuestas = respuestas;
    }

    @Override
    public String toString() {
        return "Pregunta{" +
                "idPregunta='" + idPregunta + '\'' +
                ", textoPregunta='" + textoPregunta + '\'' +
                ", respuestas=" + Arrays.toString(respuestas) +
                '}';
    }
}
