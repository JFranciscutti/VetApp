package viewmodel.recyclerview

import android.content.Intent
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import edu.example.vetapp.R
import model.Animal
import view.RevisacionActivity

class AnimalAdapter(val lista: ArrayList<Animal>) :
    RecyclerView.Adapter<AnimalAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view: View =
            LayoutInflater.from(parent.context).inflate(R.layout.animales_layout, parent, false)
        return ViewHolder(view)
    }

    override fun getItemCount(): Int {
        return lista.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.nombre.text = lista[position].nombre + " "
        holder.tipo.text = lista[position].tipo + " "
        holder.raza.text = lista[position].raza
        holder.edad.text = lista[position].edad + " "
        holder.causa.text = lista[position].causa
        holder.id.text = lista[position].id.toString() + " "
        holder.medicamentos.text = lista[position].medicamentos
        holder.doc_id.text = lista[position].id_doc


        holder.revisar.setOnClickListener(View.OnClickListener {
            try {
                val intent = Intent(it.context, RevisacionActivity::class.java)
                intent.putExtra("id_animal", lista[position].id)
                intent.putExtra("nombre", lista[position].nombre)
                intent.putExtra("tipo", lista[position].tipo)
                intent.putExtra("causas", lista[position].causa)
                it.context.startActivity(intent)

            } catch (e: Exception) {
                Log.e("ROMPE EN AnimalAdapter ", e.message.toString())

            }
        })
        if (!holder.medicamentos.text.toString().equals("-") && !holder.doc_id.text.toString()
                .equals("-")
        ) {
            holder.revisar.isClickable = false
            holder.revisar.isEnabled = false
        }

    }


    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        var nombre: TextView
        var tipo: TextView
        var raza: TextView
        var edad: TextView
        var causa: TextView
        var id: TextView
        var revisar: Button
        var medicamentos: TextView
        var doc_id: TextView

        init {
            nombre = view.findViewById(R.id.a_nombre)
            tipo = view.findViewById(R.id.a_tipo)
            raza = view.findViewById(R.id.a_raza)
            edad = view.findViewById(R.id.a_edad)
            causa = view.findViewById(R.id.a_causa)
            id = view.findViewById(R.id.a_id)
            revisar = view.findViewById(R.id.a_btn_revisar)
            medicamentos = view.findViewById(R.id.a_med_descripcion)
            doc_id = view.findViewById(R.id.a_id_doc_descrip)
        }
    }
}