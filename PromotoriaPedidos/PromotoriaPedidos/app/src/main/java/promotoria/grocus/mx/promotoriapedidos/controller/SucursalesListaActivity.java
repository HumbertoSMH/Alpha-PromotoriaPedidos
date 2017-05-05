package promotoria.grocus.mx.promotoriapedidos.controller;

import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import promotoria.grocus.mx.promotoriapedidos.R;
import promotoria.grocus.mx.promotoriapedidos.business.Promotor;
import promotoria.grocus.mx.promotoriapedidos.business.Ruta;
import promotoria.grocus.mx.promotoriapedidos.business.Visita;
import promotoria.grocus.mx.promotoriapedidos.controller.adapter.SucursalesListAdapter;
import promotoria.grocus.mx.promotoriapedidos.services.PromotorService;
import promotoria.grocus.mx.promotoriapedidos.services.RutaService;
import promotoria.grocus.mx.promotoriapedidos.services.VisitaService;
import promotoria.grocus.mx.promotoriapedidos.services.impl.PromotorServiceImpl;
import promotoria.grocus.mx.promotoriapedidos.services.impl.RutaServiceImpl;
import promotoria.grocus.mx.promotoriapedidos.services.impl.VisitaServiceImpl;
import promotoria.grocus.mx.promotoriapedidos.utils.Const;
import promotoria.grocus.mx.promotoriapedidos.utils.CustomUncaughtExceptionHandler;
import promotoria.grocus.mx.promotoriapedidos.utils.LogUtil;


public class SucursalesListaActivity extends ActionBarActivity implements AdapterView.OnItemClickListener {

    private final static String CLASSNAME = SucursalesListaActivity.class.getSimpleName();

    //Services
    private PromotorService promotorService;
    private VisitaService visitaService;
    private RutaService rutaService;

    //Business
    private Promotor promotorActual;

    //UI Elements
    TextView usuarioSucursalesTextView = null;
    TextView fechaSucursalesTextView = null;
    ListView sucursalesListView = null;
    Button salirSucursalesButton = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Thread.setDefaultUncaughtExceptionHandler(new CustomUncaughtExceptionHandler(this));
        this.visitaService = VisitaServiceImpl.getSingleton();
        this.promotorService = PromotorServiceImpl.getSingleton();
        this.promotorActual = this.promotorService.getPromotorActual();
        this.rutaService = RutaServiceImpl.getSingleton();
        //this.prepararPantalla();
    }

    private void prepararPantalla(){
        setContentView(R.layout.sucursales_lista_layout);
        this.usuarioSucursalesTextView = (TextView) findViewById( R.id.usuarioSucursalesTextView );
//        Ruta orderTrabajo = this.visitaService.getRutaActual();
        Ruta orderTrabajo = this.rutaService.refrescarRutaDesdeBase(this.visitaService.getRutaActual());

        this.usuarioSucursalesTextView.setText( "Bienvenido: " + this.promotorActual.getUsuario()  );

        this.fechaSucursalesTextView = (TextView) findViewById( R.id.fechaSucursalesTextView );
        //Camabiará la FechaString
        this.fechaSucursalesTextView.setText(orderTrabajo.getFechaProgramadaString().substring(0, 10));

        findViewById(R.id.salirSucursalesButton).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(SucursalesListaActivity.this, MainLogin.class);
                intent.addFlags( Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity( intent );
            }
        });

        this.sucursalesListView = (ListView) findViewById(R.id.sucursalesListView);
        this.sucursalesListView.setOnItemClickListener(this);

        Visita[] visitas = orderTrabajo.getVisitas();
         this.sucursalesListView.setAdapter(new SucursalesListAdapter(visitas, this));

    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_sucursales_lista, menu);
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
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        LogUtil.printLog(CLASSNAME, "onItemClick position:" + position + ", id:" + id);
        Intent intent = new Intent(SucursalesListaActivity.this, SucursalMenuActivity.class);
        intent.putExtra( Const.ParametrosIntent.POSICION_VISITA.getNombre() , position );
        this.startActivity( intent );
    }


    @Override
    protected void onResume() {
        prepararPantalla();
        super.onResume();
        LogUtil.printLog(CLASSNAME, "Se ejecuta onResume:" + this);
    }
}
