package es.upm.miw.jugadores.models;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;
import java.util.List;

import static es.upm.miw.jugadores.models.FutbolistaContract.TablaFutbolista;

public class FutbolistaRepositorio extends SQLiteOpenHelper {

    private static final String DATABASE_FILENAME = "futbolistas.db";

    private static final int DATABASE_VERSION = 1;

    public FutbolistaRepositorio(Context context) {
        super(context, DATABASE_FILENAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String sql = "CREATE TABLE " + TablaFutbolista.TABLE_NAME + "("
                + TablaFutbolista.COL_NAME_ID + " INTEGER PRIMARY KEY AUTOINCREMENT,"
                + TablaFutbolista.COL_NAME_NOMBRE + " TEXT,"
                + TablaFutbolista.COL_NAME_DORSAL + " INTEGER,"
                + TablaFutbolista.COL_NAME_LESIONADO + " INTEGER,"
                + TablaFutbolista.COL_NAME_EQUIPO + " TEXT,"
                + TablaFutbolista.COL_NAME_URL_IMAGEN + " TEXT"
                + ")";
        db.execSQL(sql);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        //Borramos la tabla
        String sql = "DROP TABLE IF EXIST " + TablaFutbolista.TABLE_NAME;
        db.execSQL(sql);
        //Generamos la nueva tabla
        onCreate(db);
    }

    public long add(Futbolista futbolista) {
        //Acceder a la BBDD en modo esccritura
        SQLiteDatabase db = this.getWritableDatabase();
        //Creamos un contenedor de valores
        ContentValues valores = new ContentValues();
        //Añadimos los valores del futbolista
        //valores.put(TablaFutbolista.COL_NAME_ID,futbolista.get_id());
        valores.put(TablaFutbolista.COL_NAME_NOMBRE, futbolista.get_nombre());
        valores.put(TablaFutbolista.COL_NAME_DORSAL, futbolista.get_dorsal());
        valores.put(TablaFutbolista.COL_NAME_LESIONADO, futbolista.is_lesionado());
        valores.put(TablaFutbolista.COL_NAME_EQUIPO, futbolista.get_equipo());
        valores.put(TablaFutbolista.COL_NAME_URL_IMAGEN, futbolista.get_url_imagen());
        return db.insert(TablaFutbolista.TABLE_NAME, null, valores);
    }

    public List<Futbolista> getAll() {
        return getAll(TablaFutbolista.COL_NAME_ID, true);
    }

    public List<Futbolista> getAll(String columna, boolean ordenAscendente) {
        List<Futbolista> resultado = new ArrayList<>();
        String consultaSQL = "SELECT * FROM " + TablaFutbolista.TABLE_NAME
                + " ORDER BY " + columna + (ordenAscendente ? " ASC" : " DESC");

        // Accedo a la DB en modo lectura
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(consultaSQL, null);

        // Recorrer cursor asignando resultados
        if (cursor.moveToFirst()) {
            while (!cursor.isAfterLast()) {
                Futbolista futbolista = new Futbolista(
                        cursor.getInt(cursor.getColumnIndex(TablaFutbolista.COL_NAME_ID)),
                        cursor.getString(cursor.getColumnIndex(TablaFutbolista.COL_NAME_NOMBRE)),
                        cursor.getInt(cursor.getColumnIndex(TablaFutbolista.COL_NAME_DORSAL)),
                        cursor.getInt(cursor.getColumnIndex(TablaFutbolista.COL_NAME_LESIONADO)) != 0,
                        cursor.getString(cursor.getColumnIndex(TablaFutbolista.COL_NAME_EQUIPO)),
                        cursor.getString(cursor.getColumnIndex(TablaFutbolista.COL_NAME_URL_IMAGEN))
                );
                resultado.add(futbolista);
                cursor.moveToNext();
            }
            cursor.close();
        }

        return resultado;
    }


    public long count() {
        String sql = "SELECT COUNT(*) FROM " + TablaFutbolista.TABLE_NAME;

        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(sql, null);
        cursor.moveToFirst();
        long numero = cursor.getLong(0);
        cursor.close();

        return numero;
    }

    public long deleteAll() {
        SQLiteDatabase db = this.getWritableDatabase();
        return db.delete(TablaFutbolista.TABLE_NAME, "1", null);
    }

    public Futbolista getFutbolistaByID(int id) {
        String sql = "SELECT * FROM " + TablaFutbolista.TABLE_NAME
                + " WHERE " + TablaFutbolista.COL_NAME_ID + " = ?";
        SQLiteDatabase db = this.getReadableDatabase();
        Futbolista futbolista = null;
        Cursor cursor = db.rawQuery(
                sql,
                new String[]{ String.valueOf(id) }
        );

        if (cursor.moveToFirst()) {
            futbolista = new Futbolista(
                    cursor.getInt(cursor.getColumnIndex(TablaFutbolista.COL_NAME_ID)),
                    cursor.getString(cursor.getColumnIndex(TablaFutbolista.COL_NAME_NOMBRE)),
                    cursor.getInt(cursor.getColumnIndex(TablaFutbolista.COL_NAME_DORSAL)),
                    cursor.getInt(cursor.getColumnIndex(TablaFutbolista.COL_NAME_LESIONADO)) != 0,
                    cursor.getString(cursor.getColumnIndex(TablaFutbolista.COL_NAME_EQUIPO)),
                    cursor.getString(cursor.getColumnIndex(TablaFutbolista.COL_NAME_URL_IMAGEN))
            );
            cursor.close();
        }

        return futbolista;
    }
}
