package com.example.skday.bandungtravelmate.Activity

import android.app.ProgressDialog
import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.text.TextUtils
import android.widget.Toast
import com.example.skday.bandungtravelmate.R
import com.google.android.gms.tasks.OnCompleteListener
import com.google.android.gms.tasks.Task
import com.google.firebase.auth.AuthResult
import com.google.firebase.auth.FirebaseAuth
import kotlinx.android.synthetic.main.activity_register.*

class RegisterActivity : AppCompatActivity() {

    var firebaseAuth: FirebaseAuth? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register)

        setSupportActionBar(toolBar)
        supportActionBar?.title = "Register"

//        firebaseAuth = FirebaseAuth.getInstance()
//        if (firebaseAuth?.currentUser != null) {
//            startActivity(Intent(applicationContext, MainActivity::class.java))
//            finish()
//        }

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

        val email = etEmail.text.toString()
        val password = etPassword.text.toString()
        val rePassword = etRePassword.text.toString()

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
        progressDialog.show()

        firebaseAuth?.createUserWithEmailAndPassword(email, password)
                ?.addOnCompleteListener(this, object : OnCompleteListener<AuthResult> {
                    override fun onComplete(task: Task<AuthResult>) {
                        if (task.isComplete) {
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
                })
    }
}
