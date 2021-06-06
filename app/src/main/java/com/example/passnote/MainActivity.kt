package com.example.passnote

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {
    lateinit var auth: FirebaseAuth
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        auth= FirebaseAuth.getInstance()

        val user = auth.currentUser

        username.text=user?.uid.toString().substring(23)

        useremail.text=user?.email.toString()

        btn_signout.setOnClickListener {
            auth.signOut()
            updateUI(user)
        }
        passmanager.setOnClickListener {
            startActivity(Intent(this,PasswordManager::class.java))
        }
        notesmanager.setOnClickListener {
            startActivity(Intent(this,NotesManager::class.java))
        }
    }
    override fun onBackPressed(){
        moveTaskToBack(true);
    }
    private fun updateUI(user: FirebaseUser?) {
        if (auth.currentUser == null) {
            Toast.makeText(this, "U Signed Out successfully", Toast.LENGTH_LONG).show()
            startActivity(Intent(this, Login::class.java))
        } else {
            Toast.makeText(this, "U Didnt signed Out", Toast.LENGTH_LONG).show()
        }

    }
}