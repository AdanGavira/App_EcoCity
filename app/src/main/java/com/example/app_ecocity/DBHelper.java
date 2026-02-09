package com.example.app_ecocity;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

//DESUSADO EN HITO3 PERO MANTENIDO PARA DEMOSTRAR SU USO ANTERIORMENTE

//Base de datos local en SQLite
/*public class DBHelper extends SQLiteOpenHelper {

    private static final String DB_NAME = "EcoCity";
    private static final int DB_VERSION = 1;

    public DBHelper(Context context){
        super(context, DB_NAME, null, DB_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE incidencias (" +
                "id INTEGER PRIMARY KEY AUTOINCREMENT," +
                "titulo TEXT," +
                "descripcion TEXT," +
                "prioridad TEXT," +
                "fecha TEXT," +
                "foto_url TEXT,"+
                "ubicacion TEXT)");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS incidencias");
        onCreate(db);
    }

    // Insertar incidencia en la base de datos
    public void insertarIncidencia(String titulo, String descripcion, String prioridad, String fecha, String fotoUrl, String ubicacion) {

        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();

        values.put("titulo", titulo);
        values.put("descripcion", descripcion);
        values.put("prioridad", prioridad);
        values.put("fecha", fecha);
        values.put("foto_url", fotoUrl);
        values.put("ubicacion", ubicacion);

        db.insert("incidencias", null, values);
        db.close();
    }

    // Recoger las incidencias guardadas
    public List<Incidencia> obtenerIncidencias() {
        List<Incidencia> lista = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = db.rawQuery("SELECT * FROM incidencias ORDER BY id DESC", null);

        Log.d("DB_TEST", "Filas encontradas: " + cursor.getCount());

        if (cursor.moveToFirst()) {
            do {
                lista.add(new Incidencia(
                        cursor.getInt(0),
                        cursor.getString(1),
                        cursor.getString(2),
                        cursor.getString(3),
                        cursor.getString(4),
                        cursor.getString(5),
                        cursor.getString(6)
                ));
            } while (cursor.moveToNext());
        }

        cursor.close();
        db.close();
        return lista;
    }

    //Obtener los datos de una licencia especifica según su Id
    public Incidencia obtenerIncidenciaPorId(int id) {

        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM incidencias WHERE id = ?", new String[]{String.valueOf(id)}
        );

        Incidencia incidencia = null;

        if (cursor.moveToFirst()) {
            incidencia = new Incidencia(
                    cursor.getInt(0),
                    cursor.getString(1),
                    cursor.getString(2),
                    cursor.getString(3),
                    cursor.getString(4),
                    cursor.getString(5),
                    cursor.getString(6)
            );
        }

        cursor.close();
        db.close();
        return incidencia;
    }

    //Actualizar los datos de una incidencia

    public void actualizarIncidencia(int id, String titulo, String descripcion, String prioridad, String fecha, String fotoUrl, String ubicacion) {

        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();

        values.put("titulo", titulo);
        values.put("descripcion", descripcion);
        values.put("prioridad", prioridad);
        values.put("fecha", fecha);
        values.put("foto_url", fotoUrl);
        values.put("ubicacion", ubicacion);

        db.update("incidencias", values, "id = ?", new String[]{String.valueOf(id)});
        db.close();
    }

    //Borrar una incidencia

    public void borrarIncidencia(int id) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete("incidencias", "id = ?", new String[]{String.valueOf(id)});
        db.close();
    }

}*/
