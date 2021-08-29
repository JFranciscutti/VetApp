package dao

import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import android.util.Log
import android.widget.Toast
import model.Animal
import model.Doctor
import java.lang.Exception

class DBHelper(var context: Context, factory: SQLiteDatabase.CursorFactory?) :
    SQLiteOpenHelper(context, DATABASE_NAME, factory, DATABASE_VERSION) {
    companion object {
        private val DATABASE_NAME = "veterinaria.db"
        private val DATABASE_VERSION = 1

        //TABLA ANIMALES
        val TABLE_ANIMALES = "animales"
        val ANIMALES_ID = "id"
        val ANIMALES_NOMBRE = "nombre"
        val ANIMALES_TIPO = "tipo"
        val ANIMALES_RAZA = "raza"
        val ANIMALES_EDAD = "edad"
        val ANIMALES_CAUSA = "causa"
        val ANIMALES_MED = "medicamentos"
        val ANIMALES_DOC = "id_doc"

        //TABLA DOCTORES
        val TABLE_DOCTORES = "doctores"
        val DOCTORES_ID = "id"
        val DOCTORES_NOMBRE = "nombre"
        val DOCTORES_APELLIDO = "apellido"

    }

    override fun onCreate(db: SQLiteDatabase?) {
        //TABLA QUE CONTIENE LOS ANIMALES
        //Animales(id,nombre,tipo,raza,edad,causas,id_doc,medicamentos)
        var createAnimales =
            ("CREATE TABLE $TABLE_ANIMALES ( $ANIMALES_ID INTEGER PRIMARY KEY, $ANIMALES_NOMBRE TEXT, $ANIMALES_TIPO TEXT, $ANIMALES_RAZA TEXT, $ANIMALES_EDAD TEXT, $ANIMALES_CAUSA TEXT, $ANIMALES_MED TEXT, $ANIMALES_DOC TEXT) ")
        db?.execSQL(createAnimales)

        //TABLA QUE CONTIENE LOS DOCTORES
        //Doctores(id,nombre,apellido)
        var createDoctores =
            ("CREATE TABLE $TABLE_DOCTORES ( $DOCTORES_ID INTEGER PRIMARY KEY, $DOCTORES_NOMBRE TEXT, $DOCTORES_APELLIDO TEXT ) ")
        db?.execSQL(createDoctores)
    }

    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {
        db?.execSQL("DROP TABLE IF EXISTS " + TABLE_ANIMALES)
        db?.execSQL("DROP TABLE IF EXISTS " + TABLE_DOCTORES)
        onCreate(db)
    }

    /*
    Dado un animal, se carga su informacion a la tabla Animales en la base de datos de la veterinaria
    Retorna true si se cargó exitosamente. Caso contrario, false
     */
    fun cargarAnimal(animal: Animal): Boolean {
        try {
            val db = this.writableDatabase
            val values = ContentValues()

            values.put(ANIMALES_NOMBRE, animal.nombre)
            values.put(ANIMALES_TIPO, animal.tipo)
            values.put(ANIMALES_RAZA, animal.raza)
            values.put(ANIMALES_EDAD, animal.edad)
            values.put(ANIMALES_CAUSA, animal.causa)
            values.put(ANIMALES_MED, "-")
            values.put(ANIMALES_DOC, "-")

            //inserto animal en la db animales
            db.insert(TABLE_ANIMALES, null, values)

            return true
        } catch (e: Exception) {
            Log.e("ERROR DATABASE: ", e.message.toString())
            return false
        }

    }

    /*
    Inicializa 5 doctores en la tabla Doctores de la base de datos de la veterinaria
     */
    fun inicializarDoctores(): Boolean {
        try {
            val db = this.writableDatabase
            val values = ContentValues()
            val d1 = Doctor(0, "Joaquin", "Franciscutti")
            val d2 = Doctor(1, "Carlos", "Bilardo")
            val d3 = Doctor(2, "René", "Favaloro")
            val d4 = Doctor(3, "Darío", "Benedetto")
            val d5 = Doctor(4, "Martin", "Palermo")
            val doctores = ArrayList<Doctor>()
            doctores.add(d1)
            doctores.add(d2)
            doctores.add(d3)
            doctores.add(d4)
            doctores.add(d5)
            for (item: Doctor in doctores) {
                values.put(DOCTORES_ID, item.id)
                values.put(DOCTORES_NOMBRE, item.nombre)
                values.put(DOCTORES_APELLIDO, item.apellido)
                db.insert(TABLE_DOCTORES, null, values)
                values.clear()
            }
            return true

        } catch (e: Exception) {
            Toast.makeText(context, e.message.toString(), Toast.LENGTH_LONG).show()
            Log.e("ERROR DATABASE: ", e.message.toString())
            return false
        }
    }

    /*
    Retorna la lista con todos los doctores que hay en la veterinaria
     */
    fun getAllDoctores(): ArrayList<Doctor> {
        val db = this.readableDatabase
        val listaDoctores: ArrayList<Doctor> = ArrayList<Doctor>()
        val query = "SELECT * FROM " + TABLE_DOCTORES

        val cursor = db.rawQuery(query, null)
        if (cursor.moveToFirst()) {
            do {
                val id = cursor.getInt(cursor.getColumnIndex(DOCTORES_ID))
                val nombre = cursor.getString(cursor.getColumnIndex(DOCTORES_NOMBRE))
                val apellido = cursor.getString(cursor.getColumnIndex(DOCTORES_APELLIDO))

                listaDoctores.add(Doctor(id, nombre, apellido))
            } while (cursor.moveToNext())
        }
        return listaDoctores
    }

    /*
    Retorna la lista con todos los animales que hay (en espera o atendidos) en la veterinaria
     */
    fun getAllAnimales(): ArrayList<Animal> {
        val db = this.readableDatabase
        val listaAnimales: ArrayList<Animal> = ArrayList<Animal>()
        val query = "SELECT * FROM " + TABLE_ANIMALES
        val cursor = db.rawQuery(query, null)
        if (cursor.moveToFirst()) {
            do {
                val id = cursor.getInt(cursor.getColumnIndex(ANIMALES_ID))
                val nombre = cursor.getString(cursor.getColumnIndex(ANIMALES_NOMBRE))
                val tipo = cursor.getString(cursor.getColumnIndex(ANIMALES_TIPO))
                val raza = cursor.getString(cursor.getColumnIndex(ANIMALES_RAZA))
                val edad = cursor.getString(cursor.getColumnIndex(ANIMALES_EDAD))
                val causa = cursor.getString(cursor.getColumnIndex(ANIMALES_CAUSA))
                val med = cursor.getString(cursor.getColumnIndex(ANIMALES_MED))
                val doc = cursor.getString(cursor.getColumnIndex(ANIMALES_DOC))

                listaAnimales.add(Animal(id, nombre, tipo, raza, edad, causa, med, doc))
            } while (cursor.moveToNext())
        }
        return listaAnimales
    }

    /*
    Carga los medicamentos y el id del doctor que lo atendió en la ficha medica del animal
     */
    fun cargarRevisacion(
        id_animal: Int,
        id_doctor: Int,
        medicamento: String
    ): Boolean {

        try {
            val db = this.writableDatabase
            val query =
                "UPDATE $TABLE_ANIMALES SET $ANIMALES_DOC = '$id_doctor', $ANIMALES_MED = '$medicamento' WHERE $ANIMALES_ID = $id_animal"
            db?.execSQL(query)
            return true
        } catch (e: Exception) {
            Log.e("ERROR DATABASE:", e.message.toString())
            return false
        }

    }

    /*
    Dado el id de un doctor, retorna la cantidad de animales que atendio
     */
    fun cant_revisaciones(id_doctor: Int): Int {
        val db = this.readableDatabase
        var cant: Int = 0
        try {
            val query =
                ("SELECT COUNT(*) FROM $TABLE_ANIMALES WHERE $ANIMALES_DOC =?")
            val cursor = db.rawQuery(query, arrayOf(id_doctor.toString()))
            if (cursor.moveToFirst()) {
                do {
                    cant = cursor.getInt(cursor.getColumnIndex("COUNT(*)"))
                } while (cursor.moveToNext())
            }
        } catch (e: Exception) {
            Log.e("ERROR DATABASE: ", e.message.toString())
        }
        return cant
    }

    /*
    Retorna en un arreglo, la cantidad de revisiones que tiene cada doctor.
    Nos sirve para la vista del ingreso, en la que mostramos todos los doctores y
    sus respectivas cantidades de revisiones
     */
    fun revisaciones(): Array<Int> {
        return arrayOf(
            cant_revisaciones(0),
            cant_revisaciones(1),
            cant_revisaciones(2),
            cant_revisaciones(3),
            cant_revisaciones(4)
        )
    }

    /*
     Retorna la cantidad de animales que hay actualmente en la base de datos
     */
    fun cant_animales(): Int {
        val db = this.readableDatabase
        var cant: Int = 0
        try {
            val query = "SELECT COUNT(*) FROM $TABLE_ANIMALES"
            val cursor = db.rawQuery(query, null)
            if (cursor.moveToFirst()) {
                do {
                    cant = cursor.getInt(cursor.getColumnIndex("COUNT(*)"))
                } while (cursor.moveToNext())
            }

        } catch (e: Exception) {
            Log.e("ERROR DATABASE: ", e.message.toString())
        }
        return cant
    }

}