package promotoria.grocus.mx.promotoriapedidos.services.impl;

import android.content.Context;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import promotoria.grocus.mx.promotoriapedidos.PromotoriaPedidosApp;

import promotoria.grocus.mx.promotoriapedidos.business.Encuesta;
import promotoria.grocus.mx.promotoriapedidos.business.Medicamento;
import promotoria.grocus.mx.promotoriapedidos.business.rest.Get.EncuestaVisitaResponse;
import promotoria.grocus.mx.promotoriapedidos.business.rest.Get.EncuestasRutaRest;
import promotoria.grocus.mx.promotoriapedidos.business.rest.Get.PreguntasEncuestaRest;
import promotoria.grocus.mx.promotoriapedidos.business.rest.Get.ProductoRest;
import promotoria.grocus.mx.promotoriapedidos.business.rest.Get.ProductosPorFarmaciaResponse;
import promotoria.grocus.mx.promotoriapedidos.business.rest.Get.RespuestasPreguntaRest;
import promotoria.grocus.mx.promotoriapedidos.dao.impl.CatalogoDao;
import promotoria.grocus.mx.promotoriapedidos.dao.impl.CatalogoDaoImpl;
import promotoria.grocus.mx.promotoriapedidos.services.CatalogosService;
import promotoria.grocus.mx.promotoriapedidos.services.JsonService;
import promotoria.grocus.mx.promotoriapedidos.utils.Const;
import promotoria.grocus.mx.promotoriapedidos.utils.Pregunta;
import promotoria.grocus.mx.promotoriapedidos.utils.Respuesta;


/**
 * Created by devmac02 on 16/07/15.
 */
public class CatalogosServiceImpl implements CatalogosService {
    private static final String CLASSNAME = VisitaServiceImpl.class.getSimpleName();

    private static CatalogosService catalogosServices;
    private CatalogoDao catalogoDao;
    private JsonService jsonService;

    private Map<Integer , List<Medicamento>> mapaMedicamentosPorFarmacia;
    private Map<String , Encuesta > mapaEncuestaPorIdEncuesta;

    private Context context;

    public CatalogosServiceImpl(Context context) {
        this.context = context;
        this.jsonService = JsonServiceImpl.getSingleton();
        this.catalogoDao = CatalogoDaoImpl.getSingleton();

    }

    public static CatalogosService getSingleton() {
        if (catalogosServices == null) {
            catalogosServices = new CatalogosServiceImpl(PromotoriaPedidosApp.getCurrentApplication());
        }
        return catalogosServices;
    }

    public ProductosPorFarmaciaResponse cargarTodosLosProductosDesdeWeb( List<Integer> idFarmaciasEnRutaActual){
        ProductosPorFarmaciaResponse productosPorFarmaciaResponse = null;
        mapaMedicamentosPorFarmacia = new HashMap< Integer , List< Medicamento > >();
       for( Integer idFarmacia : idFarmaciasEnRutaActual ){
           productosPorFarmaciaResponse = this.jsonService.realizarPeticionObtenerProductosPorFarmacia(idFarmacia);
           if( productosPorFarmaciaResponse.isSeEjecutoConExito() == false ){
                  //Si por alguna razon se presenta un problema al recuperar los productos se regresa el resultado
               return productosPorFarmaciaResponse;
           }else{
               this.cargarEnMapaLosMedicamentos( idFarmacia , productosPorFarmaciaResponse );
           }
       }
        return productosPorFarmaciaResponse;
    }

    public EncuestaVisitaResponse cargarTodasLasEncuestasDesdeWeb( int idRuta ){
        EncuestaVisitaResponse response = this.jsonService.RealizarPeticionObtenerEncuestasRuta( idRuta );
        if( response.isSeEjecutoConExito() == false ){
            return response;
        }
        //De ser correcto seguimos con el procesamiento
        mapaEncuestaPorIdEncuesta = new HashMap< String , Encuesta>();
        EncuestasRutaRest[] encuestasRest = response.getEncuestasRuta();
        for( EncuestasRutaRest itemEncuestaRest : encuestasRest ){
            Encuesta encuesta = this.armarEncuestaDesdeEncuestaRest( itemEncuestaRest );
            mapaEncuestaPorIdEncuesta.put( "" + itemEncuestaRest.getIdEncuesta() , encuesta );
        }
        return response;
    }

    private Encuesta armarEncuestaDesdeEncuestaRest(EncuestasRutaRest itemEncuestaRest) {
        Encuesta encuesta = new Encuesta();
        encuesta.setIdEncuesta( "" + itemEncuestaRest.getIdEncuesta() );
        Pregunta[] preguntas = this.armarPreguntasDesdePreguntasRest( itemEncuestaRest.getPreguntasEncuesta() );
        encuesta.setPreguntas( preguntas );
        return encuesta;
    }

    private Pregunta[] armarPreguntasDesdePreguntasRest(PreguntasEncuestaRest[] preguntasEncuesta) {
        int totalDePreguntas = preguntasEncuesta.length;
        Pregunta[] preguntas = new Pregunta[ totalDePreguntas ];
        for( int j = 0 ; j < totalDePreguntas ; j++){
            Pregunta pregunta = new Pregunta();
            PreguntasEncuestaRest itemPreguntaRest = preguntasEncuesta[j];
            pregunta.setIdPregunta( "" + itemPreguntaRest.getIdPregunta() );
            pregunta.setTextoPregunta(itemPreguntaRest.getDescripcion());
            Respuesta[] respuestas = this.armarRespuestaDesdeRespuestaRest( itemPreguntaRest.getRespuestasPregunta() );
            pregunta.setRespuestas( respuestas );
            preguntas[j] = pregunta;
        }
        return preguntas;
    }

    private Respuesta[] armarRespuestaDesdeRespuestaRest(RespuestasPreguntaRest[] respuestasPreguntaRest) {
        int totalDeRespuestas = respuestasPreguntaRest.length;
        Respuesta[] respuestas = new Respuesta[ totalDeRespuestas ];
        for( int j = 0 ; j <totalDeRespuestas ; j++ ){
            Respuesta respuesta = new Respuesta();
            RespuestasPreguntaRest itemRespuestaRest = respuestasPreguntaRest[j];
            respuesta.setIdRespuesta( "" + itemRespuestaRest.getIdRespuesta() );
            respuesta.setTextoRespuesta( itemRespuestaRest.getDescripcion() );
            respuestas[j] = respuesta;
        }
        return respuestas;
    }

    private void cargarEnMapaLosMedicamentos(Integer idFarmacia, ProductosPorFarmaciaResponse productosPorFarmaciaResponse) {
        ProductoRest[] productosEnFarmacia = productosPorFarmaciaResponse.getProductos();
        List<Medicamento> medicamentos = this.parsearArrayProductosAListaDeMedicamentos(productosEnFarmacia);
        mapaMedicamentosPorFarmacia.put( idFarmacia , medicamentos );
    }

    private List<Medicamento> parsearArrayProductosAListaDeMedicamentos(ProductoRest[] productosEnFarmacia) {
        List<Medicamento> medicamentos = new ArrayList<Medicamento>();
        for( ProductoRest itemProducto : productosEnFarmacia ){
            Medicamento medicamento = new Medicamento();
            medicamento.setNombreMedicamento( itemProducto.getNombre() );
            medicamento.setIdMedicamento("" + itemProducto.getIdProducto());
            medicamento.setPrecio(itemProducto.getPrecioPorPieza());
            medicamento.setMaximoPermitido( itemProducto.getMaximoDosisPermitidas() );
            //medicamento.setCantidad( ... );La cantidad se determina en la visita
            medicamentos.add( medicamento );
        }
        return medicamentos;
    }

    @Override
    public List<Medicamento> getCatalogoMedicamentos( int idFarmacia) {
        List<Medicamento> medicamentos = null;
        if( Const.Enviroment.currentEnviroment == Const.Enviroment.MOCK ){
            medicamentos = this.armarListaMedicamentosMock();
        }else{
            medicamentos =  this.mapaMedicamentosPorFarmacia.get( idFarmacia );
        }
        return medicamentos;
    }

    public Encuesta recuperarEncuestaPorId( String idEncuesta ){
        Encuesta encuesta = null;
        if( Const.Enviroment.currentEnviroment == Const.Enviroment.MOCK ){
            encuesta = this.crearEncuestaMock( idEncuesta );
        }else{
            encuesta =  this.mapaEncuestaPorIdEncuesta.get( idEncuesta );
        }
        return encuesta;
    }

    private Encuesta crearEncuestaMock( String idEncuesta ) {
        Encuesta encuesta = new Encuesta();
        encuesta.setIdEncuesta( idEncuesta );

        Pregunta[] preguntas = this.crearPreguntasMock( idEncuesta );
        encuesta.setPreguntas(preguntas);

        return encuesta;
    }

    private Pregunta[] crearPreguntasMock( String idEncuesta ){
        int numeroPreguntas = 10;
        Pregunta[] preguntas = new Pregunta[ numeroPreguntas ];
        for( int j = 1 ; j <= numeroPreguntas ; j++ ){
            Pregunta pregunta = new Pregunta();
            pregunta.setIdPregunta( idEncuesta + j );
            pregunta.setTextoPregunta("¿Esta es la pregunta A" + j);
            Respuesta[] respuestas = this.crearRespuestaMock( idEncuesta + j );
            pregunta.setRespuestas(respuestas);
            preguntas[ j-1 ] = pregunta;
        }
        return preguntas;
    }

    private Respuesta[] crearRespuestaMock( String idPregunta ){
        int numeroRespuestas = 3;
        Respuesta[] respuestas = new Respuesta[ numeroRespuestas ];
        for( int j = 1 ; j<= numeroRespuestas ; j++ ){
            Respuesta respuesta = new Respuesta();
            respuesta.setIdRespuesta( "" + idPregunta + j );
            respuesta.setTextoRespuesta("Respuesta opción A" + j);
            respuestas[ j - 1 ] = respuesta;
        }
        return respuestas;
    }


    private List<Medicamento> armarListaMedicamentosMock() {
        List<Medicamento> medicamentos = new ArrayList<Medicamento>();
        for( int j = 0; j < 10 ; j ++ ){
            Medicamento medicamento = new Medicamento();
            medicamento.setIdMedicamento( "" + (1000 + j) );
            medicamento.setNombreMedicamento( "Medicamento de Patente " + (1000 + j) );
            medicamento.setMaximoPermitido( 4 + j );
            medicamento.setPrecio( 9900 + j );
            medicamento.setCantidad( 0 );
            medicamentos.add( medicamento );
        }
        return medicamentos;
    }


    public void recuperarCatalogosDesdeBase(){
        this.recuperarCatalogoMedicamentosDesdeBase();
        this.recuperarCatalogoEncuestaDesdeBase();
    }

    private void recuperarCatalogoMedicamentosDesdeBase() {
        this.mapaMedicamentosPorFarmacia = new HashMap< Integer , List<Medicamento> >();
        List<Integer> listaIdFarmacias =  this.catalogoDao.recuperarListaDeIdFarmaciaEnCatalogo();
        for( Integer idFarmaciaItem : listaIdFarmacias ){
            List<Medicamento> medicamentos = this.catalogoDao.recuperarCatalogoMedicamentosPorIdFarmacia(idFarmaciaItem);
            this.mapaMedicamentosPorFarmacia.put( idFarmaciaItem , medicamentos);
        }
    }

    private void recuperarCatalogoEncuestaDesdeBase() {
        this.mapaEncuestaPorIdEncuesta = new HashMap<String, Encuesta>();

        List<Integer> listaIdEncuestas = this.catalogoDao.recuperarListaIdEncuestaEnCatalogo();
        for( Integer idEncuestaItem : listaIdEncuestas ){
            Encuesta encuesta = new Encuesta();
            List<Pregunta> catalogoPreguntas = this.catalogoDao.recuperarCatalogoPreguntasPorIdEncuesta(idEncuestaItem);
            for( Pregunta itemPregunta : catalogoPreguntas ){
                int idPregunta = Integer.parseInt(itemPregunta.getIdPregunta());
                List<Respuesta> catalogoRespuestas = this.catalogoDao.recuperarCatalogoRespuestaPorIdPregunta( idPregunta );
                itemPregunta.setRespuestas( catalogoRespuestas.toArray( new Respuesta[0] ) );
            }
            encuesta.setIdEncuesta( "" + idEncuestaItem );
            encuesta.setPreguntas( catalogoPreguntas.toArray( new Pregunta[0] ) );
            this.mapaEncuestaPorIdEncuesta.put( "" + idEncuestaItem , encuesta );
        }
    }

    public void actualizarCatalogosEnBase(){
        this.actualizarCatalogoMedicamentosEnBase();
        this.actualizarCatalogoEncuestaEnBase();
        
    }

    private void actualizarCatalogoEncuestaEnBase() {
        //EliminarContenidoDelCatalogo
        this.catalogoDao.eliminarCatalogoPreguntasRespuestas();
        //Insertar los catalogos de preguntas y resgpuestas
        Set< String > idEncuestaSet = this.mapaEncuestaPorIdEncuesta.keySet();
        for( String idEncuestaItem : idEncuestaSet ){
            Encuesta encuesta = this.mapaEncuestaPorIdEncuesta.get( idEncuestaItem );
            Pregunta[] preguntas = encuesta.getPreguntas();
            for( Pregunta itemPregunta : preguntas ){
                int idEncuesta = Integer.parseInt( idEncuestaItem);
                this.catalogoDao.insertarCatalogoPreguntas( itemPregunta , idEncuesta );
                Respuesta[] respuestas = itemPregunta.getRespuestas();
                for( Respuesta itemRespuesta : respuestas ){
                    int idPregunta = Integer.parseInt( itemPregunta.getIdPregunta() );
                    this.catalogoDao.insertarCatalogoRespuesta( itemRespuesta , idPregunta );
                }
            }
        }
    }

    private void actualizarCatalogoMedicamentosEnBase() {
        //EliminarContenidoDelCatalogo
        this.catalogoDao.eliminarCatalogoMedicamentos();
        //InsertarNuevoCatalogo
        Set<Integer> idFarmaciasSet = this.mapaMedicamentosPorFarmacia.keySet();
        for( Integer idFarmaciaItem : idFarmaciasSet  ){
            List<Medicamento> medicamentos = this.mapaMedicamentosPorFarmacia.get(idFarmaciaItem);
            for(Medicamento itemMedicamento : medicamentos ){
                this.catalogoDao.insertarCatalogoMedicamentos( itemMedicamento , idFarmaciaItem );
            }
        }
    }
}
