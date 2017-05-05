package promotoria.grocus.mx.promotoriapedidos.business.rest.Post;

import promotoria.grocus.mx.promotoriapedidos.business.rest.Response;

/**
 * Created by devmac02 on 03/08/15.
 */
public class CheckInFarmaciaResponse {

    private Response hacerCheckInFarmaciaResult;

    public CheckInFarmaciaResponse() {
        this.hacerCheckInFarmaciaResult = new Response();
    }

    public Response getHacerCheckInFarmaciaResult() {
        return hacerCheckInFarmaciaResult;
    }

    public void setHacerCheckInFarmaciaResult(Response hacerCheckInFarmaciaResult) {
        this.hacerCheckInFarmaciaResult = hacerCheckInFarmaciaResult;
    }

    @Override
    public String toString() {
        return "CheckInFarmaciaResponse{" +
                "hacerCheckInFarmaciaResult=" + hacerCheckInFarmaciaResult +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        CheckInFarmaciaResponse that = (CheckInFarmaciaResponse) o;

        return !(hacerCheckInFarmaciaResult != null ? !hacerCheckInFarmaciaResult.equals(that.hacerCheckInFarmaciaResult) : that.hacerCheckInFarmaciaResult != null);

    }

    @Override
    public int hashCode() {
        return hacerCheckInFarmaciaResult != null ? hacerCheckInFarmaciaResult.hashCode() : 0;
    }
}
