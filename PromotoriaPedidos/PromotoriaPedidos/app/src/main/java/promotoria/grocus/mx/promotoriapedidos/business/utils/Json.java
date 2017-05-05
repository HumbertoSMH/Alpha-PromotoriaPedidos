package promotoria.grocus.mx.promotoriapedidos.business.utils;

import com.fasterxml.jackson.databind.ObjectMapper;

import java.util.HashMap;
import java.util.Map;

import promotoria.grocus.mx.promotoriapedidos.business.rest.Response;

/**
 * Created by MAMM on 28/04/2015.
 */
public class Json {

    private static Map<ERROR_JSON, String> mapMSG;

    public static enum ERROR_JSON {
        LOGIN(1),
        CHECK_IN(2),
        CHECK_OUT(3),
        OBTENER_RUTA_PROMOTOR(4) ;

        private int idError;

        private ERROR_JSON(int idError) {
            this.idError = idError;
        }

        public int getIdError() {
            return idError;
        }
    }

    public static <U> U parseJson(String jsonRequest, Class<U> classType)
            throws Exception {
        U classResult = null;
        ObjectMapper mapper = new ObjectMapper();
        classResult = mapper.readValue(jsonRequest, classType);
        return classResult;
    }

    public static void solicitarMsgError( Response response, ERROR_JSON error){

        if ( mapMSG == null ) {
            mapMSG = new HashMap<>();
        }
        mapMSG.put(error, response.getMensaje());
    }

    public  static  String getMsgError(ERROR_JSON error_json){

        if (mapMSG != null){
            String msg = mapMSG.get( error_json );
            if (msg != null){
                mapMSG.remove( error_json );
                return msg;
            }
        }
        return null;
    }

}
