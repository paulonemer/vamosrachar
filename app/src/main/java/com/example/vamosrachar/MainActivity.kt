package com.example.vamosrachar

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.speech.tts.TextToSpeech
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import java.util.*

class MainActivity : AppCompatActivity(), TextToSpeech.OnInitListener {
    private lateinit var tts: TextToSpeech
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)


        var valor = findViewById<TextView>(R.id.valor)
        tts = TextToSpeech(this, this)

        fun compartilharTexto(texto: String) {
            val intent = Intent(Intent.ACTION_SEND)
            intent.type = "text/plain"
            intent.putExtra(Intent.EXTRA_TEXT, texto)
            startActivity(Intent.createChooser(intent, "Compartilhar texto"))
        }
        fun racha() {

            val pessoas = findViewById<EditText>(R.id.pessoas)
            val pessoasget = pessoas.text.toString().toDoubleOrNull()

            val preco = findViewById<EditText>(R.id.preco)
            val precoget = preco.text.toString().toDoubleOrNull()



            if (precoget != null && pessoasget != null) {
                var divisão:Double = precoget/pessoasget
                valor.text ="R$ $divisão"

            }
        }
        val rachar: Button = findViewById(R.id.rachar)
        rachar.setOnClickListener(View.OnClickListener {
           racha()

        })
        val share: Button = findViewById(R.id.compartilhar)
            share.setOnClickListener(View.OnClickListener {
                compartilharTexto("${valor.text.toString()}")
            })
        val falar: Button = findViewById(R.id.falar)
            falar.setOnClickListener(View.OnClickListener {
            tts?.speak(valor.text.toString(), TextToSpeech.QUEUE_FLUSH, null, "")
        })



    }

    override fun onInit(status: Int) {
        if (status == TextToSpeech.SUCCESS) {
            val result = tts.setLanguage(Locale("pt", "BR"))
            if (result == TextToSpeech.LANG_MISSING_DATA || result == TextToSpeech.LANG_NOT_SUPPORTED) {
                Log.e("TextToSpeech", "Idioma não suportado")
            }
        } else {
            Log.e("TextToSpeech", "Falha na inicialização")
        }
    }


}