package com.example.e_waste

import android.annotation.SuppressLint
import android.content.Intent
import android.graphics.drawable.BitmapDrawable
import android.net.Uri
import android.os.Bundle
import android.view.View
import android.widget.ImageView
import android.widget.ProgressBar
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import com.example.e_waste.databinding.ActivityHomeBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.StorageReference

// i am here
class HomeActivity : AppCompatActivity() {

    private lateinit var auth: FirebaseAuth

    private lateinit var binding: ActivityHomeBinding
    private lateinit var database: DatabaseReference

    private var imageUri: Uri? = null

    private lateinit var storageRef: StorageReference
    private lateinit var firebaseFirestore: FirebaseFirestore

//    private lateinit var etname : EditText
//    private lateinit var etdate_of_birth : EditText
//    private lateinit var etgender : RadioGroup
//    private lateinit var etaddress : EditText
//
//    private lateinit var next_btn : Button
//
//    private var db = Firebase.firestore

    private lateinit var progressBar: ProgressBar

    private var change: Int = 0


    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
//        setContentView(R.layout.activity_home)

        auth = FirebaseAuth.getInstance()

//        auth.currentUser.


        binding = ActivityHomeBinding.inflate(layoutInflater)
        setContentView(binding.root)

        progressBar = binding.homeProgressBar



        storageRef = FirebaseStorage.getInstance().reference.child("Images")
        firebaseFirestore = FirebaseFirestore.getInstance()

        registerClickEvents()


//
//        val bm = (binding.defaultDp.getDrawable() as BitmapDrawable).bitmap.toString()
//
//
//        val image_as_string = binding.defaultDp.setImageURI(imageUri).toString()
//
//


        binding.nextBtn.setOnClickListener {
            progressBar.visibility = View.VISIBLE

            if (!binding.termsAndCondition.isChecked) {
                Toast.makeText(this, "Please check on terms and condition", Toast.LENGTH_SHORT)
                    .show()
                progressBar.visibility = View.GONE
                return@setOnClickListener
            }
            if (binding.fullNameEditText.text.isEmpty() || binding.addressEditText.text.isEmpty() || binding.dateOfBirthEditText.text.isEmpty() || binding.genderRadioGroup.checkedRadioButtonId == 0) {
                Toast.makeText(this, "You cannot leave any column empty", Toast.LENGTH_SHORT).show()
                progressBar.visibility = View.GONE
                return@setOnClickListener
            }
            if (change == 1) {
                uploadImage()
            }

            val name = binding.fullNameEditText.text.toString()
            val dateofbirth = binding.dateOfBirthEditText.text.toString()
//            val gender = binding.genderRadioGroup.
            val address = binding.addressEditText.text.toString()

            database = FirebaseDatabase.getInstance().getReference("User")

//            val user = User(name , dateofbirth , gender , address)
            val user = User(name, dateofbirth, address)

            database.child(System.currentTimeMillis().toString()).setValue(user)
                .addOnSuccessListener {

                    progressBar.visibility = View.GONE

                    val intent: Intent = Intent(this, LandingPage::class.java)
                    startActivity(intent)

                    Toast.makeText(this, "Success", Toast.LENGTH_SHORT).show()
                    finish()

                }.addOnFailureListener {
                    progressBar.visibility = View.INVISIBLE
                    Toast.makeText(this, "Failed!", Toast.LENGTH_SHORT).show()
                }
        }
//        etname = findViewById(R.id.full_name_edit_text)
//        etdate_of_birth = findViewById(R.id.date_of_birth_edit_text)
//        etgender = findViewById(R.id.gender_radio_group)
//        etaddress = findViewById(R.id.address_edit_text)
//
//
//        next_btn = findViewById(R.id.next_btn)
//
//        progressBar = findViewById(R.id.home_progress_bar)
//        progressBar.visibility = View.INVISIBLE
//
//        next_btn.setOnClickListener{
//            progressBar.visibility = View.VISIBLE
//
//            val sname = etname.text.toString().trim()
//            val saddress = etaddress.text.toString().trim()
//            val sgender = etgender.checkedRadioButtonId.toString().trim()
//            val sdate_of_birth = etdate_of_birth.text.toString().trim()
//
//            val userMap = hashMapOf(
//                "name" to sname,
//                "address" to saddress,
//                "gender" to sgender,
//                "date_of_birth" to sdate_of_birth
//            )
//
//            val userId = FirebaseAuth.getInstance().currentUser!!.uid
//
//            db.collection("user").document(userId).set(userMap)
//                .addOnSuccessListener {
//                    Toast.makeText(this, "Successfully Added", Toast.LENGTH_SHORT).show()
//                    etname.text.clear()
//                    etgender.clearCheck()
//                    etdate_of_birth.text.clear()
//                    etaddress.text.clear()
//
//                    progressBar.visibility = View.INVISIBLE
//                }
//                .addOnFailureListener{
//                    progressBar.visibility = View.INVISIBLE
//                    Toast.makeText(this, "Failed!", Toast.LENGTH_SHORT).show()
//                }
//        }


//        val photo : ImageView = findViewById(R.id.default_dp)
//
//        photo.setOnClickListener{
//            val intent = Intent(Intent.ACTION_PICK)
//            intent.type = "image/*"
//            startActivityForResult(intent , 123)
//        }


//        binding = ActivityHomeBinding.inflate(layoutInflater)


    }

    //    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
//        super.onActivityResult(requestCode, resultCode, data)
//        if (requestCode == 123){
//            val photo : ImageView = findViewById(R.id.default_dp)
//            photo.setImageURI(data?.data)
//        }
//    }
    private fun registerClickEvents() {
        binding.defaultDp.setOnClickListener {
            change = 1
            resultLauncher.launch("image/*")
        }
    }

    private val resultLauncher = registerForActivityResult(
        ActivityResultContracts.GetContent()
    ) {

        imageUri = it
        binding.defaultDp.setImageURI(it)
    }

    private fun uploadImage() {
        binding.homeProgressBar.visibility = View.VISIBLE
        storageRef = storageRef.child(System.currentTimeMillis().toString())
        imageUri?.let {
            storageRef.putFile(it).addOnCompleteListener { task ->
                if (task.isSuccessful) {

                    storageRef.downloadUrl.addOnSuccessListener { uri ->

                        val map = HashMap<String, Any>()
                        map["pic"] = uri.toString()

                        firebaseFirestore.collection("images").add(map)
                            .addOnCompleteListener { firestoreTask ->

                                if (firestoreTask.isSuccessful) {

                                    progressBar.visibility = View.INVISIBLE


                                } else {
                                    Toast.makeText(
                                        this,
                                        "firestoreTask" + firestoreTask.exception?.message,
                                        Toast.LENGTH_SHORT
                                    ).show()

                                }
                                binding.homeProgressBar.visibility = View.GONE
                                binding.defaultDp.setImageResource(R.drawable.default_profilephoto)
                            }
                    }
                } else {
                    Toast.makeText(this, "task " + task.exception?.message, Toast.LENGTH_SHORT)
                        .show()
                    binding.homeProgressBar.visibility = View.GONE
                    binding.defaultDp.setImageResource(R.drawable.default_profilephoto)
                }
            }
        }
    }
}