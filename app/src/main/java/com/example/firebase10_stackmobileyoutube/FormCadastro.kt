/**
 * Tela de aula 01: -> Tela posterior = FormLogin
 */

package com.example.firebase10_stackmobileyoutube

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.annotation.NonNull
import com.google.android.gms.tasks.OnCompleteListener
import com.google.android.gms.tasks.OnFailureListener
import com.google.android.gms.tasks.OnSuccessListener
import com.google.android.gms.tasks.Task
import com.google.firebase.auth.*
import com.google.firebase.firestore.DocumentReference
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.ktx.Firebase
import java.lang.Exception
import java.util.HashMap

const val TAG = "FIRESTORE"

class FormCadastro : AppCompatActivity() {

    /**
     * Variáveis em Escopo Global:
     */
    private lateinit var editNome: EditText
    private lateinit var editEmail: EditText
    private lateinit var editSenha: EditText
    private lateinit var btCadastrar: Button
    //Aula 05: Usuário do CurrentUserId do Firebase Database:
    lateinit var usuarioId: String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_form_cadastro)

        /**
         * FindViewById:
         */
        editNome = findViewById<EditText>(R.id.edit_nome)
        editEmail = findViewById<EditText>(R.id.edit_email)
        editSenha = findViewById<EditText>(R.id.edit_senha)
        btCadastrar = findViewById<Button>(R.id.bt_cadastrar)

        /**
         * Criar funções:
         */
        btCadastrar.setOnClickListener { botaoCadastrar() }
    }

    /**
     * Implementar funções:
     */
    private fun botaoCadastrar() {
        val nome = editNome.text.toString()
        val email = editEmail.text.toString()
        val senha = editSenha.text.toString()

        if (nome.isEmpty() || email.isEmpty() || senha.isEmpty()) {
            Toast.makeText(this, "Preencha todos os campos", Toast.LENGTH_LONG).show()
        } else {
            // Aula 01.01: Criar função de cadastro do usuário no Firebase Authentication:
            cadastrarUsuario()
        }
    }

    /**
     * Implementar a função de cadastro do usuário no Firebase Authentication:
     */
    //Aula 01.02: Implementar a função para cadastrar o usuário no Firebase Authentication:
    private fun cadastrarUsuario() {
        val email = editEmail.text.toString()
        val senha = editSenha.text.toString()

        FirebaseAuth.getInstance().createUserWithEmailAndPassword(email, senha)
            .addOnCompleteListener(OnCompleteListener<AuthResult> { task ->
                if (task.isSuccessful) {

                    //Aula 01.03: Criar função para salvar os dados do usuário no Firestore Database:
                    salvarDadosUsuario()

                    Toast.makeText(this, "Cadastro realizado no Firebase.", Toast.LENGTH_SHORT)
                        .show()
                } else {
                    try {
                        throw task.exception!!
                    } catch (e: FirebaseAuthWeakPasswordException) {
                        Toast.makeText(this, "A senha tem menos de 6 dígitos.", Toast.LENGTH_SHORT)
                            .show()
                    } catch (e: FirebaseAuthUserCollisionException) {
                        Toast.makeText(
                            this,
                            "Este e - mail já está cadastrado.",
                            Toast.LENGTH_SHORT
                        ).show()
                    } catch (e: FirebaseAuthInvalidCredentialsException) {
                        Toast.makeText(this, "Este e - mail é inválidoo.", Toast.LENGTH_SHORT)
                            .show()
                    } catch (e: Exception) {
                        Toast.makeText(this, "Erro ao cadastrar usuário.", Toast.LENGTH_SHORT)
                            .show()
                    }
                }
            })
    }

    /**
     * Implementar a função de cadastro do usuário no Firestore Database:
     */
    //Aula 01.04: Implementar a função para cadastrar o usuário no Firestore Database:
    private fun salvarDadosUsuario() {
        val nome: String = editNome.text.toString()

        // Aula 01.05: Iniciar o banco de dados Firestore Database:
        val db = FirebaseFirestore.getInstance()

        val usuarios: MutableMap<String, Any> = HashMap()
        usuarios["nome"] = nome

        usuarioId = FirebaseAuth.getInstance().currentUser!!.uid

        // Aula 01.06: Salvar dados no Firebase Database; -> Segue para a FormLogin :

        val documentReference = db.collection("Usuarios").document(usuarioId)
        documentReference.set(usuarios).addOnSuccessListener(object : OnSuccessListener<Void?> {

            override fun onSuccess(p0: Void) {
                Log.d("db", "Sucesso ao salvar os dados.")
            }
        })
            .addOnFailureListener {
                Log.d("dbError", "Erro ao salvar os dados")
            }
    }
}