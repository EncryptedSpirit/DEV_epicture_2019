package tekproject.dev_epicture.epicture.Fragments.MainFragments

import android.os.Bundle
import android.view.View
import com.bumptech.glide.Glide
import kotlinx.android.synthetic.main.frag_account_layout.*
import org.json.JSONArray
import org.json.JSONObject
import tekproject.dev_epicture.epicture.Adapter.PagerAdapter
import tekproject.dev_epicture.epicture.ApiRequests.ParseRequests
import tekproject.dev_epicture.epicture.ApiRequests.UrlRequests
import tekproject.dev_epicture.epicture.BaseActivity.BaseFragment
import tekproject.dev_epicture.epicture.Fragments.TabFavFragment
import tekproject.dev_epicture.epicture.Fragments.TabPostFragment
import tekproject.dev_epicture.epicture.Interface.VolleyCallback
import tekproject.dev_epicture.epicture.Model.UserDataModel
import tekproject.dev_epicture.epicture.R

class AccountFragment: BaseFragment() {
    lateinit var mData: UserDataModel
    lateinit var customeAdapter: PagerAdapter

    override fun getLayout(): Int { return R.layout.frag_account_layout }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val username = utils.getAccountUsername(getAct())

        customeAdapter = PagerAdapter(childFragmentManager, 0)
        customeAdapter.setFragment(TabPostFragment(), "Post")
        customeAdapter.setFragment(TabFavFragment(), "Favorite")
        viewpager.adapter = customeAdapter
        tablayout.setupWithViewPager(viewpager, true)

        request.makeGetRequest(object : VolleyCallback {
            override fun onSuccessResponse(result: JSONArray) {
            }

            override fun onSuccessResponse(result: JSONObject) {
                val value = ParseRequests().getAccountData(result)
                mData = value
                displayData()
            }

            override fun onFailedResponse() {
            }

        }, UrlRequests().getAccountData(username), context!!)
    }

    private fun displayData() {
        if (context == null)
            return
        username.text = mData.username
        Glide.with(this).load(mData.avatarUrl).into(accountAvatar)
        Glide.with(this).load(mData.coverUrl).into(accountCover)
    }
}