package com.example.finalapplication

import android.app.ListActivity
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.MenuItem
import android.view.View
import android.widget.TextView
import androidx.appcompat.app.ActionBarDrawerToggle
import com.example.finalapplication.databinding.ActivityMainBinding
import com.google.android.material.navigation.NavigationView

class MainActivity : AppCompatActivity(), NavigationView.OnNavigationItemSelectedListener {
    lateinit var binding: ActivityMainBinding
    lateinit var toggle: ActionBarDrawerToggle
    lateinit var headerView: View
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // ListFragment
        val listfragment = ListFragment()
        val bundle = Bundle()

        binding.btnSearch.setOnClickListener {
            Log.d("mobileApp", "btnSearch")
            bundle.putString("area", binding.search.text.toString())

            listfragment.arguments = bundle
            supportFragmentManager.beginTransaction()
                .replace(R.id.activity_content, listfragment)
                .commit()
        }

        // NavigationItem
        binding.mainDrawerView.setNavigationItemSelectedListener(this)

        headerView = binding.mainDrawerView.getHeaderView(0)

        // action bar에 토글 버튼 추가
        toggle = ActionBarDrawerToggle(this, binding.drawer, R.string.drawer_opened, R.string.drawer_closed)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        binding.drawer.addDrawerListener(toggle)
        toggle.syncState()
    }

    // 토글 버튼 누르면 드로어 화면 나타남
    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (toggle.onOptionsItemSelected(item)) {
            return true
        }

        when(item.itemId) {
            R.id.login -> {
                true
            }
            R.id.heart -> {
                true
            }
            R.id.myComment -> {
                true
            }
        }

        return super.onOptionsItemSelected(item)
    }

    override fun onNavigationItemSelected(item: MenuItem): Boolean {
        when(item.itemId) {
            R.id.login -> {
                Log.d("mobileApp", "Login")

                if (item.title == "로그인") {
                    val intent = Intent(this, AuthActivity::class.java)
                    if (item.title == "로그인") {
                        Log.d("mobileApp", "로그인")
                        intent.putExtra("status", "logout")
                    }
                    else if (item.title == "로그아웃") {
                        intent.putExtra("status", "login")
                    }
                    startActivity(intent)

                    binding.drawer.closeDrawers()
                    true
                }
                else if (item.title == "로그아웃") {
                    MyApplication.auth.signOut()
                    MyApplication.email = null
                    onStart()
                }

            }
        }
        return false
    }

    // 로그인 했을 때 텍스트 변경
    override fun onStart() {
        super.onStart()

        val username = headerView.findViewById<TextView>(R.id.username)
        val useremail = headerView.findViewById<TextView>(R.id.user_email)
        val login = binding.mainDrawerView.menu.findItem(R.id.login)

        if (MyApplication.checkAuth()) {
            username.text = "안녕하세요!"
            useremail.text = "${MyApplication.email}"
            login.title = "로그아웃"
        }
        else {
            username.text = "로그인해주세요"
            useremail.text = "로그인해주세요"
            login.title = "로그인"
        }
    }

}