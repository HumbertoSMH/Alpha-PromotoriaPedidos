package promotoria.grocus.mx.promotoriapedidos.business.rest.Get;

/**
 * Created by devmac03 on 03/08/15.
 */
public class ProductoRest {
    private int idProducto;
    private String nombre;
    private int cantidadDosisSolicitadas;
    private int maximoDosisPermitidas;
    private int precioPorPieza;

    public ProductoRest( ) {
        this.idProducto = 0;
        this.nombre = "";
        this.cantidadDosisSolicitadas = 0;
        this.maximoDosisPermitidas = 0;
        this.precioPorPieza = 0;
    }

    public int getIdProducto() {
        return idProducto;
    }

    public void setIdProducto(int idProducto) {
        this.idProducto = idProducto;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public int getCantidadDosisSolicitadas() {
        return cantidadDosisSolicitadas;
    }

    public void setCantidadDosisSolicitadas(int cantidadDosisSolicitadas) {
        this.cantidadDosisSolicitadas = cantidadDosisSolicitadas;
    }

    public int getMaximoDosisPermitidas() {
        return maximoDosisPermitidas;
    }

    public void setMaximoDosisPermitidas(int maximoDosisPermitidas) {
        this.maximoDosisPermitidas = maximoDosisPermitidas;
    }

    public int getPrecioPorPieza() {
        return precioPorPieza;
    }

    public void setPrecioPorPieza(int precioPorPieza) {
        this.precioPorPieza = precioPorPieza;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        ProductoRest that = (ProductoRest) o;

        if (cantidadDosisSolicitadas != that.cantidadDosisSolicitadas) return false;
        if (idProducto != that.idProducto) return false;
        if (maximoDosisPermitidas != that.maximoDosisPermitidas) return false;
        if (precioPorPieza != that.precioPorPieza) return false;
        if (!nombre.equals(that.nombre)) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = idProducto;
        result = 31 * result + nombre.hashCode();
        result = 31 * result + cantidadDosisSolicitadas;
        result = 31 * result + maximoDosisPermitidas;
        result = 31 * result + precioPorPieza;
        return result;
    }

    @Override
    public String toString() {
        return "ProductoRest{" +
                "idProducto=" + idProducto +
                ", nombre='" + nombre + '\'' +
                ", cantidadDosisSolicitadas=" + cantidadDosisSolicitadas +
                ", maximoDosisPermitidas=" + maximoDosisPermitidas +
                ", precioPorPieza=" + precioPorPieza +
                '}';
    }
}
