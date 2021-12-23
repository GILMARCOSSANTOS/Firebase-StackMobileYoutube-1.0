/**
 * Tela 03: <- Tela anterior = Tela Login:
 */

package com.example.firebase10_stackmobileyoutube

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.DocumentSnapshot
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.FirebaseFirestoreException

class TelaPrincipal : AppCompatActivity() {

    /**
     * Criar os objetos:
     */
    private lateinit var nomeUsuario: TextView
    private lateinit var emailUsuario: TextView
    private lateinit var btDeslogar: Button

    /**
     * Objetos para reccuperar Instância do Firebase:
     */
    //Aula 03.02 = Recuperar instância do Firebase Database para recuperar dados:
    val db = FirebaseFirestore.getInstance()
    var usuarioId: String = ""
    var email: String = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_tela_principal)

        /**
         * Recuperar Ids dos objetos:
         */
        nomeUsuario = findViewById(R.id.textNomeUsuario)
        emailUsuario = findViewById(R.id.textEmailUsuario)
        btDeslogar = findViewById(R.id.bt_deslogar)

        /**
         * Criar método botaoDeslogar:
         */
        btDeslogar.setOnClickListener { botaoDeslogar() }

    }

    /**
     * Função botaoDeslogar():
     */
    //Aula 03.01 = Deslogar do Firebase:
    private fun botaoDeslogar() {
        FirebaseAuth.getInstance().signOut()

        val intent = Intent(this, FormLogin::class.java)
        startActivity(intent)
    }

    /**
     * Função osStart():
     */
    // Aula 03.03 = Reconhecimento do usuário autenticado (LOGADO) no Firebase:
    override fun onStart() {
        super.onStart()
        email = FirebaseAuth.getInstance().currentUser!!.email!!
        usuarioId = FirebaseAuth.getInstance().currentUser!!.uid

        val documentReference = db.collection("Usuarios").document(usuarioId)
        documentReference.addSnapshotListener { documentSnapshot, error ->
            if (documentSnapshot != null) {
                nomeUsuario.text = documentSnapshot.getString("nome")
                emailUsuario.text = email
            }
        }


    }



}