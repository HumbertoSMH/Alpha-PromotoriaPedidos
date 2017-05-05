package promotoria.grocus.mx.promotoriapedidos.controller;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import promotoria.grocus.mx.promotoriapedidos.R;
import promotoria.grocus.mx.promotoriapedidos.business.Visita;
import promotoria.grocus.mx.promotoriapedidos.business.utils.Json;
import promotoria.grocus.mx.promotoriapedidos.controller.validator.VisitaValidator;
import promotoria.grocus.mx.promotoriapedidos.services.VisitaService;
import promotoria.grocus.mx.promotoriapedidos.services.impl.VisitaServiceImpl;
import promotoria.grocus.mx.promotoriapedidos.utils.CustomUncaughtExceptionHandler;
import promotoria.grocus.mx.promotoriapedidos.utils.LogUtil;
import promotoria.grocus.mx.promotoriapedidos.utils.ValidadorUIMensajes;
import promotoria.grocus.mx.promotoriapedidos.utils.ViewUtil;


public class ComentariosActivity extends ActionBarActivity implements View.OnClickListener, DialogInterface.OnClickListener {
    public static final String CLASSNAME = ComentariosActivity.class.getSimpleName();

    //Services
    private VisitaService visitaService;
    private VisitaValidator visitaValidator;

    //Business
    private Visita visita;
    private boolean datosCapturadosCompletos;

    //UI Elements
    private TextView sucursalTexView;
    private EditText comentarioEditText;
    private Button cancelarComentarioButton;
    private Button checkOutButton;

    private ProgressDialog progressDialog;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Thread.setDefaultUncaughtExceptionHandler(new CustomUncaughtExceptionHandler(this));
        this.visitaService = VisitaServiceImpl.getSingleton();
        this.visitaValidator = VisitaValidator.getSingleton();
        this.visita = this.visitaService.getVisitaActual();
        this.prepararPantalla();

    }

    private void prepararPantalla(){
        setContentView(R.layout.comentarios_layout);

        this.sucursalTexView = (TextView)this.findViewById( R.id.sucursalTexView);
        this.sucursalTexView.setText( this.visita.getNombreFarmacia());

        this.comentarioEditText = (EditText)this.findViewById( R.id.comentarioEditText);
//        this.comentarioEditText.setText( this.visita.getComentarios() );

        this.cancelarComentarioButton = (Button)this.findViewById( R.id.cancelarComentarioButton);
        this.checkOutButton = (Button)this.findViewById( R.id.checkOutButton);

        this.cancelarComentarioButton.setOnClickListener( this );
        this.checkOutButton.setOnClickListener(this);


        this.datosCapturadosCompletos = visitaValidator.validarSeTienenTodosLosDatosCapturadosEnVisita( this.visita );


    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_comentarios, menu);
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
        //Intent intent = null;
        if( v == cancelarComentarioButton ){
            this.finish();
            return;
        }else
            if( v == checkOutButton ){
                //se valida el comentario solo si no se tienen los datos capturados
                ValidadorUIMensajes validacionComentario = this.visitaValidator.validarComentario(this.comentarioEditText);
                if( datosCapturadosCompletos == false &&
                        validacionComentario.isCorrecto() == false ){
                        this.comentarioEditText.setError( validacionComentario.getMensaje() );
                }else{
                    //Si no ha problemas con el comentario se ejecuta el checkout el proceso de checkout
                    this.visita.setComentarios(comentarioEditText.getText().toString());
                    this.visitaService.actualizarVisitaActual();
                    ViewUtil.mostrarAlertaAceptarCancelar( "¿Desea dar por terminada esta visita?" , this , this );
                }
                return;
            }
        //this.startActivity( intent );
    }


    @Override
    public void onClick(DialogInterface dialog, int which) {
        LogUtil.printLog(CLASSNAME, "AlertClicked: dialog:" + dialog + " , wich:" + which);
        if( which == AlertDialog.BUTTON_POSITIVE ){
            progressDialog = ProgressDialog.show(this, "", "Realizando Chekout");
            CheckOutTask checkOutTask = new CheckOutTask();
            checkOutTask.execute( visita );
        }else {
            //No se realiza nada
        }
    }

//    private boolean validarAntesDeCheckOutCompleto() {
//        boolean esValido = false;
//        if (this.visita.getAplicarEncuesta() == RespuestaBinaria.NO ||
//                        this.visita.getAplicarEncuesta() == RespuestaBinaria.SI &&
//                                this.visita.getEncuestaCapturada() == RespuestaBinaria.SI
//                ) {
//            esValido = true;
//
//        }
//        return esValido;
//    }

    private void cerrarVistaDeReporte() {
        Intent intent = new Intent(this, SucursalMenuActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        //intent.putExtra( Const.ParametrosIntent.POSICION_VISITA.getNombre() , this.visita.getIdVisita() );
        startActivity(intent);
    }

    private class CheckOutTask extends AsyncTask<Visita, Boolean, Boolean> {

        @Override
        protected Boolean doInBackground(Visita... params) {

            visitaService.realizarCheckOut(params[0]);
            return true;
        }

        @Override
        protected void onPostExecute(Boolean aBoolean) {
            super.onPostExecute(aBoolean);

            progressDialog.dismiss();

            boolean cerrarPantalla = false;

            String msg = Json.getMsgError(Json.ERROR_JSON.CHECK_OUT);
            if (msg != null){
                msg = " Error al cerrar el reporte: " + msg;
                //ViewUtil.mostrarMensajeRapido(ComentariosActivity.this, msg);
                ViewUtil.mostrarAlertaDeError(msg, ComentariosActivity.this);
            } else{
                ComentariosActivity.this.visitaService.actualizarVisitaActual();
                StringBuilder msgBuilder = new StringBuilder();
                msgBuilder.append( "El reporte " )
                        .append( ComentariosActivity.this.visita.getNombreFarmacia() )
                        .append( " se cerró con éxito, el número de folio es el " )
                        .append(ComentariosActivity.this.visita.getIdVisita()) ;
                ViewUtil.mostrarMensajeRapido(ComentariosActivity.this, msgBuilder.toString());
                cerrarPantalla = true;
            }

            if( cerrarPantalla == true ){
                ComentariosActivity.this.cerrarVistaDeReporte();
            }
        }
    }

}
