package com.example.brs_cout

import android.content.Intent
import android.os.Bundle
import android.view.MenuItem
import android.widget.Toast
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.core.view.GravityCompat
import androidx.drawerlayout.widget.DrawerLayout
import androidx.fragment.app.Fragment
import com.example.brs_cout.databinding.ActivityMainBinding
import com.example.brs_cout.onBoarding.LoginFragment
import com.google.android.material.navigation.NavigationView
import com.google.firebase.auth.FirebaseAuth

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val user = FirebaseAuth.getInstance().currentUser

        if (user != null) {
            // User is already logged in
            startActivity(Intent(this, StartActivity::class.java))
            finish()
        } else {
            // Not logged in, show login fragment
            supportFragmentManager.beginTransaction()
                .replace(R.id.main, LoginFragment())
                .commit()
        }
    }
}