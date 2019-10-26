package tekproject.dev_epicture.epicture

import android.content.Context
import android.os.Bundle
import android.view.MenuItem
import com.google.android.material.bottomnavigation.BottomNavigationView
import kotlinx.android.synthetic.main.act_main_layout.*
import tekproject.dev_epicture.epicture.BaseActivity.BaseActivity
import tekproject.dev_epicture.epicture.BaseActivity.BaseFragment
import tekproject.dev_epicture.epicture.Fragments.MainFragments.AccountFragment
import tekproject.dev_epicture.epicture.Fragments.MainFragments.HomeFragment
import tekproject.dev_epicture.epicture.Fragments.MainFragments.SearchFragment
import tekproject.dev_epicture.epicture.Fragments.MainFragments.UploadFragment
import androidx.core.content.ContextCompat.getSystemService
import androidx.core.app.ComponentActivity.ExtraData
import android.icu.lang.UCharacter.GraphemeClusterBreak.T
import android.view.inputmethod.InputMethodManager


open class MainActivity: BaseActivity(), BottomNavigationView.OnNavigationItemSelectedListener, BottomNavigationView.OnNavigationItemReselectedListener{
    private lateinit var fragment: BaseFragment

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.act_main_layout)

        AuthenticationUrlSaved()
        nav_view.setOnNavigationItemSelectedListener(this)
        nav_view.selectedItemId = R.id.nav_home
        nav_view.setOnNavigationItemReselectedListener(this)
    }

    private fun AuthenticationUrlSaved() {
        val url: String? = intent.extras?.getString(getString(R.string.sp_datasave_auth_data))
        if (url != null && url.isNotEmpty()) {
            val editor = getSharedPreferences(getString(R.string.sp_auth_data), Context.MODE_PRIVATE).edit()
            editor.putString(getString(R.string.sp_datasave_auth_data), url)
            editor.apply()
        }
    }

    override fun onNavigationItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.nav_home -> {
                fragment = HomeFragment()
            }
            R.id.nav_search -> {
                fragment = SearchFragment()
            }
            R.id.nav_post -> {
                fragment = UploadFragment()
            }
            R.id.nav_account -> {
                fragment =
                    AccountFragment()
            }
            else -> {
                return false
            }
        }
        setRootFragment(fragment)
        return true
    }

    override fun onNavigationItemReselected(p0: MenuItem) {}

}