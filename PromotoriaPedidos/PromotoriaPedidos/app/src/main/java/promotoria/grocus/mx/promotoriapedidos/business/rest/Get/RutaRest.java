package promotoria.grocus.mx.promotoriapedidos.business.rest.Get;

import java.util.Arrays;

/**
 * Created by devmac03 on 03/08/15.
 */
public class RutaRest {
    private int idRuta;
    private String fechaCreacion;
    private String fechaProgramada;
    private String fechaUltimaModificacion;
    private VisitaRest[] visitas;

    public RutaRest( ) {
        this.idRuta = 0;
        this.fechaCreacion = "";
        this.fechaProgramada = "";
        this.fechaUltimaModificacion = "";
        this.visitas = new VisitaRest[0];
    }

    public int getIdRuta() {
        return idRuta;
    }

    public void setIdRuta(int idRuta) {
        this.idRuta = idRuta;
    }

    public String getFechaCreacion() {
        return fechaCreacion;
    }

    public void setFechaCreacion(String fechaCreacion) {
        this.fechaCreacion = fechaCreacion;
    }

    public String getFechaProgramada() {
        return fechaProgramada;
    }

    public void setFechaProgramada(String fechaProgramada) {
        this.fechaProgramada = fechaProgramada;
    }

    public String getFechaUltimaModificacion() {
        return fechaUltimaModificacion;
    }

    public void setFechaUltimaModificacion(String fechaUltimaModificacion) {
        this.fechaUltimaModificacion = fechaUltimaModificacion;
    }

    public VisitaRest[] getVisitas() {
        return visitas;
    }

    public void setVisitas(VisitaRest[] visitas) {
        this.visitas = visitas;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        RutaRest rutaRest = (RutaRest) o;

        if (idRuta != rutaRest.idRuta) return false;
        if (!fechaCreacion.equals(rutaRest.fechaCreacion)) return false;
        if (!fechaProgramada.equals(rutaRest.fechaProgramada)) return false;
        if (!fechaUltimaModificacion.equals(rutaRest.fechaUltimaModificacion)) return false;
        if (!Arrays.equals(visitas, rutaRest.visitas)) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = idRuta;
        result = 31 * result + fechaCreacion.hashCode();
        result = 31 * result + fechaProgramada.hashCode();
        result = 31 * result + fechaUltimaModificacion.hashCode();
        result = 31 * result + Arrays.hashCode(visitas);
        return result;
    }

    @Override
    public String toString() {
        return "RutaRest{" +
                "idRuta=" + idRuta +
                ", fechaCreacion='" + fechaCreacion + '\'' +
                ", fechaProgramada='" + fechaProgramada + '\'' +
                ", fechaUltimaModificacion='" + fechaUltimaModificacion + '\'' +
                ", visitas=" + Arrays.toString(visitas) +
                '}';
    }
}
