package mx.edu.erickpaulina.ittepic.ladm_u3_practica2_firebase

import android.content.ContentValues.TAG
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.ArrayAdapter
import android.widget.ListView
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import com.google.firebase.firestore.FieldPath
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {
    val db = Firebase.firestore
    var idNotas = ArrayList<Int>()
    override fun onCreate(savedInstanceState: Bundle?) {
        setTheme(R.style.SplashScreen)
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        llenarTabla()
        btnNuevaNota.setOnClickListener {
            val intent = Intent(this,MainActivity2::class.java)
            startActivity(intent)

            AlertDialog.Builder(this).setMessage("¿DESEAS ACTUALIZAR LA LISTA?")
                .setPositiveButton("SI"){d,i->llenarTabla()}
                .setNegativeButton("NO"){d,i-> d.cancel()}
                .show()
        }
        btnSincronizar.setOnClickListener {
            actualizarFirebase()
        }


    }

    private fun actualizarFirebase(){
        db.collection("notas")
            .get()
            .addOnSuccessListener { documents ->
                for (document in documents) {
                    db.collection("notas").document(document.id.toString()).delete()
                }
                sincronizar()

            }
            .addOnFailureListener { exception ->
                Log.w(TAG, "Error getting documents: ", exception)
            }
    }

    private fun alerta(s:String){
        Toast.makeText(this,s, Toast.LENGTH_LONG).show()
    }

    private fun mensaje(s:String){
        AlertDialog.Builder(this)
            .setTitle("ATENCIÓN")
            .setMessage(s)
            .setPositiveButton("OK"){d,i->}
            .show()
    }

    fun llenarTabla(){
        val arregloNotas = Notas(this).consultar()
        listaNotas.adapter = ArrayAdapter<String>(this,android.R.layout.simple_list_item_2,android.R.id.text2,arregloNotas)
        idNotas.clear()
        idNotas = Notas(this).obtenerIDs()
        activarEvento(listaNotas)
    }

    private fun activarEvento(listaNotas : ListView){
        listaNotas.setOnItemClickListener{ adapterView, view, indiceSeleccionado, l ->
            val idSeleccionado = idNotas[indiceSeleccionado]
            AlertDialog.Builder(this)
                .setTitle("ATENCION")
                .setMessage("¿QUÉ DESEA HACER CON LA NOTA")
                .setPositiveButton("EDITAR"){d,i->actualizar(idSeleccionado)}
                .setNegativeButton("ELIMINAR"){d,i->eliminar(idSeleccionado)}
                .setNeutralButton("CANCELAR"){d,i-> d.cancel() }
                .show()
        }
    }

    private fun eliminar(idSeleccionado:Int){
        AlertDialog.Builder(this)
            .setTitle("IMPORTANTE")
            .setMessage("¿ESTA SEGURO QUE DESEA ELIMINAR ESTE ID ${idSeleccionado}")
            .setPositiveButton("SI"){d,i->
                val resultado = Notas(this).eliminar(idSeleccionado)
                if(resultado){
                    Toast.makeText(this,"SE ELIMINÓ CON ÉXITO", Toast.LENGTH_LONG).show()
                    llenarTabla()
                }else{
                    Toast.makeText(this,"ERROR, NO SE LOGRÓ ELIMINAR", Toast.LENGTH_LONG).show()
                }
            }
            .setNegativeButton("NO"){d,i-> d.cancel() }
            .show()
    }

    private fun actualizar(idSeleccionado:Int){
        val intento = Intent(this,MainActivity3::class.java)
        intento.putExtra("idActualizar",idSeleccionado.toString())
        startActivity(intento)

        AlertDialog.Builder(this).setMessage("¿DESEAS ACTUALIZAR LA LISTA?")
            .setPositiveButton("SI"){d,i->llenarTabla()}
            .setNegativeButton("NO"){d,i-> d.cancel()}
            .show()
    }


        private fun sincronizar() {
            val notas = Notas(this)
            var datos = notas.consultar()

            for (dato in datos){
                var split = dato.split("\n")
                var datosInsertar = hashMapOf(
                    "titulo" to split[0].toString(),
                    "contenido" to split[1].toString(),
                    "hora" to split[2].toString(),
                    "fecha" to split[3].toString()
                )
                db.collection("notas")
                    .add(datosInsertar)
                    .addOnSuccessListener {
                        alerta("Sincronizacion en la nube completada")
                    }
                    .addOnFailureListener {
                        mensaje("ERROR: ${it.message!!}")
                    }
            }
        }

}