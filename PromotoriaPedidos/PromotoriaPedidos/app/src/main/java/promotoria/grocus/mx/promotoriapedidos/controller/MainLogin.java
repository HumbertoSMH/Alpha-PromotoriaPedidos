package promotoria.grocus.mx.promotoriapedidos.controller;

import android.app.ProgressDialog;
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

import java.util.Date;

import promotoria.grocus.mx.promotoriapedidos.R;
import promotoria.grocus.mx.promotoriapedidos.business.Ruta;
import promotoria.grocus.mx.promotoriapedidos.business.utils.Json;
import promotoria.grocus.mx.promotoriapedidos.controller.validator.PromotorValidator;
import promotoria.grocus.mx.promotoriapedidos.services.PromotorService;
import promotoria.grocus.mx.promotoriapedidos.services.RutaService;
import promotoria.grocus.mx.promotoriapedidos.services.impl.PromotorServiceImpl;
import promotoria.grocus.mx.promotoriapedidos.services.impl.RutaServiceImpl;
import promotoria.grocus.mx.promotoriapedidos.utils.Const;
import promotoria.grocus.mx.promotoriapedidos.utils.CustomUncaughtExceptionHandler;
import promotoria.grocus.mx.promotoriapedidos.utils.LogUtil;
import promotoria.grocus.mx.promotoriapedidos.utils.Util;
import promotoria.grocus.mx.promotoriapedidos.utils.ValidadorUIMensajes;
import promotoria.grocus.mx.promotoriapedidos.utils.ViewUtil;


public class MainLogin extends ActionBarActivity  implements View.OnClickListener {

    private static final String CLASSNAME = MainLogin.class.getSimpleName();

    //Servicios
    private PromotorService promotorService;
    private RutaService rutaService;
    private PromotorValidator promotorValidator;

    //UI Elements
    EditText usuarioEditText = null;
    EditText passwordEditText = null;
    TextView idVersionTextView = null;
    Button continuarButton = null;
    ProgressDialog progressDialog;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Thread.setDefaultUncaughtExceptionHandler(new CustomUncaughtExceptionHandler(this));

        //Inicializacion de singleton
        this.promotorService = PromotorServiceImpl.getSingleton();
        this.rutaService = RutaServiceImpl.getSingleton();
        this.promotorValidator = PromotorValidator.getSingleton(  );

        this.prepararPantalla();
    }

    private void prepararPantalla(){
        setContentView(R.layout.main_login_layout);
        findViewById(R.id.continuarLoginButton).setOnClickListener(this);
        usuarioEditText = (EditText)findViewById(R.id.usuarioEditText);
        passwordEditText = (EditText)findViewById(R.id.contrasenaEditText);
        continuarButton = (Button)findViewById(R.id.continuarLoginButton);

        idVersionTextView = (TextView)findViewById(R.id.idVersionTextView);
        idVersionTextView.setText( "Ver. " + Const.VersionAPK.getUltimaVersion().version );

        continuarButton.setOnClickListener( this );
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main_login, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
//        if (id == R.id.action_settings) {
//            return true;
//        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onClick(View v) {
        if( findViewById(R.id.continuarLoginButton) == v ){
            LogUtil.printLog(CLASSNAME, "Hacen click al boton Continuar");
        }

        Boolean existenErrores = this.realizarValidacionesAntesDeLogin( usuarioEditText , passwordEditText );
        if( existenErrores == true ){
            LogUtil.printLog( CLASSNAME , "Existen errores en el registro" );
        }else{
            String usuario = usuarioEditText.getText().toString();
            String password = passwordEditText.getText().toString();

            progressDialog = ProgressDialog.show( this, "", "Realizando Login" );
            LoginTask loginTask = new LoginTask();
            loginTask.execute( usuario, password );
        }
    }

    private boolean realizarValidacionesAntesDeLogin( EditText usuarioEditText , EditText passwordEditText ){
        Boolean existenErrores = false;
        String usuario = usuarioEditText.getText().toString();
        String password = passwordEditText.getText().toString();
        ValidadorUIMensajes userValidatorMessage = promotorValidator.validarUsuario( usuarioEditText );
        if( userValidatorMessage.isCorrecto() == false ){
            usuarioEditText.setError( userValidatorMessage.getMensaje() );
            existenErrores = true;
        }

        ValidadorUIMensajes passwordValidatorMessage = promotorValidator.validarPassword( passwordEditText );
        if( passwordValidatorMessage.isCorrecto() == false ){
            passwordEditText.setError( passwordValidatorMessage.getMensaje() );
            existenErrores = true;
        }
        return existenErrores;
    }


    private class LoginTask extends AsyncTask<String, Boolean, Boolean> {

        @Override
        protected Boolean doInBackground(String... params) {

            promotorService.realizarLoggin(params[0], params[1]);
            return true;
        }

        @Override
        protected void onPostExecute(Boolean aBoolean) {
            super.onPostExecute(aBoolean);

            progressDialog.dismiss();

            boolean mostrarSiguienteAct = false;

            String msg = Json.getMsgError(Json.ERROR_JSON.LOGIN);
            if (msg != null) {
                LogUtil.printLog( CLASSNAME , "===>>>No fue posible realizar el Loggin" );
                String user = MainLogin.this.usuarioEditText.getText().toString();
                String pass = MainLogin.this.passwordEditText.getText().toString();
                //No se pudo realizar el login, Se verifica que el usuario que se intente loggear tenga ruta guardada y que sea vigente.
                if (MainLogin.this.validarPromotorConRutaEnBase(user, pass) == false) {
                    LogUtil.printLog( CLASSNAME , "===>>>No existe una ruta para el día de hoy(" + Util.getDateFromMilis(new Date().getTime()) + " 00:00" + ") para este usuario" );
                    ViewUtil.mostrarAlertaDeError(msg, MainLogin.this);
                } else {
                    LogUtil.printLog( CLASSNAME , "===>>>Se localiza la ruta del día de hoy(" + Util.getDateFromMilis(new Date().getTime()) + " 00:00" + "), se recarga desde la base de datos" );
                    promotorService.prepararDatosPromotorMovilDesdeInformacionEnBase(user, pass);
                    mostrarSiguienteAct = true;
                }
            } else {
                LogUtil.printLog( CLASSNAME , "===>>>Loggin exitoso" );
                mostrarSiguienteAct = true;
            }

            if (mostrarSiguienteAct) {
                startActivity(new Intent(MainLogin.this, SucursalesListaActivity.class));
            }
        }
    }

    private boolean validarPromotorConRutaEnBase( String user , String pass ){
        boolean tieneRutaEnBase = false;

        Ruta rutaEnBase = this.rutaService.getRutaPorClaveYPasswordDePromotor( user , pass );
        if(    rutaEnBase != null &&   //que la ruta exista
                rutaEnBase.getFechaProgramadaString().equals( Util.getDateFromMilis(new Date().getTime()) + " 00:00") == true  ){  //Que la ruta sea del día de hoy
            tieneRutaEnBase = true;
        }
        return tieneRutaEnBase;
    }

}
