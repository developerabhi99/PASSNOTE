package com.example.passnote

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.util.Patterns
import android.widget.Toast
import com.google.firebase.auth.EmailAuthProvider
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import kotlinx.android.synthetic.main.activity_sign_up.*

class SignUp : AppCompatActivity() {
    private lateinit var auth: FirebaseAuth
    val TAG:String="abc"
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sign_up)

        auth = FirebaseAuth.getInstance()

        btn_signup2.setOnClickListener {
            signupUser()
        }
    }

    private fun signupUser() {
        Inlineonly@
        if(email.text.toString().isEmpty()){
            email.error="Please enter email"
            email.requestFocus()
            return
        }
        if(!Patterns.EMAIL_ADDRESS.matcher(email.text.toString()).matches()){
            email.error="Enter valid email"
            email.requestFocus()
            return
        }
        if(pass1.text.toString().isEmpty()  || pass1.text.toString().length <6 ){
            pass1.error="Enter 7 or more digit password"
            pass1.requestFocus()
            return
        }
        if(pass2.text.toString().isEmpty()  || pass2.text.toString().length <6 ){
            pass2.error="Enter 7 or more digit password"
            pass2.requestFocus()
            return
        }
        if(pass1.text.toString()==pass2.text.toString()){

            auth.fetchSignInMethodsForEmail(email.text.toString())
                .addOnSuccessListener { result ->
                    val signInMethods = result.signInMethods!!
                    if (signInMethods.contains(EmailAuthProvider.EMAIL_PASSWORD_SIGN_IN_METHOD)) {
                        // User can sign in with email/password
                        Toast.makeText(this, "Already Registered Please Sign In ", Toast.LENGTH_SHORT).show()

                    } else {
                        createuserwithemail(email.text.toString(),pass1.text.toString())
                        //Toast.makeText(this,"Succesfully Created", Toast.LENGTH_SHORT).show()
                    }
                }




        }else{
            pass2.error="massword mismatch"
            pass2.requestFocus()
        }

    }

    private fun createuserwithemail(email: String, password: String) {
        auth.createUserWithEmailAndPassword(email, password)
            .addOnCompleteListener(this) { task ->
                if (task.isSuccessful) {
                    // Sign in success, update UI with the signed-in user's information
                    Log.d(TAG, "createUserWithEmail:success")
                    val user = auth.currentUser
                    updateUI(user)
                } else {
                    // If sign in fails, display a message to the user.
                    Log.w(TAG, "createUserWithEmail:failure", task.exception)
                    Toast.makeText(baseContext, "Authentication failed. Check passwpord size it should be atleast 7",
                        Toast.LENGTH_SHORT).show()
                    updateUI(null)
                }
            }

    }

    private fun updateUI(user: FirebaseUser?) {
        if (auth.currentUser != null) {
            Toast.makeText(this, "U Signed In successfully", Toast.LENGTH_LONG).show()
            startActivity(Intent(this, MainActivity::class.java))
        } else {
            Toast.makeText(this, "U Didnt signed in", Toast.LENGTH_LONG).show()
        }

    }

}