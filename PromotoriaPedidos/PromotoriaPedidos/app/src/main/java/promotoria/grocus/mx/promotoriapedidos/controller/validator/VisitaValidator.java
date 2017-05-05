package promotoria.grocus.mx.promotoriapedidos.controller.validator;

import android.content.Context;
import android.content.res.Resources;
import android.widget.EditText;

import promotoria.grocus.mx.promotoriapedidos.PromotoriaPedidosApp;
import promotoria.grocus.mx.promotoriapedidos.business.Visita;
import promotoria.grocus.mx.promotoriapedidos.business.utils.RespuestaBinaria;
import promotoria.grocus.mx.promotoriapedidos.utils.ValidadorGenerico;
import promotoria.grocus.mx.promotoriapedidos.utils.ValidadorUIMensajes;


/**
 * Created by devmac02 on 14/07/15.
 */
public class VisitaValidator {

    private static VisitaValidator singleton;
    private static ValidadorGenerico validadorGenerico;

    private Resources resources;
    private Context mContext;

    private VisitaValidator(Context context) {
        resources = context.getResources();
        mContext = context;
        validadorGenerico = ValidadorGenerico.getSingleton( context );
    }

    public static VisitaValidator getSingleton( ){
        if( singleton == null ){
            singleton = new VisitaValidator(PromotoriaPedidosApp.getCurrentApplication());
        }
        return singleton;
    }

    public ValidadorUIMensajes validarCodigoPaquete( EditText codigoPaqueteEditText ){
        ValidadorUIMensajes validator = new ValidadorUIMensajes();
        String codigoPaquete = codigoPaqueteEditText.getText().toString();
        if( codigoPaquete.trim().length() == 0){
            validator.setMensaje( "Capture el código" );
            validator.setCorrecto( false );
            return validator;
        }
        return validator;
    }


    public ValidadorUIMensajes validarCorreoElectronico( EditText correoElectronicoEditText ){
        ValidadorUIMensajes validator = new ValidadorUIMensajes();
        String email = correoElectronicoEditText.getText().toString();
        if( email.trim().length() == 0){
            validator.setMensaje( "Capture el email" );
            validator.setCorrecto( false );
            return validator;
        }

        if( android.util.Patterns.EMAIL_ADDRESS.matcher( email.trim()).matches(  ) == false ){
            validator.setMensaje( "Formato de email inválido" );
            validator.setCorrecto( false );
            return validator;
        }
        return validator;
    }


    public boolean validarSeTienenTodosLosDatosCapturadosEnVisita( Visita visita ){
        boolean esValido = true;
        if (visita.getPedidoCapturado() == RespuestaBinaria.NO ||
                (visita.getAplicarEncuesta() == RespuestaBinaria.SI &&
                        visita.getEncuestaCapturada() == RespuestaBinaria.NO
                )
                ) {
            esValido = false;

        }
        return esValido;
    }


    public ValidadorUIMensajes validarComentario( EditText comentarioEditText ){
        ValidadorUIMensajes validator = new ValidadorUIMensajes();
        String comentario = comentarioEditText.getText().toString();
        if( comentario.trim().length() == 0){
            validator.setMensaje( "Capture el comentario" );
            validator.setCorrecto( false );
            return validator;
        }
        return validator;
    }

}
