package promotoria.grocus.mx.promotoriapedidos.controller;

import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import promotoria.grocus.mx.promotoriapedidos.R;
import promotoria.grocus.mx.promotoriapedidos.business.Medicamento;
import promotoria.grocus.mx.promotoriapedidos.business.Visita;
import promotoria.grocus.mx.promotoriapedidos.business.utils.RespuestaBinaria;
import promotoria.grocus.mx.promotoriapedidos.controller.adapter.MedicamentosListAdapter;
import promotoria.grocus.mx.promotoriapedidos.dao.MedicamentoDao;
import promotoria.grocus.mx.promotoriapedidos.services.CatalogosService;
import promotoria.grocus.mx.promotoriapedidos.services.VisitaService;
import promotoria.grocus.mx.promotoriapedidos.services.impl.CatalogosServiceImpl;
import promotoria.grocus.mx.promotoriapedidos.services.impl.VisitaServiceImpl;
import promotoria.grocus.mx.promotoriapedidos.utils.AuxiliarCapturaPedido;
import promotoria.grocus.mx.promotoriapedidos.utils.Const;
import promotoria.grocus.mx.promotoriapedidos.utils.CustomUncaughtExceptionHandler;
import promotoria.grocus.mx.promotoriapedidos.utils.ViewUtil;


public class CapturaPedidoActivity extends ActionBarActivity implements View.OnClickListener {
    private static final String CLASSNAME = CapturaPedidoActivity.class.getSimpleName();

    //Services
    private VisitaService visitaService;
    private CatalogosService catalogosService;

    //Business
    private Visita visita;
    private Medicamento medicamento;
    private MedicamentosListAdapter medicamentosListAdapter;

    //UI Elements
    private TextView sucursalTexView;
    private ListView productosListaCapturaView;


    private Button cancelarCapturaPedidoButton;
    private Button continuarCapturaPedidoButton;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Thread.setDefaultUncaughtExceptionHandler(new CustomUncaughtExceptionHandler(this));
        this.visitaService = VisitaServiceImpl.getSingleton();
        this.catalogosService = CatalogosServiceImpl.getSingleton();
        this.visita = this.visitaService.getVisitaActual();
        this.prepararPantalla();
    }

    private void prepararPantalla() {
        setContentView(R.layout.captura_pedido_layout);

        this.sucursalTexView = (TextView) this.findViewById(R.id.sucursalTexView);
        this.sucursalTexView.setText(this.visita.getNombreFarmacia());
        this.productosListaCapturaView = (ListView) this.findViewById(R.id.productosListaCapturaView);

        this.cancelarCapturaPedidoButton = (Button) this.findViewById(R.id.cancelarCapturaPedidoButton);
        this.continuarCapturaPedidoButton = (Button) this.findViewById(R.id.continuarCapturaPedidoButton);
        this.cancelarCapturaPedidoButton.setOnClickListener(this);
        this.continuarCapturaPedidoButton.setOnClickListener(this);


        List<Medicamento> medicamentos = this.catalogosService.getCatalogoMedicamentos( Integer.parseInt( this.visita.getIdFarmacia() ) );
        this.medicamentosListAdapter = (new MedicamentosListAdapter(medicamentos, this));

        this.productosListaCapturaView.setAdapter(medicamentosListAdapter);
        this.productosListaCapturaView.setItemsCanFocus(true);


    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_captura_pedido, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onClick(View v) {
        Intent intent = null;
        if (v == cancelarCapturaPedidoButton) {
            this.finish();
            return;
        }
        if (v == continuarCapturaPedidoButton) {
            // 1.- Recuperar Datos
            List<AuxiliarCapturaPedido> elementosAuxiliaresPedido = recuperarElementosAuxiliaresPedido();

            //2.- Validar elementos
            boolean existenErrores = validarElementosSeleccionados(elementosAuxiliaresPedido);
            if(!existenErrores){
                //3.- Enviar Datos
                cargarElementosSeleccionadosEnVisita(elementosAuxiliaresPedido);
                //this.visita.setPedidoCapturado( RespuestaBinaria.SI );  //Se deshabilita en esta sección para habilitarse en la captura de firma
                this.visitaService.agregarMedicamentosAVisitaActual( this.visita.getMedicamentos() );
                this.visitaService.actualizarVisitaActual();
                intent = new Intent(this, DetallePedidoActivity.class);
                this.startActivity(intent);
            }
        }
    }


    private List<AuxiliarCapturaPedido> recuperarElementosAuxiliaresPedido(){

        AuxiliarCapturaPedido[] auxiliarCapturaPedido = this.medicamentosListAdapter.getAuxliarCapturaPedido();
        List<AuxiliarCapturaPedido> elementosPedido = new ArrayList<AuxiliarCapturaPedido>();

        for ( int i = 0; i < auxiliarCapturaPedido.length; i++ ) {
            if ( auxiliarCapturaPedido[i] != null && auxiliarCapturaPedido[i].getEstatusCaptura()
                    == Const.EstatusCapturaPedidosEnCelda.SELECCIONADO) {
                elementosPedido.add(auxiliarCapturaPedido[i] );
            }
        }
        return elementosPedido;
    }


    private boolean validarElementosSeleccionados( List<AuxiliarCapturaPedido> elementosAuxiliaresPedido ) {
        boolean existenErrores = false;

        if ( elementosAuxiliaresPedido.isEmpty() ) {
            ViewUtil.mostrarMensajeRapido(this, "Por lo menos debes capturar un producto");
            existenErrores = true;
            return existenErrores;
        }

        for ( AuxiliarCapturaPedido itemAuxiliar : elementosAuxiliaresPedido ) {

            if ( itemAuxiliar.getCantidad() == 0 || itemAuxiliar.getCantidad() > itemAuxiliar.getMedicamentoReferencia().getMaximoPermitido()
                    || itemAuxiliar.getPrecioUnitarios() == 0 ) {
                        ViewUtil.mostrarMensajeRapido(this, "Verifica los Datos del medicamento " + itemAuxiliar.getMedicamentoReferencia().getNombreMedicamento());
                existenErrores = true;
                break;
            }
        }

        return existenErrores;
    }

    public void cargarElementosSeleccionadosEnVisita( List<AuxiliarCapturaPedido> elementosAuxiliaresPedido ){
        Medicamento[] medicamentos = new  Medicamento[ elementosAuxiliaresPedido.size() ];

        for (int i=0; i<medicamentos.length; i++){
            Medicamento medicamento = new Medicamento();

            medicamento.setNombreMedicamento( elementosAuxiliaresPedido.get(i).getMedicamentoReferencia().getNombreMedicamento() );
            medicamento.setCantidad(elementosAuxiliaresPedido.get(i).getCantidad());
            medicamento.setPrecio(elementosAuxiliaresPedido.get(i).getPrecioUnitarios());

            medicamento.setIdMedicamento(elementosAuxiliaresPedido.get(i).getMedicamentoReferencia().getIdMedicamento());
            medicamento.setMaximoPermitido(elementosAuxiliaresPedido.get(i).getMedicamentoReferencia().getMaximoPermitido());

            medicamentos[i] = medicamento;

        }
        this.visita.setMedicamentos( medicamentos );


    }




}
