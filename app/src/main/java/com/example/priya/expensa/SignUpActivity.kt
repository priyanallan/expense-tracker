package com.example.priya.expensa

import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.util.Log
import android.view.View
import android.widget.Toast
import com.google.android.gms.tasks.OnCompleteListener
import com.google.android.gms.tasks.Task
import com.google.firebase.auth.AuthResult
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseAuthException
import kotlinx.android.synthetic.main.activity_signup.*

class SignUpActivity : AppCompatActivity(){

    private val TAG = "SignUpActivity"
    private lateinit var firebaseAuth: FirebaseAuth
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_signup)

        firebaseAuth = FirebaseAuth.getInstance()

        signUpButton.setOnClickListener{
            progressBar.visibility = View.VISIBLE
            firebaseAuth.createUserWithEmailAndPassword(emailEditText.text.toString().trim(), passwordEditText.text.toString().trim())
                    .addOnCompleteListener(this,object : OnCompleteListener<AuthResult>{
                        override fun onComplete(task: Task<AuthResult>) {
                            progressBar.visibility = View.GONE
                            if(task.isSuccessful){
                                val mainActivityIntent = Intent(applicationContext,MainActivity::class.java)
                                mainActivityIntent.flags = Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_NEW_TASK
                                startActivity(mainActivityIntent)
                                finish()
                            } else{
                                val firebaseAuthException = task.exception as FirebaseAuthException
                                Log.e(TAG, "Failed Sign Up", firebaseAuthException)
                                Toast.makeText(applicationContext,"Sign Up Failed", Toast.LENGTH_SHORT).show()
                                return
                            }
                        }
                    })
        }
    }
}