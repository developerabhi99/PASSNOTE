package com.example.passnote

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.FirebaseDatabase
import kotlinx.android.synthetic.main.activity_schedule_notes.*

class ScheduleNotes : AppCompatActivity() {
    lateinit var auth: FirebaseAuth
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_schedule_notes)

        val database=FirebaseDatabase.getInstance().reference
        auth= FirebaseAuth.getInstance()
        val user=auth.currentUser
        val userId=user?.uid.toString()
        savesnotes.setOnClickListener {
            val subject=namesubject.text.toString()
            val notes=notepass.text.toString()
            val passnote=notecode.text.toString()
            val notesencrtpt=encrypto(notes,passnote)
            val usernote=fetchnote(subject,notesencrtpt)
            if(!subject.isEmpty() && !notes.isEmpty() && !passnote.isEmpty()){
                database.child("NOTES MANAGER").child(userId).child(subject).setValue(usernote).addOnCompleteListener {
                    Toast.makeText(this,"SAVED SUCCESSFULLY",Toast.LENGTH_SHORT).show()
                    startActivity(Intent(this,NotesManager::class.java))
                }
            }else{
                namesubject.error="Enter the Subject name"
                namesubject.requestFocus()
                notepass.error="Enter the password"
                notepass.requestFocus()
                notecode.error="Enter the code"
                notecode.requestFocus()
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