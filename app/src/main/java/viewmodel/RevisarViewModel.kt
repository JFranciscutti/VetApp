package viewmodel

import android.content.Context
import androidx.lifecycle.ViewModel
import dao.DBHelper
import model.Doctor

class RevisarViewModel : ViewModel() {


    fun getAllDoctores(context: Context): ArrayList<Doctor> {
        val db = DBHelper(context, null)

        return db.getAllDoctores()
    }

    fun cant_revisaciones_doc(id_doctor: Int, context: Context): Int {
        val db = DBHelper(context, null)
        return db.cant_revisaciones(id_doctor)
    }

    fun cargarRevisacion(
        id_animal: Int,
        id_doctor: Int,
        medicamento: String,
        context: Context
    ): Boolean {
        val db = DBHelper(context, null)

        return db.cargarRevisacion(id_animal, id_doctor, medicamento)
    }
}