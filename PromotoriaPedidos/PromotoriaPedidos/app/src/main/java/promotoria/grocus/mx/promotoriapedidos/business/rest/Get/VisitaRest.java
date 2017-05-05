package promotoria.grocus.mx.promotoriapedidos.business.rest.Get;

/**
 * Created by devmac03 on 03/08/15.
 */
public class VisitaRest {
    private int idRutaDet;
    private int idEstatus;
    private int idEncuesta;
    private FarmaciaRest farmacia;

    public VisitaRest( ) {
        this.idRutaDet = 0;
        this.idEstatus = 0;
        this.idEncuesta = 0;
        this.farmacia = new FarmaciaRest();
    }

    public int getIdRutaDet() {
        return idRutaDet;
    }

    public void setIdRutaDet(int idRutaDet) {
        this.idRutaDet = idRutaDet;
    }

    public int getIdEstatus() {
        return idEstatus;
    }

    public void setIdEstatus(int idEstatus) {
        this.idEstatus = idEstatus;
    }

    public int getIdEncuesta() {
        return idEncuesta;
    }

    public void setIdEncuesta(int idEncuesta) {
        this.idEncuesta = idEncuesta;
    }

    public FarmaciaRest getFarmacia() {
        return farmacia;
    }

    public void setFarmacia(FarmaciaRest farmacia) {
        this.farmacia = farmacia;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        VisitaRest that = (VisitaRest) o;

        if (idEncuesta != that.idEncuesta) return false;
        if (idEstatus != that.idEstatus) return false;
        if (idRutaDet != that.idRutaDet) return false;
        if (!farmacia.equals(that.farmacia)) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = idRutaDet;
        result = 31 * result + idEstatus;
        result = 31 * result + idEncuesta;
        result = 31 * result + farmacia.hashCode();
        return result;
    }

    @Override
    public String toString() {
        return "VisitaRest{" +
                "idRutaDet=" + idRutaDet +
                ", idEstatus=" + idEstatus +
                ", idEncuesta=" + idEncuesta +
                ", farmacia=" + farmacia +
                '}';
    }
}
