package com.example.skday.bandungtravelmate.Activity

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import com.example.skday.bandungtravelmate.R
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.UserProfileChangeRequest
import kotlinx.android.synthetic.main.activity_edit.*

class EditActivity : AppCompatActivity() {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_edit)

        var user = FirebaseAuth.getInstance().currentUser
        if (user != null) {
            etName.setText(user.displayName)
            etEmail.setText(user.email)
        }

        btnUpdate.setOnClickListener {
            var profileUpdate: UserProfileChangeRequest = UserProfileChangeRequest.Builder()
                    .setDisplayName(etName.text.toString())
                    .build()
            user?.updateProfile(profileUpdate)
                    ?.addOnCompleteListener {
                        user.updateEmail(etEmail.text.toString())
                                .addOnCompleteListener {
                                    finish()
                                }
                    }
        }
    }
}
