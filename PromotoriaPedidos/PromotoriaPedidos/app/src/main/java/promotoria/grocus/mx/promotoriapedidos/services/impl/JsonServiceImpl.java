package promotoria.grocus.mx.promotoriapedidos.services.impl;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;

import promotoria.grocus.mx.promotoriapedidos.business.rest.Get.EncuestaVisitaResponse;
import promotoria.grocus.mx.promotoriapedidos.business.rest.Get.LoginResponse;
import promotoria.grocus.mx.promotoriapedidos.business.rest.Get.ProductosPorFarmaciaResponse;
import promotoria.grocus.mx.promotoriapedidos.business.rest.Get.RutaPromotorResponse;
import promotoria.grocus.mx.promotoriapedidos.business.rest.Post.CheckInFarmaciaRequest;
import promotoria.grocus.mx.promotoriapedidos.business.rest.Post.CheckInFarmaciaResponse;
import promotoria.grocus.mx.promotoriapedidos.business.rest.Post.CheckInFarmaciaRest;
import promotoria.grocus.mx.promotoriapedidos.business.rest.Post.CheckOutFarmaciaRequest;
import promotoria.grocus.mx.promotoriapedidos.business.rest.Post.CheckOutFarmaciaResponse;
import promotoria.grocus.mx.promotoriapedidos.business.rest.Post.CheckOutFarmaciaRest;
import promotoria.grocus.mx.promotoriapedidos.business.rest.Response;
import promotoria.grocus.mx.promotoriapedidos.services.JsonService;
import promotoria.grocus.mx.promotoriapedidos.utils.Const;
import promotoria.grocus.mx.promotoriapedidos.utils.LogUtil;
import promotoria.grocus.mx.promotoriapedidos.utils.StringUtils;
import promotoria.grocus.mx.promotoriapedidos.utils.Util;


/**
 * Created by MAMM on 28/04/2015.
 */
public class JsonServiceImpl implements JsonService {

    private static final String CLASSNAME = JsonServiceImpl.class.getSimpleName();

    private final String URL_BASE = "http://services.alphagroup.mx/GrocusMerchandising.svc";
    private final String URL_LOGIN = URL_BASE + "/validarLoginPromotor";
    private final String URL_OBTENERRUTA = URL_BASE + "/obtenerRutaPromotor";
    private final String URL_OBTENERPRODUCTOPORFARMACIA = URL_BASE + "/obtenerProductosPorFarmacia";
    private final String URL_OBTENERENCUESTARUTA = URL_BASE + "/obtenerEncuestasRuta";
    private final String URL_CHECKIN = URL_BASE + "/hacerCheckInFarmacia";
    private final String URL_CHECKOUT = URL_BASE + "/hacerCheckOutFarmacia";




    private static JsonService singleton;

    public static JsonService getSingleton(){

        if ( singleton == null ){
            singleton = new JsonServiceImpl();
        }
        return  singleton;
    }

    public RutaPromotorResponse realizarPeticionObtenerRutaPromotor(String usuario){
        LogUtil.printLog( CLASSNAME , "realizarPeticionObtenerRutaPromotor .." );
        HttpClient httpClient = new DefaultHttpClient();

        HttpGet httpGet = new HttpGet(URL_OBTENERRUTA + "?clavePromotor=" + usuario );

        httpGet.setHeader("content-type", "application/json");
        RutaPromotorResponse rutaPromotorResponse;
        try{

            LogUtil.printLog(CLASSNAME, "antes  del jsonnn = " + httpGet.getURI().toString());
            HttpResponse resp = httpClient.execute(httpGet);

            String respStr = EntityUtils.toString(resp.getEntity());
            if( Const.Enviroment.currentEnviroment == Const.Enviroment.FAKE ){
                //respStr="{\"claveError\":\"\",\"mensaje\":\"\",\"seEjecutoConExito\":true,\"rutaPromotor\":{}}";
                //respStr="{\"claveError\":\"\",\"mensaje\":\"\",\"seEjecutoConExito\":true,\"rutaPromotor\":{\"fechaCreacion\":\"2015-07-25 10:20\",\"fechaProgramada\":\"2015-07-31 00:00\",\"fechaUltimaModificacion\":\"2015-07-25 12:16\",\"idRuta\":6,\"visitas\":[{\"farmacia\":{\"domicilio\":\"Av. Coyoacan 1035\",\"idFarmacia\":1,\"nombre\":\"Farmacia Similares X\"},\"idEncuesta\":3,\"idEstatus\":4,\"idRutaDet\":10},{\"farmacia\":{\"domicilio\":\"Av. Revolución 1067\",\"idFarmacia\":3,\"nombre\":\"Farmacia Guadalajara Mixcoac\"},\"idEncuesta\":3,\"idEstatus\":4,\"idRutaDet\":11},{\"farmacia\":{\"domicilio\":\"Av. Cuauhtemoc 129\",\"idFarmacia\":2,\"nombre\":\"Farmacia San Pablo Doctores\"},\"idEncuesta\":3,\"idEstatus\":4,\"idRutaDet\":12}]}}";
                respStr = "{\"claveError\":\"\",\"mensaje\":\"\",\"seEjecutoConExito\":true,\"rutaPromotor\":{\"fechaCreacion\":\"2015-07-25 10:20\",\"fechaProgramada\":\"2015-08-06 00:00\",\"fechaUltimaModificacion\":\"2015-07-25 12:16\",\"idRuta\":6,\"visitas\":[{\"farmacia\":{\"domicilio\":\"Av. Coyoacan 1035\",\"idFarmacia\":1,\"nombre\":\"Farmacia Similares X\"},\"idEncuesta\":3,\"idEstatus\":4,\"idRutaDet\":10},{\"farmacia\":{\"domicilio\":\"Av. Revolución 1067\",\"idFarmacia\":3,\"nombre\":\"Farmacia Guadalajara Mixcoac\"},\"idEncuesta\":3,\"idEstatus\":4,\"idRutaDet\":11},{\"farmacia\":{\"domicilio\":\"Av. Cuauhtemoc 129\",\"idFarmacia\":2,\"nombre\":\"Farmacia San Pablo Doctores\"},\"idEncuesta\":3,\"idEstatus\":4,\"idRutaDet\":12}]}}";
            }
            LogUtil.printLog(CLASSNAME, "Respuesta del jsonnn = " + respStr);

            rutaPromotorResponse = Util.parseJson( respStr  , RutaPromotorResponse.class );

            LogUtil.printLog( CLASSNAME , "Respuesta del realizarPeticionObtenerRutaPromotor = " + rutaPromotorResponse );

            return rutaPromotorResponse;
        }
        catch(Exception ex)
        {
            LogUtil.logError(CLASSNAME, "Error de consumo de JSON " + ex.getMessage());
            rutaPromotorResponse = new RutaPromotorResponse();
            rutaPromotorResponse.setSeEjecutoConExito( false );
            rutaPromotorResponse.setMensaje( ex.getMessage() );
            rutaPromotorResponse.setClaveError( "" + 1999 );
        }

        return rutaPromotorResponse;
    }


    public ProductosPorFarmaciaResponse realizarPeticionObtenerProductosPorFarmacia(int idFarmacia){
        LogUtil.printLog( CLASSNAME , "realizarPeticionObtenerProductosPorFarmacia .." );
        HttpClient httpClient = new DefaultHttpClient();
        HttpGet httpGet = new HttpGet(URL_OBTENERPRODUCTOPORFARMACIA + "?idFarmacia=" + idFarmacia );
        httpGet.setHeader("content-type", "application/json");
        ProductosPorFarmaciaResponse productosPorFarmaciaResponse = null;
        try{
            LogUtil.printLog( CLASSNAME , "antes  del jsonnn = " + httpGet.getURI().toString());
            HttpResponse resp = httpClient.execute(httpGet);

            String respStr = EntityUtils.toString(resp.getEntity());
            LogUtil.printLog( CLASSNAME , "Respuesta del jsonnn = " + respStr);

            if( Const.Enviroment.currentEnviroment == Const.Enviroment.FAKE ){
//                respStr = "{\"claveError\":\"\",\"mensaje\":\"\",\"seEjecutoConExito\":true,\"productos\":[]}";
                //respStr = "{\"claveError\":\"\",\"mensaje\":\"\",\"seEjecutoConExito\":true,\"productos\":[{\"cantidadDosisSolicitadas\":0,\"idProducto\":1,\"maximoDosisPermitidas\":300,\"nombre\":\"Producto 1\",\"precioPorPieza\":0}]}";
            }

            productosPorFarmaciaResponse = Util.parseJson( respStr  , ProductosPorFarmaciaResponse.class );

            LogUtil.printLog( CLASSNAME , "Respuesta del realizarPeticionObtenerProductosPorFarmacia = " + productosPorFarmaciaResponse );

            return productosPorFarmaciaResponse;
        }
        catch(Exception ex)
        {
            LogUtil.logError(CLASSNAME, "Error de consumo de JSON " + ex.getMessage());
            productosPorFarmaciaResponse = new  ProductosPorFarmaciaResponse();
            productosPorFarmaciaResponse.setSeEjecutoConExito( false );
            productosPorFarmaciaResponse.setMensaje( ex.getMessage() );
            productosPorFarmaciaResponse.setClaveError( "" + 1999 );
        }

        return productosPorFarmaciaResponse;
    }
//
//    public CatalogoProductosResponse realizarPeticionCatalogoProductos(){
//        LogUtil.printLog( CLASSNAME , "realizarPeticionCatalogoProductos .." );
//        HttpClient httpClient = new DefaultHttpClient();
//        HttpGet httpGet = new HttpGet(URL_CATALOGO_PRODUCTOS );
//        httpGet.setHeader("content-type", "application/json");
//        CatalogoProductosResponse catalogoProductosResponse = null;
//        try{
//
//            LogUtil.printLog( CLASSNAME , "antes  del jsonnn = " + httpGet.getURI().toString());
//            HttpResponse resp = httpClient.execute(httpGet);
//
//            String respStr = EntityUtils.toString(resp.getEntity());
//            LogUtil.printLog( CLASSNAME , "Respuesta del jsonnn = " + respStr);
//
//            catalogoProductosResponse = new CatalogoProductosResponse();
//
//            catalogoProductosResponse = Util.parseJson( respStr  , CatalogoProductosResponse.class );
//
//            LogUtil.printLog(CLASSNAME, "Respuesta del realizarPeticionCatalogoProductos = " + catalogoProductosResponse);
//
//            return catalogoProductosResponse;
//        }
//        catch(Exception ex)
//        {
//            LogUtil.logError(CLASSNAME, "Error de consumo de JSON " + ex.getMessage());
//            catalogoProductosResponse = new  CatalogoProductosResponse();
//            catalogoProductosResponse.setSeEjecutoConExito( false );
//            catalogoProductosResponse.setMensaje( ex.getMessage() );
//            catalogoProductosResponse.setClaveError( "" + 1999 );
//        }
//
//        return catalogoProductosResponse;
//    }
//
    public EncuestaVisitaResponse RealizarPeticionObtenerEncuestasRuta(int idRuta){
        LogUtil.printLog( CLASSNAME , "realizarPeticionEncuestaVisita .." );
        HttpClient httpClient = new DefaultHttpClient();
        HttpGet httpGet = new HttpGet(URL_OBTENERENCUESTARUTA + "?idRuta=" + idRuta );
        httpGet.setHeader("content-type", "application/json");
        EncuestaVisitaResponse encuestaVisitaResponse = null;
        try{

            LogUtil.printLog(CLASSNAME, "antes  del jsonnn = " + httpGet.getURI().toString());
            HttpResponse resp = httpClient.execute(httpGet);

            String respStr = EntityUtils.toString(resp.getEntity());
            LogUtil.printLog(CLASSNAME, "Respuesta del jsonnn = " + respStr);

            encuestaVisitaResponse = Util.parseJson( respStr  , EncuestaVisitaResponse.class );

            LogUtil.printLog(CLASSNAME, "Respuesta del realizarPeticionEncuestaVisita = " + encuestaVisitaResponse);

            return encuestaVisitaResponse;
        }
        catch(Exception ex)
        {
            LogUtil.logError(CLASSNAME, "Error de consumo de JSON " + ex.getMessage());
            encuestaVisitaResponse = new  EncuestaVisitaResponse();
            encuestaVisitaResponse.setSeEjecutoConExito( false );
            encuestaVisitaResponse.setMensaje( ex.getMessage() );
            encuestaVisitaResponse.setClaveError( "" + 1999 );
        }

        return encuestaVisitaResponse;
    }

    public LoginResponse realizarPeticionLoginGet(String usuario, String password){
        LogUtil.printLog(CLASSNAME, "realizarPeticionLoginGet ..");
        HttpClient httpClient = new DefaultHttpClient();
        HttpGet httpGet =
                new HttpGet(URL_LOGIN + "?clavePromotor=" + usuario + "&passwordPromotor=" + password );
        httpGet.setHeader("content-type", "application/json");
        LoginResponse loginResponse = null;
        try{
            LogUtil.printLog(CLASSNAME, "antes  del jsonnn = " + httpGet.getURI().toString());
            HttpResponse resp = httpClient.execute(httpGet);

            String respStr = EntityUtils.toString(resp.getEntity());
            if(Const.Enviroment.currentEnviroment == Const.Enviroment.FAKE ){
//                respStr = "{\"claveError\":\"\",\"mensaje\":\"\",\"seEjecutoConExito\":true}";
                //respStr = "{\"claveError\":100,\"mensaje\":\"Este mensaje error mam\",\"seEjecutoConExito\":false}";
            }

            LogUtil.printLog(CLASSNAME ,"Respuesta del jsonnn = " + respStr);

            loginResponse = new LoginResponse();

            loginResponse = Util.parseJson(respStr, LoginResponse.class);

            LogUtil.printLog(CLASSNAME ,"Respuesta del realizarPeticionLoginGet = " + loginResponse);
            return loginResponse;
        }
        catch(Exception ex)
        {
            LogUtil.logError(CLASSNAME, "Error de consumo de JSON " + ex.getMessage());
            loginResponse = new  LoginResponse ();
            loginResponse.setSeEjecutoConExito( false );
            loginResponse.setMensaje( ex.getMessage() );
            loginResponse.setClaveError( "" + 1999 );
        }

        return loginResponse;
    }


//    public EncuestasRutaResponse realizarPeticionEncuestasRuta(int idRuta){
//        LogUtil.printLog( CLASSNAME , "realizarPeticionEncuestasRuta .." );
//        HttpClient httpClient = new DefaultHttpClient();
//        HttpGet httpGet =
//                new HttpGet(URL_ENCUESTAS_RUTA + "?idRuta=" + idRuta );
//        httpGet.setHeader("content-type", "application/json");
//        EncuestasRutaResponse encuestasRutaResponse = null;
//        try{
//            LogUtil.printLog(CLASSNAME, "antes  del jsonnn = " + httpGet.getURI().toString());
//            HttpResponse resp = httpClient.execute(httpGet);
//
//            String respStr = EntityUtils.toString(resp.getEntity());
//            if(Const.Enviroment.currentEnviroment == Const.Enviroment.FAKE ){
//                respStr = "{\"claveError\":\"\",\"mensaje\":\"\",\"seEjecutoConExito\":true,\"encuestasRuta\":[]}";
//                //respStr = "{\"claveError\":\"199\",\"mensaje\":\"No existen encuestas definidas\",\"seEjecutoConExito\":false,\"encuestasRuta\":null}";
//                respStr = "{\"claveError\":\"\",\"mensaje\":\"\",\"seEjecutoConExito\":true,\"encuestasRuta\":[{\"descripcion\":\"Encuesta de calidad\",\"idEncuesta\":1,\"preguntasEncuesta\":[{\"descripcion\":\"¿Qué le parece la calidad del producto?\",\"idPregunta\":1,\"respuestasPregunta\":[{\"descripcion\":\"Buena\",\"idRespuesta\":1},{\"descripcion\":\"Regular\",\"idRespuesta\":2},{\"descripcion\":\"Mala\",\"idRespuesta\":3}]},{\"descripcion\":\"¿Recomendaría el producto?\",\"idPregunta\":2,\"respuestasPregunta\":[{\"descripcion\":\"Si\",\"idRespuesta\":4},{\"descripcion\":\"No\",\"idRespuesta\":5}]}]}]}";
//            }
//
//            LogUtil.printLog(CLASSNAME ,"Respuesta del jsonnn = " + respStr);
//
//            encuestasRutaResponse = new EncuestasRutaResponse();
//            encuestasRutaResponse = Util.parseJson( respStr  , EncuestasRutaResponse.class );
//            LogUtil.printLog(CLASSNAME ,"Respuesta del realizarPeticionEncuestasRuta = " + encuestasRutaResponse );
//            return encuestasRutaResponse;
//        }
//        catch(Exception ex)
//        {
//            LogUtil.logError(CLASSNAME, "Error de consumo de JSON " + ex.getMessage());
//            encuestasRutaResponse = new  EncuestasRutaResponse ();
//            encuestasRutaResponse.setSeEjecutoConExito( false );
//            encuestasRutaResponse.setMensaje( ex.getMessage() );
//            encuestasRutaResponse.setClaveError( "" + 1999 );
//        }
//
//        return encuestasRutaResponse;
//    }
//
//
//    public CatalogoMotivoRetiroResponse realizarPeticionCatalogoMotivoRetiro(){
//        LogUtil.printLog( CLASSNAME , "realizarPeticionCatalogoMotivoRetiro .." );
//        HttpClient httpClient = new DefaultHttpClient();
//        HttpGet httpGet =
//                new HttpGet(URL_CATALOGO_MOTIVO_RETIRO  );
//        httpGet.setHeader("content-type", "application/json");
//        CatalogoMotivoRetiroResponse catalogoMotivoRetiroResponse = null;
//        try{
//            LogUtil.printLog(CLASSNAME, "antes  del jsonnn = " + httpGet.getURI().toString());
//            HttpResponse resp = httpClient.execute(httpGet);
//
//            String respStr = EntityUtils.toString(resp.getEntity());
//            if(Const.Enviroment.currentEnviroment == Const.Enviroment.FAKE ){
//                //respStr = "{\"claveError\":\"\",\"mensaje\":\"\",\"seEjecutoConExito\":true,\"catalogoMotivoRetiro\":[]}";
//                //respStr = "{\"claveError\":\"199\",\"mensaje\":\"No fue posible obtener el catalogo de motivos\",\"seEjecutoConExito\":false,\"catalogoMotivoRetiro\":null}";
//                respStr = "{\"claveError\":\"\",\"mensaje\":\"\",\"seEjecutoConExito\":true,\"catalogoMotivoRetiro\":[{\"descripcionMotivoRetiro\":\"El jefe no se encuentra\",\"idMotivoRetiro\":1},{\"descripcionMotivoRetiro\":\"Cambio de ruta\",\"idMotivoRetiro\":2},{\"descripcionMotivoRetiro\":\"El jefe no quiere firmar por querer más tiempo de apoyo en tienda\",\"idMotivoRetiro\":3},{\"descripcionMotivoRetiro\":\"Otro\",\"idMotivoRetiro\":999}]}";
//            }
//            LogUtil.printLog(CLASSNAME ,"Respuesta del jsonnn = " + respStr);
//
//            catalogoMotivoRetiroResponse = new CatalogoMotivoRetiroResponse();
//            catalogoMotivoRetiroResponse = Util.parseJson( respStr  , CatalogoMotivoRetiroResponse.class );
//            LogUtil.printLog(CLASSNAME ,"Respuesta del realizarPeticionCatalogoMotivoRetiro = " + catalogoMotivoRetiroResponse );
//            return catalogoMotivoRetiroResponse;
//        }
//        catch(Exception ex)
//        {
//            LogUtil.logError(CLASSNAME, "Error de consumo de JSON " + ex.getMessage());
//            catalogoMotivoRetiroResponse = new  CatalogoMotivoRetiroResponse ();
//            catalogoMotivoRetiroResponse.setSeEjecutoConExito( false );
//            catalogoMotivoRetiroResponse.setMensaje( ex.getMessage() );
//            catalogoMotivoRetiroResponse.setClaveError( "" + 1999 );
//        }
//
//        return catalogoMotivoRetiroResponse;
//
//    }
//

    public CheckInFarmaciaResponse realizarCheckinPost(CheckInFarmaciaRest checkInFarmaciaRest){
        LogUtil.printLog( CLASSNAME , "realizarCheckinPost  checkInFramacia:" + checkInFarmaciaRest );
        CheckInFarmaciaRequest request = new CheckInFarmaciaRequest();
        request.setCheckInFarmacia(checkInFarmaciaRest);

        HttpClient httpClient = new DefaultHttpClient();
        HttpPost httpPost =
                new HttpPost( URL_CHECKIN );
        httpPost.setHeader("content-type", "application/json; charset=utf-8");
        CheckInFarmaciaResponse response = null;
        try{

            String jsonStringRequest = Util.getStringJSON( request );
            jsonStringRequest = StringUtils.removerCaracteresNoASCII(jsonStringRequest);
            //jsonStringRequest = URLEncoder.encode(jsonStringRequest, "UTF-8");

            StringEntity stringEntity = new StringEntity( jsonStringRequest );
            httpPost.setEntity( stringEntity );

            LogUtil.printLog(CLASSNAME, "antes  del jsonnn = " + httpPost.getURI().toString());
            LogUtil.printLog(CLASSNAME, "StringEntity = " + jsonStringRequest );

            HttpResponse resp = httpClient.execute(httpPost);

            String respStr = EntityUtils.toString(resp.getEntity());
            if( Const.Enviroment.currentEnviroment == Const.Enviroment.FAKE ){
                //respStr = "{\"hacerCheckInFarmaciaResult\":{\"claveError\":\"\",\"mensaje\":\"\",\"seEjecutoConExito\":true}}";
//                respStr = "{\"claveError\":\"\",\"mensaje\":\"\",\"seEjecutoConExito\":true}";
//                respStr = "{\"hacerCheckInFarmaciaResult\":{\"claveError\":\"666\",\"mensaje\":\"??????????(`_´)????????????.\",\"seEjecutoConExito\":true}}";
            }

            LogUtil.printLog( CLASSNAME , "Respuesta del jsonnn = " + respStr );

             response = new CheckInFarmaciaResponse();

            response = Util.parseJson( respStr  , CheckInFarmaciaResponse.class );

            LogUtil.printLog(CLASSNAME ,"Respuesta del realizarCheckinPost = " + response);

            return response;
        }
        catch(Exception ex)
        {
            LogUtil.logError(CLASSNAME, "Error de consumo de JSON " + ex.getMessage());
             response = new CheckInFarmaciaResponse();
            Response modelResponse = response.getHacerCheckInFarmaciaResult();
            modelResponse.setSeEjecutoConExito( false );
            modelResponse.setMensaje( ex.getMessage() );
            modelResponse.setClaveError( "" + 1999 );


        }

        return response;
    }

    public CheckOutFarmaciaResponse realizarCheckOutPost(CheckOutFarmaciaRest checkOutFarmacia){
        LogUtil.printLog(CLASSNAME, "realizarCheckOutPost");
        CheckOutFarmaciaRequest request = new CheckOutFarmaciaRequest();
        request.setCheckOutFarmacia(checkOutFarmacia);
        HttpClient httpClient = new DefaultHttpClient();
        HttpPost httpPost =
                new HttpPost( URL_CHECKOUT );
        httpPost.setHeader("content-type", "application/json; charset=utf-8");
        CheckOutFarmaciaResponse response = null;

        try{

                String jsonStringRequest = Util.getStringJSON( request );

            jsonStringRequest = StringUtils.removerCaracteresNoASCII(jsonStringRequest);
            //jsonStringRequest = URLEncoder.encode(jsonStringRequest, "UTF-8");


            StringEntity stringEntity = new StringEntity( jsonStringRequest );
            httpPost.setEntity( stringEntity );

            LogUtil.printLog(CLASSNAME, "StringEntity = " + jsonStringRequest );
            LogUtil.printLog( CLASSNAME , "antes  del jsonnn = " + httpPost.getURI().toString() );
            HttpResponse resp = httpClient.execute(httpPost);

            String respStr = EntityUtils.toString(resp.getEntity());
            if(Const.Enviroment.currentEnviroment == Const.Enviroment.FAKE ){
//                respStr = "{\"claveError\":\"\",\"mensaje\":\"\",\"seEjecutoConExito\":true}";
//                respStr = "{\"claveError\":100,\"mensaje\":\"Error al intentar realizar el checkout mam\",\"seEjecutoConExito\":false}";
//                respStr = "{\"hacerCheckOutFarmaciaResult\":{\"claveError\":\"666\",\"mensaje\":\"??????????(`_´)????????????.\",\"seEjecutoConExito\":true}}";
            }

            LogUtil.printLog( CLASSNAME , "Respuesta del jsonnn = " + respStr );

            response = new CheckOutFarmaciaResponse();

            response = Util.parseJson( respStr  , CheckOutFarmaciaResponse.class );

            LogUtil.printLog(CLASSNAME ,"Respuesta del realizarCheckOutPost = " + response);

            return response;
        }
        catch(Exception ex)
        {
            LogUtil.logError(CLASSNAME, "Error de consumo de JSON " + ex.getMessage());
            response = new CheckOutFarmaciaResponse();
            Response modelResponse = response.getHacerCheckOutFarmaciaResult();
            modelResponse.setSeEjecutoConExito( false );
            modelResponse.setMensaje( ex.getMessage() );
            modelResponse.setClaveError( "" + 1999 );
        }

        return response;
    }

}
