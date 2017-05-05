package promotoria.grocus.mx.promotoriapedidos.business;

import java.util.Arrays;

import promotoria.grocus.mx.promotoriapedidos.business.utils.EstatusVisita;
import promotoria.grocus.mx.promotoriapedidos.business.utils.RespuestaBinaria;
import promotoria.grocus.mx.promotoriapedidos.utils.PreguntaRespuesta;
import promotoria.grocus.mx.promotoriapedidos.utils.UtilLocation;

/**
 * Created by devmac02 on 06/07/15.
 */
public class Visita {
    private String idVisita;
    private String idFarmacia;
    private String nombreFarmacia;
    private String direccionFarmacia;
    private EstatusVisita estatusVisita;
    private String fechaCheckIn;
    private String fechaCheckOut;
    private UtilLocation location;
    private Persona gerente;
    private byte[] firmaGerente;
    private String comentarios;
    private Medicamento[] medicamentos;
    private PreguntaRespuesta[] preguntaRespuesta;
    private String email;
    private RespuestaBinaria encuestaCapturada;
    private RespuestaBinaria pedidoCapturado;
    private RespuestaBinaria aplicarEncuesta;
    private String idEncuesta;
    private RespuestaBinaria checkInNotificado;


    public Visita( ) {
        this.idVisita = "";
        this.idFarmacia = "";
        this.nombreFarmacia = "";
        this.direccionFarmacia = "";
        this.estatusVisita = EstatusVisita.EN_RUTA;
        this.fechaCheckIn = "";
        this.fechaCheckOut = "";
        this.location = new UtilLocation();
        this.gerente = new Persona();
        this.firmaGerente = new byte[0];
        this.comentarios = "";
        this.preguntaRespuesta = new PreguntaRespuesta[0];
        this.medicamentos = new Medicamento[0];
        this.email = "";
        this.encuestaCapturada = RespuestaBinaria.NO;
        this.pedidoCapturado = RespuestaBinaria.NO;
        this.aplicarEncuesta = RespuestaBinaria.NO;
        this.idEncuesta = "";
        this.checkInNotificado = RespuestaBinaria.NO;
    }

    public String getIdVisita() {
        return idVisita;
    }

    public void setIdVisita(String idVisita) {
        this.idVisita = idVisita;
    }

    public String getIdFarmacia() {
        return idFarmacia;
    }

    public void setIdFarmacia(String idFarmacia) {
        this.idFarmacia = idFarmacia;
    }

    public String getNombreFarmacia() {
        return nombreFarmacia;
    }

    public void setNombreFarmacia(String nombreFarmacia) {
        this.nombreFarmacia = nombreFarmacia;
    }

    public String getDireccionFarmacia() {
        return direccionFarmacia;
    }

    public void setDireccionFarmacia(String direccionFarmacia) {
        this.direccionFarmacia = direccionFarmacia;
    }

    public EstatusVisita getEstatusVisita() {
        return estatusVisita;
    }

    public void setEstatusVisita(EstatusVisita estatusVisita) {
        this.estatusVisita = estatusVisita;
    }

    public String getFechaCheckIn() {
        return fechaCheckIn;
    }

    public void setFechaCheckIn(String fechaCheckIn) {
        this.fechaCheckIn = fechaCheckIn;
    }

    public String getFechaCheckOut() {
        return fechaCheckOut;
    }

    public void setFechaCheckOut(String fechaCheckOut) {
        this.fechaCheckOut = fechaCheckOut;
    }

    public UtilLocation getLocation() {
        return location;
    }

    public void setLocation(UtilLocation location) {
        this.location = location;
    }

    public Persona getGerente() {
        return gerente;
    }

    public void setGerente(Persona gerente) {
        this.gerente = gerente;
    }

    public byte[] getFirmaGerente() {
        return firmaGerente;
    }

    public void setFirmaGerente(byte[] firmaGerente) {
        this.firmaGerente = firmaGerente;
    }

    public String getComentarios() {
        return comentarios;
    }

    public void setComentarios(String comentarios) {
        this.comentarios = comentarios;
    }

    public PreguntaRespuesta[] getPreguntaRespuesta() {
        return preguntaRespuesta;
    }

    public void setPreguntaRespuesta(PreguntaRespuesta[] preguntaRespuesta) {
        this.preguntaRespuesta = preguntaRespuesta;
    }

    public Medicamento[] getMedicamentos() {
        return medicamentos;
    }

    public void setMedicamentos(Medicamento[] medicamentos) {
        this.medicamentos = medicamentos;
    }

    public String getEmail() { return email; }

    public void setEmail(String email) { this.email = email; }

    public RespuestaBinaria getEncuestaCapturada() { return encuestaCapturada; }

    public void setEncuestaCapturada(RespuestaBinaria encuestaCapturada) { this.encuestaCapturada = encuestaCapturada; }

    public RespuestaBinaria getPedidoCapturado() { return pedidoCapturado; }

    public void setPedidoCapturado(RespuestaBinaria pedidoCapturado) { this.pedidoCapturado = pedidoCapturado; }

    public RespuestaBinaria getAplicarEncuesta() {
        return aplicarEncuesta;
    }

    public void setAplicarEncuesta(RespuestaBinaria aplicarEncuesta) {
        this.aplicarEncuesta = aplicarEncuesta;
    }

    public String getIdEncuesta() {
        return idEncuesta;
    }

    public void setIdEncuesta(String idEncuesta) {
        this.idEncuesta = idEncuesta;
    }

    public RespuestaBinaria getCheckInNotificado() {
        return checkInNotificado;
    }

    public void setCheckInNotificado(RespuestaBinaria checkInNotificado) {
        this.checkInNotificado = checkInNotificado;
    }

    @Override
    public String toString() {
        return "Visita{" +
                "idVisita='" + idVisita + '\'' +
                ", idFarmacia='" + idFarmacia + '\'' +
                ", nombreFarmacia='" + nombreFarmacia + '\'' +
                ", direccionFarmacia='" + direccionFarmacia + '\'' +
                ", estatusVisita=" + estatusVisita +
                ", fechaCheckIn='" + fechaCheckIn + '\'' +
                ", fechaCheckOut='" + fechaCheckOut + '\'' +
                ", location=" + location +
                ", gerente=" + gerente +
                ", firmaGerente=" + Arrays.toString(firmaGerente) +
                ", comentarios='" + comentarios + '\'' +
                ", medicamentos=" + Arrays.toString(medicamentos) +
                ", preguntaRespuesta=" + Arrays.toString(preguntaRespuesta) +
                ", email='" + email + '\'' +
                ", encuestaCapturada=" + encuestaCapturada +
                ", pedidoCapturado=" + pedidoCapturado +
                ", aplicarEncuesta=" + aplicarEncuesta +
                ", idEncuesta='" + idEncuesta + '\'' +
                ", checkInNotificado=" + checkInNotificado +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Visita visita = (Visita) o;

        if (idVisita != null ? !idVisita.equals(visita.idVisita) : visita.idVisita != null)
            return false;
        if (idFarmacia != null ? !idFarmacia.equals(visita.idFarmacia) : visita.idFarmacia != null)
            return false;
        if (nombreFarmacia != null ? !nombreFarmacia.equals(visita.nombreFarmacia) : visita.nombreFarmacia != null)
            return false;
        if (direccionFarmacia != null ? !direccionFarmacia.equals(visita.direccionFarmacia) : visita.direccionFarmacia != null)
            return false;
        if (estatusVisita != visita.estatusVisita) return false;
        if (fechaCheckIn != null ? !fechaCheckIn.equals(visita.fechaCheckIn) : visita.fechaCheckIn != null)
            return false;
        if (fechaCheckOut != null ? !fechaCheckOut.equals(visita.fechaCheckOut) : visita.fechaCheckOut != null)
            return false;
        if (location != null ? !location.equals(visita.location) : visita.location != null)
            return false;
        if (gerente != null ? !gerente.equals(visita.gerente) : visita.gerente != null)
            return false;
        if (!Arrays.equals(firmaGerente, visita.firmaGerente)) return false;
        if (comentarios != null ? !comentarios.equals(visita.comentarios) : visita.comentarios != null)
            return false;
        // Probably incorrect - comparing Object[] arrays with Arrays.equals
        if (!Arrays.equals(medicamentos, visita.medicamentos)) return false;
        // Probably incorrect - comparing Object[] arrays with Arrays.equals
        if (!Arrays.equals(preguntaRespuesta, visita.preguntaRespuesta)) return false;
        if (email != null ? !email.equals(visita.email) : visita.email != null) return false;
        if (encuestaCapturada != visita.encuestaCapturada) return false;
        if (pedidoCapturado != visita.pedidoCapturado) return false;
        if (aplicarEncuesta != visita.aplicarEncuesta) return false;
        if (idEncuesta != null ? !idEncuesta.equals(visita.idEncuesta) : visita.idEncuesta != null)
            return false;
        return checkInNotificado == visita.checkInNotificado;

    }

    @Override
    public int hashCode() {
        int result = idVisita != null ? idVisita.hashCode() : 0;
        result = 31 * result + (idFarmacia != null ? idFarmacia.hashCode() : 0);
        result = 31 * result + (nombreFarmacia != null ? nombreFarmacia.hashCode() : 0);
        result = 31 * result + (direccionFarmacia != null ? direccionFarmacia.hashCode() : 0);
        result = 31 * result + (estatusVisita != null ? estatusVisita.hashCode() : 0);
        result = 31 * result + (fechaCheckIn != null ? fechaCheckIn.hashCode() : 0);
        result = 31 * result + (fechaCheckOut != null ? fechaCheckOut.hashCode() : 0);
        result = 31 * result + (location != null ? location.hashCode() : 0);
        result = 31 * result + (gerente != null ? gerente.hashCode() : 0);
        result = 31 * result + (firmaGerente != null ? Arrays.hashCode(firmaGerente) : 0);
        result = 31 * result + (comentarios != null ? comentarios.hashCode() : 0);
        result = 31 * result + (medicamentos != null ? Arrays.hashCode(medicamentos) : 0);
        result = 31 * result + (preguntaRespuesta != null ? Arrays.hashCode(preguntaRespuesta) : 0);
        result = 31 * result + (email != null ? email.hashCode() : 0);
        result = 31 * result + (encuestaCapturada != null ? encuestaCapturada.hashCode() : 0);
        result = 31 * result + (pedidoCapturado != null ? pedidoCapturado.hashCode() : 0);
        result = 31 * result + (aplicarEncuesta != null ? aplicarEncuesta.hashCode() : 0);
        result = 31 * result + (idEncuesta != null ? idEncuesta.hashCode() : 0);
        result = 31 * result + (checkInNotificado != null ? checkInNotificado.hashCode() : 0);
        return result;
    }
}
