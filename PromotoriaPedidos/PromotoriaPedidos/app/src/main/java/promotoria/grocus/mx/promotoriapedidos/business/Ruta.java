package promotoria.grocus.mx.promotoriapedidos.business;

import java.util.Arrays;

/**
 * Created by devmac02 on 06/07/15.
 */
public class Ruta {
    private String idRuta;
    private Visita[] visitas;
    private Promotor promotor;
    private String fechaInicio;
    private String fechaFin;
    private String fechaProgramadaString;
    private String fechaCreacionString;
    private String fechaUltimaModificacion;

    public Ruta() {
        this.idRuta = "";
        this.visitas = new Visita[0];
        this.promotor = new Promotor();
        this.fechaInicio = "";
        this.fechaFin = "";
        this.fechaProgramadaString = "";
        this.fechaCreacionString = "";
        this.fechaUltimaModificacion = "";
    }

    public String getIdRuta() {
        return idRuta;
    }

    public void setIdRuta(String idRuta) {
        this.idRuta = idRuta;
    }

    public Visita[] getVisitas() {
        return visitas;
    }

    public void setVisitas(Visita[] visitas) {
        this.visitas = visitas;
    }

    public Promotor getPromotor() {
        return promotor;
    }

    public void setPromotor(Promotor promotor) {
        this.promotor = promotor;
    }

    public String getFechaInicio() {
        return fechaInicio;
    }

    public void setFechaInicio(String fechaInicio) {
        this.fechaInicio = fechaInicio;
    }

    public String getFechaFin() {
        return fechaFin;
    }

    public void setFechaFin(String fechaFin) {
        this.fechaFin = fechaFin;
    }

    public String getFechaProgramadaString() {
        return fechaProgramadaString;
    }

    public void setFechaProgramadaString(String fechaProgramadaString) {
        this.fechaProgramadaString = fechaProgramadaString;
    }

    public String getFechaCreacionString() {
        return fechaCreacionString;
    }

    public void setFechaCreacionString(String fechaCreacionString) {
        this.fechaCreacionString = fechaCreacionString;
    }

    public String getFechaUltimaModificacion() {
        return fechaUltimaModificacion;
    }

    public void setFechaUltimaModificacion(String fechaUltimaModificacion) {
        this.fechaUltimaModificacion = fechaUltimaModificacion;
    }

    @Override
    public String toString() {
        return "Ruta{" +
                "idRuta='" + idRuta + '\'' +
                ", visitas=" + Arrays.toString(visitas) +
                ", promotor=" + promotor +
                ", fechaInicio='" + fechaInicio + '\'' +
                ", fechaFin='" + fechaFin + '\'' +
                ", fechaProgramadaString='" + fechaProgramadaString + '\'' +
                ", fechaCreacionString='" + fechaCreacionString + '\'' +
                ", fechaUltimaModificacion='" + fechaUltimaModificacion + '\'' +
                '}';
    }
}
