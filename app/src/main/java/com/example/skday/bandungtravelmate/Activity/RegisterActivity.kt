package com.example.skday.bandungtravelmate.Activity

import android.app.ProgressDialog
import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.text.TextUtils
import android.view.MenuItem
import android.widget.Toast
import com.example.skday.bandungtravelmate.R
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.UserProfileChangeRequest
import kotlinx.android.synthetic.main.activity_register.*

class RegisterActivity : AppCompatActivity() {

    var firebaseAuth: FirebaseAuth? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register)

        setSupportActionBar(toolBar)
        supportActionBar?.title = "Register"
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        btnRegister.setOnClickListener {
            registerUser()
        }

        tvLogin.setOnClickListener {
            startActivity(Intent(this, LoginActivity::class.java))
        }
    }

    @Suppress("DEPRECATION")
    private fun registerUser() {
        val progressDialog = ProgressDialog(this)

        val name = etName.text.toString()
        val email = etEmail.text.toString()
        val password = etPassword.text.toString()
        val rePassword = etRePassword.text.toString()

        if (TextUtils.isEmpty(name)) {
            Toast.makeText(this, "Please enter your name", Toast.LENGTH_LONG).show()
            return
        }
        if (TextUtils.isEmpty(email)) {
            Toast.makeText(this, "Please enter your email", Toast.LENGTH_LONG).show()
            return
        }
        if (TextUtils.isEmpty(password)) {
            Toast.makeText(this, "Please enter your password", Toast.LENGTH_LONG).show()
            return
        }
        if (TextUtils.isEmpty(rePassword)) {
            Toast.makeText(this, "Please re-enter your password", Toast.LENGTH_LONG).show()
            return
        }
        if (password != rePassword) {
            Toast.makeText(this, "Password not match", Toast.LENGTH_LONG).show()
            return
        }

        progressDialog.setMessage("Registering Please Wait...")
        progressDialog.setCancelable(false)
        progressDialog.show()

        firebaseAuth?.createUserWithEmailAndPassword(email, password)
                ?.addOnCompleteListener(this) { task ->
                    if (task.isComplete) {
                        var profilUpdate: UserProfileChangeRequest = UserProfileChangeRequest
                                .Builder()
                                .setDisplayName(name)
                                .build()
                        firebaseAuth?.currentUser?.updateProfile(profilUpdate)
                        Toast.makeText(this@RegisterActivity, "Successfully Registered", Toast.LENGTH_LONG).show()
                        var intent = Intent(this@RegisterActivity, MainActivity::class.java)
                        intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TOP
                        startActivity(intent)
                        finish()
                    } else {
                        Toast.makeText(this@RegisterActivity, "Registration Error", Toast.LENGTH_LONG).show();
                    }
                    progressDialog.dismiss()
                }
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        return if (item?.itemId == android.R.id.home){
            finish()
            true
        } else{
            super.onOptionsItemSelected(item)
        }
    }
}
