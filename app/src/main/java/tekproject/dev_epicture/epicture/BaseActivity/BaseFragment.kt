package tekproject.dev_epicture.epicture.BaseActivity

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import tekproject.dev_epicture.epicture.ApiRequests.Requests
import tekproject.dev_epicture.epicture.MainActivity
import tekproject.dev_epicture.epicture.R
import tekproject.dev_epicture.epicture.Tools.Utils

open class BaseFragment: Fragment() {
    val frameId = 0
    val request = Requests()
    val utils = Utils()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(getLayout(), container, false)
    }

    fun getAct(): MainActivity {
        return activity as MainActivity
    }

    open fun getLayout(): Int { return R.layout.frag_default_layout }
    open fun shouldAuthoriseBack(): Boolean { return false }
    open fun onBackPressed() {} //Here add a custom action when back is pressed
}