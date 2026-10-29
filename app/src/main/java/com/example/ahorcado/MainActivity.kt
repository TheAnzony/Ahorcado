package com.example.ahorcado

import android.os.Bundle
import android.view.View
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.savedstate.serialization.saved
import com.example.ahorcado.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity(), View.OnClickListener {

    private lateinit var binding : ActivityMainBinding

    //PARTIDA ACTIVA = TRUE
    private var partidaActiva = false

    //ERROES COMETIDOS
    private var errores = 0

    //ARRAY CON LAS IMAGENES
    private lateinit var imagenes : IntArray

    //LETRAS MOSTRADAS EN LA PANTALLA
    private var mostradas = 0

    //GUARDA LAS LETRAS ACERTADAS
    private var reveladas = CharArray(5){' '}

    //GUARDA LOS FALLOS COMETIDOS
    private var fallosAcumulados = CharArray(6){' '}


    //INTENTOS TOTALES
    private val intentos = 6

    //PALABRAS DEL AHORCADO
    private val palabras = arrayOf(
        "PERRO", "LIBRO", "PLAYA", "FRUTA", "MUNDO",
        "CIELO", "PIANO", "VERDE", "NUBES", "TIGRE"
    )

    //PALABRA SELECCIONADA PARA LA PARTIDA
    private lateinit var palabra : String

    //CASILLAS DONDE SE MUESTRAN LAS LETRAS
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

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        outState.putInt("errores",errores)
        outState.putInt("mostradas",mostradas)
        outState.putString("palabra",palabra)
        outState.putCharArray("reveladas",reveladas)
        outState.putIntArray("imagenes",imagenes)
        outState.putBoolean("activa", partidaActiva)
        outState.putCharArray("fallosAcumulados",fallosAcumulados)
    }

    override fun onRestoreInstanceState(savedInstanceState: Bundle) {
        super.onRestoreInstanceState(savedInstanceState)
        errores = savedInstanceState.getInt("errores")
        mostradas = savedInstanceState.getInt("mostradas")
        palabra = savedInstanceState.getString("palabra") ?: palabra
        reveladas = savedInstanceState.getCharArray("reveladas") ?: reveladas
        imagenes = savedInstanceState.getIntArray("imagenes") ?: imagenes
        partidaActiva = savedInstanceState.getBoolean("activa")
        fallosAcumulados = savedInstanceState.getCharArray("fallosAcumulados") ?: fallosAcumulados


        binding.imagen.setImageResource(imagenes[errores])
        letras = listOf(
            binding.tvLetra1,
            binding.tvLetra2,
            binding.tvLetra3,
            binding.tvLetra4,
            binding.tvLetra5
        )

        for (i in letras.indices){

            if (reveladas[i] != ' '){
                letras[i].text = reveladas[i].toString()
            }
        }

        val res = intentos-errores
        binding.tvIntentos.text = "Te quedan $res intentos"

        for (i in fallosAcumulados.indices){
            if (fallosAcumulados[i] != ' '){
                binding.tvFallos.append(fallosAcumulados[i] + " ")
            }
        }
    }


    private fun setMyListener(){
        binding.btnReset.setOnClickListener(this)
        binding.btnEnviar.setOnClickListener(this)
    }

    //COMIENZA EL JUEGO EN SU PRIMER ESTADO E INICIALIZANDO TODOS LAS VARIABLES
    private fun empezarPartida(){

        palabra = palabras.random() //SELECCIONAMOS UNA PALABRA ALEATROIA DE LAS POSIBLES PALABRAS

        //ESTADO POR DEFECTO AL EMPEZAR LA PARTIDA
        errores = 0
        partidaActiva = true
        mostradas = 0
        binding.tvIntentos.text = "Te quedan $intentos intentos"

        //METEMOS TODAS LA IMAGENES EN EL ARRAY
        imagenes = intArrayOf(
            R.drawable.ahorcado_0,
            R.drawable.ahorcado_1,
            R.drawable.ahorcado_2,
            R.drawable.ahorcado_3,
            R.drawable.ahorcado_4,
            R.drawable.ahorcado_5,
            R.drawable.ahorcado_6
        )

        //MOSTRAMOS LA PRIMERA IMAGEN
        binding.imagen.setImageResource(imagenes[errores])

        //METEMOS DENTRO DE LETRAS TODOS LOS TEXT VIEW DONDE SE MOSTRARAN LAS LETRAS
        letras = listOf(
            binding.tvLetra1,
            binding.tvLetra2,
            binding.tvLetra3,
            binding.tvLetra4,
            binding.tvLetra5
        )

    }

    //RESETEA LA PARTIDA
    private fun reiniciar(){
        limpiarLetras()
        limpiarErrores()
        empezarPartida()
    }

    //LIMPIA EL TEXT VIEW DE LAS LETRAS ERRONEAS INTRODUCIDAS
    private fun limpiarErrores(){
        binding.tvFallos.text = ""
        fallosAcumulados = CharArray(6){' '}
    }

    //Limpia las letras mostradas
    private fun limpiarLetras(){
        letras.forEach { it.text = "" }
        reveladas = CharArray(5){' '}
    }


    //REACCION DE LOS BOTONES
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
                        reveladas[i] = letra
                    }
                }
            }else{
                fallosAcumulados[errores] = letra
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

    //COMPRUEBA SI HAS PERDIDO O GANADO
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