package view

import android.annotation.SuppressLint
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.LinearLayout
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import edu.example.vetapp.R
import viewmodel.AnimalesViewModel
import viewmodel.recyclerview.AnimalAdapter

class AnimalesActivity : AppCompatActivity() {
    lateinit var rv_animales: RecyclerView
    lateinit var animalesVM: AnimalesViewModel
    lateinit var adaptador: AnimalAdapter

    @SuppressLint("WrongConstant")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_animales)

        //Traigo el ViewModel
        animalesVM = ViewModelProvider(this).get(AnimalesViewModel::class.java)
        //Inicializo el RecyclerView
        rv_animales = findViewById(R.id.rv_animales)
        rv_animales.layoutManager = LinearLayoutManager(this, LinearLayout.VERTICAL, false)
        //Le pongo el adaptador al RecyclerView
        adaptador = AnimalAdapter(animalesVM.getAllAnimales(this))
        rv_animales.adapter = adaptador
        //Agrego separador de items
        rv_animales.addItemDecoration(DividerItemDecoration(this, DividerItemDecoration.VERTICAL))
    }
}