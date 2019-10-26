package tekproject.dev_epicture.epicture.Adapter

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter
import tekproject.dev_epicture.epicture.BaseActivity.BaseFragment

class PagerAdapter (fm: FragmentManager, behavior: Int) : FragmentPagerAdapter(fm, behavior) {
    private val listFrag: MutableList<BaseFragment> = ArrayList()
    private val listTitle: MutableList<String> = ArrayList()

    override fun getCount(): Int {
        return listTitle.size
    }

    override fun getItem(position: Int): Fragment {
        return listFrag[position]
    }

    override fun getPageTitle(position: Int): CharSequence? {
        return listTitle[position]
    }

    fun setFragment(fragment: BaseFragment, title: String) {
        listFrag.add(fragment)
        listTitle.add(title)
    }
}