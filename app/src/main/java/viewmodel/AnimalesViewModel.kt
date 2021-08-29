package viewmodel

import android.content.Context
import androidx.lifecycle.ViewModel
import dao.DBHelper
import model.Animal

class AnimalesViewModel : ViewModel() {

    fun getAllAnimales(context: Context): ArrayList<Animal> {
        val db = DBHelper(context, null)
        return db.getAllAnimales()
    }

}