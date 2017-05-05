package promotoria.grocus.mx.promotoriapedidos.business.rest.Post;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by devmac02 on 03/08/15.
 */
public class PedidoFarmacia {
    String encargado;
    String correo;
    String firmaEncargado;
    List<ListadoDetalle> listadoDetalle;

    public PedidoFarmacia() {
        this.encargado = "";
        this.correo = "";
        this.firmaEncargado = "";
        this.listadoDetalle = new ArrayList<ListadoDetalle>(0);
    }

    public String getEncargado() {
        return encargado;
    }

    public void setEncargado(String encargado) {
        this.encargado = encargado;
    }

    public String getCorreo() {
        return correo;
    }

    public void setCorreo(String correo) {
        this.correo = correo;
    }

    public String getFirmaEncargado() {
        return firmaEncargado;
    }

    public void setFirmaEncargado(String firmaEncargado) {
        this.firmaEncargado = firmaEncargado;
    }

    public List<ListadoDetalle> getListadoDetalle() {
        return listadoDetalle;
    }

    public void setListadoDetalle(List<ListadoDetalle> listadoDetalle) {
        this.listadoDetalle = listadoDetalle;
    }

    @Override
    public String toString() {
        return "PedidoFarmaciaRest{" +
                "encargado='" + encargado + '\'' +
                ", correo='" + correo + '\'' +
                ", firmaEncargado='" + firmaEncargado + '\'' +
                ", listadoDetalle=" + listadoDetalle +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        PedidoFarmacia that = (PedidoFarmacia) o;

        if (encargado != null ? !encargado.equals(that.encargado) : that.encargado != null)
            return false;
        if (correo != null ? !correo.equals(that.correo) : that.correo != null) return false;
        if (firmaEncargado != null ? !firmaEncargado.equals(that.firmaEncargado) : that.firmaEncargado != null)
            return false;
        return !(listadoDetalle != null ? !listadoDetalle.equals(that.listadoDetalle) : that.listadoDetalle != null);

    }

    @Override
    public int hashCode() {
        int result = encargado != null ? encargado.hashCode() : 0;
        result = 31 * result + (correo != null ? correo.hashCode() : 0);
        result = 31 * result + (firmaEncargado != null ? firmaEncargado.hashCode() : 0);
        result = 31 * result + (listadoDetalle != null ? listadoDetalle.hashCode() : 0);
        return result;
    }
}
