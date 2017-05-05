package promotoria.grocus.mx.promotoriapedidos.controller.adapter;

import android.content.Context;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.TextView;

import java.util.List;

import promotoria.grocus.mx.promotoriapedidos.R;
import promotoria.grocus.mx.promotoriapedidos.business.Medicamento;
import promotoria.grocus.mx.promotoriapedidos.utils.AuxiliarCapturaPedido;
import promotoria.grocus.mx.promotoriapedidos.utils.Const;
import promotoria.grocus.mx.promotoriapedidos.utils.LogUtil;
import promotoria.grocus.mx.promotoriapedidos.utils.StringUtils;

/**
 * Created by devmac02 on 15/07/15.
 */
public class MedicamentosListAdapter extends BaseAdapter  {

    private static final String CLASSNAME = MedicamentosListAdapter.class.getSimpleName();

    private List<Medicamento> medicamentos;
    private Context context;
    private View[] cells;
    private AuxiliarCapturaPedido[] auxiliarCapturaPedido;
//    private int[] cantidades;
//    private double[] preciosUnitarios;
    private int contadorCeldasVistas = 0;

    public MedicamentosListAdapter( List<Medicamento> medicamentos, Context context) {
        LogUtil.printLog(CLASSNAME, ".MedicamentosListAdapter() medicamentos:" + medicamentos);
        this.medicamentos = medicamentos;
        this.cells = new View[ this.medicamentos.size() ];
//        this.cantidades = new int[ this.medicamentos.size() ];
//        this.preciosUnitarios = new double[ this.medicamentos.size() ];
        this.auxiliarCapturaPedido = new AuxiliarCapturaPedido[this.medicamentos.size()];
        this.context = context;
    }

    @Override
    public int getCount() {
        return medicamentos.size();
    }

    @Override
    public Object getItem(int position) {
        return medicamentos.get(position);
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

            rowView = inflater.inflate( R.layout.contenedor_captura_pedidos_layout , parent,
                    false );
            Medicamento itemMedicamento = this.medicamentos.get( position ) ;

            TextView productoTextView = (TextView) rowView.findViewById( R.id.nombreMedicamentoTextView);
            productoTextView.setText( itemMedicamento.getNombreMedicamento() );



            TextView dosisTextView = (TextView) rowView.findViewById( R.id.cantidadTextView);
            dosisTextView.setText( "Máximo dosis permitida: (" + itemMedicamento.getMaximoPermitido() + ")" );


            if( this.auxiliarCapturaPedido[position] == null ){
                this.auxiliarCapturaPedido[position] = new AuxiliarCapturaPedido();
                this.auxiliarCapturaPedido[position].setMedicamentoReferencia(itemMedicamento);
            }

            EditText cantidadEditText = (EditText) rowView.findViewById( R.id.cantidadEditText );
            EditText precioEditText = (EditText) rowView.findViewById( R.id.precioEditText);
            String precioDecimal = StringUtils.transformarPrecio100APrecioDecimal("" + this.auxiliarCapturaPedido[position].getPrecioUnitarios());

//            LogUtil.printLog( CLASSNAME , "Valor incial precio y cantidad :" + precioDecimal + " " +  cantidadEditText);


//        if( cantidadEditText.setEnabled(false)  ){
//                cantidadEditText.setText( "" );
//                precioEditText.setText( "" );
//            }else {
                cantidadEditText.setText( "" + this.auxiliarCapturaPedido[position].getCantidad() );
                precioEditText.setText( "" +  precioDecimal );
//            }


            this.cells[ position ] = rowView;
            contadorCeldasVistas++;

            this.prepararIndicadorDeCelda(this.auxiliarCapturaPedido[position], rowView, position);

          cantidadEditText.addTextChangedListener( new CantidadWatcher( cantidadEditText , itemMedicamento.getMaximoPermitido() , position ) );
          precioEditText.addTextChangedListener( new PrecioWatcher( precioEditText , position ) );


        return rowView;
    }

    private void prepararIndicadorDeCelda(AuxiliarCapturaPedido auxiliarCapturaPedido, View rowView, int position) {

        CheckBox habilitarMedicamentocheckBox = (CheckBox) rowView.findViewById( R.id.habilitarMedicamentocheckBox );

        EditText cantidadEditText = (EditText) rowView.findViewById( R.id.cantidadEditText );
        EditText precioEditText = (EditText) rowView.findViewById( R.id.precioEditText);

        if(Const.EstatusCapturaPedidosEnCelda.CREADO == auxiliarCapturaPedido.getEstatusCaptura()){
            habilitarMedicamentocheckBox.setChecked(false);

            cantidadEditText.setEnabled(false);

            precioEditText.setEnabled(false);
        }else
            if(Const.EstatusCapturaPedidosEnCelda.SELECCIONADO == auxiliarCapturaPedido.getEstatusCaptura()){
                habilitarMedicamentocheckBox.setChecked(true);

                cantidadEditText.setEnabled(true);

                precioEditText.setEnabled(true);
            }

        habilitarMedicamentocheckBox.setOnCheckedChangeListener(new StatusListener(position, rowView));

    }


    public View[] getCells() {
        return cells;
    }

    public AuxiliarCapturaPedido[] getAuxliarCapturaPedido() {
        return auxiliarCapturaPedido;
    }

    public boolean todasLasCeldasFueronVistas() {
        return contadorCeldasVistas==cells.length;
    }

    class CantidadWatcher implements TextWatcher {

        private EditText cantidadEditText ;
        private int maxCantidad;
        private int posicion;

        public CantidadWatcher( EditText cantidadEditText , int maxCantidad , int posicion){
            this.cantidadEditText = cantidadEditText;
            this.maxCantidad =  maxCantidad;
            this.posicion = posicion;
        }

        public void afterTextChanged(Editable s) {}
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {}
        public void onTextChanged(CharSequence s, int start, int before, int count) {
            LogUtil.printLog( CLASSNAME ,  "text:" + s );
            int cantidadElegida = -1;
            try{
                cantidadElegida = Integer.parseInt(s.toString());
                MedicamentosListAdapter.this.auxiliarCapturaPedido[posicion].setCantidad(cantidadElegida);
            }catch(Exception exc){
                LogUtil.printLog( CLASSNAME ,  "Error al obtener de cantidad: exc:" + exc.getMessage() );
            }
            if( cantidadElegida > -1 ){
                if( this.maxCantidad < cantidadElegida ){
                    this.cantidadEditText.setError( "La cantidad no puede ser mayor al máximo permitido" );
                }
            }
        }
    }

    class PrecioWatcher implements TextWatcher {

        private EditText precioEditText ;
        private int posicion;

        public PrecioWatcher( EditText precioEditText , int posicion){
            this.precioEditText = precioEditText;
            this.posicion = posicion;
        }

        public void afterTextChanged(Editable s) {}
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {}
        public void onTextChanged(CharSequence s, int start, int before, int count) {
                LogUtil.printLog( CLASSNAME ,  "text:" + s );
//                double precioUnicoIndicado = -1;
                try{
//                    precioUnicoIndicado = Double.parseDouble( s.toString() );

                    String precioConDecimal = StringUtils.transformarPrecioADecimalConCentavos( s.toString());
                    String precioEnInteger =  precioConDecimal.replace(".", "");

                    MedicamentosListAdapter.this.auxiliarCapturaPedido[posicion].setPrecioUnitarios(Integer.parseInt(precioEnInteger));
                }catch(Exception exc){
                    LogUtil.printLog( CLASSNAME ,  "Error al obtener de cantidad: exc:" + exc.getMessage() );
                }
         }
    }

    class  StatusListener implements CompoundButton.OnCheckedChangeListener {

        private int posicion;
        private View rowView;

        public StatusListener( int posicion, View rowView){
            this.posicion = posicion;
            this.rowView = rowView;
        }

        public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
            LogUtil.printLog(CLASSNAME, "Nuevo valor del Check:" + b);
            EditText cantidadEditText = (EditText) rowView.findViewById(R.id.cantidadEditText);
            EditText precioEditText = (EditText) rowView.findViewById(R.id.precioEditText);


            try{
                if(b == true) {
                    MedicamentosListAdapter.this.auxiliarCapturaPedido[posicion].setEstatusCaptura(Const.EstatusCapturaPedidosEnCelda.SELECCIONADO);
                    cantidadEditText.setEnabled(true);
                    cantidadEditText.setText("");
                    precioEditText.setEnabled(true);
                    precioEditText.setText("");
                }else{
                    MedicamentosListAdapter.this.auxiliarCapturaPedido[posicion].setEstatusCaptura(Const.EstatusCapturaPedidosEnCelda.CREADO);
                    cantidadEditText.setEnabled(false);
                    cantidadEditText.setText("" + 0);
                    precioEditText.setEnabled(false);
                    precioEditText.setText("" + 0.0);
                }
            }catch(Exception exc){
                LogUtil.printLog( CLASSNAME ,  "Error al obtener de cantidad: exc:" + exc.getMessage() );
            }


        }
    }


}
