package tekproject.dev_epicture.epicture.BaseActivity

import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.FragmentManager
import tekproject.dev_epicture.epicture.R

open class BaseActivity: AppCompatActivity() {

    private fun getCurrentFragment(): BaseFragment? {
        val find = supportFragmentManager.findFragmentById(R.id.act_root_view) as? BaseFragment
        return find ?: supportFragmentManager.findFragmentById(R.id.act_root_view) as? BaseFragment
    }

    override fun onBackPressed() {
        if (getCurrentFragment()?.shouldAuthoriseBack() == false) {
            getCurrentFragment()?.onBackPressed()
        } else {
            super.onBackPressed()
        }
    }

    fun setRootFragment(newFragment: BaseFragment, view: Int = R.id.act_root_view) {
        val frameId = newFragment.frameId.takeIf { it > 0 } ?: view
        try {
            if (newFragment.frameId == 0) {
                supportFragmentManager.popBackStackImmediate(null, FragmentManager.POP_BACK_STACK_INCLUSIVE)
            } else {
                supportFragmentManager.popBackStackImmediate(newFragment.frameId, FragmentManager.POP_BACK_STACK_INCLUSIVE)
            }
        } catch (e:Throwable) {
            return
        }
        val fragment = supportFragmentManager.beginTransaction()
        fragment.replace(frameId, newFragment)
        fragment.commit()
    }
}