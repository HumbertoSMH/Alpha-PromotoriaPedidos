package promotoria.grocus.mx.promotoriapedidos.business.persistence;

/**
 * Created by devmac02 on 16/07/15.
 */
public class TablasDeCatalogos {

    // To prevent someone from accidentally instantiating the PromotorMovilPersistence class,
    // give it an empty constructor.
    public TablasDeCatalogos() {}
    private static final String TEXT_TYPE = " TEXT ";
    private static final String INTEGER_TYPE = " integer ";
    private static final String COMMA_SEP = " , ";
    private static final String SEMICOMMA_SEP = " ; ";
    private static final String NOT_NULL = " NOT NULL ";
    private static final String TEXT_DEFINITION_1 = TEXT_TYPE +  COMMA_SEP;
    private static final String TEXT_DEFINITION_2 = TEXT_TYPE;
    private static final String INTEGER_DEFINITION_1 = INTEGER_TYPE +  COMMA_SEP;
    private static final String INTEGER_DEFINITION_2 = INTEGER_TYPE;


    public static enum CatalogoMedicamentos {
        COLUMN_NAME_IDMEDICAMENTO ( 0 , "idMedicamento"),
        COLUMN_NAME_NOMBREMEDICAMENTO ( 1 , "nombreMedicamento"),
        COLUMN_NAME_MAXIMOPERMITIDO ( 2 , "maximoPermitido" ),
        COLUMN_NAME_PRECIO ( 3 , "precio" ),
        COLUMN_NAME_CANTIDAD (4 , "cantidad" ),
        COLUMN_NAME_IDFARMACIA ( 5 , "idFarmacia" );

        public static final String TABLE_NAME = "CatalogoMedicamentos";

        public int index;
        public String column;

        private CatalogoMedicamentos( int index , String column ){
            this.index = index;
            this.column = column;
        }

        public static String[] getColumns(){
            CatalogoMedicamentos[] rutasArray = CatalogoMedicamentos.values();
            int size = rutasArray.length;
            String[] columnas = new String[ size];
            for( int j = 0; j < size ; j ++ ){
                columnas[j] = rutasArray[j].column;
            }
            return columnas;
        }
    }

    public static enum CatalogoPreguntas {
        COLUMN_NAME_IDPREGUNTA ( 0 , "idPregunta"),
        COLUMN_NAME_DESCRIPCIONPREGUNTA ( 1 , "descripcionPregunta"),
        COLUMN_NAME_IDENCUESTA( 2 , "idEncuesta" );

        public static final String TABLE_NAME = "CatalogoPreguntas";

        public int index;
        public String column;

        private CatalogoPreguntas( int index , String column ){
            this.index = index;
            this.column = column;
        }

        public static String[] getColumns(){
            CatalogoPreguntas[] rutasArray = CatalogoPreguntas.values();
            int size = rutasArray.length;
            String[] columnas = new String[ size];
            for( int j = 0; j < size ; j ++ ){
                columnas[j] = rutasArray[j].column;
            }
            return columnas;
        }
    }


    public static enum CatalogoRespuestas {
        COLUMN_NAME_IDRESPUESTA ( 0 , "idRespuesta"),
        COLUMN_NAME_DESCRIPCIONRESPUESTA ( 1 , "descripcionRespuesta"),
        COLUMN_NAME_IDPREGUNTA( 2 , "idPregunta" );

        public static final String TABLE_NAME = "CatalogoRespuestas";

        public int index;
        public String column;

        private CatalogoRespuestas( int index , String column ){
            this.index = index;
            this.column = column;
        }

        public static String[] getColumns(){
            CatalogoRespuestas[] rutasArray = CatalogoRespuestas.values();
            int size = rutasArray.length;
            String[] columnas = new String[ size];
            for( int j = 0; j < size ; j ++ ){
                columnas[j] = rutasArray[j].column;
            }
            return columnas;
        }
    }


    public static final String SQL_CREATE_TABLE__CATALOGO_MEDICAMENTOS =
            "CREATE TABLE " + CatalogoMedicamentos.TABLE_NAME + " (" +
                    CatalogoMedicamentos.COLUMN_NAME_IDMEDICAMENTO.column + INTEGER_DEFINITION_1 +
                    CatalogoMedicamentos.COLUMN_NAME_NOMBREMEDICAMENTO.column + TEXT_DEFINITION_1 +
                    CatalogoMedicamentos.COLUMN_NAME_MAXIMOPERMITIDO.column + INTEGER_DEFINITION_1 +
                    CatalogoMedicamentos.COLUMN_NAME_PRECIO.column + INTEGER_DEFINITION_1 +
                    CatalogoMedicamentos.COLUMN_NAME_CANTIDAD.column + INTEGER_DEFINITION_1 +

                    CatalogoMedicamentos.COLUMN_NAME_IDFARMACIA.column + INTEGER_DEFINITION_2 +
                    " ); ";

    public static final String SQL_DELETE_CATALOGO_MEDICAMENTOS = "DROP TABLE IF EXISTS " + CatalogoMedicamentos.TABLE_NAME + " ; ";

    public static final String SQL_CREATE_TABLE_CATALOGO_PREGUNTAS =
            "CREATE TABLE " + CatalogoPreguntas.TABLE_NAME + " (" +
                    CatalogoPreguntas.COLUMN_NAME_IDPREGUNTA.column + INTEGER_DEFINITION_1  +
                    CatalogoPreguntas.COLUMN_NAME_DESCRIPCIONPREGUNTA.column + TEXT_DEFINITION_1 +
                    CatalogoPreguntas.COLUMN_NAME_IDENCUESTA.column + INTEGER_DEFINITION_2 +
                    " ); ";

    public static final String SQL_DELETE_CATALOGO_PREGUNTAS = "DROP TABLE IF EXISTS " + CatalogoPreguntas.TABLE_NAME + " ; ";


    public static final String SQL_CREATE_TABLE_CATALOGO_RESPUESTAS =
            "CREATE TABLE " + CatalogoRespuestas.TABLE_NAME + " (" +
                    CatalogoRespuestas.COLUMN_NAME_IDRESPUESTA.column + INTEGER_DEFINITION_1  +
                    CatalogoRespuestas.COLUMN_NAME_DESCRIPCIONRESPUESTA.column + TEXT_DEFINITION_1 +
                    CatalogoRespuestas.COLUMN_NAME_IDPREGUNTA.column + INTEGER_DEFINITION_2 +
                    " ); ";

    public static final String SQL_DELETE_CATALOGO_RESPUESTAS = "DROP TABLE IF EXISTS " + CatalogoRespuestas.TABLE_NAME + " ; ";

}
