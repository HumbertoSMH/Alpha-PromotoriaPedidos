package promotoria.grocus.mx.promotoriapedidos.business.rest.Post;

/**
 * Created by devmac02 on 03/08/15.
 */
public class CheckInFarmaciaRequest {
    private  CheckInFarmaciaRest checkInFarmacia;

    public CheckInFarmaciaRequest() {
        this.checkInFarmacia = new CheckInFarmaciaRest();
    }

    public CheckInFarmaciaRest getCheckInFarmacia() {
        return checkInFarmacia;
    }

    public void setCheckInFarmacia(CheckInFarmaciaRest checkInFarmacia) {
        this.checkInFarmacia = checkInFarmacia;
    }

    @Override
    public String toString() {
        return "CheckInFarmaciaRequest{" +
                "checkInFarmacia=" + checkInFarmacia +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        CheckInFarmaciaRequest that = (CheckInFarmaciaRequest) o;

        return !(checkInFarmacia != null ? !checkInFarmacia.equals(that.checkInFarmacia) : that.checkInFarmacia != null);

    }

    @Override
    public int hashCode() {
        return checkInFarmacia != null ? checkInFarmacia.hashCode() : 0;
    }
}
