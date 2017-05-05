package promotoria.grocus.mx.promotoriapedidos.controller;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;

import java.util.Date;

import promotoria.grocus.mx.promotoriapedidos.R;
import promotoria.grocus.mx.promotoriapedidos.business.Encuesta;
import promotoria.grocus.mx.promotoriapedidos.business.Visita;
import promotoria.grocus.mx.promotoriapedidos.business.utils.RespuestaBinaria;
import promotoria.grocus.mx.promotoriapedidos.controller.adapter.EncuestaListAdapter;
import promotoria.grocus.mx.promotoriapedidos.services.CatalogosService;
import promotoria.grocus.mx.promotoriapedidos.services.VisitaService;
import promotoria.grocus.mx.promotoriapedidos.services.impl.CatalogosServiceImpl;
import promotoria.grocus.mx.promotoriapedidos.services.impl.VisitaServiceImpl;
import promotoria.grocus.mx.promotoriapedidos.utils.Const;
import promotoria.grocus.mx.promotoriapedidos.utils.CustomUncaughtExceptionHandler;
import promotoria.grocus.mx.promotoriapedidos.utils.LogUtil;
import promotoria.grocus.mx.promotoriapedidos.utils.Pregunta;
import promotoria.grocus.mx.promotoriapedidos.utils.PreguntaRespuesta;
import promotoria.grocus.mx.promotoriapedidos.utils.Util;
import promotoria.grocus.mx.promotoriapedidos.utils.ViewUtil;


public class EncuestaListaActivity extends ActionBarActivity implements View.OnClickListener, DialogInterface.OnClickListener{

    private final static String CLASSNAME = EncuestaListaActivity.class.getSimpleName();

    //Services
    private VisitaService visitaService;
    private CatalogosService encuestaService;

    //Business
    private Visita visita;
    private Encuesta encuesta;



    //UI Elements
    TextView sucursalTexView = null;
    ListView encuentaListView = null;
    Button cancelarCapturaEncuestaButton = null;
    Button guardarEncuestaButton = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Thread.setDefaultUncaughtExceptionHandler(new CustomUncaughtExceptionHandler(this));
        this.visitaService = VisitaServiceImpl.getSingleton();
        this.encuestaService = CatalogosServiceImpl.getSingleton();

        Intent intent = this.getIntent();
        this.visita = this.visitaService.getVisitaActual();
        this.encuesta = this.encuestaService.recuperarEncuestaPorId( visita.getIdEncuesta() );

        this.prepararPantalla();
    }

    private void prepararPantalla(){
        setContentView(R.layout.encuesta_lista_layout);
        this.sucursalTexView = (TextView)findViewById( R.id.sucursalTexView );
        this.sucursalTexView.setText( this.visita.getNombreFarmacia() );

        this.cancelarCapturaEncuestaButton = (Button)findViewById( R.id.cancelarCapturaEncuestaButton );
        this.guardarEncuestaButton = (Button)findViewById( R.id.guardarEncuestaButton );


        this.cancelarCapturaEncuestaButton.setOnClickListener( this );
        this.guardarEncuestaButton.setOnClickListener( this );

        this.encuentaListView = (ListView)findViewById( R.id.detallePedidoListView);
        this.encuentaListView.setAdapter(new EncuestaListAdapter(this.visita, this.encuesta, this));

    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_encuesta_lista, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onClick(View v) {

        if( v == cancelarCapturaEncuestaButton ){
            this.finish();
            return;
        }else
        if( v == guardarEncuestaButton ){
            if( this.todasLasPreguntasContestadas() == true){
                ViewUtil.mostrarAlertaAceptarCancelar("¿Desea guardar esta encuesta?", this, this);
            }else{
                String message = "¡No se han contestado todas las preguntas!";
                ViewUtil.mostrarMensajeRapido(this, message);
            }
        }

    }

    @Override
    public void onClick(DialogInterface dialog, int which) {
        LogUtil.printLog(CLASSNAME, "AlertClicked: dialog:" + dialog + " , wich:" + which);
        if( which == AlertDialog.BUTTON_POSITIVE ) {
            this.guardarEncuestaEnVisitaActual();
            this.prepararActualizacionVisita();
            String message = "¡Encuesta guardada!";
            ViewUtil.mostrarMensajeRapido(this, message);
            Intent intent = new Intent( this , SucursalMenuActivity.class );
//            this.finish();
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            startActivity( intent );
        }
    }

    private void prepararActualizacionVisita() {
        this.visita.setEncuestaCapturada(RespuestaBinaria.SI );
        this.visitaService.actualizarVisitaActual();
    }

    private boolean todasLasPreguntasContestadas() {
        EncuestaListAdapter preguntasListAdapter = (EncuestaListAdapter)encuentaListView.getAdapter();
        return preguntasListAdapter.todasLasCeldasFueronVistas();
    }

    private void guardarEncuestaEnVisitaActual() {
        //ININ MAMM Se procede a guardar la visita en base de datos
        this.visitaService.guardarEncuesta( this.recuperarEncuestaContestada() );
    }

    private PreguntaRespuesta[] recuperarEncuestaContestada() {
        EncuestaListAdapter preguntasListAdapter = (EncuestaListAdapter)encuentaListView.getAdapter();

        View[] cells = preguntasListAdapter.getCells();
        Pregunta[] preguntas = preguntasListAdapter.getPreguntas();
        PreguntaRespuesta[] Arraypr = new PreguntaRespuesta[preguntas.length];


        for( int i = 0; i < cells.length ; i++ ){
            Pregunta itemPregunta = preguntas[i];
            Spinner respuestaEncuestaSpinner = (Spinner) cells[i].findViewById(R.id.respuestaEncuestaSpinner);
            int posicionRespuesta = respuestaEncuestaSpinner.getSelectedItemPosition();
            PreguntaRespuesta pr = new PreguntaRespuesta();

            pr.setPregunta(itemPregunta);
            pr.setRespuestaElegida(itemPregunta.getRespuestas()[posicionRespuesta]);
            Arraypr[i] = pr;
            LogUtil.printLog(CLASSNAME, "pr:" + pr);
        }
        return Arraypr;
    }
}
