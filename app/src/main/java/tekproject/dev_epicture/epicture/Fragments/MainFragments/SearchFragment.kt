package tekproject.dev_epicture.epicture.Fragments.MainFragments

import android.content.Context
import android.os.Bundle
import android.view.KeyEvent
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import kotlinx.android.synthetic.main.frag_search_layout.*
import org.json.JSONArray
import org.json.JSONObject
import tekproject.dev_epicture.epicture.Adapter.SearchAdapter
import tekproject.dev_epicture.epicture.ApiRequests.ParseRequests
import tekproject.dev_epicture.epicture.ApiRequests.UrlRequests
import tekproject.dev_epicture.epicture.BaseActivity.BaseFragment
import tekproject.dev_epicture.epicture.Interface.VolleyCallback
import tekproject.dev_epicture.epicture.Model.HomeImagesModel
import tekproject.dev_epicture.epicture.R




class SearchFragment: BaseFragment() {
    private var mData: MutableList<HomeImagesModel> = mutableListOf()
    private lateinit var customAdapter: SearchAdapter

    override fun getLayout(): Int { return R.layout.frag_search_layout }

    override fun onResume() {
        super.onResume()
        val show_keyboard = getAct().getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        show_keyboard.toggleSoftInput(InputMethodManager.SHOW_FORCED, InputMethodManager.HIDE_IMPLICIT_ONLY);
    }

    override fun onPause() {
        super.onPause()
        val show_keyboard = getAct().getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        show_keyboard.hideSoftInputFromWindow(view?.windowToken, 0)
    }
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        edittxt.requestFocus()
        edittxt.setOnKeyListener(object : View.OnKeyListener {
            override fun onKey(v: View, keyCode: Int, event: KeyEvent): Boolean {
                if (event.action == KeyEvent.ACTION_DOWN && keyCode == KeyEvent.KEYCODE_ENTER) {
                    rcv.visibility = View.VISIBLE
                    animation.visibility = View.GONE
                    err_txt.visibility = View.GONE
                    mData.clear()
                    request.makeGetRequest(object : VolleyCallback {
                        override fun onSuccessResponse(result: JSONArray) {
                            if (result.length() == 0) {
                                failed()
                            } else {
                                for (i in 0 until result.length()) {
                                    val value = ParseRequests().getSearchImage(result, i)
                                    if (value != null) {
                                        mData.add(value)
                                    }
                                }
                                displayData()
                            }
                        }

                        override fun onSuccessResponse(result: JSONObject) {}
                        override fun onFailedResponse() {}
                    }, UrlRequests().getSearchImages(search = edittxt.text.toString()), getAct())
                    return true
                }
                return false
            }
        })
    }


    private fun failed() {
        rcv.visibility = View.GONE
        animation.visibility = View.VISIBLE
        err_txt.visibility = View.VISIBLE
    }


    private fun displayData() {
        if (context == null)
            return
        rcv.layoutManager = LinearLayoutManager(context)
        customAdapter = SearchAdapter(getAct(), mData)
        rcv.adapter = customAdapter
    }
}