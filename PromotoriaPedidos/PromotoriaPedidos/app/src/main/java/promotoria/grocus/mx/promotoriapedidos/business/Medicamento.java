package promotoria.grocus.mx.promotoriapedidos.business;

/**
 * Created by devmac02 on 06/07/15.
 */
public class Medicamento {
    private String idMedicamento;
    private String nombreMedicamento;
    private int maximoPermitido;
    private int precio;
    private int cantidad;

    public Medicamento( ) {
        this.idMedicamento = "";
        this.nombreMedicamento = "";
        this.maximoPermitido = 0;
        this.precio = 0;
        this.cantidad = 0;
    }

    public String getIdMedicamento() {
        return idMedicamento;
    }

    public void setIdMedicamento(String idMedicamento) {
        this.idMedicamento = idMedicamento;
    }

    public String getNombreMedicamento() {
        return nombreMedicamento;
    }

    public void setNombreMedicamento(String nombreMedicamento) {
        this.nombreMedicamento = nombreMedicamento;
    }

    public int getMaximoPermitido() {
        return maximoPermitido;
    }

    public void setMaximoPermitido(int maximoPermitido) {
        this.maximoPermitido = maximoPermitido;
    }

    public int getPrecio() {
        return precio;
    }

    public void setPrecio(int precio) {
        this.precio = precio;
    }

    public int getCantidad() {
        return cantidad;
    }

    public void setCantidad(int cantidad) {
        this.cantidad = cantidad;
    }

    @Override
    public String toString() {
        return "Medicamento{" +
                "idMedicamento='" + idMedicamento + '\'' +
                ", nombreMedicamento='" + nombreMedicamento + '\'' +
                ", maximoPermitido=" + maximoPermitido +
                ", precio=" + precio +
                ", cantidad=" + cantidad +
                '}';
    }
}
