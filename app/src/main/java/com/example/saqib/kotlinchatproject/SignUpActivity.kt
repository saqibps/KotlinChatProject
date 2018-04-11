package com.example.saqib.kotlinchatproject

import android.app.ProgressDialog
import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.google.android.gms.tasks.Task
import com.google.firebase.auth.AuthResult
import com.google.firebase.auth.FirebaseAuth
import kotlinx.android.synthetic.main.activity_sign_up.*

class SignUpActivity : AppCompatActivity() {

    lateinit var auth:FirebaseAuth
    lateinit var progress:ProgressDialog

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sign_up)

        auth = FirebaseAuth.getInstance()

        if (auth.currentUser != null) {

        }
        progress = ProgressDialog(this)
        progress.setCancelable(false)
        progress.setMessage("Please Wait...")

        btnSignIn.setOnClickListener {
            val emailString = email_et.text.toString()
            val passwordString = password_et.text.toString()
            signIn(emailString, passwordString)
        }
        btnSignUp.setOnClickListener {
            val emailString = email_et.text.toString()
            val passwordString = password_et.text.toString()
            signUp(emailString, passwordString)
        }
    }

    fun signIn(emailString:String,passwordString:String) {
        progress.show()
        auth.signInWithEmailAndPassword(emailString, passwordString)
                .addOnCompleteListener{task ->
                    progress.dismiss()
                    if (task.isSuccessful) {
                        Toast.makeText(this, "Signed in!", Toast.LENGTH_SHORT).show()
                        gotoChat()
                    } else{
                        Toast.makeText(this, "Error While Signing Up : ${task.exception?.message}", Toast.LENGTH_SHORT).show()
                    }
                }
    }
    fun signUp(emailString: String,passwordString: String) {
        progress.show()
        auth.createUserWithEmailAndPassword(emailString,passwordString)
                .addOnCompleteListener { task ->
                    progress.dismiss()
                if (task.isSuccessful){
                    Toast.makeText(this, "Signed Up!", Toast.LENGTH_SHORT).show()
                    gotoChat()
                } else {
                    Toast.makeText(this, "Error while Signing Up: ${task.exception?.message}", Toast.LENGTH_SHORT).show()
                }
                }
    }

    fun gotoChat() {
        startActivity(Intent(this, ChatActivity::class.java))
        finish()
    }
}
