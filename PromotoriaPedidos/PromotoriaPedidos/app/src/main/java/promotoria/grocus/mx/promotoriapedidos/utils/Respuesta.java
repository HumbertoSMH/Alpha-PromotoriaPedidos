package promotoria.grocus.mx.promotoriapedidos.utils;

/**
 * Created by devmac02 on 06/07/15.
 */
public class Respuesta {
    private String idRespuesta;
    private String textoRespuesta;

    public Respuesta() {
        this.idRespuesta = "";
        this.textoRespuesta = "";
    }

    public String getIdRespuesta() {
        return idRespuesta;
    }

    public void setIdRespuesta(String idRespuesta) {
        this.idRespuesta = idRespuesta;
    }

    public String getTextoRespuesta() {
        return textoRespuesta;
    }

    public void setTextoRespuesta(String textoRespuesta) {
        this.textoRespuesta = textoRespuesta;
    }

    @Override
    public String toString() {
        return "Respuesta{" +
                "idRespuesta='" + idRespuesta + '\'' +
                ", textoRespuesta='" + textoRespuesta + '\'' +
                '}';
    }
}
