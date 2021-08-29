package view

import android.annotation.SuppressLint
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.*
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import dao.DBHelper
import edu.example.vetapp.R
import viewmodel.IngresoViewModel
import viewmodel.recyclerview.AnimalAdapter
import viewmodel.recyclerview.DoctorAdapter

class IngresoActivity : AppCompatActivity() {
    lateinit var nombre: EditText
    lateinit var sp_tipo: Spinner
    lateinit var raza: EditText
    lateinit var edad: EditText
    lateinit var causa: EditText
    lateinit var btn_ingresar: Button
    lateinit var btn_doctores: Button
    lateinit var btn_animales: Button
    lateinit var rv_doctores: RecyclerView
    lateinit var adaptador: DoctorAdapter

    lateinit var ingresoVM: IngresoViewModel

    val tipos = arrayOf("Perro", "Gato", "Conejo")

    @SuppressLint("WrongConstant")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_ingreso)

        ingresoVM = ViewModelProvider(this).get(IngresoViewModel::class.java)
        inicializarComponentes()
        inicializarSpinner()
        ingresoVM.initDoctores(this)

        var tipoSeleccionado: String = ""
        sp_tipo.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onNothingSelected(parent: AdapterView<*>?) {
                Toast.makeText(applicationContext, "No hay items seleccionados", Toast.LENGTH_LONG)
                    .show()
            }

            override fun onItemSelected(
                parent: AdapterView<*>?,
                view: View?,
                position: Int,
                id: Long
            ) {
                tipoSeleccionado = tipos[position]
            }
        }

        btn_ingresar.setOnClickListener(View.OnClickListener {
            var cant_animales = ingresoVM.getCantAnimales(this)
            if (cant_animales < 20) {
                if (ingresoVM.cargarAnimal(
                        nombre.text.toString(),
                        tipoSeleccionado,
                        raza.text.toString(),
                        edad.text.toString(),
                        causa.text.toString(),
                        it.context
                    )
                )
                    Toast.makeText(
                        it.context,
                        "Animal cargado correctamente.",
                        Toast.LENGTH_LONG
                    ).show()
                else
                    Toast.makeText(it.context, "Fallo al cargar animal", Toast.LENGTH_LONG).show()

                clearFields()
            } else {
                Toast.makeText(
                    this,
                    "Alcanzamos la cantidad máxima diaria de animales",
                    Toast.LENGTH_LONG
                )
                    .show()
                btn_ingresar.isEnabled = false
                btn_ingresar.isClickable = false
            }
        })

        btn_doctores.setOnClickListener(View.OnClickListener {
            rv_doctores.layoutManager = LinearLayoutManager(this, LinearLayout.VERTICAL, false)
            adaptador =
                DoctorAdapter(ingresoVM.getAllDoctores(this), ingresoVM.getRevisiones(this))
            rv_doctores.adapter = adaptador
            rv_doctores.addItemDecoration(
                DividerItemDecoration(
                    this,
                    DividerItemDecoration.VERTICAL
                )
            )

        })

        btn_animales.setOnClickListener(View.OnClickListener {
            var intent = Intent(this, AnimalesActivity::class.java)
            startActivity(intent)
        })
    }

    private fun inicializarComponentes() {
        nombre = findViewById(R.id.i_nombre)
        sp_tipo = findViewById(R.id.i_tipo)
        raza = findViewById(R.id.i_raza)
        edad = findViewById(R.id.i_edad)
        causa = findViewById(R.id.i_causa)
        btn_ingresar = findViewById(R.id.i_ingresar)
        btn_doctores = findViewById(R.id.i_btn_doctores)
        btn_animales = findViewById(R.id.i_btn_animales)
        rv_doctores = findViewById(R.id.rv_doctores)
    }

    private fun inicializarSpinner() {
        sp_tipo.adapter = ArrayAdapter(this, android.R.layout.simple_spinner_item, tipos)
    }

    private fun clearFields() {
        nombre.text.clear()
        sp_tipo.clearFocus()
        raza.text.clear()
        edad.text.clear()
        causa.text.clear()

    }
}