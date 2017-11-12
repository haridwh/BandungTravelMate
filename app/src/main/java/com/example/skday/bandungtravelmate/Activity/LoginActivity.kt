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
import kotlinx.android.synthetic.main.activity_login.*

class LoginActivity : AppCompatActivity() {

    var firebaseAuth: FirebaseAuth? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        setSupportActionBar(toolBar)
        supportActionBar?.title = "Login"
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        firebaseAuth = FirebaseAuth.getInstance()

        btnLogin.setOnClickListener {
            userLogin()
        }
        tvSignup.setOnClickListener {
            finish()
        }
    }

    @Suppress("DEPRECATION")
    fun userLogin() {
        val progressDialog = ProgressDialog(this)

        var email = etEmail.text.toString()
        var password = etPassword.text.toString()

        if (TextUtils.isEmpty(email)) {
            Toast.makeText(this, "Please enter your email", Toast.LENGTH_LONG).show()
            return
        }
        if (TextUtils.isEmpty(password)) {
            Toast.makeText(this, "Please enter your password", Toast.LENGTH_LONG).show()
            return
        }

        progressDialog.setMessage("Please Wait...")
        progressDialog.show()

        firebaseAuth?.signInWithEmailAndPassword(email, password)
                ?.addOnCompleteListener(this) { task ->
                    progressDialog.dismiss()
                    if (task.isSuccessful) {
                        val intent = Intent(this@LoginActivity, MainActivity::class.java)
                        intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TOP
                        startActivity(intent)
                        finish()
                    } else {
                        Toast.makeText(this@LoginActivity, "Something went wrong, Try again later...", Toast.LENGTH_LONG).show();
                    }
                }
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        return if (item?.itemId == android.R.id.home) {
            finish()
            true
        } else {
            super.onOptionsItemSelected(item)
        }
    }
}
