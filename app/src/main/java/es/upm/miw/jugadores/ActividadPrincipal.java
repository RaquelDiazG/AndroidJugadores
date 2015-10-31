package es.upm.miw.jugadores;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.util.List;

import es.upm.miw.jugadores.models.Futbolista;
import es.upm.miw.jugadores.models.FutbolistaRepositorio;

public class ActividadPrincipal extends AppCompatActivity {

    private List<Futbolista> listaFutbolistas;
    ListView lvlistadofutbolistas;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_actividad_principal);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });

        //Creamos una instancia del repositorio
        FutbolistaRepositorio repositorio=new FutbolistaRepositorio(getApplicationContext());
        //Creamos un futbolista
        Futbolista futbolista1=new Futbolista(-1,"Jugador 1",1,true,"Primera","http://misimagenesde.com/wp-content/uploads/2009/02/balones-de-futbol.jpg");
        //Añadimos el futbolista al repositorio
        repositorio.add(futbolista1);

        //Recuperamos todos los futbolistas
        this.listaFutbolistas=repositorio.getAll();
        ArrayAdapter<Futbolista> adaptador=new FutbolistaAdapter(this,listaFutbolistas);
        lvlistadofutbolistas=(ListView)findViewById(R.id.listadoFutbolistas);
        lvlistadofutbolistas.setAdapter(adaptador);

        lvlistadofutbolistas.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                // TODO editar futbolistas
                // Toast.makeText(contexto, futbolistas.get(position).toString(), Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(ActividadPrincipal.this, ActividadMostrarFutbolista.class);
                // TO DO Parcelable
                intent.putExtra("MOSTRAR_Futbolista", listaFutbolistas.get(position));
                startActivity(intent);
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_actividad_principal, menu);
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
}
