package mx.edu.erickpaulina.ittepic.ladm_u3_practica2_firebase

import android.content.ContentValues
import android.content.Context

class Notas(p:Context) {
    var idNotas = 0
    var titulo = ""
    var contenido = ""
    var hora = ""
    var fecha = ""
    val pnt = p

    fun insertar(): Boolean{
        val tablaNotas = BaseDatos(pnt,"notas",null,1).writableDatabase
        val datos = ContentValues()
        datos.put("titulo",titulo)
        datos.put("contenido",contenido)
        datos.put("hora",hora)
        datos.put("fecha",fecha)
        val resultado = tablaNotas.insert("NOTAS",null,datos)

        if(resultado == -1L) {
            return false
        }
        return true
    }

    fun consultar(): ArrayList<String>{
        //SELECT * FROM CONDUCTOR
        val tablaNotas = BaseDatos(pnt, "notas",null,1).readableDatabase
        val resultado = ArrayList<String>()
        val cursor = tablaNotas.query("NOTAS", arrayOf("*"), null,null,null,null,null)
        //Cursor es un objeto tipo tabla dinamica que contiene los resultados de una consulta
        if(cursor.moveToFirst()){
            //Si se posiciona en 1er renglon resultado, si se obtuvo resultados
            do{
                //LEER LA DATA
                var dato = cursor.getString(1)+"\n"+cursor.getString(2)+"\n"+cursor.getString(3)+"\n"+cursor.getString(4)
                resultado.add(dato)
            }while(cursor.moveToNext())
        }else{
            //Si moveFirst regresa falso, entra al ELSE significando que no hay ni siquiera 1 resultado
            resultado.add("NO SE ENCONTRO DATA A MOSTRAR")
        }
        return resultado
    }

    fun obtenerIDs(): ArrayList<Int>{
        //SELECT * FROM CONDUCTOR
        val tablaNotas = BaseDatos(pnt, "notas",null,1).readableDatabase
        val resultado = ArrayList<Int>()
        val cursor = tablaNotas.query("NOTAS", arrayOf("*"), null,null,null,null,null)
        //Cursor es un objeto tipo tabla dinamica que contiene los resultados de una consulta
        if(cursor.moveToFirst()){
            //Si se posiciona en 1er renglon resultado, si se obtuvo resultados
            do{
                //LEER LA DATA
                var dato = cursor.getInt(0)
                resultado.add(dato)
            }while(cursor.moveToNext())
        }
        return resultado
    }

    fun eliminar(idEliminar:Int):Boolean{
        val tablaNotas = BaseDatos(pnt,"notas",null,1).writableDatabase
        val resultado = tablaNotas.delete("NOTAS","IDNOTA=?", arrayOf(idEliminar.toString()))
        if(resultado==0) return false
        return true
    }

    fun consultar(idABuscar:String): Notas {
        val tablaNotas = BaseDatos(pnt,"notas",null,1).readableDatabase
        val cursor = tablaNotas.query("NOTAS", arrayOf("*"),"IDNOTA=?", arrayOf(idABuscar),null,null,null)
        val nota = Notas(MainActivity2())
        if(cursor.moveToFirst()){
            nota.titulo = cursor.getString(1)
            nota.contenido = cursor.getString(2)
            nota.hora = cursor.getString(3)
            nota.fecha = cursor.getString(4)
        }
        return nota
    }

    fun actualizar(idActualizar : String):Boolean{
        val tablaNotas = BaseDatos(pnt,"notas",null,1).writableDatabase
        val datos = ContentValues()

        datos.put("titulo",titulo)
        datos.put("contenido",contenido)
        datos.put("hora",hora)
        datos.put("fecha",fecha)
        val resultado = tablaNotas.update("NOTAS",datos,"IDNOTA=?", arrayOf(idActualizar))
        if(resultado==0)return false
        return true
    }
}