package view

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.*
import androidx.lifecycle.ViewModelProvider
import edu.example.vetapp.R
import model.Animal
import model.Doctor
import org.w3c.dom.Text
import viewmodel.RevisarViewModel

class RevisacionActivity : AppCompatActivity() {
    var id_animal: Int = 0
    lateinit var nombre_animal: TextView
    lateinit var tipo_animal: TextView
    lateinit var causas_animal: TextView
    lateinit var medicamentos: EditText
    lateinit var btn_confirmar: Button
    lateinit var revisionVM: RevisarViewModel
    lateinit var sp_doctores: Spinner
    lateinit var doctores: ArrayList<Doctor>
    lateinit var doc_seleccionado: Doctor


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_revisacion)

        revisionVM = ViewModelProvider(this).get(RevisarViewModel::class.java)

        inicializarComponentes()
        inicializarSpinner()
        mapperElements()

        sp_doctores.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(
                parent: AdapterView<*>?,
                view: View?,
                position: Int,
                id: Long
            ) {
                doc_seleccionado = doctores.get(position)
            }

            override fun onNothingSelected(parent: AdapterView<*>?) {
                Toast.makeText(applicationContext, "No hay doctor seleccionado", Toast.LENGTH_LONG)
                    .show()
            }
        }


        btn_confirmar.setOnClickListener(View.OnClickListener {
            if (medicamentos.text.toString().isEmpty())
                Toast.makeText(this, "Ingrese los medicamentos", Toast.LENGTH_LONG).show()
            else {
                if (doc_disponible(doc_seleccionado)) {
                    if (revisionVM.cargarRevisacion(
                            id_animal,
                            doc_seleccionado.id,
                            medicamentos.text.toString(),
                            it.context
                        )
                    ) {
                        Toast.makeText(this, "Revision confirmada!", Toast.LENGTH_LONG).show()
                        this.finish()
                    } else
                        Toast.makeText(this, "No se cargó la revisión.", Toast.LENGTH_LONG).show()
                } else {
                    Toast.makeText(this, "El doctor tiene el día lleno", Toast.LENGTH_LONG).show()
                }
            }
        })
    }

    private fun mapperElements() {
        nombre_animal.text = intent.getStringExtra("nombre")
        tipo_animal.text = intent.getStringExtra("tipo")
        causas_animal.text = intent.getStringExtra("causas")
        id_animal = intent.getIntExtra("id_animal", 0)
    }


    private fun inicializarComponentes() {
        nombre_animal = findViewById(R.id.r_nombre)
        tipo_animal = findViewById(R.id.r_tipo)
        causas_animal = findViewById(R.id.r_causa_animal)
        medicamentos = findViewById(R.id.r_med_input)
        btn_confirmar = findViewById(R.id.r_btn_confirmar)
        sp_doctores = findViewById(R.id.sp_doctores)
    }

    private fun inicializarSpinner() {
        doctores = revisionVM.getAllDoctores(this)
        var id_doctores = arrayOf(
            doctores.get(0).nombre + " " + doctores.get(0).apellido,
            doctores.get(1).nombre + " " + doctores.get(1).apellido,
            doctores.get(2).nombre + " " + doctores.get(2).apellido,
            doctores.get(3).nombre + " " + doctores.get(3).apellido,
            doctores.get(4).nombre + " " + doctores.get(4).apellido
        )
        sp_doctores.adapter = ArrayAdapter(this, android.R.layout.simple_spinner_item, id_doctores)
    }

    private fun doc_disponible(doc: Doctor): Boolean {
        return revisionVM.cant_revisaciones_doc(doc.id, this) < 5
    }

}