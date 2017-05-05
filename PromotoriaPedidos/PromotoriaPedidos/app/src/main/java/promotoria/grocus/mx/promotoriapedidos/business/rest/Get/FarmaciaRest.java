package promotoria.grocus.mx.promotoriapedidos.business.rest.Get;

/**
 * Created by devmac03 on 03/08/15.
 */
public class FarmaciaRest {
    private int idFarmacia;
    private String domicilio;
    private String nombre;

    public FarmaciaRest( ) {
        this.idFarmacia = 0;
        this.domicilio = "";
        this.nombre = "";
    }

    public int getIdFarmacia() {
        return idFarmacia;
    }

    public void setIdFarmacia(int idFarmacia) {
        this.idFarmacia = idFarmacia;
    }

    public String getDomicilio() {
        return domicilio;
    }

    public void setDomicilio(String domicilio) {
        this.domicilio = domicilio;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        FarmaciaRest that = (FarmaciaRest) o;

        if (idFarmacia != that.idFarmacia) return false;
        if (!domicilio.equals(that.domicilio)) return false;
        if (!nombre.equals(that.nombre)) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = idFarmacia;
        result = 31 * result + domicilio.hashCode();
        result = 31 * result + nombre.hashCode();
        return result;
    }

    @Override
    public String toString() {
        return "FarmaciaRest{" +
                "idFarmacia=" + idFarmacia +
                ", domicilio='" + domicilio + '\'' +
                ", nombre='" + nombre + '\'' +
                '}';
    }
}
