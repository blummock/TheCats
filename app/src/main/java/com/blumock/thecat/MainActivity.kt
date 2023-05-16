package com.blumock.thecat

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.commitNow
import com.blumock.api.activity.AbstractActivity
import com.blumock.api.di.AbstractRepositoryComponent
import com.blumock.common.toggle
import com.blumock.data.di.UseCaseComponent
import com.blumock.favorites.ui.FavoritesFragment
import com.blumock.feeding.ui.FeedingFragment
import com.blumock.thecat.di.ActivityComponent
import com.google.android.material.navigation.NavigationBarView
import com.google.android.material.progressindicator.LinearProgressIndicator

class MainActivity : AppCompatActivity(), AbstractActivity {

    private lateinit var activityComponent: AbstractRepositoryComponent

    override fun getComponent() = UseCaseComponent.create(activityComponent)

    override fun progressIndicator(show: Boolean) {
        findViewById<LinearProgressIndicator>(R.id.loading).apply {
            if (show) show() else hide()
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        activityComponent = ActivityComponent.create((application as App).component)
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        if (savedInstanceState == null) {
            val feedingFragment = FeedingFragment.newInstance()
            val favoritesFragment = FavoritesFragment.newInstance()
            supportFragmentManager.commitNow {
                add(R.id.container, favoritesFragment, Destination.FAVORITES.name)
                detach(favoritesFragment)
                add(R.id.container, feedingFragment, Destination.FEED.name)
                setReorderingAllowed(true)
            }
        }
        findViewById<NavigationBarView>(R.id.bottomNavigation).run {
            setOnItemSelectedListener { item ->
                if (selectedItemId == item.itemId) return@setOnItemSelectedListener true
                when (item.itemId) {
                    R.id.action_to_feed -> {
                        supportFragmentManager.toggle(Destination.FEED.name, R.id.container)
                        true
                    }
                    R.id.action_to_favorites -> {
                        supportFragmentManager.toggle(Destination.FAVORITES.name, R.id.container)
                        true
                    }
                    else -> false
                }
            }
        }
    }

    private enum class Destination {
        FAVORITES,
        FEED
    }
}