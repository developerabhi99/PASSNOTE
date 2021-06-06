package com.example.passnote

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import kotlinx.android.synthetic.main.activity_scheduler.*

class Scheduler : AppCompatActivity() {
    lateinit var auth: FirebaseAuth
    lateinit var databaseReference: DatabaseReference
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_scheduler)
       // val database=FirebaseDatabase.getInstance().reference

        auth= FirebaseAuth.getInstance()

        val user = auth.currentUser

        val userId =user?.uid.toString()

        savescheduler.setOnClickListener {
            if(nameapp.text.toString().isEmpty()){
                nameapp.error="Enter the App Name"
                nameapp.requestFocus()
            }
            if(apppass.text.toString().isEmpty()){
                apppass.error="Enter the Password Name"
                apppass.requestFocus()
            }

            if(passcode.text.toString().isEmpty()){
                passcode.error="Enter the code Name"
                passcode.requestFocus()
            }
            if(!nameapp.text.toString().isEmpty() && !apppass.text.toString().isEmpty() && !passcode.text.toString().isEmpty()){
                val encrypt = encrypto(apppass.text.toString().trim(),passcode.text.toString().trim())
                val passports=realdata(nameapp.text.toString(), encrypt?.trim())
                databaseReference=FirebaseDatabase.getInstance().reference
                databaseReference.child("PASSWORD MANAGER").child(userId).child(nameapp.text.toString()).setValue(passports).addOnCompleteListener {
                    Toast.makeText(this,"SAVED Successfully", Toast.LENGTH_SHORT).show()
                    startActivity(Intent(this,PasswordManager::class.java))
                }
            }

        }

    }
    private fun encrypto(s: String, js: String): String? {
        val j = js.toIntOrNull()
        var s1: String? = ""
        for (i in 0 until s.length) {
            val a = s[i]
            val v = a.code
            val k = v + j!!
            val d = k.toChar()
            s1 += d
        }
        //System.out.println(s1);
        return s1
    }

}