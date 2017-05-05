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

import promotoria.grocus.mx.promotoriapedidos.R;
import promotoria.grocus.mx.promotoriapedidos.business.Medicamento;
import promotoria.grocus.mx.promotoriapedidos.business.Visita;
import promotoria.grocus.mx.promotoriapedidos.controller.adapter.DetallePedidoMedicamentosListAdapter;
import promotoria.grocus.mx.promotoriapedidos.services.CatalogosService;
import promotoria.grocus.mx.promotoriapedidos.services.VisitaService;
import promotoria.grocus.mx.promotoriapedidos.services.impl.CatalogosServiceImpl;
import promotoria.grocus.mx.promotoriapedidos.services.impl.VisitaServiceImpl;
import promotoria.grocus.mx.promotoriapedidos.utils.CustomUncaughtExceptionHandler;
import promotoria.grocus.mx.promotoriapedidos.utils.StringUtils;


public class DetallePedidoActivity extends ActionBarActivity implements View.OnClickListener {

    //Services
    private VisitaService visitaService;

    //Business
    private Visita visita;
    private Medicamento medicamento;

    //ui elements
    private TextView sucursalTexView;
    private ListView detallePedidoListView;
    private TextView totalMedicamentosTextView;
    private TextView precioTotalTextView;
    private Button cancelarDetallePedidoButton;
    private Button continuarDetallePedidoButton;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Thread.setDefaultUncaughtExceptionHandler(new CustomUncaughtExceptionHandler(this));
        this.visitaService = VisitaServiceImpl.getSingleton();
        this.visita = this.visitaService.getVisitaActual( );
        this.prepararPantalla();

    }

    private void prepararPantalla() {
        setContentView(R.layout.detalle_pedido_layout);

        this.sucursalTexView = (TextView)this.findViewById( R.id.sucursalTexView );
        this.sucursalTexView.setText( this.visita.getNombreFarmacia() );

        this.totalMedicamentosTextView = (TextView)this.findViewById( R.id.totalMedicamentosTextView );
        this.totalMedicamentosTextView.setText( "Total de Medicamentos: " + this.visita.getMedicamentos().length );


        this.precioTotalTextView = (TextView)this.findViewById( R.id.precioTotalTextView );

//        String precioTotalString = StringUtils.transformarPrecioADecimalConCentavos("" + this.visitaService.getTotalDeCompraEnVisitaActual());

        String precioTotalDouble = this.visitaService.getTotalDeCompraEnVisitaActual();

        this.precioTotalTextView.setText( "Precio total de Venta: $" + precioTotalDouble );

        this.detallePedidoListView = (ListView)this.findViewById( R.id.detallePedidoListView );
        this.detallePedidoListView.setAdapter( new DetallePedidoMedicamentosListAdapter( this.visita.getMedicamentos() , this ) );


        this.cancelarDetallePedidoButton = (Button)this.findViewById( R.id.cancelarDetallePedidoButton );
        this.continuarDetallePedidoButton = (Button)this.findViewById( R.id.continuarDetallePedidoButton );

        this.cancelarDetallePedidoButton.setOnClickListener( this );
        this.continuarDetallePedidoButton.setOnClickListener( this );

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_detalle_pedido, menu);
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
        if( v ==  cancelarDetallePedidoButton ){
            this.finish();
            return;
        }
        else if( v == continuarDetallePedidoButton ){
            Intent intent = new Intent( this , FirmaPedidoActivity.class );
            this.startActivity( intent );
            return;
        }
    }
}
