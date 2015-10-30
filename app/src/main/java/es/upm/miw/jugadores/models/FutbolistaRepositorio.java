package es.upm.miw.jugadores.models;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import static es.upm.miw.jugadores.models.FutbolistaContract.TablaFutbolista;

public class FutbolistaRepositorio extends SQLiteOpenHelper{

    private static final String DATABASE_FILENAME="futbolistas.db";

    private static final int DATABASE_VERSION=1;

    public FutbolistaRepositorio(Context context) {
        super(context, DATABASE_FILENAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String sql="CREATE TABLE "+TablaFutbolista.TABLE_NAME+"("
        +TablaFutbolista.COL_NAME_ID+" INTEGER PRIMARY KEY AUTOINCREMENT,"
        +TablaFutbolista.COL_NAME_NOMBRE+" TEXT,"
        +TablaFutbolista.COL_NAME_DORSAL+" INTEGER,"
        +TablaFutbolista.COL_NAME_LESIONADO+" INTEGER,"
        +TablaFutbolista.COL_NAME_EQUIPO+" TEXT,"
        +TablaFutbolista.COL_NAME_URL_IMAGEN+" TEXT"
        +")";
        db.execSQL(sql);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        //Borramos la tabla
        String sql="DROP TABLE IF EXIST "+TablaFutbolista.TABLE_NAME;
        db.execSQL(sql);
        //Generamos la nueva tabla
        onCreate(db);
    }

    public long add(Futbolista futbolista){
        //Acceder a la BBDD en modo esccritura
        SQLiteDatabase db=this.getWritableDatabase();
        //Creamos un contenedor de valores
        ContentValues valores=new ContentValues();
        //Añadimos los valores del futbolista
        //valores.put(TablaFutbolista.COL_NAME_ID,futbolista.get_id());
        valores.put(TablaFutbolista.COL_NAME_NOMBRE,futbolista.get_nombre());
        valores.put(TablaFutbolista.COL_NAME_DORSAL,futbolista.get_dorsal());
        valores.put(TablaFutbolista.COL_NAME_LESIONADO,futbolista.is_lesionado());
        valores.put(TablaFutbolista.COL_NAME_EQUIPO,futbolista.get_equipo());
        valores.put(TablaFutbolista.COL_NAME_URL_IMAGEN,futbolista.get_url_imagen());
        return db.insert(TablaFutbolista.TABLE_NAME,null,valores);
    }
}
