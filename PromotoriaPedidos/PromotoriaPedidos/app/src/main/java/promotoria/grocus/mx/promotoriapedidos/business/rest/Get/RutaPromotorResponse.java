package promotoria.grocus.mx.promotoriapedidos.business.rest.Get;

import promotoria.grocus.mx.promotoriapedidos.business.rest.Response;

/**
 * Created by devmac03 on 03/08/15.
 */
public class RutaPromotorResponse extends Response {
    private RutaRest rutaPromotor;

    public RutaPromotorResponse( ) {
        this.rutaPromotor = new RutaRest();
    }

    public RutaRest getRutaPromotor() {
        return rutaPromotor;
    }

    public void setRutaPromotor(RutaRest rutaPromotor) {
        this.rutaPromotor = rutaPromotor;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;

        RutaPromotorResponse that = (RutaPromotorResponse) o;

        if (!rutaPromotor.equals(that.rutaPromotor)) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = super.hashCode();
        result = 31 * result + rutaPromotor.hashCode();
        return result;
    }

    @Override
    public String toString() {
        return "RutaPromotorResponse{" +
                "rutaPromotor=" + rutaPromotor +
                '}';
    }
}
