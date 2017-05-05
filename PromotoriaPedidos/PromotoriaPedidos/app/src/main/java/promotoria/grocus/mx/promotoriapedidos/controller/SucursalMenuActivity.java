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
import android.widget.TextView;

import promotoria.grocus.mx.promotoriapedidos.R;
import promotoria.grocus.mx.promotoriapedidos.business.Visita;
import promotoria.grocus.mx.promotoriapedidos.business.utils.EstatusVisita;
import promotoria.grocus.mx.promotoriapedidos.business.utils.Json;
import promotoria.grocus.mx.promotoriapedidos.business.utils.RespuestaBinaria;
import promotoria.grocus.mx.promotoriapedidos.controller.validator.VisitaValidator;
import promotoria.grocus.mx.promotoriapedidos.services.VisitaService;
import promotoria.grocus.mx.promotoriapedidos.services.impl.VisitaServiceImpl;
import promotoria.grocus.mx.promotoriapedidos.utils.Const;
import promotoria.grocus.mx.promotoriapedidos.utils.CustomUncaughtExceptionHandler;
import promotoria.grocus.mx.promotoriapedidos.utils.LogUtil;
import promotoria.grocus.mx.promotoriapedidos.utils.ViewUtil;


public class SucursalMenuActivity extends ActionBarActivity implements View.OnClickListener, DialogInterface.OnClickListener {
    public static final String CLASSNAME = SucursalMenuActivity.class.getSimpleName();

    //Services
    private VisitaService visitaService;
    private VisitaValidator visitaValidator;

    //Business
    private Visita visita;

    //UI Elements
    private TextView sucursalTexView;
    private TextView direccionSucursaltextView;
    private Button checkInMenuButton;
    private Button capturarEncuestaMenuButton;
    private Button levantarPedidoButton;
    private Button checkOutButton;
    private Button cancelarButton;
    private TextView estatusMenuTextView;


    private ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Thread.setDefaultUncaughtExceptionHandler(new CustomUncaughtExceptionHandler(this));
        this.visitaService = VisitaServiceImpl.getSingleton();
        this.visitaValidator = VisitaValidator.getSingleton();

        Intent intent = this.getIntent();
        int posicion = intent.getIntExtra(Const.ParametrosIntent.POSICION_VISITA.getNombre(), -1);
        if( posicion != -1 ){
            this.visita = this.visitaService.getVisitaPorPosicionEnLista( posicion );
        }else{
            this.visita = this.visitaService.getVisitaActual( );
        }

        //this.prepararPantalla();
    }

    private void prepararPantalla(){
        setContentView(R.layout.sucursal_menu_layout);

        sucursalTexView = (TextView)this.findViewById( R.id.sucursalTexView );
        sucursalTexView.setText(this.visita.getNombreFarmacia());

        direccionSucursaltextView = (TextView)this.findViewById( R.id.direccionSucursaltextView );
        direccionSucursaltextView.setText(this.visita.getDireccionFarmacia());

        estatusMenuTextView = (TextView)this.findViewById( R.id.estatusMenuTextView );
        estatusMenuTextView.setText(this.visita.getEstatusVisita().getNombreEstatus());
        estatusMenuTextView.setBackgroundColor(this.visita.getEstatusVisita().getColor());

        checkInMenuButton = (Button)this.findViewById( R.id.checkInMenuButton );
        capturarEncuestaMenuButton = (Button)this.findViewById( R.id.capturarEncuestaMenuButton );
        levantarPedidoButton = (Button)this.findViewById( R.id.levantarPedidoButton );
        checkOutButton = (Button)this.findViewById( R.id.checkOutButton );
        cancelarButton = (Button)this.findViewById( R.id.cancelarButton );

        checkInMenuButton.setOnClickListener( this );
        capturarEncuestaMenuButton.setOnClickListener(this);
        levantarPedidoButton.setOnClickListener( this );
        checkOutButton.setOnClickListener(this);
        cancelarButton.setOnClickListener(this);

        //validaciones para mostrar en pantalla
        this.aplicarReglasParaHabilitarBotones();
    }

    private void aplicarReglasParaHabilitarBotones() {

        switch ( this.visita.getEstatusVisita() ) {
            case CHECK_IN:
                this.checkInMenuButton.setEnabled(false);
                this.capturarEncuestaMenuButton.setEnabled(false);
                this.levantarPedidoButton.setEnabled( true );
                this.checkOutButton.setEnabled(true );
                if( this.visita.getAplicarEncuesta() == RespuestaBinaria.SI
                        && this.visita.getEncuestaCapturada() == RespuestaBinaria.NO ){
                    this.capturarEncuestaMenuButton.setEnabled(true);
                }
                if( this.visita.getPedidoCapturado() == RespuestaBinaria.SI ){
                    this.levantarPedidoButton.setEnabled(false );
                }
                break;
            case NO_ENVIADO:
                this.checkInMenuButton.setEnabled(false);
                this.capturarEncuestaMenuButton.setEnabled(false);
                this.levantarPedidoButton.setEnabled( false );
                this.checkOutButton.setEnabled( true );
                break;
            case CHECK_OUT:
            case CHECK_OUT_REQUEST:
            case CANCELADA:
            case NO_VISITADA:
                this.checkInMenuButton.setEnabled(false);
                this.capturarEncuestaMenuButton.setEnabled(false);
                this.levantarPedidoButton.setEnabled( false );
                this.checkOutButton.setEnabled(false );
                break;
            default:
                this.checkInMenuButton.setEnabled(true);
                this.capturarEncuestaMenuButton.setEnabled(false);
                this.levantarPedidoButton.setEnabled( false );
                this.checkOutButton.setEnabled(false );
                break;
        }
    }

    @Override
    protected void onResume() {
        LogUtil.printLog( CLASSNAME , "OnResume invocado...." );
        super.onResume();
        this.prepararPantalla();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_sucursal_menu, menu);
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
        Intent intent = null;
        //Pendiente Implementacion de CheckIn
        if( v == checkInMenuButton ){
            progressDialog = ProgressDialog.show(this, "", "Realizando CheckIn");
            CheckinTask checkinTask = new CheckinTask();
            checkinTask.execute( visita );
            return;
        }
        if( v == capturarEncuestaMenuButton ){
            intent = new Intent( this, EncuestaListaActivity.class);
        }
        else if( v == levantarPedidoButton ){
            intent = new Intent( this, CapturaPedidoActivity.class);
        }
        else if( v == checkOutButton ){
            //Regla Negocio
            //Caso 1. - Si el usuario esta en estatus no enviado, significa que ya intentó realizar un checkput previamente y por tanto se procede a ejecutar el checkOut directamente
            if( this.visita.getEstatusVisita() == EstatusVisita.NO_ENVIADO ){
                progressDialog = ProgressDialog.show( this, "", "Realizando Chekout" );
                CheckOutTask checkOutTask = new CheckOutTask();
                checkOutTask.execute(visita);
            }
            //Caso 2. - El usuario ya capturo toda la información necesaria y se pinta la pantalla de comentario para cerrar el flujo
            else if( this.visitaValidator.validarSeTienenTodosLosDatosCapturadosEnVisita( this.visita ) == true){
                intent = new Intent( this , ComentariosActivity.class);
                this.startActivity(intent);
            }
            //Caso 3. -No se tienen todos los datos capturados se indica a través de una alerta y se despliega el comentario
            else{
                ViewUtil.mostrarAlertaAceptarCancelar( "No se tienen todos los datos Capturados. ¿Desea concluir la visita?" , this , this );
            }
            return;
        }
        else if( v == cancelarButton ){
            this.finish();
            return;
        }
        this.visitaService.actualizarVisitaActual();
        this.startActivity(intent);
    }


    @Override
    public void onClick(DialogInterface dialog, int which) {
        LogUtil.printLog(CLASSNAME, "AlertClicked: dialog:" + dialog + " , wich:" + which);
        if( which == AlertDialog.BUTTON_POSITIVE ){
            Intent intent = new Intent( this , ComentariosActivity.class);
            this.startActivity(intent);
        }else {
            //No se realiza nada
        }
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

            boolean mostrarSiguienteAct = false;

            String msg = Json.getMsgError(Json.ERROR_JSON.CHECK_OUT);
            if (msg != null){
                ViewUtil.mostrarAlertaDeError(msg, SucursalMenuActivity.this);
            } else{
                mostrarSiguienteAct = true;
            }

            if ( mostrarSiguienteAct ){
                Intent intent = new Intent(SucursalMenuActivity.this, SucursalesListaActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);
            }
        }
    }


        private class CheckinTask extends AsyncTask<Visita, Boolean, Boolean> {

        @Override
        protected Boolean doInBackground(Visita... params) {
            LogUtil.printLog(CLASSNAME, "doInBackground...");
            SucursalMenuActivity.this.visitaService.realizarCheckIn(params[0]);
            return true;
        }

            @Override
            protected void onPostExecute( Boolean aBoolean) {
                LogUtil.printLog( CLASSNAME , "doInBackground..." );
                super.onPostExecute( aBoolean );

                progressDialog.dismiss();
                String msg = Json.getMsgError(Json.ERROR_JSON.CHECK_IN);
                if (msg != null){
                    ViewUtil.mostrarAlertaDeError(msg, SucursalMenuActivity.this);
                } else{
                    Visita visitaActual = SucursalMenuActivity.this.visitaService.getVisitaActual();

                    Const.ModalidadCheckIn modoCheckIn = Const.ModalidadCheckIn.NOTIFICADO_ENLINEA;
                    if( visita.getCheckInNotificado() == RespuestaBinaria.NO ){
                        modoCheckIn = Const.ModalidadCheckIn.NOTIFICADO_FUERALINEA;
                    }
                    ViewUtil.mostrarMensajeRapido( SucursalMenuActivity.this , "Se realiza el Check - In \nModo: " + modoCheckIn.nombre );
                }
                prepararPantalla();
            }
    }
}
