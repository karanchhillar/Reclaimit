package com.example.e_waste

import android.annotation.SuppressLint
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.TextUtils
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth

class SignUp : AppCompatActivity() {
    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sign_up)

        val email : EditText = findViewById(R.id.signup_email)
        val password : EditText = findViewById(R.id.signup_pass)
        val confirmPassword : EditText = findViewById(R.id.signup_conf_pass)
        val signUp : Button = findViewById(R.id.signup_btn)

        val auth = FirebaseAuth.getInstance()

        signUp.setOnClickListener{
            val emailText = email.text.toString()
            val passwordText = password.text.toString()
            val conPassText = confirmPassword.text.toString()

            if (TextUtils.isEmpty(emailText) || TextUtils.isEmpty(passwordText) || TextUtils.isEmpty(conPassText)) {
                Toast.makeText(this, "Cannot be Empty", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }
            if (passwordText.length < 6) {
                Toast.makeText(this, "Password should be more than 6 letters", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }
            if (passwordText != conPassText) {
                Toast.makeText(this, "Both are not same", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            auth.createUserWithEmailAndPassword(emailText,passwordText).addOnCompleteListener{madhav ->
                if(madhav.isSuccessful){
                    Toast.makeText(this , "success" , Toast.LENGTH_SHORT).show()
                    val intent1 = Intent(this , HomeActivity2::class.java)
                    startActivity(intent1)
                }
                else{
                    Toast.makeText(this , "something went wrong!" , Toast.LENGTH_SHORT).show()
                }
            }
        }
    }

}