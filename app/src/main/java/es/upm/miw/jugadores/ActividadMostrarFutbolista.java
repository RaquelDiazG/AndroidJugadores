package es.upm.miw.jugadores;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

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

             TareaCargarImagen tarea = new TareaCargarImagen(futbolista.get_url_imagen());
            //ivImagenFutbolista.setTag(futbolista.get_url_imagen());
             tarea.execute(ivImagenFutbolista);

             //Picasso.with(contexto).load(url).into(imageView);
            // http://square.github.io/picasso/
             //Picasso.with(contexto).load(futbolista.get_url_imagen()).into(ivImagenFutbolista);
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

    private class TareaCargarImagen extends AsyncTask<ImageView, Void, Bitmap> {

        private String url;
        private ImageView imageView = null;

        public TareaCargarImagen(String url) {
            this.url = url;
        }


        @Override
        protected Bitmap doInBackground(ImageView... imageViews) {
            this.imageView = imageViews[0];
            Bitmap bmp = null;
            try {
                //URL ulrn = new URL(URL_OBJETIVO);
                URL ulrn=new URL(url);
                HttpURLConnection con = (HttpURLConnection) ulrn.openConnection();
                InputStream is = con.getInputStream();
                bmp = BitmapFactory.decodeStream(is);
            } catch (Exception e) {
                Log.e("ERROR", e.getMessage());
            }
            return bmp;
        }

        @Override
        protected void onPostExecute(Bitmap result) {
            imageView.setImageBitmap(result);
        }
    } // TareaCargarImagen
}
