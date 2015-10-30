package es.upm.miw.jugadores;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.List;

import es.upm.miw.jugadores.models.Futbolista;

public class FutbolistaAdapter extends ArrayAdapter<Futbolista>{

    private Context _contexto;
    private List<Futbolista>_futbolistas;

    public FutbolistaAdapter(Context context, List<Futbolista> futbolistas) {
        super(context, R.layout.layout_listado_futbolista,futbolistas);
        this._futbolistas=futbolistas;
        this._contexto=context;
    }

    @Override
    public boolean isEnabled(int position) {
        return true;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if(convertView==null){
            LayoutInflater inflater=(LayoutInflater)_contexto.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView=inflater.inflate(R.layout.layout_listado_futbolista, null);
        }
        //Recuperamos el futbolista sobre el que se ha pulsado
        Futbolista futbolista=this._futbolistas.get(position);
        //Añadimos los datos del futbolista a la vista
        if(futbolista!=null){
            TextView tvid=(TextView)convertView.findViewById(R.id.tvFutbolistaId);
            tvid.setText(String.valueOf(futbolista.get_id()));
            TextView tvnombre=(TextView)convertView.findViewById(R.id.tvFutbolistaNombre);
            tvnombre.setText(futbolista.get_nombre());
            TextView tvequipo=(TextView)convertView.findViewById(R.id.tvFutbolistaEquipo);
            tvequipo.setText(futbolista.get_equipo());
        }
        return convertView;
    }
}
