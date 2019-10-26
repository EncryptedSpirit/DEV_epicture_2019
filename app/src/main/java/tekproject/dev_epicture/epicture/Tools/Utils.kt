package tekproject.dev_epicture.epicture.Tools

import android.view.View
import android.widget.FrameLayout
import com.google.android.material.snackbar.Snackbar
import android.content.Context
import tekproject.dev_epicture.epicture.MainActivity
import tekproject.dev_epicture.epicture.R
import tekproject.dev_epicture.epicture.SplashScreen


class Utils {
    fun snackMsg(act: MainActivity, msg: String = act.getString(R.string.error_occured), time: Int = 0) {
        val nav  = act.findViewById<View>(R.id.nav_view)
        Snackbar.make(act.findViewById(R.id.act_root_view), msg, time)
            .apply {view.layoutParams = (view.layoutParams as FrameLayout.LayoutParams)
                .apply {setMargins(leftMargin, topMargin, rightMargin, nav.height)}}.show()
    }

    /*
    fun isNetworkAvailable(act: Context): Boolean {
        val connectivityManager = act.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager?
        val activeNetworkInfo = connectivityManager!!.activeNetworkInfo
        return activeNetworkInfo != null && activeNetworkInfo.isConnected
    }
*/
    fun getAccessToken(act: MainActivity): String {
        val url = getAuthUrl(act)
        val test: List<String> = url.split("&", "#")

        return test[1].replace("access_token=", "")
    }

    fun getExpiresIn(act: MainActivity): String {
        val url = getAuthUrl(act)
        val test: List<String> = url.split("&", "#")

        return test[2].replace("expires_in=", "")
    }

    fun getTokenType(act: MainActivity): String {
        val url = getAuthUrl(act)
        val test: List<String> = url.split("&", "#")

        return test[3].replace("token_type=", "")
    }

    fun getRefreshToken(act: MainActivity): String {
        val url = getAuthUrl(act)
        val test: List<String> = url.split("&", "#")

        return test[4].replace("refresh_token=", "")
    }

    fun getAccountUsername(act: MainActivity): String {
        val url = getAuthUrl(act)
        val test: List<String> = url.split("&", "#")

        return test[5].replace("account_username=", "")
    }

    fun getAccountId(act: MainActivity): String {
        val url = getAuthUrl(act)
        val test: List<String> = url.split("&", "#")

        return test[6].replace("account_id=", "")
    }

    fun getAuthUrl(act: MainActivity): String {
        val prefs = act.getSharedPreferences(act.getString(R.string.sp_auth_data), Context.MODE_PRIVATE)
        val url = prefs.getString(act.getString(R.string.sp_datasave_auth_data), "No name defined")

        return url ?: "none"
    }

    fun getAuthUrl(act: SplashScreen): String {
        val prefs = act.getSharedPreferences(act.getString(R.string.sp_auth_data), Context.MODE_PRIVATE)
        val url = prefs.getString(act.getString(R.string.sp_datasave_auth_data), "No name defined")

        return url ?: "none"
    }

}