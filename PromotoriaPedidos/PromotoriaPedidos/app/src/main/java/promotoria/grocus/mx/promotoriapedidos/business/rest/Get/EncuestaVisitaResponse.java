package promotoria.grocus.mx.promotoriapedidos.business.rest.Get;

import java.util.Arrays;

import promotoria.grocus.mx.promotoriapedidos.business.rest.Response;

/**
 * Created by devmac03 on 03/08/15.
 */
public class EncuestaVisitaResponse extends Response {
    private EncuestasRutaRest[] encuestasRuta;

    public EncuestaVisitaResponse(){
        this.encuestasRuta = new EncuestasRutaRest[0];
    }

    public EncuestasRutaRest[] getEncuestasRuta() {
        return encuestasRuta;
    }

    public void setEncuestasRuta(EncuestasRutaRest[] encuestasRuta) {
        this.encuestasRuta = encuestasRuta;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;

        EncuestaVisitaResponse that = (EncuestaVisitaResponse) o;

        if (!Arrays.equals(encuestasRuta, that.encuestasRuta)) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = super.hashCode();
        result = 31 * result + Arrays.hashCode(encuestasRuta);
        return result;
    }

    @Override
    public String toString() {
        return "EncuestaVisitaResponse{" +
                "encuestasRuta=" + Arrays.toString(encuestasRuta) +
                '}';
    }
}
