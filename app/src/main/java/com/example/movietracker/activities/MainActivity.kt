package com.example.movietracker.activities

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.example.movietracker.R
import com.example.movietracker.fragments.ContentAddingFragment
import com.example.movietracker.fragments.MoviesFragment
import com.example.movietracker.fragments.StatisticsFragment
import com.example.movietracker.fragments.TvShowsFragment
import com.google.android.material.bottomnavigation.BottomNavigationView


class MainActivity : AppCompatActivity() {

    private var flagMovies: Boolean = false
    private var flagShows: Boolean = false
    private var flagAdding: Boolean = false
    private var flagStatistics: Boolean = false

    private val onNavigationItemSelectedListener =
        BottomNavigationView.OnNavigationItemSelectedListener { item ->
            when (item.itemId) {
                R.id.navigation_home -> {
                    inflator(flagMovies, MoviesFragment())
                    reassigner(
                        boolean1 = true,
                        boolean2 = false,
                        boolean3 = false,
                        boolean4 = false
                    )
                    return@OnNavigationItemSelectedListener true
                }
                R.id.navigation_dashboard -> {
                    inflator(flagShows, TvShowsFragment())
                    reassigner(
                        boolean1 = false,
                        boolean2 = true,
                        boolean3 = false,
                        boolean4 = false
                    )
                    return@OnNavigationItemSelectedListener true
                }
                R.id.navigation_notifications -> {
                    inflator(flagAdding, ContentAddingFragment())
                    reassigner(
                        boolean1 = false,
                        boolean2 = false,
                        boolean3 = true,
                        boolean4 = false
                    )
                    return@OnNavigationItemSelectedListener true
                }
                R.id.navigation_statistics -> {
                    inflator(flagStatistics, StatisticsFragment())
                    reassigner(
                        boolean1 = false,
                        boolean2 = false,
                        boolean3 = false,
                        boolean4 = true
                    )
                    return@OnNavigationItemSelectedListener true
                }
            }
            true
        }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        supportFragmentManager.beginTransaction().replace(R.id.constraint_main, MoviesFragment())
            .addToBackStack(null).commit()
        flagMovies = true
        val navView: BottomNavigationView = findViewById(R.id.nav_view)
        navView.setOnNavigationItemSelectedListener(onNavigationItemSelectedListener)
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        when {
            flagMovies -> {
                outState.putString("bool", "flagMovies")
            }
            flagShows -> {
                outState.putString("bool", "flagShows")
            }
            flagAdding -> {
                outState.putString("bool", "flagAdding")
            }
            flagStatistics -> {
                outState.putString("bool", "flagStatistics")
            }
        }

    }

    override fun onRestoreInstanceState(savedInstanceState: Bundle?) {
        super.onRestoreInstanceState(savedInstanceState)
        when (savedInstanceState?.getString("bool")) {
            "flagMovies" -> {
                inflator(false, MoviesFragment())
                reassigner(boolean1 = true, boolean2 = false, boolean3 = false, boolean4 = false)
            }
            "flagShows" -> {
                inflator(false, TvShowsFragment())
                reassigner(boolean1 = false, boolean2 = true, boolean3 = false, boolean4 = false)
            }
            "flagAdding" -> {
                inflator(false, ContentAddingFragment())
                reassigner(boolean1 = false, boolean2 = false, boolean3 = true, boolean4 = false)
            }
            "flagStatistics" -> {
                inflator(false, StatisticsFragment())
                reassigner(boolean1 = false, boolean2 = false, boolean3 = false, boolean4 = true)
            }
        }
    }

    private fun reassigner(
        boolean1: Boolean,
        boolean2: Boolean,
        boolean3: Boolean,
        boolean4: Boolean
    ) {
        flagMovies = boolean1
        flagShows = boolean2
        flagAdding = boolean3
        flagStatistics = boolean4
    }

    private fun inflator(boolean: Boolean, fragment: Fragment) {
        if (!boolean) {
            supportFragmentManager.beginTransaction()
                .replace(R.id.constraint_main, fragment)
                .addToBackStack(null)
                .commit()
        }
    }
}
