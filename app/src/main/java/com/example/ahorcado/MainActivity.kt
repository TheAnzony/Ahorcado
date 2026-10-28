package com.example.ahorcado

import android.os.Bundle
import android.view.View
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.ahorcado.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity(), View.OnClickListener {

    private lateinit var binding : ActivityMainBinding
    private var partidaActiva = false
    private var errores = 0
    private lateinit var imagenes : IntArray

    private var mostradas = 0

    private val intentos = 6

    private val palabras = arrayOf(
        "PERRO", "LIBRO", "PLAYA", "FRUTA", "MUNDO",
        "CIELO", "PIANO", "VERDE", "NUBES", "TIGRE"
    )

    private lateinit var palabra : String

    private lateinit var letras : List<TextView>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setMyListener()
        empezarPartida()
        ViewCompat.setOnApplyWindowInsetsListener(binding.main) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
    }

    private fun setMyListener(){
        binding.btnReset.setOnClickListener(this)
        binding.btnEnviar.setOnClickListener(this)
    }


    private fun empezarPartida(){
        imagenes = intArrayOf(
            R.drawable.ahorcado_0,
            R.drawable.ahorcado_1,
            R.drawable.ahorcado_2,
            R.drawable.ahorcado_3,
            R.drawable.ahorcado_4,
            R.drawable.ahorcado_5,
            R.drawable.ahorcado_6
        )

        binding.imagen.setImageResource(imagenes[0])

        letras = listOf(
            binding.tvLetra1,
            binding.tvLetra2,
            binding.tvLetra3,
            binding.tvLetra4,
            binding.tvLetra5
        )

        palabra = palabras.random()
        errores = 0
        partidaActiva = true
        mostradas = 0
        binding.tvIntentos.text = "Te quedan $intentos intentos"

    }

    private fun reiniciar(){
        limpiarLetras()
        limpiarErrores()
        empezarPartida()
    }

    private fun limpiarErrores(){
        binding.tvFallos.text = ""
    }

    //Limpia las letras mostradas
    private fun limpiarLetras(){
        letras.forEach { it.text = "" }
    }



    override fun onClick(boton: View?){

        if (boton == null) return

        if (boton == binding.btnReset){
            reiniciar()
            return
        }

        if (!partidaActiva) return



        if(boton == binding.btnEnviar){

            val texto = binding.etLetra.text.toString().uppercase()

            if (texto.isEmpty()) return

            val letra = texto[0]

            if (palabra.contains(letra)){
                for (i in palabra.indices){
                    if (letra == palabra[i] && letras[i].text.isEmpty()){
                        letras[i].text = letra.toString()
                        mostradas++
                    }
                }
            }else{
                errores++
                binding.imagen.setImageResource(imagenes[errores])
                val res = intentos-errores
                binding.tvIntentos.text = "Te quedan $res intentos"
                binding.tvFallos.text = binding.tvFallos.text.toString() + "$letra "

            }

            binding.etLetra.text.clear()
            comprobarPartida()

        }

    }

    private fun comprobarPartida(){
        if (errores == intentos){
            partidaActiva = false
            binding.tvIntentos.text = getString(R.string.perdido)
        }
        if (mostradas == 5){
            partidaActiva = false
            binding.tvIntentos.text = getString(R.string.ganado)
            binding.imagen.setImageResource(R.drawable.ahorcado_ganado)

        }

    }



}