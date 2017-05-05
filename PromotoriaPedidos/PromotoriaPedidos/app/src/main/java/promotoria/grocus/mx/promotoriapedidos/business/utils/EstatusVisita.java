package promotoria.grocus.mx.promotoriapedidos.business.utils;

/**
 * Created by MAMM on 19/04/15.
 */
public enum EstatusVisita {
    EN_RUTA( 4 , "En ruta" , 0xfffffcdF ), //Amarillo Claro
    CHECK_IN ( 5 , "Check-In" , 0xFFFFFF66 ),  //amarillo
    CHECK_OUT_REQUEST( 7 , "Check-Out sin validar" , 0xFFaae879), //VERDE
    CHECK_OUT( 6 , "Check-Out" , 0xFFaae879 ),  //verde
    CANCELADA( 8 , "Cancelada" , 0xFF9179D7 ),  //Morado
    NO_VISITADA( 9 , "Sin visitar" , 0xFF9179D7 ),  //Morado
    NO_ENVIADO( 999 , "No enviado" , 0xFFD53541 );  //Rojo


    //http://www.color-hex.com/Color

    public static final int EN_RUTA_ID_WEB = 4;
    public static final int CHECK_IN_ID_WEB = 5;
    public static final int CHECK_OUT_INCOMPLETO_ID_WEB = 7;
    public static final int CHECK_OUT_COMPLETO_ID_WEB = 6;
    public static final int CANCELADA_ID_WEB = 8;
    public static final int NO_VISITADA_ID_WEB = 9;


    private int idEstatus;
    private String nombreEstatus;
    private int color;

    private EstatusVisita(int idEstatus, String nombreEstatus, int color){
        this.idEstatus = idEstatus;
        this.nombreEstatus = nombreEstatus;
        this.color = color;
    }

    public int getIdEstatus() { return idEstatus; }

    public String getNombreEstatus() {
        return nombreEstatus;
    }

    public int getColor() {
        return color;
    }
}
