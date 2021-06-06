package com.example.passnote

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import kotlinx.android.synthetic.main.activity_login.*
import kotlinx.android.synthetic.main.activity_main.*

class Login : AppCompatActivity() {
    lateinit var auth: FirebaseAuth
    val TAG:String="abc"
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)
      auth= FirebaseAuth.getInstance()


        btn_forgetpass.setOnClickListener{
            startActivity(Intent(this, Forgetpassword::class.java))
        }

        btn_signin.setOnClickListener {
            if(signinemail.text.toString().isEmpty()){
                signinemail.error="Please enter email"
                signinemail.requestFocus()

            }

            if(signinpass.text.toString().isEmpty()){
                signinpass.error="Enter valid password"
                signinpass.requestFocus()

            }
            if(!signinemail.text.toString().isEmpty() and !signinpass.text.toString().isEmpty()){
                signin(signinemail.text.toString(),signinpass.text.toString())
            }


        }
        btn_signup.setOnClickListener {
            val i=Intent(this, SignUp::class.java)
            startActivity(i)
        }

    }
    override fun onStart() {
        super.onStart()
        val currentUser = auth.currentUser
        if(currentUser != null){
            startActivity(Intent(this,MainActivity::class.java))
        }
    }
    override fun onBackPressed(){
        moveTaskToBack(true);
    }

    private fun signin(email: String, pass: String) {
        auth.signInWithEmailAndPassword(email,pass)
            .addOnCompleteListener(this) { task ->
                if (task.isSuccessful) {
                    // Sign in success, update UI with the signed-in user's information
                    Log.d(TAG, "signInWithEmail:success")
                    val user = auth.currentUser
                    updateUI(user)
                } else {
                    // If sign in fails, display a message to the user.
                    Log.w(TAG, "signInWithEmail:failure", task.exception)
                    Toast.makeText(baseContext, "Authentication failed.",
                        Toast.LENGTH_SHORT).show()
                    updateUI(null)
                }
            }
    }

    private fun updateUI(user: FirebaseUser?) {
        if (user != null) {
            Toast.makeText(this, "U Signed In successfully", Toast.LENGTH_LONG).show()
            startActivity(Intent(this, MainActivity::class.java))
        } else {
            Toast.makeText(this, "U Didnt signed in", Toast.LENGTH_LONG).show()
        }
    }


}