package promotoria.grocus.mx.promotoriapedidos.business.persistence;

/**
 * Created by devmac02 on 16/07/15.
 */
public class TablasDeNegocio {

    // To prevent someone from accidentally instantiating the PromotorMovilPersistence class,
    // give it an empty constructor.
    public TablasDeNegocio() {}
    private static final String TEXT_TYPE = " TEXT ";
    private static final String INTEGER_TYPE = " integer ";
    private static final String COMMA_SEP = " , ";
    private static final String SEMICOMMA_SEP = " ; ";
    private static final String NOT_NULL = " NOT NULL ";
    private static final String TEXT_DEFINITION_1 = TEXT_TYPE +  COMMA_SEP;
    private static final String TEXT_DEFINITION_2 = TEXT_TYPE;
    private static final String INTEGER_DEFINITION_1 = INTEGER_TYPE +  COMMA_SEP;
    private static final String INTEGER_DEFINITION_2 = INTEGER_TYPE;

    /* Inner class that defines the table contents */
    public static enum Rutas {
        COLUMN_NAME_IDRUTA (0 , "idRuta"),
        COLUMN_NAME_FECHAINICIO( 1 , "fechaInicio" ),
        COLUMN_NAME_FECHAFIN ( 2 , "fechaFin" ),
        COLUMN_NAME_FECHAPROGRAMADASTRING (3 , "fechaProgramadaString" ),
        COLUMN_NAME_FECHACREACIONSTRING ( 4 ,  "fechaCreacionString" ),
        COLUMN_NAME_FECHAULTIMAMODIFICACION(5 , "fechaUltimaModificacion" ),
        COLUMN_NAME_CLAVEPROMOTOR( 6 , "clavePromotor"),
        COLUMN_NAME_PASSWORDPROMOTOR( 7 , "passwordPromotor");

        public static final String TABLE_NAME = "Rutas";

        public int index;
        public String column;

        private Rutas( int index , String column ){
            this.index = index;
            this.column = column;
        }

        public static String[] getColumns(){
            Rutas[] rutasArray = Rutas.values();
            int size = rutasArray.length;
            String[] columnas = new String[ size];
            for( int j = 0; j < size ; j ++ ){
                columnas[j] = rutasArray[j].column;
            }
            return columnas;
        }
    }

    /* Inner class that defines the table contents */
    public static enum Visitas {
        COLUMN_NAME_IDVISITA ( 0 , "idVisita"),
        COLUMN_NAME_ESTATUSVISITA ( 1 , "estatusVisita" ),
        COLUMN_NAME_FECHACHECKIN ( 2 , "fechaCheckIn" ),
        COLUMN_NAME_FECHACHECKOUT (3 , "fechaCheckout" ),

        COLUMN_NAME_GERENTE ( 4 ,  "gerente" ),
        COLUMN_NAME_EMAILGERENTE ( 5 , "emailGerente" ),
        COLUMN_NAME_FIRMAGERENTE (6 , "firmaGerente" ),
        COLUMN_NAME_COMENTARIOS ( 7 , "comentarios" ),

        COLUMN_NAME_APLICARENCUESTA (8 , "aplicarEncuesta"),
        COLUMN_NAME_IDENCUESTA (9 , "idEncuesta"),
        COLUMN_NAME_ENCUESTACAPTURADA ( 10 , "encuestaCapturada" ),
        COLUMN_NAME_PEDIDOCAPTURADO ( 11 , "pedidoCapturado" ),

        COLUMN_NAME_IDFARMACIA( 12 , "idFarmacia" ),
        COLUMN_NAME_NOMBREFARMACIA( 13 , "nombreFarmacia" ),
        COLUMN_NAME_DIRECCIONFARMACIA(14,"direccionFarmacia"),

        //SeccionLocation
        COLUMN_NAME_LATITUD( 15 , "latitud" ),
        COLUMN_NAME_LONGITUD( 16 , "longitud" ),
        //Verificación de CheckIn
        COLUMN_NAME_CHECKINNOTIFICADO ( 17 , "checkInNotificado"),
        //Ruta
        COLUMN_NAME_IDRUTA ( 18 , "idRuta" );

        public static final String TABLE_NAME = "Visita";

        public int index;
        public String column;

        private Visitas( int index , String column ){
            this.index = index;
            this.column = column;
        }

        public static String[] getColumns(){
            Visitas[] rutasArray = Visitas.values();
            int size = rutasArray.length;
            String[] columnas = new String[ size];
            for( int j = 0; j < size ; j ++ ){
                columnas[j] = rutasArray[j].column;
            }
            return columnas;
        }
    }

    public static enum Medicamentos {
        COLUMN_NAME_IDMEDICAMENTO ( 0 , "idMedicamento"),
        COLUMN_NAME_NOMBREMEDICAMENTO ( 1 , "nombreMedicamento"),
        COLUMN_NAME_MAXIMOPERMITIDO ( 2 , "maximoPermitido" ),
        COLUMN_NAME_PRECIO ( 3 , "precio" ),
        COLUMN_NAME_CANTIDAD (4 , "cantidad" ),
        COLUMN_NAME_IDVISITA ( 5 , "idVisita" );

        public static final String TABLE_NAME = "Medicamentos";

        public int index;
        public String column;

        private Medicamentos( int index , String column ){
            this.index = index;
            this.column = column;
        }

        public static String[] getColumns(){
            Medicamentos[] rutasArray = Medicamentos.values();
            int size = rutasArray.length;
            String[] columnas = new String[ size];
            for( int j = 0; j < size ; j ++ ){
                columnas[j] = rutasArray[j].column;
            }
            return columnas;
        }
    }

    public static enum Encuestas {
        COLUMN_NAME_IDPREGUNTA ( 0 , "idPregunta"),
        COLUMN_NAME_DESCRIPCIONPREGUNTA ( 1 , "descripcionPregunta"),
        COLUMN_NAME_IDRESPUESTA ( 2 , "idRespuesta" ),
        COLUMN_NAME_DESCRIPCIONRESPUESTA ( 3 , "descripcionRespuesta" ),
        COLUMN_NAME_IDENTIFICADORENCUESTA( 4 , "identificadorEncuesta"),
        COLUMN_NAME_IDVISITA ( 5 , "idVisita" );

        public static final String TABLE_NAME = "Encuestas";

        public int index;
        public String column;

        private Encuestas( int index , String column ){
            this.index = index;
            this.column = column;
        }

        public static String[] getColumns(){
            Encuestas[] rutasArray = Encuestas.values();
            int size = rutasArray.length;
            String[] columnas = new String[ size];
            for( int j = 0; j < size ; j ++ ){
                columnas[j] = rutasArray[j].column;
            }
            return columnas;
        }
    }


    public static final String SQL_CREATE_TABLE_RUTAS =
            "CREATE TABLE " + Rutas.TABLE_NAME + " (" +
                    Rutas.COLUMN_NAME_IDRUTA.column + INTEGER_DEFINITION_2 + " PRIMARY KEY, " +
                    Rutas.COLUMN_NAME_FECHAINICIO.column + TEXT_DEFINITION_1 +
                    Rutas.COLUMN_NAME_FECHAFIN.column + TEXT_DEFINITION_1 +
                    Rutas.COLUMN_NAME_FECHAPROGRAMADASTRING.column + TEXT_DEFINITION_1 +
                    Rutas.COLUMN_NAME_FECHACREACIONSTRING.column + TEXT_DEFINITION_1 +
                    Rutas.COLUMN_NAME_FECHAULTIMAMODIFICACION.column + TEXT_DEFINITION_1 +
                    Rutas.COLUMN_NAME_CLAVEPROMOTOR.column + TEXT_DEFINITION_1 +
                    Rutas.COLUMN_NAME_PASSWORDPROMOTOR.column + TEXT_DEFINITION_2 +
                    " ); ";

    public static final String SQL_DELETE_RUTAS = "DROP TABLE IF EXISTS " + Rutas.TABLE_NAME + " ; ";

    public static final String SQL_CREATE_TABLE_VISITAS =
            "CREATE TABLE " + Visitas.TABLE_NAME + " (" +
                    Visitas.COLUMN_NAME_IDVISITA.column + INTEGER_DEFINITION_2 + " PRIMARY KEY, " +
                    Visitas.COLUMN_NAME_ESTATUSVISITA.column + TEXT_DEFINITION_1 +
                    Visitas.COLUMN_NAME_FECHACHECKIN.column + TEXT_DEFINITION_1 +
                    Visitas.COLUMN_NAME_FECHACHECKOUT.column + TEXT_DEFINITION_1 +

                    Visitas.COLUMN_NAME_GERENTE.column + TEXT_DEFINITION_1 +
                    Visitas.COLUMN_NAME_EMAILGERENTE.column + TEXT_DEFINITION_1 +
                    Visitas.COLUMN_NAME_FIRMAGERENTE.column + TEXT_DEFINITION_1 +
                    Visitas.COLUMN_NAME_COMENTARIOS.column + TEXT_DEFINITION_1 +

                    Visitas.COLUMN_NAME_APLICARENCUESTA.column + TEXT_DEFINITION_1 +
                    Visitas.COLUMN_NAME_IDENCUESTA.column + TEXT_DEFINITION_1 +
                    Visitas.COLUMN_NAME_ENCUESTACAPTURADA.column + TEXT_DEFINITION_1 +
                    Visitas.COLUMN_NAME_PEDIDOCAPTURADO.column + TEXT_DEFINITION_1 +

                    Visitas.COLUMN_NAME_IDFARMACIA.column + INTEGER_DEFINITION_1 +
                    Visitas.COLUMN_NAME_NOMBREFARMACIA.column + TEXT_DEFINITION_1 +
                    Visitas.COLUMN_NAME_DIRECCIONFARMACIA.column + TEXT_DEFINITION_1 +

                    Visitas.COLUMN_NAME_LATITUD.column + TEXT_DEFINITION_1 +
                    Visitas.COLUMN_NAME_LONGITUD.column + TEXT_DEFINITION_1 +

                    Visitas.COLUMN_NAME_CHECKINNOTIFICADO.column + TEXT_DEFINITION_1 +

                    Visitas.COLUMN_NAME_IDRUTA.column + INTEGER_DEFINITION_2 +
                    " ); ";

    public static final String SQL_DELETE_VISITAS = "DROP TABLE IF EXISTS " + Visitas.TABLE_NAME + " ; ";

    public static final String SQL_CREATE_TABLE_MEDICAMENTOS =
            "CREATE TABLE " + Medicamentos.TABLE_NAME + " (" +
                    Medicamentos.COLUMN_NAME_IDMEDICAMENTO.column + INTEGER_DEFINITION_1 +
                    Medicamentos.COLUMN_NAME_NOMBREMEDICAMENTO.column + TEXT_DEFINITION_1 +
                    Medicamentos.COLUMN_NAME_MAXIMOPERMITIDO.column + INTEGER_DEFINITION_1 +
                    Medicamentos.COLUMN_NAME_PRECIO.column + INTEGER_DEFINITION_1 +
                    Medicamentos.COLUMN_NAME_CANTIDAD.column + INTEGER_DEFINITION_1 +

                    Medicamentos.COLUMN_NAME_IDVISITA.column + INTEGER_DEFINITION_2 +
                    " ); ";

    public static final String SQL_DELETE_MEDICAMENTOS = "DROP TABLE IF EXISTS " + Medicamentos.TABLE_NAME + " ; ";

    public static final String SQL_CREATE_TABLE_ENCUESTA =
            "CREATE TABLE " + Encuestas.TABLE_NAME + " (" +
                    Encuestas.COLUMN_NAME_IDPREGUNTA.column + INTEGER_DEFINITION_1  +
                    Encuestas.COLUMN_NAME_DESCRIPCIONPREGUNTA.column + TEXT_DEFINITION_1 +
                    Encuestas.COLUMN_NAME_IDRESPUESTA.column + INTEGER_DEFINITION_1 +
                    Encuestas.COLUMN_NAME_DESCRIPCIONRESPUESTA.column + TEXT_DEFINITION_1 +
                    Encuestas.COLUMN_NAME_IDENTIFICADORENCUESTA.column + TEXT_DEFINITION_1 +
                    Encuestas.COLUMN_NAME_IDVISITA.column + INTEGER_DEFINITION_2 +
                    " ); ";

    public static final String SQL_DELETE_ENCUESTAS = "DROP TABLE IF EXISTS " + Encuestas.TABLE_NAME + " ; ";
}
