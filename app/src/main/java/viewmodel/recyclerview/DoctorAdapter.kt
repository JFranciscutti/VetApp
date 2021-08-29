package viewmodel.recyclerview

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import edu.example.vetapp.R
import model.Doctor

class DoctorAdapter(val lista: ArrayList<Doctor>, val cant_animales: Array<Int>) :
    RecyclerView.Adapter<DoctorAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DoctorAdapter.ViewHolder {
        val view =
            LayoutInflater.from(parent.context).inflate(R.layout.doctores_layout, parent, false)
        return ViewHolder(view)
    }

    override fun getItemCount(): Int {
        return lista.size
    }

    override fun onBindViewHolder(holder: DoctorAdapter.ViewHolder, position: Int) {
        holder.nombre.text = lista[position].nombre + " "
        holder.apellido.text = lista[position].apellido
        holder.id.text = lista[position].id.toString()
        holder.cant.text = cant_animales[position].toString()
    }


    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        var nombre: TextView
        var apellido: TextView
        var id: TextView
        var cant: TextView

        init {
            nombre = view.findViewById(R.id.t_nombre)
            apellido = view.findViewById(R.id.t_apellido)
            id = view.findViewById(R.id.t_id)
            cant = view.findViewById(R.id.t_cant_animales)
        }
    }
}