package com.example.passnote

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.google.firebase.auth.EmailAuthProvider
import com.google.firebase.auth.FirebaseAuth
import kotlinx.android.synthetic.main.activity_forgetpassword.*

class Forgetpassword : AppCompatActivity() {
    lateinit var auth: FirebaseAuth
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_forgetpassword)
        auth = FirebaseAuth.getInstance()


        btn_pass2.setOnClickListener {
            if (!forgetpass.text.toString().isEmpty()) {
                auth.fetchSignInMethodsForEmail(forgetpass.text.toString())
                    .addOnSuccessListener { result ->
                        val signInMethods = result.signInMethods!!
                        if (signInMethods.contains(EmailAuthProvider.EMAIL_PASSWORD_SIGN_IN_METHOD)) {
                            // User can sign in with email/password
                            Toast.makeText(this, "OldUser", Toast.LENGTH_SHORT).show()
                            forgetpassword(forgetpass.text.toString())
                        } else {
                            Toast.makeText(this, "Please SignUP You Are New User", Toast.LENGTH_SHORT).show()
                        }
                    }
            }

        }
    }


    private fun forgetpassword(email: String) {

        auth.sendPasswordResetEmail(email)
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    Toast.makeText(this, "Email Send SuccessFully check your Email", Toast.LENGTH_SHORT).show()
                    startActivity(Intent(this, Login::class.java))

                }else{
                    Toast.makeText(this, "Failed Try after some time", Toast.LENGTH_SHORT).show()
                }
            }


    }
}