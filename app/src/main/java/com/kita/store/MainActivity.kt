package com.kita.store

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.kita.store.fragment.FragmentAccount
import com.kita.store.fragment.FragmentHome
import com.kita.store.fragment.FragmentProduct
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        bottom.setOnNavigationItemSelectedListener(bottomNavi)
        addFragment(FragmentHome())
    }

    private val bottomNavi = BottomNavigationView.OnNavigationItemSelectedListener { menuItem ->
        when(menuItem.itemId){
            R.id.home->{
                addFragment(FragmentHome())
                return@OnNavigationItemSelectedListener true
            }
            R.id.product->{
                addFragment(FragmentProduct())
                return@OnNavigationItemSelectedListener true
            }
            R.id.account->{
                addFragment(FragmentAccount())
                return@OnNavigationItemSelectedListener true
            }
        }

        false
    }

    private fun addFragment(frg: Fragment){
        supportFragmentManager.beginTransaction()
            .replace(R.id.fragment, frg)
            .commit()
    }
}
