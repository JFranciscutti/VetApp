package viewmodel

import android.content.Context
import android.widget.Toast
import androidx.lifecycle.ViewModel
import dao.DBHelper
import model.Animal
import model.Doctor

class IngresoViewModel : ViewModel() {

    fun cargarAnimal(
        nombre: String,
        tipo: String,
        raza: String,
        edad: String,
        causa: String,
        context: Context
    ): Boolean {
        val db: DBHelper = DBHelper(context, null)
        if (emptyFields(nombre, raza, edad, causa))
            return false
        else
            return db.cargarAnimal(Animal(0, nombre, tipo, raza, edad, causa, "-", "-"))
    }

    fun getAllDoctores(context: Context): ArrayList<Doctor> {
        val db = DBHelper(context, null)

        return db.getAllDoctores()
    }

    private fun emptyFields(
        nombre: String,
        raza: String,
        edad: String,
        causa: String
    ): Boolean {
        return nombre.isEmpty() && raza.isEmpty() && edad.isEmpty() && causa.isEmpty()
    }

    fun getCantAnimales(context: Context): Int {
        val db = DBHelper(context, null)
        return db.cant_animales()
    }

    fun initDoctores(context: Context) {
        val db = DBHelper(context, null)
        db.inicializarDoctores()
    }

    fun getRevisiones(context: Context): Array<Int> {
        val db = DBHelper(context,null)
        return db.revisaciones()
    }
}