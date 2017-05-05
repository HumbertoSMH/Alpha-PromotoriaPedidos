package promotoria.grocus.mx.promotoriapedidos.business.rest.Post;

import promotoria.grocus.mx.promotoriapedidos.business.rest.Response;

/**
 * Created by devmac02 on 04/08/15.
 */
public class CheckOutFarmaciaResponse {
    private Response hacerCheckOutFarmaciaResult;

    public CheckOutFarmaciaResponse() {
        this.hacerCheckOutFarmaciaResult = new Response();
    }

    public Response getHacerCheckOutFarmaciaResult() {
        return hacerCheckOutFarmaciaResult;
    }

    public void setHacerCheckOutFarmaciaResult(Response hacerCheckOutFarmaciaResult) {
        this.hacerCheckOutFarmaciaResult = hacerCheckOutFarmaciaResult;
    }

    @Override
    public String toString() {
        return "CheckOutFarmaciaResponse{" +
                "hacerCheckOutFarmaciaResult=" + hacerCheckOutFarmaciaResult +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        CheckOutFarmaciaResponse that = (CheckOutFarmaciaResponse) o;

        return !(hacerCheckOutFarmaciaResult != null ? !hacerCheckOutFarmaciaResult.equals(that.hacerCheckOutFarmaciaResult) : that.hacerCheckOutFarmaciaResult != null);

    }

    @Override
    public int hashCode() {
        return hacerCheckOutFarmaciaResult != null ? hacerCheckOutFarmaciaResult.hashCode() : 0;
    }
}
