package com.blumock.thecat

import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import com.blumock.favorites.ui.FavoritesFragment
import com.blumock.feeding.ui.FeedingFragment
import com.blumock.thecat.activity.AbstractActivity
import com.blumock.thecat.di.AbstractUseCasesComponent
import com.blumock.thecat.di.ActivityComponent
import com.blumock.thecat.di.DaggerActivityComponent
import com.blumock.thecat.di.DaggerUseCasesComponent

class MainActivity : AppCompatActivity(), AbstractActivity {

    private lateinit var component: ActivityComponent

    override fun getComponent(): AbstractUseCasesComponent = DaggerUseCasesComponent.builder()
        .activityComponent(component)
        .build()

    override fun onCreate(savedInstanceState: Bundle?) {
        component = DaggerActivityComponent.builder()
            .databaseComponent((application as App).databaseComponent)
            .networkComponent((application as App).networkComponent)
            .build()
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        if (savedInstanceState == null) {
            supportFragmentManager.beginTransaction()
                .replace(R.id.container, FeedingFragment.newInstance())
                .commitNow()
        }
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.top_menu, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == R.id.action_to_favorites) {
            val tag = FavoritesFragment::class.java.name
            if (supportFragmentManager.findFragmentByTag(tag) != null) return true
            supportFragmentManager.beginTransaction()
                .replace(R.id.container, FavoritesFragment.newInstance(), tag)
                .addToBackStack(null)
                .commit()
            return true
        }
        return false
    }
}