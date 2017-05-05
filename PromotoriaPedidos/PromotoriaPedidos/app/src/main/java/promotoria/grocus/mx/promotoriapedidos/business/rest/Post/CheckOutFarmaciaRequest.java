package promotoria.grocus.mx.promotoriapedidos.business.rest.Post;

/**
 * Created by devmac02 on 04/08/15.
 */
public class CheckOutFarmaciaRequest {
    private  CheckOutFarmaciaRest checkOutFarmacia;

    public CheckOutFarmaciaRequest() {
        this.checkOutFarmacia = new CheckOutFarmaciaRest();
    }

    public CheckOutFarmaciaRest getCheckOutFarmacia() {
        return checkOutFarmacia;
    }

    public void setCheckOutFarmacia(CheckOutFarmaciaRest checkOutFarmacia) {
        this.checkOutFarmacia = checkOutFarmacia;
    }

    @Override
    public String toString() {
        return "CheckOutFarmaciaRequest{" +
                "checkInFarmacia=" + checkOutFarmacia +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        CheckOutFarmaciaRequest that = (CheckOutFarmaciaRequest) o;

        return !(checkOutFarmacia != null ? !checkOutFarmacia.equals(that.checkOutFarmacia) : that.checkOutFarmacia != null);

    }

    @Override
    public int hashCode() {
        return checkOutFarmacia != null ? checkOutFarmacia.hashCode() : 0;
    }
}
