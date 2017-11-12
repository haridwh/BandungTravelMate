package com.example.skday.bandungtravelmate.Activity

import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AlertDialog
import android.support.v7.app.AppCompatActivity
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import com.example.skday.bandungtravelmate.R
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import kotlinx.android.synthetic.main.activity_profile.*

class ProfileActivity : AppCompatActivity() {

    var firebaseAuth: FirebaseAuth? = null
    var user: FirebaseUser? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_profile)

        setSupportActionBar(toolBar)
        supportActionBar?.title = "Profile"
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        firebaseAuth = FirebaseAuth.getInstance()
        if (firebaseAuth?.currentUser == null) {
            startActivity(Intent(this, RegisterActivity::class.java))
            finish()
        }
        user = firebaseAuth?.currentUser
        tvName.text = user?.displayName
        tvEmail.text = user?.email
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        val inflater: MenuInflater = menuInflater
        inflater.inflate(R.menu.menu_profile, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        return when (item?.itemId) {
            android.R.id.home -> {
                finish()
                true
            }
            R.id.menu_edit -> {
                true
            }
            R.id.menu_logout -> {
                var builder: AlertDialog.Builder = AlertDialog.Builder(this)
                builder.setMessage("Are you sure you want to log out?")
                builder.setPositiveButton("Yes") { dialog, _ ->
                    firebaseAuth?.signOut()
                    val intent = Intent(this@ProfileActivity, MainActivity::class.java)
                    intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TOP
                    dialog.dismiss()
                    startActivity(intent)
                    finish()
                }
                builder.setNegativeButton("No") { dialog, _ ->
                    dialog.dismiss()
                }
                val alert = builder.create()
                alert.show()
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }
}
