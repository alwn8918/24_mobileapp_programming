package com.example.finalapplication

import android.app.ListActivity
import android.content.Intent
import android.content.SharedPreferences
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.MenuItem
import android.view.View
import android.widget.LinearLayout
import android.widget.RelativeLayout
import android.widget.TextView
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.preference.PreferenceManager
import com.example.finalapplication.databinding.ActivityMainBinding
import com.google.android.material.navigation.NavigationView
import java.io.BufferedReader
import java.io.File

class MainActivity : AppCompatActivity(), NavigationView.OnNavigationItemSelectedListener {
    lateinit var binding: ActivityMainBinding
    lateinit var toggle: ActionBarDrawerToggle
    lateinit var headerView: View
    lateinit var sharedPreferences: SharedPreferences

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // ListFragment
        val listfragment = ListFragment()
        val bundle = Bundle()

        binding.btnSearch.setOnClickListener {
            Log.d("mobileApp", "btnSearch")
            var areacode: String? = null
            when(binding.search.text.toString()) {
                "서울" -> areacode = "1"
                "인천" -> areacode = "2"
                "대전" -> areacode = "3"
                "대구" -> areacode = "4"
                "광주" -> areacode = "5"
                "부산" -> areacode = "6"
                "울산" -> areacode = "7"
                "세종" -> areacode = "8"
                "경기" -> areacode = "31"
                "강원" -> areacode = "32"
                "충북" -> areacode = "33"
                "충남" -> areacode = "34"
                "경북" -> areacode = "35"
                "경남" -> areacode = "36"
                "전북" -> areacode = "37"
                "전남" -> areacode = "38"
                "제주" -> areacode = "39"
            }

            bundle.putString("area", areacode)

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
            R.id.setting -> {
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
            R.id.setting -> {
                Log.d("mobileApp", "setting")

                val intent = Intent(this, SettingActivity::class.java)
                startActivity(intent)
            }
        }
        return false
    }

    // 로그인 했을 때 텍스트 변경
    override fun onStart() {
        super.onStart()

        val username = headerView.findViewById<TextView>(R.id.username)
        val useremail = headerView.findViewById<TextView>(R.id.useremail)
        val nickname = headerView.findViewById<TextView>(R.id.nickname)
        val gender = headerView.findViewById<View>(R.id.profile)
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
            nickname.text = " "
            gender.setBackgroundColor(Color.parseColor("#E6E6E6"))
        }

    }

    // 설정에서 값 바꾸면 바로 적용
    override fun onResume() {
        super.onResume()

        // sharedPreferences
        sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this)
        val color = sharedPreferences.getString("color", "#0174BE")
        binding.mainView.setBackgroundColor(Color.parseColor(color))

        val username = headerView.findViewById<TextView>(R.id.username)
        val useremail = headerView.findViewById<TextView>(R.id.useremail)
        val nickname = headerView.findViewById<TextView>(R.id.nickname)
        val profile = headerView.findViewById<View>(R.id.profile)
        val login = binding.mainDrawerView.menu.findItem(R.id.login)

        if (MyApplication.checkAuth()) {
            val name = sharedPreferences.getString("username", " ")
            username.text = name

            val nick = sharedPreferences.getString("nickname", " ")
            nickname.text = "(" + nick + ")"

            val gender = sharedPreferences.getString("gender", "#E6E6E6")
            profile.setBackgroundColor(Color.parseColor(gender))
        }
        else {
            username.text = "로그인해주세요"
            useremail.text = "로그인해주세요"
            login.title = "로그인"
            nickname.text = " "
            profile.setBackgroundColor(Color.parseColor("#E6E6E6"))
        }

        val drawer = headerView.findViewById<LinearLayout>(R.id.drawer_view)
        drawer.setBackgroundColor(Color.parseColor(color))

        supportActionBar?.setBackgroundDrawable(ColorDrawable(Color.parseColor(color)))

        val file = File(filesDir, "recent.txt")
        val readStream: BufferedReader = file.reader().buffered()
        binding.recent.text = "최근에 본 장소: " + readStream.readLine()

    }

}