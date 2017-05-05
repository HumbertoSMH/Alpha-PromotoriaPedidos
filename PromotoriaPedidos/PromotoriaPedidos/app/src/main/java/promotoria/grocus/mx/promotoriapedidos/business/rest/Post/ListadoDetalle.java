package promotoria.grocus.mx.promotoriapedidos.business.rest.Post;

/**
 * Created by devmac02 on 03/08/15.
 */
public class ListadoDetalle {
    private int idProducto;
    private int cantidad;
    private int precioUnitario;

    public ListadoDetalle() {
        this.idProducto = 0;
        this.cantidad = 0;
        this.precioUnitario = 0;
    }

    public int getIdProducto() {
        return idProducto;
    }

    public void setIdProducto(int idProducto) {
        this.idProducto = idProducto;
    }

    public int getCantidad() {
        return cantidad;
    }

    public void setCantidad(int cantidad) {
        this.cantidad = cantidad;
    }

    public int getPrecioUnitario() {
        return precioUnitario;
    }

    public void setPrecioUnitario(int precioUnitario) {
        this.precioUnitario = precioUnitario;
    }

    @Override
    public String toString() {
        return "ListadoDetalleRest{" +
                "idProducto=" + idProducto +
                ", cantidad=" + cantidad +
                ", precioUnitario=" + precioUnitario +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        ListadoDetalle that = (ListadoDetalle) o;

        if (idProducto != that.idProducto) return false;
        if (cantidad != that.cantidad) return false;
        return precioUnitario == that.precioUnitario;

    }

    @Override
    public int hashCode() {
        int result = idProducto;
        result = 31 * result + cantidad;
        result = 31 * result + precioUnitario;
        return result;
    }
}
