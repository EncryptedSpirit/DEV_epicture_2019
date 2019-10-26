package tekproject.dev_epicture.epicture

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.view.View
import kotlinx.android.synthetic.main.act_splashscreen_layout.*
import org.json.JSONArray
import tekproject.dev_epicture.epicture.ApiRequests.ParseRequests
import tekproject.dev_epicture.epicture.ApiRequests.Requests
import tekproject.dev_epicture.epicture.ApiRequests.UrlRequests
import tekproject.dev_epicture.epicture.BaseActivity.BaseActivity
import tekproject.dev_epicture.epicture.Fragments.AuthenticationFragment
import tekproject.dev_epicture.epicture.Interface.VolleyCallback
import tekproject.dev_epicture.epicture.Model.HomeImagesModel

class SplashScreen : BaseActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.act_splashscreen_layout)

        Handler().postDelayed({
            setRootFragment(AuthenticationFragment(), R.id.splash_root_view)
            animation.visibility = View.GONE
        }, 2000)
/*
        val request = Requests()
        val mData: MutableList<HomeImagesModel> = mutableListOf()

        request.makeGetRequest(object : VolleyCallback {
            override fun onSuccessResponse(result: JSONArray) {
                for (index in 0 until result.length()) {
                    val value = ParseRequests().getHomeImages(result, index)
                    if (value != null) {
                        mData.add(value)
                    }
                }
                textView.text = mData[0].toString()
            }

            override fun onFailedResponse() {
            }
        }, UrlRequests().getHomeImages(), applicationContext)
 */
    }
}
