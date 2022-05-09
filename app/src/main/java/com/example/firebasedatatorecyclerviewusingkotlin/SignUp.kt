package com.example.firebasedatatorecyclerviewusingkotlin

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.telephony.TelephonyCallback
import android.text.TextUtils
import android.util.Patterns
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DatabaseReference
import org.w3c.dom.Text

class SignUp : AppCompatActivity() {

    private lateinit var email_et: EditText
    private lateinit var password_et: EditText
    private lateinit var btn: Button
    private lateinit var firebaseAuth: FirebaseAuth
    private lateinit var dbref: DatabaseReference

    private var email = ""
    private var password = ""

    //actionbarも追加したい

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sign_up)

        email_et = findViewById(R.id.email_et)
        password_et = findViewById(R.id.password_et)
        btn = findViewById(R.id.btn)

        firebaseAuth = FirebaseAuth.getInstance()

        val btnuser = findViewById<Button>(R.id.btn2)

        btnuser.setOnClickListener(){
            startActivity(Intent(this, UserlistActivity::class.java))
        }

        btn.setOnClickListener(){
            validData()
        }
    }

    private fun validData(){
        //emailとpasswordがeditTextなのでstring状態にする
        email = email_et.text.toString().trim()
        password = password_et.text.toString().trim()

        if(!Patterns.EMAIL_ADDRESS.matcher(email).matches()){
            email_et.error = "Invalid email format"
        }else if(TextUtils.isEmpty(password)){
            password_et.error = "Please enter password"
        }else if(TextUtils.isEmpty(email)){
            email_et.error = "Please enter Email"
        }else if(password.length < 6){
            password_et.error = "Password should be at least 6 characters"
        }else{
            //data is valid
            firebaseSignUp()
        }
    }

    private fun firebaseSignUp(){

        firebaseAuth.createUserWithEmailAndPassword(email, password)
            .addOnSuccessListener {
                val firebaseUser = firebaseAuth.currentUser
                val email = firebaseUser!!.email
                Toast.makeText(this, "Signup with $email", Toast.LENGTH_SHORT).show()

                startActivity(Intent(this, UserlistActivity::class.java))
                finish()
            }
            .addOnFailureListener(){e ->
                Toast.makeText(this, "failed to signup", Toast.LENGTH_SHORT).show()
            }



    }
}