package mx.edu.erickpaulina.ittepic.ladm_u3_practica2_firebase

import android.os.Build
import android.os.Bundle
import android.widget.*
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_main2.*
import java.time.LocalDate
import java.time.LocalTime


class MainActivity2 : AppCompatActivity() {


    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main2)

        botonG.setOnClickListener {
            val notas = Notas(this)

            notas.titulo = titulo.text.toString()
            notas.contenido = contenido.text.toString()
            notas.hora = LocalTime.now().toString()
            notas.fecha = LocalDate.now().toString()
            val resultado = notas.insertar()//regresa true si se insertó, false si no se insertó
            if(resultado){
                //positivo
                Toast.makeText(this,"ÉXITO, SE INSERTO",Toast.LENGTH_LONG).show()
                this.finish()
            }else{
                //negativo
                Toast.makeText(this,"ERROR NO SE INSERTO",Toast.LENGTH_LONG).show()
            }
        }

        atras.setOnClickListener{
            finish()
        }
    }


}