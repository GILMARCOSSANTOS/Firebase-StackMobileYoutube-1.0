/**
 * Tela de aula 02: <- Tela anterior = FormCadastro
 */

package com.example.firebase10_stackmobileyoutube
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import com.google.android.gms.tasks.OnCompleteListener
import com.google.android.gms.tasks.Task
import com.google.firebase.auth.AuthResult
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException
import com.google.firebase.auth.FirebaseAuthWeakPasswordException

class FormLogin : AppCompatActivity() {

    /**
     * Variáveis de Escopo Global:
     */
    //  val  = String
    private lateinit var text_tela_cadastro: TextView
    private lateinit var edit_senha: EditText
    private lateinit var edit_email: EditText
    private lateinit var bt_entrar: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_form_login)

        /**
         * Ocultar ActionBar:
         */
        supportActionBar?.hide()

        /**
         * Inicialização das variáveis:
         */
        edit_email = findViewById(R.id.edit_email)
        edit_senha = findViewById(R.id.edit_senha)
        bt_entrar = findViewById(R.id.bt_entrar)
        text_tela_cadastro = findViewById(R.id.textTelaCadastro)

        /**
         * Criar funções:
         */
        text_tela_cadastro.setOnClickListener { textTelaCadastro() }
        bt_entrar.setOnClickListener { botaoEntrar() }

    }

    /**
     * Função botaoEntrar():
     */
    private fun botaoEntrar() {
        val email = edit_email.text.toString()
        val senha = edit_senha.text.toString()

        if (email.isEmpty() || senha.isEmpty()){
            Toast.makeText(this, "Preencha os campos.", Toast.LENGTH_SHORT).show()
        } else{
            //Aula 01.02 = Criar função para autenticar (LOGIN)usuário no Firebase Database:
            autenticarUsuario()
        }

    }

    /**
     * Aula 02.2 = Implementar função de autenticação (LOGIN) de usuário no Firebase Database:
     */
    private fun autenticarUsuario() {
        val email = edit_email.text.toString()
        val senha = edit_senha.text.toString()

        FirebaseAuth.getInstance().signInWithEmailAndPassword(email, senha)
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    val intent = Intent(this, TelaPrincipal::class.java)
                    startActivity(intent)
                }else{
//                    try {
//                        throw task.exception!!
//                    }catch (e: FirebaseAuthInvalidCredentialsException){
//                        Toast.makeText(this, "Senha incorreta!", Toast.LENGTH_SHORT).show()
//                    }catch (e: FirebaseAuthWeakPasswordException){
//                        Toast.makeText(this, "dddddddddSenha incorreta!", Toast.LENGTH_SHORT).show()
//                    }
                    Toast.makeText(this, "E - mail ou Senha incorreto.", Toast.LENGTH_SHORT).show()

                }
            }
    }

    /**
     * Função textTelaCadastro():
     */
    private fun textTelaCadastro() {
        val intent = Intent(this, FormCadastro::class.java)
           startActivity(intent)
    }

    /**
     * Função onStart():
     */
    // Aula 02.02 = Abrir tela que específica casa já tenha usuário autencicado (LOGADO) no Firebase Database:
    override fun onStart() {
        super.onStart()
        val usuarioAtual = FirebaseAuth.getInstance().currentUser

        if (usuarioAtual != null){
            // Aula 03.02 = Criar função de Intent para Tela Principal:
             telaPrincipal()

         }
    }

    /**
     * 04.02 = Implementar Função telaPrincipal():
     */
    private fun telaPrincipal() {
        val intent = Intent(this, TelaPrincipal::class.java)
        startActivity(intent)
    }
}