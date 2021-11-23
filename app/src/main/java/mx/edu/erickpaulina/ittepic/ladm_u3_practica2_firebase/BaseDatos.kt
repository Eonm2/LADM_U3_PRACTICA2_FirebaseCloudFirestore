package mx.edu.erickpaulina.ittepic.ladm_u3_practica2_firebase

import android.database.sqlite.SQLiteDatabase
import android.content.Context
import android.database.sqlite.SQLiteOpenHelper



class BaseDatos(
    context: Context?,
    name: String?,
    factory: SQLiteDatabase.CursorFactory?,
    version: Int
) : SQLiteOpenHelper(context, name, factory, version) {
    override fun onCreate(p: SQLiteDatabase?) {
        p?.execSQL("CREATE TABLE NOTAS(IDNOTA INTEGER PRIMARY KEY AUTOINCREMENT, TITULO VARCHAR(200), CONTENIDO VARCHAR(200), HORA TIME, FECHA DATE)")

    }

    override fun onUpgrade(p: SQLiteDatabase?, p1: Int, p2: Int) {
    }

}