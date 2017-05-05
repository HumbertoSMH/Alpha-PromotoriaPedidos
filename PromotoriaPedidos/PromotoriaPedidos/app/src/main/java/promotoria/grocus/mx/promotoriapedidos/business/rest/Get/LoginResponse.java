package promotoria.grocus.mx.promotoriapedidos.business.rest.Get;


import promotoria.grocus.mx.promotoriapedidos.business.rest.Response;

/**
 * Created by MAMM on 28/04/2015.
 */
public class LoginResponse extends Response {

    public static LoginResponse generarResponseExitoso(){
        LoginResponse response = new LoginResponse();
        response.setSeEjecutoConExito( true );
        response.setMensaje( "" );
        response.setClaveError( "" );
        return response;
    }
}
