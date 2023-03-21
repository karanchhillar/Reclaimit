package com.example.e_waste

import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.UserManager
import android.view.View
import android.widget.ProgressBar
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import com.example.e_waste.databinding.ActivityHome2Binding
import com.example.e_waste.databinding.ActivityHomeBinding
import com.example.e_waste.model.UserModel
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.google.firebase.storage.FirebaseStorage
import com.squareup.picasso.Picasso
import java.util.*

class HomeActivity2 : AppCompatActivity() {

    private lateinit var auth: FirebaseAuth
    private lateinit var binding: ActivityHome2Binding
    private lateinit var database: FirebaseDatabase
    private var selectedImg: Uri? = null
    private lateinit var storage: FirebaseStorage
    private lateinit var dialog: AlertDialog.Builder
    private var change = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityHome2Binding.inflate(layoutInflater)

        auth = FirebaseAuth.getInstance()
        val progressBar: ProgressBar = binding.homeProgressBar2

        database = FirebaseDatabase.getInstance()

         change = intent.extras?.getString("change").toString()

        if (change == "Yes"){
            database!!.reference.child("Users").addValueEventListener(object : ValueEventListener {
                override fun onDataChange(snapshot: DataSnapshot) {
                    for (snapshot1 in snapshot.children) {
                        val user = snapshot1.getValue(UserModel::class.java)
                        if (user!!.uid == FirebaseAuth.getInstance().uid) {
                            auth.currentUser?.email
                            Picasso.get().load(user.image_url).into(binding.defaultDp2)
                            binding.fullNameEditText2.setText(user.name)
                            binding.dateOfBirthEditText2.setText(user.age)
                            binding.addressEditText2.setText(user.address)
                        }
                    }
                }

                override fun onCancelled(error: DatabaseError) {
                }
            })

        }

        else{
            database!!.reference.child("Users").addValueEventListener(object : ValueEventListener {
                override fun onDataChange(snapshot: DataSnapshot) {
                    for (snapshot1 in snapshot.children) {
                        val user = snapshot1.getValue(UserModel::class.java)
                        if (user!!.uid == FirebaseAuth.getInstance().uid) {
                            progressBar.visibility = View.VISIBLE
                            changer()
                        }
                    }
                }

                override fun onCancelled(error: DatabaseError) {
                }
            })
        }

//        progressBar.visibility = View.VISIBLE


//        if (auth.currentUser != null){
//            startActivity(Intent(this , LandingPage::class.java))
////            finish()
//        }

        setContentView(binding.root)

        dialog = AlertDialog.Builder(this).setMessage("Updating Profile").setCancelable(false)

//        database = FirebaseDatabase.getInstance()

        storage = FirebaseStorage.getInstance()


        var change = 1

        binding.defaultDp2.setOnClickListener {
            val intent = Intent()
            change = 0
            intent.action = Intent.ACTION_GET_CONTENT
            intent.type = "image/*"
            startActivityForResult(intent, 1)
        }
        binding.nextBtn2.setOnClickListener {
            progressBar.visibility = View.VISIBLE

            if (!binding.termsAndCondition2.isChecked) {
                Toast.makeText(this, "Please check on terms and condition", Toast.LENGTH_SHORT)
                    .show()
                progressBar.visibility = View.GONE
                return@setOnClickListener
            }
            if (binding.fullNameEditText2.text.isEmpty() || binding.addressEditText2.text.isEmpty() || binding.dateOfBirthEditText2.text.isEmpty() || binding.genderRadioGroup2.checkedRadioButtonId == 0) {
                Toast.makeText(this, "You cannot leave any column empty", Toast.LENGTH_SHORT).show()
                progressBar.visibility = View.GONE
                return@setOnClickListener
            } else {
                uploadData(change)
            }
//            if (change == 1) {
//                uploadImage()
//            }
        }
    }

    private fun changer() {
        val intent: Intent = Intent(this, Work::class.java)
        startActivity(intent)
    }

    private fun uploadData(change_fun_used: Int) {
        var imageTemp: String = ""
        if (change_fun_used == 1) {
            imageTemp =
                "https://firebasestorage.googleapis.com/v0/b/e-waste-5ecff.appspot.com/o/Profile%2F1678388561636?alt=media&token=3bf3ad6e-3efd-418f-b515-97fc6931e93d"

            uploadInfo(imageTemp, change_fun_used)
            return
        }
        val reference = storage.reference.child("Profile").child(Date().time.toString())
        selectedImg?.let {
            reference.putFile(it).addOnCompleteListener {
                if (it.isSuccessful) {
                    reference.downloadUrl.addOnSuccessListener { task ->
                        uploadInfo(task.toString(), change_fun_used)
                    }
                }
            }
        }
    }

    private fun uploadInfo(imgUrl: String, change_fun_used: Int) {
        var imageTemp: String = imgUrl
        if (change_fun_used == 1) {
            imageTemp =
                "https://firebasestorage.googleapis.com/v0/b/e-waste-5ecff.appspot.com/o/Profile%2F1678388561636?alt=media&token=3bf3ad6e-3efd-418f-b515-97fc6931e93d"
        }
        val user = UserModel(
            binding.fullNameEditText2.text.toString(),
            binding.dateOfBirthEditText2.text.toString(),
            binding.addressEditText2.text.toString(),
            imageTemp,
            auth.uid.toString()
        )
        database.reference.child("Users").child(auth.uid.toString()).setValue(user)
            .addOnSuccessListener {
                Toast.makeText(this, "Data Inserted", Toast.LENGTH_SHORT).show()
                startActivity(Intent(this, Work::class.java))
                finish()
            }
    }


    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (data != null) {
            if (data.data != null) {
                selectedImg = data.data!!
                binding.defaultDp2.setImageURI(selectedImg)
            }
        }
    }
}