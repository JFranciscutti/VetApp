package model

import java.io.Serializable

data class Animal(
    val id: Int = 0,
    val nombre: String,
    val tipo: String,
    val raza: String,
    val edad: String,
    val causa: String,
    val medicamentos: String,
    val id_doc: String
) : Serializable
