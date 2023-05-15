package com.blumock.thecat

import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import com.blumock.api.activity.AbstractActivity
import com.blumock.api.di.AbstractRepositoryComponent
import com.blumock.data.di.UseCaseComponent
import com.blumock.favorites.ui.FavoritesFragment
import com.blumock.feeding.ui.FeedingFragment
import com.blumock.thecat.di.ActivityComponent

class MainActivity : AppCompatActivity(), AbstractActivity {

    private lateinit var activityComponent: AbstractRepositoryComponent

    override fun getComponent() = UseCaseComponent.create(activityComponent)

    override fun onCreate(savedInstanceState: Bundle?) {
        activityComponent = ActivityComponent.create((application as App).component)
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