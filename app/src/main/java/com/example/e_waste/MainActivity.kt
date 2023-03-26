package com.example.e_waste

import android.annotation.SuppressLint
import android.app.Activity
import android.content.ContentValues.TAG
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.TextUtils
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.ProgressBar
import android.widget.TextView
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import com.example.e_waste.model.UserModel
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.tasks.Task
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.GoogleAuthProvider
import com.google.firebase.auth.ktx.actionCodeSettings
import com.google.firebase.auth.ktx.auth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.google.firebase.ktx.Firebase

class MainActivity : AppCompatActivity() {

    private lateinit var auth : FirebaseAuth
    private lateinit var googleSignInClient : GoogleSignInClient
    private lateinit var database: FirebaseDatabase
    private lateinit var progressBar : ProgressBar
    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)


        setContentView(R.layout.activity_main)
        progressBar = findViewById(R.id.main_activity_progressbar)
        progressBar.visibility = View.VISIBLE

        val button : TextView = findViewById(R.id.register_text)
        val loginButton : Button = findViewById(R.id.login_btn)

        val email : EditText = findViewById(R.id.email)
        val password : EditText = findViewById(R.id.password)

        auth = FirebaseAuth.getInstance()

        database = FirebaseDatabase.getInstance()


        database!!.reference.child("Users").addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                for (snapshot1 in snapshot.children){
                    val user = snapshot1.getValue(UserModel::class.java)
                    if (user!!.uid == FirebaseAuth.getInstance().uid){

                        changer(progressBar)
                    }
                }
            }

            override fun onCancelled(error: DatabaseError) {
            }
        })

//        if (auth.currentUser != null){
//            startActivity(Intent(this , LandingPage::class.java))
//            finish()
//        }

        progressBar.visibility = View.GONE

        val actionCodeSettings = actionCodeSettings {
            // URL you want to redirect back to. The domain (www.example.com) for this
            // URL must be whitelisted in the Firebase Console.
            url = "https://www.example.com/finishSignUp?cartId=1234"
            // This must be true
            handleCodeInApp = true
            setIOSBundleId("com.example.ios")
            setAndroidPackageName(
                "com.example.android",
                true, /* installIfNotAvailable */
                "12" /* minimumVersion */)
        }

        Firebase.auth.sendSignInLinkToEmail(email.toString(), actionCodeSettings)
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    Log.d(TAG, "Email sent.")
                }
            }

        // button is register here text
        button.setOnClickListener{
            val intent = Intent(this , SignUp::class.java)
            startActivity(intent)
        }

        loginButton.setOnClickListener{

            val emailText = email.text.toString()
            val passwordText = password.text.toString()

            if (TextUtils.isEmpty(emailText) || TextUtils.isEmpty(passwordText) ){
                Toast.makeText(this, "Cannot be Empty", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }
            auth.signInWithEmailAndPassword(emailText,passwordText).addOnCompleteListener{madhav ->
                if(madhav.isSuccessful){
                    Toast.makeText(this , "success" , Toast.LENGTH_SHORT).show()
                    val intent1 = Intent(this , HomeActivity2::class.java)
                    startActivity(intent1)
                }
                else{
                    Toast.makeText(this , "something went wrong!"+ madhav.exception?.message , Toast.LENGTH_SHORT).show()
                }
            }

        }

        val gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
            .requestIdToken(getString(R.string.default_web_client_id)).requestEmail().build()

        googleSignInClient = GoogleSignIn.getClient(this , gso)

        googleSignInClient.signOut()

        findViewById<TextView>(R.id.register_text).setOnClickListener{
            val intent : Intent = Intent(this , SignUp::class.java)
            startActivity(intent)
        }

        findViewById<Button>(R.id.sign_in_btn).setOnClickListener{
            signInGoogle()
        }
    }

    private fun changer(progressBar : ProgressBar){
        val intent : Intent = Intent(this , Work::class.java)
        progressBar.visibility = View.INVISIBLE
        startActivity(intent)
    }

    private fun signInGoogle() {
        val signInIntent = googleSignInClient.signInIntent
        launcher.launch(signInIntent)
    }
    private val launcher = registerForActivityResult(ActivityResultContracts.StartActivityForResult()){
            result ->
        if (result.resultCode == Activity.RESULT_OK){
            val task = GoogleSignIn.getSignedInAccountFromIntent(result.data)
            handleResults(task)
        }
    }

    private fun handleResults(task: Task<GoogleSignInAccount>) {
        if (task.isSuccessful){
            val account : GoogleSignInAccount? = task.result
            if (account != null){
                updateUI(account)
            }else{
                Toast.makeText(this , task.exception.toString() , Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun updateUI(account: GoogleSignInAccount) {
        val credential = GoogleAuthProvider.getCredential(account.idToken , null)

        auth.signInWithCredential(credential)
            .addOnCompleteListener{
                if (it.isSuccessful){
                    val intent : Intent = Intent(this , HomeActivity2::class.java)
                    startActivity(intent)
                }else{
                    Toast.makeText(this , it.exception.toString() , Toast.LENGTH_SHORT).show()
                }
            }
    }
}