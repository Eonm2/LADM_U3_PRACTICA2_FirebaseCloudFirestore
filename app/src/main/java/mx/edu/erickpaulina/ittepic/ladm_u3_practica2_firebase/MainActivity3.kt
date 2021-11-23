package mx.edu.erickpaulina.ittepic.ladm_u3_practica2_firebase


import android.os.Build
import android.os.Bundle
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_main3.*
import java.time.LocalDate
import java.time.LocalTime

class MainActivity3 : AppCompatActivity() {
    var id = ""

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main3)

        var extra = intent.extras
        id = extra!!.getString("idActualizar")!!
        val notas = Notas(this).consultar(id)
        titulo1.setText(notas.titulo)
        contenido1.setText(notas.contenido)

        botonG1.setOnClickListener {
            val notasActualizar = Notas(this)
            notasActualizar.titulo = titulo1.text.toString()
            notasActualizar.contenido = contenido1.text.toString()
            notasActualizar.hora = LocalTime.now().toString()
            notasActualizar.fecha = LocalDate.now().toString()

            val resulado = notasActualizar.actualizar(id)
            if(resulado){
                Toast.makeText(this,"ÉXITO, SE ACTUALIZÓ",Toast.LENGTH_LONG).show()
            }else{
                Toast.makeText(this,"ERROR, NO SE PUDO ACTUALIZAR",Toast.LENGTH_LONG).show()
            }
        }

        atras1.setOnClickListener {
            finish()
        }


    }
}