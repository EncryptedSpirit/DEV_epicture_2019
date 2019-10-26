package tekproject.dev_epicture.epicture.Fragments

import android.os.Bundle
import android.view.View
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import kotlinx.android.synthetic.main.frag_tabpost_layout.*
import org.json.JSONArray
import org.json.JSONObject
import tekproject.dev_epicture.epicture.Adapter.PostAdapter
import tekproject.dev_epicture.epicture.ApiRequests.ParseRequests
import tekproject.dev_epicture.epicture.ApiRequests.UrlRequests
import tekproject.dev_epicture.epicture.BaseActivity.BaseFragment
import tekproject.dev_epicture.epicture.Interface.VolleyCallback
import tekproject.dev_epicture.epicture.Model.PostModel
import tekproject.dev_epicture.epicture.R

class TabPostFragment: BaseFragment() {
    lateinit var customAdapter: PostAdapter
    private val COLUMNS = 2
    private var mData: MutableList<PostModel> = mutableListOf()

    override fun getLayout(): Int { return R.layout.frag_tabpost_layout }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        request.makeGetRequest(object : VolleyCallback {
            override fun onSuccessResponse(result: JSONArray) {
                for (index in 0 until result.length()) {
                    val value = ParseRequests().getAccountPostImage(result, index)
                    val imageId     = value.imageId
                    val imageUrl    = value.imageUrl

                    mData.add(PostModel(imageUrl, imageId))
                }
                displayData()
            }

            override fun onSuccessResponse(result: JSONObject) {}
            override fun onFailedResponse() {
                rcv_anim?.visibility = View.VISIBLE
            }
        }, UrlRequests().getAccountPostImage(utils.getAccountUsername(getAct())), context!!, "bearer")
    }

    private fun displayData() {
        if (context == null)
            return
        customAdapter = PostAdapter(getAct(), mData)
        val staggeredGridLayoutManager = StaggeredGridLayoutManager(COLUMNS, LinearLayoutManager.VERTICAL)
        rcv.layoutManager = staggeredGridLayoutManager
        rcv.adapter = customAdapter
    }
}