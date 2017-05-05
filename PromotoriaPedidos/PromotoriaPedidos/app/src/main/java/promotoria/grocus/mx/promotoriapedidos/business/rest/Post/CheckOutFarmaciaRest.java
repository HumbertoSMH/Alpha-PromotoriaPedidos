package promotoria.grocus.mx.promotoriapedidos.business.rest.Post;

/**
 * Created by devmac02 on 03/08/15.
 */
public class CheckOutFarmaciaRest {
    String clavePromotor;
    int idRutaDetalle;
    int idEstatus;
    String fechaCheckIn;
    String latitud;
    String longitud;
    String comentarios;
    PedidoFarmacia pedidoFarmacia;
    EncuestaRespuesta encuestaRespuesta;

    public CheckOutFarmaciaRest() {
        this.clavePromotor = "";
        this.idRutaDetalle = 0;
        this.idEstatus = 0;
        this.fechaCheckIn = "";
        this.latitud = "";
        this.longitud = "";
        this.comentarios = "";
        this.pedidoFarmacia = new PedidoFarmacia();
        this.encuestaRespuesta = new EncuestaRespuesta();
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

    public int getIdEstatus() {
        return idEstatus;
    }

    public void setIdEstatus(int idEstatus) {
        this.idEstatus = idEstatus;
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

    public String getComentarios() {
        return comentarios;
    }

    public void setComentarios(String comentarios) {
        this.comentarios = comentarios;
    }

    public PedidoFarmacia getPedidoFarmacia() {
        return pedidoFarmacia;
    }

    public void setPedidoFarmacia(PedidoFarmacia pedidoFarmacia) {
        this.pedidoFarmacia = pedidoFarmacia;
    }

    public EncuestaRespuesta getEncuestaRespuesta() {
        return encuestaRespuesta;
    }

    public void setEncuestaRespuesta(EncuestaRespuesta encuestaRespuesta) {
        this.encuestaRespuesta = encuestaRespuesta;
    }

    @Override
    public String toString() {
        return "CheckOutFarmaciaRest{" +
                "clavePromotor='" + clavePromotor + '\'' +
                ", idRutaDetalle=" + idRutaDetalle +
                ", idEstatus=" + idEstatus +
                ", fechaCheckIn='" + fechaCheckIn + '\'' +
                ", latitud='" + latitud + '\'' +
                ", longitud='" + longitud + '\'' +
                ", comentarios='" + comentarios + '\'' +
                ", pedidoFarmaciaRest=" + pedidoFarmacia +
                ", encuestaRespuestaRest=" + encuestaRespuesta +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        CheckOutFarmaciaRest that = (CheckOutFarmaciaRest) o;

        if (idRutaDetalle != that.idRutaDetalle) return false;
        if (idEstatus != that.idEstatus) return false;
        if (clavePromotor != null ? !clavePromotor.equals(that.clavePromotor) : that.clavePromotor != null)
            return false;
        if (fechaCheckIn != null ? !fechaCheckIn.equals(that.fechaCheckIn) : that.fechaCheckIn != null)
            return false;
        if (latitud != null ? !latitud.equals(that.latitud) : that.latitud != null) return false;
        if (longitud != null ? !longitud.equals(that.longitud) : that.longitud != null)
            return false;
        if (comentarios != null ? !comentarios.equals(that.comentarios) : that.comentarios != null)
            return false;
        if (pedidoFarmacia != null ? !pedidoFarmacia.equals(that.pedidoFarmacia) : that.pedidoFarmacia != null)
            return false;
        return !(encuestaRespuesta != null ? !encuestaRespuesta.equals(that.encuestaRespuesta) : that.encuestaRespuesta != null);

    }

    @Override
    public int hashCode() {
        int result = clavePromotor != null ? clavePromotor.hashCode() : 0;
        result = 31 * result + idRutaDetalle;
        result = 31 * result + idEstatus;
        result = 31 * result + (fechaCheckIn != null ? fechaCheckIn.hashCode() : 0);
        result = 31 * result + (latitud != null ? latitud.hashCode() : 0);
        result = 31 * result + (longitud != null ? longitud.hashCode() : 0);
        result = 31 * result + (comentarios != null ? comentarios.hashCode() : 0);
        result = 31 * result + (pedidoFarmacia != null ? pedidoFarmacia.hashCode() : 0);
        result = 31 * result + (encuestaRespuesta != null ? encuestaRespuesta.hashCode() : 0);
        return result;
    }
}
