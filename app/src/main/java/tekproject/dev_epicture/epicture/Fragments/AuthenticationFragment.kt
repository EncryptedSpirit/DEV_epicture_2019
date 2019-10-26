package tekproject.dev_epicture.epicture.Fragments

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.webkit.WebView
import android.webkit.WebViewClient
import kotlinx.android.synthetic.main.frag_authentication_layout.*
import tekproject.dev_epicture.epicture.BaseActivity.BaseFragment
import tekproject.dev_epicture.epicture.MainActivity
import tekproject.dev_epicture.epicture.R
import tekproject.dev_epicture.epicture.SplashScreen
import tekproject.dev_epicture.epicture.Tools.Utils

class AuthenticationFragment: BaseFragment() {
    override fun getLayout(): Int { return R.layout.frag_authentication_layout }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val url = Utils().getAuthUrl((activity as SplashScreen))

//        if (url == "none") {
            webview.loadUrl(getString(R.string.auth_url))
            webview.webViewClient = object : WebViewClient() {
                override fun shouldOverrideUrlLoading(view: WebView, url: String): Boolean {
                    if (activity != null) {
                        changetAct(url)
                    }
                    return true
                }
            }
//        } else {
//            changetAct(url)
//         }
    }

    private fun changetAct(url: String) {
        val nextAction = Intent((activity as SplashScreen), MainActivity::class.java)
        nextAction.putExtra(getString(R.string.sp_datasave_auth_data), url)
        startActivity(nextAction)
        (activity as SplashScreen).finish()
    }
}