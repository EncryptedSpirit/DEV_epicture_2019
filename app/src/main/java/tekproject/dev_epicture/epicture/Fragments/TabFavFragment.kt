package tekproject.dev_epicture.epicture.Fragments

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import kotlinx.android.synthetic.main.frag_tabpost_layout.*
import org.json.JSONArray
import org.json.JSONObject
import tekproject.dev_epicture.epicture.Adapter.FavAdapter
import tekproject.dev_epicture.epicture.ApiRequests.ParseRequests
import tekproject.dev_epicture.epicture.ApiRequests.UrlRequests
import tekproject.dev_epicture.epicture.BaseActivity.BaseFragment
import tekproject.dev_epicture.epicture.Interface.VolleyCallback
import tekproject.dev_epicture.epicture.Model.FavModel
import tekproject.dev_epicture.epicture.Model.PostModel
import tekproject.dev_epicture.epicture.R

class TabFavFragment: BaseFragment() {
    lateinit var customAdapter: FavAdapter
    private val COLUMNS = 2
    private var mData: MutableList<FavModel> = mutableListOf()

    override fun getLayout(): Int { return R.layout.frag_tabfav_layout }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        request.makeGetRequest(object : VolleyCallback {
            override fun onSuccessResponse(result: JSONObject) {}

            override fun onSuccessResponse(result: JSONArray) {
                for (index in 0 until result.length()) {
                    val value = ParseRequests().getFavoriteImgId(result, index)

                    mData.add(FavModel("", value))
                }
                getUrlRequest()
            }
            override fun onFailedResponse() {}

        }, UrlRequests().getFavoriteImgId(utils.getAccountUsername(getAct())), context!!, "bearer")

    }

    private fun getUrlRequest() {
        if (context == null)
            return
        for (index in 0 until mData.size) {
            request.makeGetRequest(object : VolleyCallback {
                override fun onSuccessResponse(result: JSONArray) {
                }

                override fun onSuccessResponse(result: JSONObject) {
                    val value = ParseRequests().getFavorite(result, 0)
                    mData[index].mImageUrl = value
                    customAdapter.notifyDataSetChanged()
                }

                override fun onFailedResponse() {}

            }, UrlRequests().getFavorite(mData[index].mImageId), context!!, "bearer")
        }
        displayData()
    }

    private fun displayData() {
        if (context == null)
            return
        rcv_anim.visibility = View.GONE
        if (mData.size == 0) {
            rcv_anim.visibility = View.VISIBLE
        }
        customAdapter = FavAdapter(getAct(), mData)
        val staggeredGridLayoutManager = StaggeredGridLayoutManager(COLUMNS, LinearLayoutManager.VERTICAL)
        rcv.layoutManager = staggeredGridLayoutManager
        rcv.adapter = customAdapter
    }
}