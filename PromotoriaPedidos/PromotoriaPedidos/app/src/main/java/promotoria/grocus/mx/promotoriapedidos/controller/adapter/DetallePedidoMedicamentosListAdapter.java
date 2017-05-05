package promotoria.grocus.mx.promotoriapedidos.controller.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.EditText;
import android.widget.TextView;

import promotoria.grocus.mx.promotoriapedidos.R;
import promotoria.grocus.mx.promotoriapedidos.business.Medicamento;
import promotoria.grocus.mx.promotoriapedidos.utils.LogUtil;
import promotoria.grocus.mx.promotoriapedidos.utils.StringUtils;

/**
 * Created by devmac02 on 15/07/15.
 */
public class DetallePedidoMedicamentosListAdapter extends BaseAdapter  {

    private static final String CLASSNAME = DetallePedidoMedicamentosListAdapter.class.getSimpleName();

    private Medicamento[] medicamentos;
    private Context context;

    public DetallePedidoMedicamentosListAdapter( Medicamento[] medicamentos, Context context) {
        LogUtil.printLog(CLASSNAME, ".DetallePedidoMedicamentosListAdapter() medicamentos:" + medicamentos);
        this.medicamentos = medicamentos;
        this.context = context;
    }

    @Override
    public int getCount() {
        return medicamentos.length;
    }

    @Override
    public Object getItem(int position) {
        return medicamentos[position];
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View rowView = null;

            LogUtil.printLog( CLASSNAME , ".getView position:" + position );
            LayoutInflater inflater = (LayoutInflater) context
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);

            rowView = inflater.inflate( R.layout.contenedor_detalle_pedido_layout , parent,
                    false );
            Medicamento itemMedicamento = this.medicamentos[ position ] ;

            TextView nombreMedicamentoTextView = (TextView) rowView.findViewById( R.id.nombreMedicamentoTextView);;
            nombreMedicamentoTextView.setText( itemMedicamento.getNombreMedicamento() );


            TextView cantidadTextView = (TextView) rowView.findViewById( R.id.cantidadTextView);
             int totalItemMedicamento = itemMedicamento.getCantidad() * itemMedicamento.getPrecio();

//            cantidadTextView.setText( " (" + itemMedicamento.getCantidad() + ") X $" + StringUtils.
//                    transformarPrecioADecimalConCentavos("" + itemMedicamento.getPrecio()) + " = $" + StringUtils.
//                    transformarPrecioADecimalConCentavos("" + totalItemMedicamento));

            String precioDecimal = StringUtils.transformarPrecio100APrecioDecimal("" + itemMedicamento.getPrecio());
            String totalDecimal = StringUtils.transformarPrecio100APrecioDecimal("" + totalItemMedicamento);

            cantidadTextView.setText( " (" + itemMedicamento.getCantidad() + ") X $" + precioDecimal + " = $" + totalDecimal );

        return rowView;
    }

}
