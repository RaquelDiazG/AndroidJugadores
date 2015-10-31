package es.upm.miw.jugadores;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;

import es.upm.miw.jugadores.models.Futbolista;

public class ActividadMostrarFutbolista extends AppCompatActivity {

    TextView tvId, tvNombre, tvDorsal, tvEquipo;
    ImageView ivLesionado, ivImagenFutbolista;
    Context contexto;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_actividad_mostrar_futbolista);

        contexto = getApplicationContext();

        // Mostrar el icono back
        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);

        // Recuperar piloto
        Futbolista futbolista = getIntent().getParcelableExtra("MOSTRAR_Futbolista");
        Log.i("MOSTRAR", futbolista.toString());

        // Identificar vistas
        tvId               = (TextView)  findViewById(R.id.tvMostrarFutbolistaId);
        tvNombre           = (TextView)  findViewById(R.id.tvMostrarFutbolistaNombre);
        tvDorsal           = (TextView)  findViewById(R.id.tvMostrarFutbolistaDorsal);
        tvEquipo           = (TextView)  findViewById(R.id.tvMostrarFutbolistaEquipo);
        ivLesionado        = (ImageView) findViewById(R.id.ivMostrarFutbolistaLesionado);
        ivImagenFutbolista = (ImageView) findViewById(R.id.ivMostrarFutbolistaImagen);

        // Asignar valores a las vistas
        tvId.setText(String.format("%d", futbolista.get_id()));
        tvNombre.setText(futbolista.get_nombre());
        tvDorsal.setText(String.format("%d", futbolista.get_dorsal()));
        tvEquipo.setText(futbolista.get_equipo());
        ivLesionado.setImageResource(futbolista.is_lesionado()
                ? android.R.drawable.checkbox_on_background
                : android.R.drawable.checkbox_off_background);

        if (futbolista.get_url_imagen() == null)
            ivImagenFutbolista.setImageResource(R.drawable.no_foto);
        else if (!hayRed())
            ivImagenFutbolista.setImageResource(R.drawable.icon_desconectado);
        else {  // recuperar imagen
            // Drawable imagen = null;
            // Resources res = this.getApplicationContext().getResources();
            // InputStream is = new URL(piloto.get_url_imagen()).openStream();
            // imagen = new BitmapDrawable(res, BitmapFactory.decodeStream(is));
            // ivImagenPiloto.setImageDrawable(imagen);
            //
            // podría ser... pero lanza NetworkOnMainThreadException

            // TareaCargarImagen tarea = new TareaCargarImagen();
            // ivImagenPiloto.setTag(piloto.get_url_imagen());
            // tarea.execute(ivImagenPiloto);

            // Picasso.with(context).load(url).into(imageView);
            // http://square.github.io/picasso/
            // Picasso.with(contexto).load(futbolista.get_url_imagen()).into(ivImagenFutbolista);
        }

        setResult(RESULT_OK);
    }

    /**
     * Determina si hay conexión a la red (wifi ó movil)
     * @return bool resultado
     */
    public boolean hayRed() {
        ConnectivityManager cm = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        boolean conectividad = false;

        NetworkInfo activeNetwork = cm.getActiveNetworkInfo();
        if (activeNetwork != null ) {
            boolean hayWifi = activeNetwork.getType() == ConnectivityManager.TYPE_WIFI;
            boolean hayMobile = activeNetwork.getType() == ConnectivityManager.TYPE_MOBILE;
            conectividad = hayWifi || hayMobile;
        }

        return conectividad;
    }
}
