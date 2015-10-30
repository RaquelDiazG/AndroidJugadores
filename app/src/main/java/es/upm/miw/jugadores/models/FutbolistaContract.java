package es.upm.miw.jugadores.models;

import android.provider.BaseColumns;

public abstract class FutbolistaContract {

    public static class TablaFutbolista implements BaseColumns {

        public static final String TABLE_NAME="futbolistas";

        public static final String COL_NAME_ID=_ID;
        public static final String COL_NAME_NOMBRE="nombre";
        public static final String COL_NAME_DORSAL="dorsal";
        public static final String COL_NAME_LESIONADO="lesionado";
        public static final String COL_NAME_EQUIPO="equipo";
        public static final String COL_NAME_URL_IMAGEN="url_imagen";
    }
}
