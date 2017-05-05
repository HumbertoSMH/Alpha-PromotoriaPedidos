package promotoria.grocus.mx.promotoriapedidos.services;


import promotoria.grocus.mx.promotoriapedidos.business.rest.Get.EncuestaVisitaResponse;
import promotoria.grocus.mx.promotoriapedidos.business.rest.Get.LoginResponse;
import promotoria.grocus.mx.promotoriapedidos.business.rest.Get.ProductosPorFarmaciaResponse;
import promotoria.grocus.mx.promotoriapedidos.business.rest.Get.RutaPromotorResponse;
import promotoria.grocus.mx.promotoriapedidos.business.rest.Post.CheckInFarmaciaResponse;
import promotoria.grocus.mx.promotoriapedidos.business.rest.Post.CheckInFarmaciaRest;
import promotoria.grocus.mx.promotoriapedidos.business.rest.Post.CheckOutFarmaciaResponse;
import promotoria.grocus.mx.promotoriapedidos.business.rest.Post.CheckOutFarmaciaRest;

/**
 * Created by MAMM on 28/04/2015.
 */
public interface JsonService {

    public LoginResponse realizarPeticionLoginGet(String usuario, String password);
    public RutaPromotorResponse realizarPeticionObtenerRutaPromotor(String usuario);
    public ProductosPorFarmaciaResponse realizarPeticionObtenerProductosPorFarmacia(int idFarmacia);
    public EncuestaVisitaResponse RealizarPeticionObtenerEncuestasRuta(int idRuta);
    public CheckInFarmaciaResponse realizarCheckinPost(CheckInFarmaciaRest checkInFarmaciaRest);

    public CheckOutFarmaciaResponse realizarCheckOutPost(CheckOutFarmaciaRest checkout);
//    public CheckInFarmaciaResponse realizarCheckOutPost(CheckInFarmaciaRest checkOutFarmaciaRest);


}
