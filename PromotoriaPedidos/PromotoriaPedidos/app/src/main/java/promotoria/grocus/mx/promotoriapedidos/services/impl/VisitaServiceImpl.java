package promotoria.grocus.mx.promotoriapedidos.services.impl;

import android.content.Context;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import promotoria.grocus.mx.promotoriapedidos.PromotoriaPedidosApp;
import promotoria.grocus.mx.promotoriapedidos.business.Medicamento;
import promotoria.grocus.mx.promotoriapedidos.business.Promotor;
import promotoria.grocus.mx.promotoriapedidos.business.Ruta;
import promotoria.grocus.mx.promotoriapedidos.business.Visita;
import promotoria.grocus.mx.promotoriapedidos.business.rest.Get.EncuestaVisitaResponse;
import promotoria.grocus.mx.promotoriapedidos.business.rest.Get.ProductosPorFarmaciaResponse;
import promotoria.grocus.mx.promotoriapedidos.business.rest.Get.RutaPromotorResponse;
import promotoria.grocus.mx.promotoriapedidos.business.rest.Get.RutaRest;
import promotoria.grocus.mx.promotoriapedidos.business.rest.Get.VisitaRest;
import promotoria.grocus.mx.promotoriapedidos.business.rest.Post.CheckInFarmaciaResponse;
import promotoria.grocus.mx.promotoriapedidos.business.rest.Post.CheckInFarmaciaRest;
import promotoria.grocus.mx.promotoriapedidos.business.rest.Post.CheckOutFarmaciaResponse;
import promotoria.grocus.mx.promotoriapedidos.business.rest.Post.CheckOutFarmaciaRest;
import promotoria.grocus.mx.promotoriapedidos.business.rest.Post.DetalleRespuestasRest;
import promotoria.grocus.mx.promotoriapedidos.business.rest.Post.EncuestaRespuesta;
import promotoria.grocus.mx.promotoriapedidos.business.rest.Post.ListadoDetalle;
import promotoria.grocus.mx.promotoriapedidos.business.rest.Post.PedidoFarmacia;
import promotoria.grocus.mx.promotoriapedidos.business.rest.Response;
import promotoria.grocus.mx.promotoriapedidos.business.utils.EstatusVisita;
import promotoria.grocus.mx.promotoriapedidos.business.utils.Json;
import promotoria.grocus.mx.promotoriapedidos.business.utils.RespuestaBinaria;
import promotoria.grocus.mx.promotoriapedidos.dao.EncuestaDao;
import promotoria.grocus.mx.promotoriapedidos.dao.MedicamentoDao;
import promotoria.grocus.mx.promotoriapedidos.dao.RutaDao;
import promotoria.grocus.mx.promotoriapedidos.dao.VisitaDao;
import promotoria.grocus.mx.promotoriapedidos.dao.impl.EncuestaDaoImpl;
import promotoria.grocus.mx.promotoriapedidos.dao.impl.MedicamentoDaoImpl;
import promotoria.grocus.mx.promotoriapedidos.dao.impl.RutaDaoImpl;
import promotoria.grocus.mx.promotoriapedidos.dao.impl.VisitaDaoImpl;
import promotoria.grocus.mx.promotoriapedidos.services.CatalogosService;
import promotoria.grocus.mx.promotoriapedidos.services.JsonService;
import promotoria.grocus.mx.promotoriapedidos.services.LocationService;
import promotoria.grocus.mx.promotoriapedidos.services.RutaService;
import promotoria.grocus.mx.promotoriapedidos.services.VisitaService;
import promotoria.grocus.mx.promotoriapedidos.utils.Const;
import promotoria.grocus.mx.promotoriapedidos.utils.LogUtil;
import promotoria.grocus.mx.promotoriapedidos.utils.PreguntaRespuesta;
import promotoria.grocus.mx.promotoriapedidos.utils.StringUtils;
import promotoria.grocus.mx.promotoriapedidos.utils.Util;
import promotoria.grocus.mx.promotoriapedidos.utils.UtilLocation;
import promotoria.grocus.mx.promotoriapedidos.utils.ViewUtil;


/**
 * Created by MAMM on 19/04/15.
 */
public class VisitaServiceImpl implements VisitaService {
    private static final String CLASSNAME = VisitaServiceImpl.class.getSimpleName();

    private static VisitaService visitaService;
    private CatalogosService catalogosService;
    private LocationService locationService;
//    private RutaService rutaService;
    private JsonService jsonService;
    private RutaService rutaService;

    private RutaDao rutaDao;
    private VisitaDao visitaDao;
    private MedicamentoDao medicamentosDao;
    private EncuestaDao encuestaDao;


    private Context context;
    private Ruta rutaActual;
    private Visita visitaActual;




    public VisitaServiceImpl(Context context){
        this.context = context;
        this.locationService = LocationServiceImpl.getSingleton();
        this.catalogosService = CatalogosServiceImpl.getSingleton();
        this.jsonService = JsonServiceImpl.getSingleton();
        this.rutaService = RutaServiceImpl.getSingleton();
        this.rutaDao = RutaDaoImpl.getSingleton();
        this.encuestaDao = EncuestaDaoImpl.getSingleton();
        this.visitaDao = VisitaDaoImpl.getSingleton();
        this.medicamentosDao = MedicamentoDaoImpl.getSingleton();
        this.rutaActual = new Ruta();
    }

    public static VisitaService getSingleton(){
        if( visitaService == null ){
            visitaService = new VisitaServiceImpl( PromotoriaPedidosApp.getCurrentApplication() );
        }
        return visitaService;
    }



    public void recuperarRuta( Promotor promotor ) {
        LogUtil.printLog(CLASSNAME, "recuperarRuta promotor:" + promotor);
        Ruta ruta = null;

        if(Const.Enviroment.currentEnviroment == Const.Enviroment.MOCK ){
            ruta = this.prepararRutaMock( promotor );
            this.rutaActual = ruta;
            this.testDaoMock( ruta);
        }else{
            RutaPromotorResponse rutaPromotorResponse = jsonService.realizarPeticionObtenerRutaPromotor(promotor.getUsuario());
            if ( !rutaPromotorResponse.isSeEjecutoConExito() ){
                Json.solicitarMsgError(rutaPromotorResponse, Json.ERROR_JSON.LOGIN);
            } else {
                ruta = parsearRuta( rutaPromotorResponse.getRutaPromotor() , promotor );
//                //INI MAMM siempre que se registre un usuario se actualizan las rutas en base.
                this.rutaActual = this.rutaService.cargarRuta( ruta );
                this.cargarCatalogos(promotor);
//                //END MAMM
            }
        }
    }

    public void cargarCatalogos( Promotor promotor ) {
        LogUtil.printLog(CLASSNAME, "cargarCatalogos:");

        if (Const.Enviroment.currentEnviroment == Const.Enviroment.MOCK) {
            //Cuando se es mock no se actualizan
        } else {
            List<Integer> idFarmacias = this.obtenerSoloIdFarmaciasDeLaRutaActual();
            int idRuta = Integer.parseInt( this.rutaActual.getIdRuta() );
            ProductosPorFarmaciaResponse productoPorFarmaciaResponse = this.catalogosService.cargarTodosLosProductosDesdeWeb(idFarmacias);
            if (!productoPorFarmaciaResponse.isSeEjecutoConExito()) {
                Json.solicitarMsgError(productoPorFarmaciaResponse, Json.ERROR_JSON.LOGIN);
            } else {
                EncuestaVisitaResponse encuestaVisitaResponse = this.catalogosService.cargarTodasLasEncuestasDesdeWeb( idRuta );
                if (!encuestaVisitaResponse.isSeEjecutoConExito()) {
                    Json.solicitarMsgError(encuestaVisitaResponse, Json.ERROR_JSON.LOGIN);
                } else {
                    //Actualizar los catalogos
                    this.catalogosService.actualizarCatalogosEnBase();
                }
            }
        }
    }

    private List<Integer> obtenerSoloIdFarmaciasDeLaRutaActual() {
        Visita[] visitas = this.rutaActual.getVisitas();
        List<Integer> idFarmacias = new ArrayList<Integer>();
        for( Visita itemVisita : visitas ){
            idFarmacias.add( Integer.parseInt(itemVisita.getIdFarmacia() ) );
        }
        return idFarmacias;
    }


    public void guardarEncuesta( PreguntaRespuesta[] preguntasRespuestas ){
        int idVisita = Integer.parseInt(visitaActual.getIdVisita());
        this.encuestaDao.deleteEncuestaByIdVisita(idVisita);
        this.encuestaDao.insertEncuesta( preguntasRespuestas , this.visitaActual);
        this.visitaActual.setPreguntaRespuesta( this.encuestaDao.getEncuestasByIdVisita( idVisita ) );
    }
//
//
//
//
//    public void recuperarMotivosDeRetiro( ) {
//        LogUtil.printLog( CLASSNAME , "recuperarMotivosDeRetiro:"  );
//        Ruta ruta = null;
//
//        if(Const.Enviroment.currentEnviroment == Const.Enviroment.MOCK ){
//            this.catalogoMotivoRetiro  = this.prepararCatalogoMotivoRetiroMock( );
//        }else{
//            CatalogoMotivoRetiroResponse catalogoMotivoRetiroResponse = jsonService.realizarPeticionCatalogoMotivoRetiro( );
//            if ( !catalogoMotivoRetiroResponse.isSeEjecutoConExito() ){
//                Json.solicitarMsgError( catalogoMotivoRetiroResponse, Json.ERROR_JSON.LOGIN );
//            } else {
//                this.catalogoMotivoRetiro = catalogoMotivoRetiroResponse.getCatalogoMotivoRetiro();
//                //INI MAMM Se ordenan los motivos
//                Collections.sort(this.catalogoMotivoRetiro, new Comparator<MotivoRetiro>() {
//                    @Override
//                    public int compare(MotivoRetiro p1, MotivoRetiro p2) {
//                        return p1.getIdMotivoRetiro() - p2.getIdMotivoRetiro(); // Ascending
//                    }
//                });
//                //END MAMM
//            }
//        }
//    }
//
//    private List<MotivoRetiro> prepararCatalogoMotivoRetiroMock() {
//        List<MotivoRetiro> catMotivoRetiro = new ArrayList<MotivoRetiro>();
//        for( int j = 0; j < 5 ; j++ ){
//            MotivoRetiro motivo = new MotivoRetiro();
//            motivo.setIdMotivoRetiro( j + 1 );
//            motivo.setDescripcionMotivoDescartado("Motivo de retiro " + (j + 1));
//            catMotivoRetiro.add( motivo );
//        }
//        MotivoRetiro motivoOtro = new MotivoRetiro();
//        motivoOtro.setIdMotivoRetiro(Const.ID_MOTIVO_RETIRO_OTRO);
//        motivoOtro.setDescripcionMotivoDescartado( "Otro" );
//        catMotivoRetiro.add( motivoOtro );
//        return catMotivoRetiro;
//    }
//
//    private boolean actualizarRutaSiExisteCambio(Ruta ruta , Promotor promotorLogueado) {
//        boolean actualizarRuta = false;
//        Promotor promotorActual = (PromotorServiceImpl.getSingleton()).getPromotorActual();
//        if( promotorActual == null  ){
//            LogUtil.printLog( CLASSNAME , "No se tiene registros previos de promotor, actualizarRuta = true" );
//            actualizarRuta = true;
//        }else
//        if( promotorActual.getClavePromotor().equals( promotorLogueado.getClavePromotor() ) == false ){
//            LogUtil.printLog( CLASSNAME , "Cambia de promotor registrado, actualizarRuta = true" );
//            actualizarRuta = true;
//        }else{
//            LogUtil.printLog( CLASSNAME , "Se vuelve a loggear el promotor, se valida para actualizar la ruta" );
//            if( this.rutaActual.getIdRuta().equals( ruta.getIdRuta()) == true ){
//                if( this.rutaActual.getFechaUltimaModificacion().equals( ruta.getFechaUltimaModificacion()) == false){
//                    LogUtil.printLog( CLASSNAME , "La ruta del día sufrio un cambio, actualizarRuta = true" );
//                    actualizarRuta = true;
//                }else{  /* SI NO HAY CAMBIO EN LA RUTA NO SE REALIZA LA ACTUALIZACION*/
//                    LogUtil.printLog( CLASSNAME , "La ruta del día no ha sufrido cambio, actualizarRuta = false" );
//                }
//            }else{
//                    LogUtil.printLog( CLASSNAME , "Cambia el idRuta, actualizarRuta = true" );
//                    actualizarRuta = true;
//            }
//        }
//        return actualizarRuta;
//    }
//
//    private void armarMapaDeCadenasEnRuta() {
//        Visita[] visitas = rutaActual.getVisitas();
//        this.cadenasAplicadasEnRuta = new HashSet< Cadena>();
//        for( Visita itemVisita : visitas ){
//            this.cadenasAplicadasEnRuta.add( itemVisita.getCadena() );
//        }
//    }

    private Ruta parsearRuta( RutaRest rutasPromotor , Promotor promotor){

        Ruta ruta = new Ruta();
        ruta.setIdRuta( "" + rutasPromotor.getIdRuta() );
        ruta.setFechaCreacionString( rutasPromotor.getFechaCreacion() );
        ruta.setFechaUltimaModificacion(rutasPromotor.getFechaUltimaModificacion());
        ruta.setFechaProgramadaString(rutasPromotor.getFechaProgramada());

        ruta.setPromotor( promotor );
        if( rutasPromotor.getVisitas().length > 0 ){
            ruta.setVisitas( this.parsearVisitasDesdeResponse( rutasPromotor.getVisitas() ) );
        }
        return ruta;
    }
//
    private Visita[] parsearVisitasDesdeResponse( VisitaRest[] visitasRest) {
        int size = visitasRest.length;
        Visita[] arrayvisitas = new Visita[ size ];
        for( int j = 0; j < size;j++){
            Visita v = new Visita();
            VisitaRest vResponse = visitasRest[j];
            v.setIdVisita("" + vResponse.getIdRutaDet());

            v.setIdEncuesta("" + vResponse.getIdEncuesta());
            v.setAplicarEncuesta(vResponse.getIdEncuesta() > 0 ? RespuestaBinaria.SI : RespuestaBinaria.NO);
            v.setEstatusVisita(this.obtenerEstatusEnMovil(vResponse.getIdEstatus()));
            v.setIdFarmacia("" + vResponse.getFarmacia().getIdFarmacia());
            v.setNombreFarmacia(vResponse.getFarmacia().getNombre());
            v.setDireccionFarmacia(vResponse.getFarmacia().getDomicilio());

            arrayvisitas[j] = v;
        }
        return arrayvisitas;
    }
//
    private EstatusVisita obtenerEstatusEnMovil(int idEstatus) {
        EstatusVisita estatusVisita = null;
        if( idEstatus > 0 ){
            switch ( idEstatus ) {
                case EstatusVisita.EN_RUTA_ID_WEB:
                    estatusVisita = EstatusVisita.EN_RUTA;
                    break;
                case EstatusVisita.CHECK_IN_ID_WEB:
                    estatusVisita = EstatusVisita.CHECK_IN;
                    break;
                case EstatusVisita.CHECK_OUT_INCOMPLETO_ID_WEB:
                    estatusVisita = EstatusVisita.CHECK_OUT;
                    break;
                case EstatusVisita.CHECK_OUT_COMPLETO_ID_WEB:
                    estatusVisita = EstatusVisita.CHECK_OUT;
                    break;
                case EstatusVisita.CANCELADA_ID_WEB:
                    estatusVisita = EstatusVisita.CANCELADA;
                    break;
                case EstatusVisita.NO_VISITADA_ID_WEB:
                    estatusVisita = EstatusVisita.NO_VISITADA;
                    break;
                default:
                    break;
            }
        }
        return estatusVisita;
    }
//
//    private Cadena parsearCadenaDesdeVisitaTiendaResponse(TiendaVisita tiendaResponse) {
//        Cadena c = new Cadena();
//        CadenaTienda ctResponse = tiendaResponse.getCadenaTienda();
//        c.setIdCadena( "" + ctResponse.getIdCadena() );
//        c.setNombreCadena(ctResponse.getNombre());
//        return c;
//    }
//
//    private Tienda parsearTiendaDesdeVisitaTiendaResponse( TiendaVisita tiendaResponse ){
//        Tienda tienda = new Tienda();
//        tienda.setIdTienda( "" + tiendaResponse.getIdTienda() );
//        tienda.setNombreTienda( tiendaResponse.getNombre() );
//        tienda.setTelefono(  tiendaResponse.getTelefono() );
//
//        Direccion dir = new Direccion();
//        dir.setCalle( tiendaResponse.getCalle() );
//        dir.setNumeroExterior(tiendaResponse.getNumeroExterior());
//        dir.setNumeroInterior(tiendaResponse.getNumeroInterior());
//        dir.setCodigoPostal(tiendaResponse.getCodigoPostal());
//        dir.setColonia(tiendaResponse.getColonia());
//        dir.setDelegacion(tiendaResponse.getDelegacionMunicpio());
//        dir.setEstado(tiendaResponse.getEstado());
//        dir.setPais("México");
//        dir.setReferencia(tiendaResponse.getReferencia());
//        tienda.setDireccion( dir );
//
//        UtilLocation loc = new UtilLocation();
//        loc.setLatitud( "" + tiendaResponse.getLatitud() );
//        loc.setLongitud("" + tiendaResponse.getLongitud());
//        tienda.setLocation(loc);
//
//        return tienda;
//    }
//
//
    private Ruta prepararRutaMock(Promotor promotor) {
        LogUtil.printLog( CLASSNAME , "Inicia prepararRutaMock" );
        Ruta ruta = new Ruta();
        ruta.setPromotor( promotor );
        ruta.setFechaProgramadaString(Util.getDateTimeFromMilis(new Date().getTime()));
        ruta.setFechaCreacionString(Util.getDateTimeFromMilis(new Date().getTime() + 150000));
        ruta.setFechaUltimaModificacion(Util.getDateTimeFromMilis(new Date().getTime() + 300000));
        ruta.setIdRuta("100001");
        Visita[] visitas = this.crearVisitasMock();
        ruta.setVisitas( visitas );
        LogUtil.printLog( CLASSNAME , " Ruta:"  + ruta);
        return ruta;
    }

    private Visita[] crearVisitasMock(){
        int numeroVisitas = 10;
        Visita[] visitas = new Visita[ numeroVisitas ];
        for( int j = 0 ; j < numeroVisitas ; j++ ){
            Visita visita = new Visita();
            visita.setIdVisita( "" + j );
            if(j < 8 ){
                if(j == 1 ){ visita.setEstatusVisita(EstatusVisita.EN_RUTA);}
                if(j == 2 ){ visita.setEstatusVisita(EstatusVisita.CHECK_IN);}
                if(j == 3 ){ visita.setEstatusVisita(EstatusVisita.CHECK_OUT);}
                if(j == 4 ){ visita.setEstatusVisita(EstatusVisita.CHECK_OUT_REQUEST);}
                if(j == 5 ){ visita.setEstatusVisita(EstatusVisita.CANCELADA);}
                if(j == 6 ){ visita.setEstatusVisita(EstatusVisita.NO_VISITADA);}
                if(j == 7 ){ visita.setEstatusVisita(EstatusVisita.NO_ENVIADO);}
            }else{
                visita.setEstatusVisita(EstatusVisita.EN_RUTA);
            }
            Medicamento[] medicamentos = this.crearMedicamentosMock( "" + (j + 1) );
            visita.setMedicamentos( medicamentos );
            visita.setNombreFarmacia("Farmacias Similares " + (j + 1));
            visita.setDireccionFarmacia("Avenida " + (j + 1) + " Colonia Campestre Delgacion Miguel Hidalgo, C.P. 095732 Mexico DF");
            visitas[j] = visita;
        }
        return visitas;
    }


    private Medicamento[] crearMedicamentosMock( String idVisita ){
        int size= 12;
        Medicamento[] medicamentos = new Medicamento[size];
        for( int j = 0; j < size ; j++){
            Medicamento medicamento = new Medicamento();
            medicamento.setIdMedicamento( "" + (j+1) );
            medicamento.setNombreMedicamento( "Medicamento de alto impacto 239grm" + (j+1) );
            medicamento.setMaximoPermitido((j % 2) + 3);
            medicamento.setPrecio( 9900 );
            medicamento.setCantidad( (j % 2) + 1 );
            medicamentos[j] = medicamento;

        }
        return medicamentos;
    }


//    private RevisionProducto[] crearProductosMock(String idVisita ) {
//        int numeroProductoInicial = 3;
//        RevisionProducto[] rpArray = new RevisionProducto[ numeroProductoInicial ];
//        for( int j = 1 ; j <= numeroProductoInicial ; j++ ){
//            RevisionProducto rp = new RevisionProducto();
//            Producto p = new Producto();
//            p.setCodigo("Prod 0" + j);
//            p.setDescripcion( "Producto especial básico " + j );
//            p.setPrecioBase(j * 100);
//            rp.setProducto( p );
//            rp.setExistencia( 5 );
//            rp.setPrecioEnTienda( j * 100 );
//            rp.setExhibicionAdicional( RespuestaBinaria.SI );
//            rp.setNumeroFrente( 4 );
//            rpArray[j - 1] = rp;
//        }
//
//        return rpArray;
//    }
//
//
//    private RevisionFoto[] crearRervisionFotosMock(String idVisita ) {
//        int numeroFotosIniciales = 3;
//        RevisionFoto[] rfArray = new RevisionFoto[ numeroFotosIniciales ];
//        for( int j = 1 ; j <= numeroFotosIniciales ; j++ ){
//            RevisionFoto rf = new RevisionFoto();
//            long milisec = j*5000;
//            rf.setIdFoto( Util.getDateTimeFromMilis_hastaSegundos( new Date().getTime() + milisec )  );
//            rf.setFechaCaptura( "2015/04/20 12:34" );
//            rf.setFoto(UtilsMock.getImageMock( this.context ) );
//            rfArray[j - 1] = rf;
//        }
//
//        return rfArray;
//    }
//
//    private EncuestaPersona[] crearEncuestaPersonaMock(String idEncuesta) {
//        Encuesta encuesta = this.encuestaService.recuperarEncuestaPorId(idEncuesta);
//        Pregunta[] preguntas = encuesta.getPreguntasEncuesta();
//
//        int numPersonasEncuestadas = 3;
//        EncuestaPersona[] encuestaPersonas = new EncuestaPersona[ numPersonasEncuestadas ];
//
//        for( int j = 1 ; j <= numPersonasEncuestadas ; j++ ){
//            EncuestaPersona encuestaPersona = new EncuestaPersona();
//            PreguntaRespuesta[] preguntaRespuesta = new PreguntaRespuesta[ preguntas.length ];
//
//
//            for( int i = 0 ; i < preguntas.length ; i++ ){
//                PreguntaRespuesta pr = new PreguntaRespuesta();
//                pr.setPregunta( preguntas[i] );
//                pr.setRespuestaElegida( preguntas[i].getRespuestasPregunta()[0]);
//                preguntaRespuesta[ i ] = pr;
//            }
//
//            encuestaPersona.setPreguntaRespuesta( preguntaRespuesta );
//            Persona persona = new Persona();
//            persona.setNombre( "Persona " + j );
//            encuestaPersona.setPersona(persona);
//            encuestaPersonas[j - 1 ] = encuestaPersona;
//        }
//
//        return encuestaPersonas;
//    }
//
//    private Tienda crearTiendaMock( int item ){
//        Tienda tienda = new Tienda();
//        tienda.setIdTienda( "" + item );
//        tienda.setNombreTienda( "Comercializadora Suc. 0" + item );
//        tienda.setTelefono( "5512000" + item);
//
//        Direccion dir = new Direccion();
//        dir.setCalle( "Calle " + item );
//        dir.setNumeroExterior("10" + item);
//        dir.setNumeroInterior( "Interior 1" + item );
//        dir.setCodigoPostal( "0660" + item );
//        dir.setColonia( "Colonia "+ item );
//        dir.setDelegacion( "Cuauhtemoc" );
//        dir.setEstado( "Distrito Federal" );
//        dir.setPais( "México" );
//        dir.setReferencia( "Frente al oxxo #0" + item );
//        tienda.setDireccion( dir );
//
//        UtilLocation loc = new UtilLocation();
//        loc.setLatitud( "19.43260" );
//        loc.setLongitud("-99.13320");
//        tienda.setLocation(loc);
//
//        return tienda;
//    }

    public Ruta getRutaActual() {
        return rutaActual;
    }

    public Response realizarCheckIn( Visita visita  ){
        Response response = null;
        CheckInFarmaciaRest checkIn = armarCheckIn(visita);
        CheckInFarmaciaResponse CheckInResponse =  this.jsonService.realizarCheckinPost(checkIn);

        visita.setEstatusVisita(EstatusVisita.CHECK_IN);

            if ( !CheckInResponse.getHacerCheckInFarmaciaResult().isSeEjecutoConExito() ){
//                Json.solicitarMsgError(CheckInResponse.getHacerCheckInFarmaciaResult(), Json.ERROR_JSON.CHECK_IN);
//                response =  CheckInResponse.getHacerCheckInFarmaciaResult();
                visita.setCheckInNotificado(RespuestaBinaria.NO);
            } else {
                visita.setFechaCheckIn(Util.getDateTimeFromMilis(new Date().getTime()));
                visita.setCheckInNotificado(RespuestaBinaria.SI);
                //this.visitaDao.updateVisita(visita, Integer.parseInt(this.rutaActual.getIdRuta()));
                //this.actualizarVisitaActual();
                //this.rutaActual = this.rutaService.refrescarRutaDesdeBase( this.rutaActual );
            }
        this.visitaDao.updateVisita(visita, Integer.parseInt(this.rutaActual.getIdRuta()));
        this.rutaActual = this.rutaService.refrescarRutaDesdeBase( this.rutaActual );

        return response;
    }


    private Response realizarCheckInEnProcesoCheckOut( Visita visita  ){
        Response response = null;
        CheckInFarmaciaRest checkIn = armarCheckIn(visita);
        CheckInFarmaciaResponse CheckInResponse =  this.jsonService.realizarCheckinPost(checkIn);

        visita.setEstatusVisita(EstatusVisita.CHECK_IN);

        if ( !CheckInResponse.getHacerCheckInFarmaciaResult().isSeEjecutoConExito() ){
                Json.solicitarMsgError(CheckInResponse.getHacerCheckInFarmaciaResult(), Json.ERROR_JSON.CHECK_OUT);
                response =  CheckInResponse.getHacerCheckInFarmaciaResult();
//            ViewUtil.mostrarMensajeRapido(this.context, "El Check-In no se pudo enviar");
            visita.setCheckInNotificado(RespuestaBinaria.NO);
        } else {
            visita.setFechaCheckIn(Util.getDateTimeFromMilis(new Date().getTime()));
            visita.setCheckInNotificado(RespuestaBinaria.SI);
            this.visitaDao.updateVisita(visita, Integer.parseInt(this.rutaActual.getIdRuta()));
            this.rutaActual = this.rutaService.refrescarRutaDesdeBase( this.rutaActual );

        }

        return response;
    }

    private CheckInFarmaciaRest armarCheckIn( Visita visita ){
        Promotor promotor = PromotorServiceImpl.getSingleton().getPromotorActual();
        CheckInFarmaciaRest checkIn = new CheckInFarmaciaRest();

        LogUtil.printLog( CLASSNAME , "realizarCheckIn visita:" + visita + ", Promotor:" + promotor );

        if( Const.Enviroment.currentEnviroment == Const.Enviroment.MOCK ){
            visita.setFechaCheckIn( Util.getDateTimeFromMilis( new Date().getTime() ) )   ;
            visita.setEstatusVisita( EstatusVisita.CHECK_IN );
        }else{
            checkIn.setClavePromotor(promotor.getClavePromotor());
            checkIn.setIdRutaDetalle(Integer.parseInt(visita.getIdVisita()));
            checkIn.setFechaCheckIn(Util.getDateTimeFromMilis(new Date().getTime()));
            checkIn.setIdEstatus(EstatusVisita.CHECK_IN.getIdEstatus());
            checkIn.setLatitud("" + this.locationService.getLatitude());
            checkIn.setLongitud("" + this.locationService.getLongitude());

            LogUtil.printLog(CLASSNAME, "checkIn:" + checkIn);


        }
        return checkIn;
    }

    public Response realizarCheckOut( Visita visita ){
        LogUtil.printLog( CLASSNAME , "realizarCheckOut visita:" + visita  );
        Response response = null;

        //Proceso para validar si se requeire realizar el CheckIn
        if( visita.getCheckInNotificado() == RespuestaBinaria.NO ){
            response = this.realizarCheckInEnProcesoCheckOut( visita );
            if( response != null &&
                response.isSeEjecutoConExito() == false ){
                return response;
            } //ELSE Si no existe error se continua con el proceso de CheckOut de manera normal
        }

        Promotor promotor = PromotorServiceImpl.getSingleton().getPromotorActual();
        CheckOutFarmaciaRest checkout = new CheckOutFarmaciaRest();

        checkout.setClavePromotor(promotor.getClavePromotor());
        checkout.setIdRutaDetalle(Integer.parseInt(visita.getIdVisita()));
        checkout.setIdEstatus( EstatusVisita.CHECK_OUT.getIdEstatus() );
        checkout.setFechaCheckIn(Util.getDateTimeFromMilis(new Date().getTime()));
        checkout.setLatitud("" + this.locationService.getLatitude());
        checkout.setLongitud("" + this.locationService.getLongitude());
        checkout.setComentarios( visita.getComentarios() );
        checkout.setPedidoFarmacia(this.armarPedidoFarmacia(visita));
        checkout.setEncuestaRespuesta(this.armarEncuestaRespuesta(visita));

        CheckOutFarmaciaResponse checkOutResponse = this.jsonService.realizarCheckOutPost(checkout);
        if ( !checkOutResponse.getHacerCheckOutFarmaciaResult().isSeEjecutoConExito() ){
            Json.solicitarMsgError( checkOutResponse.getHacerCheckOutFarmaciaResult(), Json.ERROR_JSON.CHECK_OUT );
            response =  checkOutResponse.getHacerCheckOutFarmaciaResult();
            visita.setFechaCheckOut( Util.getDateTimeFromMilis( new Date().getTime() ) );
            visita.setEstatusVisita( EstatusVisita.NO_ENVIADO);
            this.actualizarVisitaActual();
//            this.recuperarRuta(promotor);
        } else {
            visita.setFechaCheckOut(Util.getDateTimeFromMilis(new Date().getTime()));
            visita.setEstatusVisita(EstatusVisita.CHECK_OUT);
            this.actualizarVisitaActual();
            this.limpiarDatosEnMemoriaDeLaVisita(visita);
            this.eliminarRegistrosEnTablasDeLaVisita();
            this.recuperarRuta( promotor );
        }
        return response;
    }

    private PedidoFarmacia armarPedidoFarmacia( Visita visita ){
        PedidoFarmacia pfr = new PedidoFarmacia();
        pfr.setEncargado(visita.getGerente().getNombre());
        pfr.setCorreo(visita.getEmail());
        pfr.setFirmaEncargado(ViewUtil.obtenerStringBase64(visita.getFirmaGerente()));
        List<ListadoDetalle> listadoDetalle = this.armarListadoDetalle(visita);
        pfr.setListadoDetalle(listadoDetalle);

        return pfr;
    }

    private EncuestaRespuesta armarEncuestaRespuesta( Visita visita ){
        EncuestaRespuesta err = new EncuestaRespuesta();
        err.setIdEncuesta(Integer.parseInt(visita.getIdEncuesta()));
        List<DetalleRespuestasRest> detalleRespuestasRest = this.armarDetalleRespuestas(visita);
        err.setDetalleRespuestas(detalleRespuestasRest);

        return err;
    }

    private List<ListadoDetalle> armarListadoDetalle( Visita visita){
        List<ListadoDetalle> listadoDetalle = new ArrayList<ListadoDetalle>();
        Medicamento[] medicamentoArray = visita.getMedicamentos();

        for( Medicamento itemMedicamento : medicamentoArray){
            ListadoDetalle listadoMed = new ListadoDetalle();
            listadoMed.setIdProducto( Integer.parseInt( itemMedicamento.getIdMedicamento() ) );
            listadoMed.setCantidad(itemMedicamento.getCantidad());

//            String precioConDecimal = StringUtils.transformarPrecioADecimalConCentavos( "" + itemMedicamento.getPrecio() );
//            String precioEnInteger =  precioConDecimal.replace( "." , "" );

            listadoMed.setPrecioUnitario( itemMedicamento.getPrecio() );
            listadoDetalle.add( listadoMed );
        }

        return listadoDetalle;

    }

    private List<DetalleRespuestasRest> armarDetalleRespuestas( Visita visita){
        List<DetalleRespuestasRest> listDetalleRespuestas = new ArrayList<DetalleRespuestasRest>();
        PreguntaRespuesta[] prArray = visita.getPreguntaRespuesta();

        for( PreguntaRespuesta itempr : prArray){
            DetalleRespuestasRest dr = new DetalleRespuestasRest();

            dr.setIdPregunta(Integer.parseInt(itempr.getPregunta().getIdPregunta()));
            dr.setIdRespuestaSeleccionada(Integer.parseInt(itempr.getRespuestaElegida().getIdRespuesta()));
            listDetalleRespuestas.add( dr );
        }

        return listDetalleRespuestas;

    }
//
//    public Response realizarCheckOut( Visita visita ){
//        LogUtil.printLog( CLASSNAME , "realizarCheckOut visita:" + visita  );
//        Response response = null;
//
//        Promotor promotor = PromotorServiceImpl.getSingleton().getPromotorActual();
//        CheckOutTienda checkout = new CheckOutTienda();
//
//        checkout.setClavePromotor( promotor.getClavePromotor() );
//        checkout.setFechaCreacion( Util.getDateTimeFromMilis( new Date().getTime() ) );
//        checkout.setIdVisita(Integer.parseInt(visita.getIdVisita()));
//
//        Location locationActual = this.locationService.getLocation(); //Se recupera solo para pintar el toast
//        checkout.setLatitud( "" + this.locationService.getLatitude() );
//        checkout.setLongitud("" + this.locationService.getLongitude());
//
//        checkout.setVisitaTienda( this.armarVisitaTienda( visita ) );
//
//
//        CheckOutTiendaResponse checkOutResponse = this.jsonService.realizarCheckOutPost(checkout);
//        if ( !checkOutResponse.getHacerCheckOutTiendaResult().isSeEjecutoConExito() ){
//            Json.solicitarMsgError( checkOutResponse.getHacerCheckOutTiendaResult(), Json.ERROR_JSON.CHECK_OUT );
//            response =  checkOutResponse.getHacerCheckOutTiendaResult();
//            visita.setFechaCheckout( Util.getDateTimeFromMilis( new Date().getTime() ) )   ;
//            visita.setEstatusVisita( EstatusVisita.CHECK_OUT_REQUEST);
//            this.actualizarVisitaActual();
//            this.recuperarRuta( promotor );
//        } else {
//            visita.setFechaCheckout( Util.getDateTimeFromMilis( new Date().getTime() ) )   ;
//            visita.setEstatusVisita( EstatusVisita.CHECK_OUT);
//            this.actualizarVisitaActual();
//            this.limpiarDatosEnMemoriaDeLaVisita(visita);
//            this.eliminarRegistrosEnTablasDeLaVisita( );
//            this.recuperarRuta( promotor );
//        }
//        return response;
//    }
//
    private void limpiarDatosEnMemoriaDeLaVisita( Visita visita) {
        visita.setFirmaGerente( new byte[0]);
        visita.setPreguntaRespuesta(new PreguntaRespuesta[0]);
        Runtime.getRuntime().gc();
    }

    private void eliminarRegistrosEnTablasDeLaVisita( ){
        int idVisitaActual = Integer.parseInt( this.visitaActual.getIdVisita() );
        this.encuestaDao.deleteEncuestaByIdVisita(idVisitaActual);
        this.medicamentosDao.deleteMedicamentoByIdVisita(idVisitaActual);
        //this.visitaDao.deleteVisitaById( idVisitaActual );
    }
//
//    private VisitaTienda armarVisitaTienda(Visita visita) {
//        VisitaTienda vt = new VisitaTienda();
//        vt.setComentarios( visita.getComentarios() );
//        vt.setIdTienda(Integer.parseInt(visita.getTienda().getIdTienda()));
//        vt.setIdVisita( Integer.parseInt(visita.getIdVisita()) );
//        vt.setNombreJefeDepartamento( visita.getGerente().getNombre() );
//        vt.setFirmaJefeDepartamento(ViewUtil.obtenerStringBase64 (visita.getFirmaGerente() ) );
//        List<EntrevistaEncuesta> entrevista = this.armaListaEntrevistaEncuesta( visita );
//        vt.setEntrevistasEncuesta( entrevista );
//
//        List<String> fotos = this.armarListFotosCapturadas( visita );
//        vt.setFotosTienda( fotos );
//
//        List<ProductoTienda> listaProductos = this.armarListaProductos( visita );
//        vt.setProductosTienda( listaProductos );
//
//        //INI MAMM motivo retiro
//        vt.setIdMotivoRetiro( visita.getIdMotivoRetiro() );
//        vt.setDescripcionMotivoDescartado( visita.getDescripcionMotivoDescartado() );
//        //END MAMM motivo retiro
//
//        return vt;
//    }
//
//    private List<ProductoTienda> armarListaProductos(Visita visita) {
//        List<ProductoTienda> listaProductos = new ArrayList<ProductoTienda>();
//        RevisionProducto[] revProductoArray = visita.getRevisionProductos();
//        for( RevisionProducto itemRevProd : revProductoArray ){
//            ProductoTienda pt = new ProductoTienda();
//            pt.setCodigo( itemRevProd.getProducto().getCodigo() );
//            pt.setDescripcion( itemRevProd.getProducto().getDescripcion() );
//            pt.setPrecioBase( itemRevProd.getProducto().getPrecioBase() );
//            pt.setExhibicionAdicional(itemRevProd.getExhibicionAdicional().isBoolRespuesta() );
//            pt.setExistencia( itemRevProd.getExistencia() );
//            pt.setNumeroFrente( itemRevProd.getNumeroFrente() );
//            pt.setPrecioTienda( itemRevProd.getPrecioEnTienda());
//            listaProductos.add( pt );
//        }
//        return listaProductos;
//    }
//
//    private List<String> armarListFotosCapturadas(Visita visita) {
//        List<String> fotos = new ArrayList<String>();
//        RevisionFoto[] revfotosArray = visita.getRevisionFoto();
//        for( RevisionFoto itemFoto : revfotosArray ){
//            fotos.add( ViewUtil.obtenerStringBase64( itemFoto.getFoto() ) );
//        }
//        return fotos;
//    }
//
//
//
//    private List<EntrevistaEncuesta> armaListaEntrevistaEncuesta(Visita visita ) {
//        EncuestaPersona[] encuestaPersona = visita.getEncuestaPersonas();
//        List<EntrevistaEncuesta> eeList = new ArrayList<EntrevistaEncuesta>();
//        for(EncuestaPersona itemEncuestaPersona : encuestaPersona ){
//            EntrevistaEncuesta ee = new EntrevistaEncuesta();
//            ee.setIdEncuesta( Integer.parseInt( visita.getIdEncuesta() )  );
//            PreguntaRespuesta[] pregRespArray = itemEncuestaPersona.getPreguntaRespuesta();
//            List<DetalleRespuesta> detRespList = new ArrayList<DetalleRespuesta>();
//            for( PreguntaRespuesta itemPregResp : pregRespArray){
//                DetalleRespuesta detResp = new DetalleRespuesta();
//                detResp.setIdPregunta( Integer.parseInt( itemPregResp.getPregunta().getIdPregunta() ) );
//                detResp.setIdRespuestaSeleccionada(Integer.parseInt(itemPregResp.getRespuestaElegida().getIdRespuesta()));
//                detRespList.add( detResp );
//            }
//            ee.setDetalleRespuestas( detRespList );
//            eeList.add( ee );
//        }
//        return eeList;
//    }
//
//
//    public Visita recuperarVisitaPorIdVisita(String idVisita){
//        Visita[] visitas = this.rutaActual.getVisitas();
//        Visita visitaBuscada = null;
//        for( Visita item : visitas ){
//            if(item.getIdVisita().equals( idVisita )  ){
//                visitaBuscada = item;
//            }
//        }
//        LogUtil.printLog( CLASSNAME , "recuperarVisitaPorIdVisita: idVisita:" + idVisita + ", visita:" +  visitaBuscada );
//        return visitaBuscada;
//    }
//
//    public Set<Cadena> getCadenasEnRuta(){
//        return this.cadenasAplicadasEnRuta;
//    }
//
//    public List<MotivoRetiro> getCatalogoMotivoRetiro(){
//        return this.catalogoMotivoRetiro;
//    }
//
//
    public void agregarMedicamentosAVisitaActual( Medicamento[] medicamentos ){
        //Se eliminan los medicamentos que se tenian hasta este momento
        Integer idVisita = Integer.parseInt( this.visitaActual.getIdVisita() ) ;
        this.medicamentosDao.deleteMedicamentoByIdVisita( idVisita );
        for( Medicamento itemMedicamento : medicamentos ){
            this.medicamentosDao.insertMedicamento( itemMedicamento , idVisita );
        }
        visitaActual.setMedicamentos( this.medicamentosDao.getMedicamentosByIdVisita( idVisita ) );
    }
//
    @Override
    public Visita getVisitaPorPosicionEnLista(int posicion) {
        Visita visitaMemoria = this.visitaService.getRutaActual().getVisitas()[ posicion ];
        if( Const.Enviroment.currentEnviroment == Const.Enviroment.MOCK ){
            //No se define nada para el mock
            this.visitaActual = visitaMemoria;
        }else{
            //throw  new RuntimeException( "Pendiente implementar los DAOs para recuperar la visita actual" );
            visitaActual =  this.visitaDao.getVisitaById( Integer.parseInt( visitaMemoria.getIdVisita() ) );
            int idVisita = Integer.parseInt(visitaActual.getIdVisita());
            visitaActual.setMedicamentos(this.medicamentosDao.getMedicamentosByIdVisita(idVisita)); ;
            visitaActual.setPreguntaRespuesta(this.encuestaDao.getEncuestasByIdVisita(idVisita));
        }

        return visitaActual;
    }
//
//
    public Visita getVisitaActual() {
        return visitaActual;
    }


    @Override
    public void actualizarVisitaActual( ){
        int idRutaActual = Integer.parseInt( rutaActual.getIdRuta() );
        this.visitaDao.updateVisita( visitaActual , idRutaActual );
    }
//
//
//    @Override
//    public void eliminarRevisionProductoDeVisita(RevisionProducto revisionProductoActual) {
//        int idVisita = Integer.parseInt(visitaActual.getIdVisita());
//        this.productoDao.deleteProductoById( revisionProductoActual.getProducto().getCodigo() , idVisita );
//        visitaActual.setRevisionProductos(this.productoDao.getProductosByIdVisita(idVisita)) ;
//    }
//
//    public void actualizarRevisionProductoAVisitaActual( RevisionProducto revisionProductoActual ){
//        int idVisita = Integer.parseInt(visitaActual.getIdVisita());
//        this.productoDao.updateProducto( revisionProductoActual , idVisita );
//        visitaActual.setRevisionProductos(this.productoDao.getProductosByIdVisita(idVisita)) ;
//    }
//
//    public void guardarRevisionFoto(RevisionFoto revFoto){
//        int idVisita = Integer.parseInt(visitaActual.getIdVisita());
//        this.fotografiaDao.insertFotografia( revFoto , idVisita );
//        visitaActual.setRevisionFoto(this.fotografiaDao.getRevisionFotoByIdVisita( idVisita));
//    }
//
//    public void guardarEncuesta( EncuestaPersona encuestaPersona){
//        int idVisita = Integer.parseInt(visitaActual.getIdVisita());
//        this.encuestaDao.insertEncuesta( encuestaPersona , idVisita );
//        this.visitaActual.setEncuestaPersonas( this.encuestaDao.getEncuestasByIdVisita( idVisita ) );
//    }
//
//
//    @Override
//    public void eliminarRevisionFotografia(  String idRevisionFoto  ) {
//        int idVisita = Integer.parseInt(visitaActual.getIdVisita());
//        this.fotografiaDao.deleteFotoById( idRevisionFoto  , idVisita );
//        visitaActual.setRevisionFoto( this.fotografiaDao.getRevisionFotoByIdVisita(idVisita) ); ;
//    }
//
    private void testDaoMock(Ruta ruta) {
        this.rutaDao.insertRuta( ruta );
        Ruta rutaTemp = this.rutaDao.getRutaById(Integer.parseInt(ruta.getIdRuta()));
        LogUtil.printLog( CLASSNAME , "Se recupera ruta de base:" + rutaTemp );
        Visita[] visitas = ruta.getVisitas();
        for(Visita itemVisita : visitas ){
            this.visitaDao.insertVisita( itemVisita , Integer.parseInt( ruta.getIdRuta() ) );
        }
        Visita[] visitasArray = this.visitaDao.getVisitasByIdRuta( Integer.parseInt( ruta.getIdRuta() ) );
        LogUtil.printLog( CLASSNAME , "Se recupera visitasArray de base:" + visitasArray );
//
        Visita currentVisita = visitas[1];
        Medicamento[] med = currentVisita.getMedicamentos();
        for( Medicamento itemrp : med ){
            this.medicamentosDao.insertMedicamento( itemrp , Integer.parseInt( currentVisita.getIdVisita() ) );
        }
        Medicamento[] medArray = this.medicamentosDao.getMedicamentosByIdVisita(Integer.parseInt(currentVisita.getIdVisita()));
        currentVisita.setMedicamentos( medArray );
        LogUtil.printLog( CLASSNAME , "Se recupera RevisionProducto de base:" + medArray );
//
//        RevisionFoto[] rf = currentVisita.getRevisionFoto();
//        for( RevisionFoto itemRF : rf ){
//            this.fotografiaDao.insertFotografia(itemRF, Integer.parseInt(currentVisita.getIdVisita()));
//        }
//        RevisionFoto[] rfArray = this.fotografiaDao.getRevisionFotoByIdVisita(Integer.parseInt(currentVisita.getIdVisita()));
//        currentVisita.setRevisionFoto(rfArray);
//        LogUtil.printLog( CLASSNAME , "Se recupera RevisionFoto de base:" + rfArray );
//
//        EncuestaPersona[] ep = currentVisita.getEncuestaPersonas();
//        for( EncuestaPersona itemEP : ep ){
//            this.encuestaDao.insertEncuesta(itemEP, Integer.parseInt(currentVisita.getIdVisita()));
//        }
//        EncuestaPersona[] epArray = this.encuestaDao.getEncuestasByIdVisita(Integer.parseInt(currentVisita.getIdVisita()));
//        LogUtil.printLog( CLASSNAME , "Se recupera EncuestaPersona de base:" + epArray );
    }

    public String getTotalDeCompraEnVisitaActual(){
        int total = 0;
        Medicamento[] medicamentos = this.getVisitaActual().getMedicamentos();
        if( medicamentos.length > 0){
            for( Medicamento itemMedicamento : medicamentos ){
                total += itemMedicamento.getCantidad() * itemMedicamento.getPrecio();
            }
        }
        String totalDecimal = StringUtils.transformarPrecio100APrecioDecimal("" + total);
        return totalDecimal;
    }


    public void recuperarRutaDesdeBase( String user , String pass ){
        Ruta rutaEnBase = this.rutaDao.getRutaPorClaveYPasswordDePromotor( user, pass );
        this.rutaActual = this.rutaService.refrescarRutaDesdeBase( rutaEnBase );
    }


}
