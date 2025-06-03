package com.example.brs_cout

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.MenuItem
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.Toast
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContentProviderCompat.requireContext
import androidx.core.view.GravityCompat
import androidx.drawerlayout.widget.DrawerLayout
import androidx.fragment.app.Fragment // Import Fragment
import com.example.brs_cout.app.LocaleManager
import com.example.brs_cout.databinding.ActivityStartBinding
import com.example.brs_cout.drawerMenu.AboutUsFragment
import com.example.brs_cout.drawerMenu.ContactUsFragment
import com.example.brs_cout.drawerMenu.HelpFragment
import com.example.brs_cout.drawerMenu.SettingsFragment
import com.example.brs_cout.home.HomeFragment
import com.example.brs_cout.profile.ProfileFragment
import com.example.brs_cout.search.SearchFragment
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.android.material.navigation.NavigationView
import com.google.firebase.auth.FirebaseAuth

class StartActivity : AppCompatActivity(), NavigationView.OnNavigationItemSelectedListener {

    private lateinit var drawerLayout: DrawerLayout
    private lateinit var toolbar: androidx.appcompat.widget.Toolbar // Use fully qualified name to avoid conflict
    private lateinit var navigationView: NavigationView
    private lateinit var binding: ActivityStartBinding // Replace with your Activity's binding class

    private val auth = FirebaseAuth.getInstance()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityStartBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Initialize views
        drawerLayout = binding.drawerLayout
        toolbar = binding.toolbar
        navigationView = binding.navView // Initialize bottomNavMenu

        // Set up Toolbar as ActionBar
        setSupportActionBar(toolbar)

        // Set up NavigationView listener
        navigationView.setNavigationItemSelectedListener(this)

        // Set up ActionBarDrawerToggle for DrawerLayout
        val toggle = ActionBarDrawerToggle(
            this, // Activity context
            drawerLayout,
            toolbar,
            R.string.nav_open, // String resource for opening drawer
            R.string.nav_close // String resource for closing drawer
        )
        drawerLayout.addDrawerListener(toggle)
        toggle.syncState()

        // Load initial fragment if Activity is created for the first time
        if (savedInstanceState == null) {
            loadFragment(HomeFragment(), "home_tag") // Use a tag for your fragments
            navigationView.setCheckedItem(R.id.homePage) // Assuming this ID exists in navigation_menu
            binding.bottomNavMenu.selectedItemId = R.id.homePage // Set initial selected bottom nav item
        }

        // Set up BottomNavigationView listener
        initBottomNav()

        // Handle back press
        onBackPressedDispatcher.addCallback(this, object : androidx.activity.OnBackPressedCallback(true) {
            override fun handleOnBackPressed() {
                if (drawerLayout.isDrawerOpen(GravityCompat.START)) {
                    drawerLayout.closeDrawer(GravityCompat.START)
                } else {
                    // If fragment back stack is empty, allow default back press (exit app or go to previous activity)
                    if (supportFragmentManager.backStackEntryCount > 0) {
                        supportFragmentManager.popBackStack()
                    } else {
                        // If no fragments on back stack, let the system handle back press (exit app)
                        isEnabled = false // Disable this callback to allow default behavior
                        onBackPressedDispatcher.onBackPressed() // Call system's back press
                    }
                }
            }
        })
    }

    private fun initBottomNav() = with(binding) {
        binding.bottomNavMenu.setOnItemSelectedListener { item ->
            when (item.itemId) {
                R.id.homePage -> {
                    // Use a tag for your fragments for better management
                    loadFragment(HomeFragment(), "home_tag", addToBackStack = true)
                    true
                }
                R.id.searchPage -> {
                    loadFragment(SearchFragment(), "search_tag", addToBackStack = true)
                    true
                }
                R.id.profilePage -> {
                    loadFragment(ProfileFragment(), "profile_tag", addToBackStack = true)
                    true
                }
                else -> {
                    loadFragment(HomeFragment(), "home_tag", addToBackStack = true)
                    true
                }
            }
        }
    }

    /**
     * Loads a fragment into the R.id.fragment_container.
     * @param fragment The fragment to load.
     * @param tag A unique tag for the fragment.
     * @param addToBackStack Whether to add the transaction to the back stack.
     */
    private fun loadFragment(fragment: Fragment, tag: String, addToBackStack: Boolean = false) {
        val transaction = supportFragmentManager.beginTransaction()

        // Check if the fragment already exists and is visible (for show/hide logic)
        // If you are always 'replacing', this check might be simplified.
        // For consistent behavior like the original fragment, we'll replace.
        transaction.replace(R.id.fragment_container, fragment, tag)

        if (addToBackStack) {
            transaction.addToBackStack(tag) // Use the tag for back stack entry name
        } else {
            // If not adding to back stack, clear existing back stack entries
            // This behavior was in your original fragment (popBackStackInclusive)
            if (supportFragmentManager.backStackEntryCount > 0) {
                supportFragmentManager.popBackStack(null, androidx.fragment.app.FragmentManager.POP_BACK_STACK_INCLUSIVE)
            }
        }
        transaction.commit()

        // Hide soft keyboard when a fragment is loaded
        val imm = getSystemService(Context.INPUT_METHOD_SERVICE) as? InputMethodManager
        imm?.hideSoftInputFromWindow(currentFocus?.windowToken, 0)
    }

    override fun onNavigationItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.nav_about -> loadFragment(AboutUsFragment(), "about_tag", addToBackStack = false)
            R.id.nav_help -> loadFragment(HelpFragment(), "help_tag", addToBackStack = false)
            R.id.nav_settings -> loadFragment(SettingsFragment(), "settings_tag", addToBackStack = false)
            R.id.nav_contact -> loadFragment(ContactUsFragment(), "contact_tag", addToBackStack = false)
            R.id.nav_logout -> {
                auth.signOut()
                val intent = Intent(this, MainActivity::class.java)
                startActivity(intent)
                finish()
            }
        }
        drawerLayout.closeDrawer(GravityCompat.START)
        return true
    }

    override fun attachBaseContext(newBase: Context?) {
        super.attachBaseContext(newBase?.let { LocaleManager.setLocale(it) })
    }
}