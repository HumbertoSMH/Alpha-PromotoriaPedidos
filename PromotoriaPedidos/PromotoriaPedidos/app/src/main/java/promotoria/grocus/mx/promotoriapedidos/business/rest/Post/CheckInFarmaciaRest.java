package promotoria.grocus.mx.promotoriapedidos.business.rest.Post;

/**
 * Created by devmac02 on 03/08/15.
 */
public class CheckInFarmaciaRest {
    String clavePromotor;
    int idRutaDetalle;
    int idEstatus;
    String fechaCheckIn;
    String latitud;
    String longitud;

    public CheckInFarmaciaRest() {
        this.clavePromotor = "";
        this.idRutaDetalle = 0;
        this.idEstatus = 0;
        this.fechaCheckIn = "";
        this.latitud = "";
        this.longitud = "";
    }

    public String getClavePromotor() {
        return clavePromotor;
    }

    public void setClavePromotor(String clavePromotor) {
        this.clavePromotor = clavePromotor;
    }

    public int getIdRutaDetalle() {
        return idRutaDetalle;
    }

    public void setIdRutaDetalle(int idRutaDetalle) {
        this.idRutaDetalle = idRutaDetalle;
    }

    public String getFechaCheckIn() {
        return fechaCheckIn;
    }

    public void setFechaCheckIn(String fechaCheckIn) {
        this.fechaCheckIn = fechaCheckIn;
    }

    public String getLatitud() {
        return latitud;
    }

    public void setLatitud(String latitud) {
        this.latitud = latitud;
    }

    public String getLongitud() {
        return longitud;
    }

    public void setLongitud(String longitud) {
        this.longitud = longitud;
    }

    public int getIdEstatus() {
        return idEstatus;
    }

    public void setIdEstatus(int idEstatus) {
        this.idEstatus = idEstatus;
    }

    @Override
    public String toString() {
        return "CheckInFarmaciaRest{" +
                "clavePromotor='" + clavePromotor + '\'' +
                ", idRutaDetalle=" + idRutaDetalle +
                ", idEstatus=" + idEstatus +
                ", fechaCheckIn='" + fechaCheckIn + '\'' +
                ", latitud='" + latitud + '\'' +
                ", longitud='" + longitud + '\'' +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        CheckInFarmaciaRest that = (CheckInFarmaciaRest) o;

        if (idRutaDetalle != that.idRutaDetalle) return false;
        if (idEstatus != that.idEstatus) return false;
        if (clavePromotor != null ? !clavePromotor.equals(that.clavePromotor) : that.clavePromotor != null)
            return false;
        if (fechaCheckIn != null ? !fechaCheckIn.equals(that.fechaCheckIn) : that.fechaCheckIn != null)
            return false;
        if (latitud != null ? !latitud.equals(that.latitud) : that.latitud != null) return false;
        return !(longitud != null ? !longitud.equals(that.longitud) : that.longitud != null);

    }

    @Override
    public int hashCode() {
        int result = clavePromotor != null ? clavePromotor.hashCode() : 0;
        result = 31 * result + idRutaDetalle;
        result = 31 * result + idEstatus;
        result = 31 * result + (fechaCheckIn != null ? fechaCheckIn.hashCode() : 0);
        result = 31 * result + (latitud != null ? latitud.hashCode() : 0);
        result = 31 * result + (longitud != null ? longitud.hashCode() : 0);
        return result;
    }
}
