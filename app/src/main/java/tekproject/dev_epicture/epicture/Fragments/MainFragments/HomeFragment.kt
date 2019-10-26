package tekproject.dev_epicture.epicture.Fragments.MainFragments

import android.os.Bundle
import android.view.View
import android.view.animation.AnimationUtils
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.floatingactionbutton.FloatingActionButton
import kotlinx.android.synthetic.main.act_main_layout.*
import kotlinx.android.synthetic.main.frag_home_layout.*
import org.json.JSONArray
import org.json.JSONObject
import tekproject.dev_epicture.epicture.Adapter.HomeAdapter
import tekproject.dev_epicture.epicture.ApiRequests.ParseRequests
import tekproject.dev_epicture.epicture.ApiRequests.UrlRequests
import tekproject.dev_epicture.epicture.BaseActivity.BaseFragment
import tekproject.dev_epicture.epicture.Interface.VolleyCallback
import tekproject.dev_epicture.epicture.Model.HomeImagesModel
import tekproject.dev_epicture.epicture.R

class HomeFragment : BaseFragment() {
    val mData: MutableList<HomeImagesModel> = mutableListOf()
    var mSort = "hot"
    lateinit var customAdapter: HomeAdapter

    override fun getLayout(): Int { return R.layout.frag_home_layout }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        sortGallery()
        rcv.setHasFixedSize(true)
        rcv.layoutManager = LinearLayoutManager(context)
        rcv.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                val bottomNavigation = getAct().nav_view
                if (dy > 0 && bottomNavigation.isShown) {
                    bottomNavigation.visibility = View.GONE
                } else if (dy < 0 ) {
                    bottomNavigation.visibility = View.VISIBLE
                }
            }
        })
        loadData()
    }

    private fun sortGallery() {
        lol2.setOnClickListener {
                displayFAB(viral)
                displayFAB(top)
                displayFAB(time)
        }

        viral.setOnClickListener {
            sort("viral")
            hideFAB(viral)
            hideFAB(top)
            hideFAB(time)
        }
        top.setOnClickListener {
            sort("top")
            hideFAB(viral)
            hideFAB(top)
            hideFAB(time)
        }
        time.setOnClickListener {
            sort("time")
            hideFAB(viral)
            hideFAB(top)
            hideFAB(time)
        }
    }

    fun sort(type: String) {
        mSort = type
        mData.clear()
        customAdapter.notifyDataSetChanged()
        loadData()
        Toast.makeText(context, "Sort by $type", Toast.LENGTH_SHORT).show()
    }
    private fun hideFAB(fab1: FloatingActionButton) {
        fab1.startAnimation(AnimationUtils.loadAnimation(getAct(), R.anim.fab_hide))
        fab1.isClickable = false
    }

    private fun displayFAB(fab1: FloatingActionButton) {
        fab1.startAnimation(AnimationUtils.loadAnimation(getAct(), R.anim.fab_show))
        fab1.isClickable = true
    }

    private fun loadData() {
        shimmerLayout.startShimmerAnimation()
        request.makeGetRequest(object : VolleyCallback {
            override fun onSuccessResponse(result: JSONArray) {
                for (index in 0 until result.length()) {
                    val value = ParseRequests().getHomeImages(result, index)
                    if (value != null) {
                        mData.add(value)
                    }
                }
                checkFavoriteImg()
            }
            override fun onSuccessResponse(result: JSONObject) {}
            override fun onFailedResponse() {
            }
        }, UrlRequests().getHomeImages(sort = mSort), context!!)
    }

    private fun checkFavoriteImg() {
        if (context == null)
            return
        for (index in 0 until mData.size) {
            request.makeGetRequest(object : VolleyCallback {
                override fun onSuccessResponse(result: JSONObject) {
                    val value = ParseRequests().isFavorite(result, 0)
                    mData[index].isFavorite = value.toString()
                    customAdapter.notifyDataSetChanged()
                }

                override fun onSuccessResponse(result: JSONArray) {}
                override fun onFailedResponse() {}
            }, UrlRequests().getFavorite(mData[index].imageId), context!!, "bearer")
        }
        displayData()
    }

    private fun displayData() {
        if (context == null)
            return
        customAdapter = HomeAdapter(rcv, context!!, mData)
        rcv.adapter = customAdapter
        shimmerLayout.stopShimmerAnimation()
        shimmerLayout.visibility = View.GONE
    }
}