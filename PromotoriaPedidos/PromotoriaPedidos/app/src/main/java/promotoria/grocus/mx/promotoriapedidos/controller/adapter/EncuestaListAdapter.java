package promotoria.grocus.mx.promotoriapedidos.controller.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.Spinner;
import android.widget.TextView;

import promotoria.grocus.mx.promotoriapedidos.R;
import promotoria.grocus.mx.promotoriapedidos.business.Encuesta;
import promotoria.grocus.mx.promotoriapedidos.business.Visita;
import promotoria.grocus.mx.promotoriapedidos.utils.LogUtil;
import promotoria.grocus.mx.promotoriapedidos.utils.Pregunta;
import promotoria.grocus.mx.promotoriapedidos.utils.Respuesta;
import promotoria.grocus.mx.promotoriapedidos.utils.ViewUtil;

/**
 * Created by devmac02 on 16/07/15.
 */
public class EncuestaListAdapter extends BaseAdapter implements AdapterView.OnItemSelectedListener {
    private static final String CLASSNAME = EncuestaListAdapter.class.getSimpleName();

    private Pregunta[] preguntas;
    private Visita visita;
    private Context context;
    private View[] cells;
    private int contadorCeldasVistas = 0;


    public EncuestaListAdapter( Visita visita , Encuesta encuesta, Context context) {
        LogUtil.printLog( CLASSNAME , ".EncuestaListAdapter() encuesta:" + encuesta );
        this.preguntas = encuesta.getPreguntas();
        this.cells = new View[ this.preguntas.length ];
        this.visita = visita;
        this.context = context;
    }

    @Override
    public int getCount() {
        return preguntas.length;
    }

    @Override
    public Object getItem(int position) {
        return preguntas[position];
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View rowView = null;
        if( this.cells[ position ] != null ){
            rowView = this.cells[ position ];
        }else{
            LogUtil.printLog( CLASSNAME , ".getView position:" + position );
            LayoutInflater inflater = (LayoutInflater) context
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE );

            rowView = inflater.inflate( R.layout.contenedor_encuesta_preguntas_layout , parent,
                    false );
            Pregunta itemPregunta = this.preguntas[ position ];

            TextView preguntaEncuestaTextView = (TextView) rowView.findViewById( R.id.preguntaEncuestaTextView );
            preguntaEncuestaTextView.setText(itemPregunta.getTextoPregunta());

            Spinner respuestaEncuestaSpinner = (Spinner) rowView.findViewById( R.id.respuestaEncuestaSpinner );
            String[] respuestasString = recuperarTextosFromRespuestas(itemPregunta.getRespuestas());
            ArrayAdapter<String> respuestasAdapter = new ArrayAdapter<String>( this.context, R.layout.custom_spinner_item, respuestasString );

            respuestaEncuestaSpinner.setAdapter( respuestasAdapter );
            this.cells[ position ] = rowView;
            contadorCeldasVistas++;
        }
        return rowView;
    }

    private String[] recuperarTextosFromRespuestas( Respuesta[] respuestas){
        String[] respuestaString = null;
        if( respuestas ==null ){
            respuestaString =  new String[0];
        }
        else{
            respuestaString =  new  String[ respuestas.length ];
            for( int j = 0; j < respuestas.length ; j++ ){
                respuestaString[j] = respuestas[j].getTextoRespuesta();
            }
        }
        return respuestaString;
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {
        //
    }

    public View[] getCells() {
        return cells;
    }

    public Pregunta[] getPreguntas() {
        return preguntas;
    }

    public boolean todasLasCeldasFueronVistas() {
        return contadorCeldasVistas==cells.length;
    }
}
