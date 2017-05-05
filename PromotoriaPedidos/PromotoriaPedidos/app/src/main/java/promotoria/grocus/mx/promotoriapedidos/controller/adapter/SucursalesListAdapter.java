package promotoria.grocus.mx.promotoriapedidos.controller.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import promotoria.grocus.mx.promotoriapedidos.R;
import promotoria.grocus.mx.promotoriapedidos.business.Visita;
import promotoria.grocus.mx.promotoriapedidos.utils.LogUtil;

/**
 * Created by devmac02 on 15/07/15.
 */
public class SucursalesListAdapter extends BaseAdapter {

    private static final String CLASSNAME = SucursalesListAdapter.class.getSimpleName();

    private Visita[] visitas;
    private Context context;
    public SucursalesListAdapter(Visita[] visitas, Context context) {
        LogUtil.printLog(CLASSNAME, ".MedicosListAdapter() visitas:" + visitas);
        this.visitas = visitas;
        this.context = context;
    }

    @Override
    public int getCount() {
        return visitas.length;
    }

    @Override
    public Object getItem(int position) {
        return visitas[position];
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LogUtil.printLog( CLASSNAME , ".getView position:" + position );
        LayoutInflater inflater = (LayoutInflater) context
                .getSystemService( Context.LAYOUT_INFLATER_SERVICE );

        View rowView = inflater.inflate( R.layout.contenedor_sucursales_layout , parent,
                false );
        Visita itemVisita = this.visitas[ position ];

        TextView medicoTextView = (TextView) rowView.findViewById( R.id.nombreSucursalTextView );
        medicoTextView.setText( itemVisita.getNombreFarmacia() );


        TextView direccionTextView = (TextView) rowView.findViewById( R.id.direccionTextView );
        direccionTextView.setText( itemVisita.getDireccionFarmacia() );


        RelativeLayout celdaMedicoLinearLayout = (RelativeLayout)rowView.findViewById( R.id.celdaSucursalLinearLayout );
        celdaMedicoLinearLayout.setBackgroundColor( itemVisita.getEstatusVisita().getColor() );
//        celdaMedicoLinearLayout.setBackgroundColor( itemVisita.getEstatusVisita().getColor() - 50);

        TextView estatusTextView = (TextView)rowView.findViewById( R.id.estatusTextView );
        estatusTextView.setText( itemVisita.getEstatusVisita().getNombreEstatus() );

        return rowView;
    }


}
