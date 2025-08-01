package com.tops.onlinestorage

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.AttributeSet
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.tops.onlinestorage.databinding.ActivityAddUserBinding
import com.tops.onlinestorage.model.Users
import com.tops.onlinestorage.viewmodel.UserViewModel

class AddUserActivity : AppCompatActivity() {

    private lateinit var binding: ActivityAddUserBinding

    private val userviewmodel: UserViewModel by viewModels()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAddUserBinding.inflate(layoutInflater)
        setContentView(binding.root)

        userviewmodel.insertSuccess.observe(this) { success ->
            if (success) {
                Log.d("AddUser", "Insert successful, navigating to MainActivity")
                Toast.makeText(this, "User added!", Toast.LENGTH_SHORT).show()

                // ✅ Navigate only after insert succeeds
                val intent = Intent(this, MainActivity::class.java)
                startActivity(intent)
                finish()
            } else {
                Toast.makeText(this, "Failed to add user", Toast.LENGTH_SHORT).show()
            }
        }

        binding.btnSubmit.setOnClickListener {
            val email = binding.etEmail.text.toString()
            val firstName = binding.etFirstName.text.toString()
            val lastName = binding.etLastName.text.toString()
            val phone = binding.etPhone.text.toString()
            val gender = when (binding.rgGender.checkedRadioButtonId) {
                R.id.rbMale -> "Male"
                R.id.rbFemale -> "Female"
                R.id.rbOther -> "Other"
                else -> ""
            }

            val user = Users(firstName = firstName, lastName = lastName, email = email, phone = phone, gender = gender)

            Log.d("AddUser", "Calling insertUser with: $user") // ✅ Debug log

            userviewmodel.insertUser(this,user)

        }

    }


}