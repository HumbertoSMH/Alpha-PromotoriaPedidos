package promotoria.grocus.mx.promotoriapedidos.utils;

import android.util.Log;

/**
 * Clase de constantes y parametros de la aplicación
 *
 * Created by MAMM on 15/04/15.
 *
 */
public class Const {

    /*Nivel de logger*/
    public static final int LOG_LEVEL = Log.INFO;

    public enum Enviroment{
        MOCK,
        FAKE,
        DES,
        DIS;

        public static Enviroment currentEnviroment = FAKE;
    }

    public static final int POSICION_CERO = 0;
    public static final int POSICION_UNO = 1;
    public static final int POSICION_DOS = 2;
    public static final int POSICION_TRES = 3;
    public static final int POSICION_CUATRO = 4;
    public static final int POSICION_CINCO = 5;
    public static final int POSICION_SEIS = 6;
    public static final int POSICION_SIETE = 7;
    public static final int POSICION_OCHO = 8;
    public static final int POSICION_NUEVE = 9;
    public static final int POSICION_DIEZ = 10;
    public static final int POSICION_ONCE = 11;
    public static final int POSICION_DOCE = 12;
    public static final int POSICION_TRECE = 13;
    public static final int POSICION_CATORCE = 14;
    public static final int POSICION_QUINCE = 15;
    public static final int POSICION_DIECISEIS = 16;
    public static final int POSICION_DIECISIETE = 17;
    public static final int POSICION_DIECIOCHO = 18;
    public static final int POSICION_DIECINUEVE = 19;

    public static final int LONGITUD_ARRAY_VACIO = 0;
    public static final int CANTIDAD_MINIMA_FOTOS = 3;
    public static final int ID_MOTIVO_RETIRO_OTRO = 999;


    public static final String[][] VOWELS = new String[][] {
            { "á", "é", "í", "ó", "ú", "Á", "É", "Í", "Ó", "Ú", "ñ" , "Ñ" , "ü" , "Ü" },
            { "a", "e", "i", "o", "u", "A", "E", "I", "O", "U" , "n" , "N" , "u" , "U"} };

    public enum ParametrosIntent{
        POSICION_VISITA( "posicionVisita" , 1);

        private String nombre;
        private int idParametro;
        private ParametrosIntent( String nombre , int idParametro ){
            this.nombre = nombre;
            this.idParametro = idParametro;
        }

        public String getNombre() { return nombre; }

        public int getIdParametro() {
            return idParametro;
        }
    }

    public enum ModalidadCheckIn{
        NOTIFICADO_ENLINEA( "En línea" ),
        NOTIFICADO_FUERALINEA( "Fuera de Línea" );

        public  String nombre;
        private ModalidadCheckIn( String nombre  ){
            this.nombre = nombre;
        }
    }

    public enum TipoDespliegueActivity{
        ALTA,
        CONSULTA;
    }


    public enum VersionAPK{
        VER_1_0_0( "1.0.0" , "Versión inicial de la aplicación móvil PromotoriaPedidos" ),
        VER_1_0_1( "1.0.1" , "1er Versión entregable a producción" );

        public String version;
        public String descripcion;
        VersionAPK( String version , String descripcion ){
            this.version = version;
            this.descripcion = descripcion;
        }

        public static VersionAPK getUltimaVersion() {
            VersionAPK[]  versiones = VersionAPK.values();
            return versiones[ versiones.length -1 ];
        }
    }

    // If you change the database schema, you must increment the database version.
    public static final int DATABASE_VERSION = 1;
    public static final String PATH = "/data/data/promotoria.grocus.mx.promotoriapedidos/databases/";
    public static final String DATABASE_NAME = "PromotoriaPedidos.db";


    public enum EstatusCapturaPedidosEnCelda{
        CREADO,
        SELECCIONADO,
        VALIDADO;
    }

}
