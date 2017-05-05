package promotoria.grocus.mx.promotoriapedidos.utils;

import promotoria.grocus.mx.promotoriapedidos.business.Medicamento;

/**
 * Created by devmac02 on 21/07/15.
 */
public class AuxiliarCapturaPedido {

    private int precioUnitarios;
    private int cantidad;
    private Medicamento medicamentoReferencia;
    private Const.EstatusCapturaPedidosEnCelda estatusCaptura;



    public AuxiliarCapturaPedido() {
        this.cantidad = 0;
        this.precioUnitarios = 0;
        this.medicamentoReferencia = new Medicamento();
        this.estatusCaptura = Const.EstatusCapturaPedidosEnCelda.CREADO;
    }

    public int getPrecioUnitarios() {
        return precioUnitarios;
    }

    public void setPrecioUnitarios(int precioUnitarios) {
        this.precioUnitarios = precioUnitarios;
    }

    public int getCantidad() {
        return cantidad;
    }

    public Medicamento getMedicamentoReferencia() {
        return medicamentoReferencia;
    }

    public void setMedicamentoReferencia(Medicamento medicamentoReferencia) {
        this.medicamentoReferencia = medicamentoReferencia;
    }

    public void setCantidad(int cantidad) {
        this.cantidad = cantidad;
    }

    public Const.EstatusCapturaPedidosEnCelda getEstatusCaptura() {
        return estatusCaptura;
    }

    public void setEstatusCaptura(Const.EstatusCapturaPedidosEnCelda estatusCaptura) {
        this.estatusCaptura = estatusCaptura;
    }

    @Override
    public String toString() {
        return "AuxliarCapturaPedido{" +
                "precioUnitarios=" + precioUnitarios +
                ", cantidad=" + cantidad +
                ", medicamentoReferencia=" + medicamentoReferencia +
                ", estatusCaptura=" + estatusCaptura +
                '}';
    }
}
