package promotoria.grocus.mx.promotoriapedidos.business.rest.Get;

import java.util.Arrays;

import promotoria.grocus.mx.promotoriapedidos.business.rest.Response;

/**
 * Created by devmac03 on 03/08/15.
 */
public class ProductosPorFarmaciaResponse extends Response {
    private ProductoRest[] productos;

    public ProductosPorFarmaciaResponse( ) {
        this.productos = productos;
    }

    public ProductoRest[] getProductos() {
        return productos;
    }

    public void setProductos(ProductoRest[] productos) {
        this.productos = productos;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;

        ProductosPorFarmaciaResponse that = (ProductosPorFarmaciaResponse) o;

        if (!Arrays.equals(productos, that.productos)) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = super.hashCode();
        result = 31 * result + Arrays.hashCode(productos);
        return result;
    }

    @Override
    public String toString() {
        return "ProductosPorFarmaciaResponse{" +
                "productos=" + Arrays.toString(productos) +
                '}';
    }
}
