package tekproject.dev_epicture.epicture.Adapter

import android.animation.Animator
import android.content.Context
import android.os.Handler
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.airbnb.lottie.LottieAnimationView
import com.bumptech.glide.Glide
import tekproject.dev_epicture.epicture.Model.HomeImagesModel
import tekproject.dev_epicture.epicture.R
import org.json.JSONArray
import org.json.JSONObject
import tekproject.dev_epicture.epicture.ApiRequests.Requests
import tekproject.dev_epicture.epicture.ApiRequests.UrlRequests
import tekproject.dev_epicture.epicture.Interface.ILoadData
import tekproject.dev_epicture.epicture.Interface.VolleyCallback
import android.widget.Toast
import androidx.appcompat.widget.PopupMenu


class HomeAdapter(recyclerView: RecyclerView, context: Context, data: MutableList<HomeImagesModel>): RecyclerView.Adapter<HomeAdapter.HomeViewHolder>() {
    val mContext = context
    val mData = data
    lateinit var loader: ILoadData

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HomeViewHolder {
        val view: View =
            LayoutInflater.from(mContext).inflate(R.layout.rcv_home_feed_layout, parent, false)
        return HomeViewHolder(view)
    }

    override fun getItemCount(): Int {
        return mData.size
    }

    override fun onBindViewHolder(holder: HomeViewHolder, position: Int) {
        val currentItem = mData[position]

        val imageUrl: String = currentItem.imageUrl
        val username: String = currentItem.username
        var imageTitle: String = currentItem.imageTile
        val avatar: String = currentItem.avatarUrl

        if (imageTitle.length >= 25)
            imageTitle = imageTitle.replaceRange(25, imageTitle.length, "...")
        holder.title.text = imageTitle
        holder.userN.text = username
        if (imageUrl.endsWith(".gif"))
            Glide.with(mContext).asGif().load(imageUrl).into(holder.image)
        else
            Glide.with(mContext).load(imageUrl).into(holder.image)
        Glide.with(mContext).load(avatar).into(holder.avatar)
        if (currentItem.isFavorite == "true") {
            holder.fav.setImageResource(R.drawable.ic_favorite_fill)
        } else {
            holder.fav.setImageResource(R.drawable.ic_favorite)
        }

        holder.image.setOnClickListener(object : View.OnClickListener {
            @Volatile
            var i = 0

            override fun onClick(v: View) {
                i++
                val handler = Handler()
                val r = Runnable {
                    if (i == 1) {
                        //wide open
                    }
                }
                if (i == 1) {
                    handler.postDelayed(r, 150)
                } else if (i == 2) {
                    handler.removeCallbacks(r)
                    i = 0
                    fav_animation(currentItem.imageId, holder.anim_fav, holder.fav)
                }
            }
        })
        holder.fav.setOnClickListener {
            fav_animation(currentItem.imageId, holder.anim_fav, holder.fav)
        }

        holder.more.setOnClickListener {
            val popup = PopupMenu(mContext, holder.more)

            popup.menuInflater.inflate(R.menu.homedrop, popup.menu)
            popup.setOnMenuItemClickListener { item ->
                when (item.title) {
                    "share" -> {
                        Toast.makeText(mContext, mContext.getString(R.string.sharemsg), Toast.LENGTH_SHORT).show()
                    }
                    "report" -> {
                        Toast.makeText(mContext, mContext.getString(R.string.reportmsg), Toast.LENGTH_SHORT).show()
                    }
                }
                true
            }

            popup.show() //showing popup menu
        }

    }

    inner class HomeViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val image = itemView.findViewById<ImageView>(R.id.feed_img)
        val title = itemView.findViewById<TextView>(R.id.title_img)
        val userN = itemView.findViewById<TextView>(R.id.username)
        val avatar = itemView.findViewById<ImageView>(R.id.account_icon)
        val more  = itemView.findViewById<ImageButton>(R.id.close_btn)
        val fav = itemView.findViewById<ImageButton>(R.id.favorite_btn)
        val anim_fav = itemView.findViewById<LottieAnimationView>(R.id.fav_anim)

    }

    private fun fav_animation(imageId: String, anim_fav: LottieAnimationView, fav: ImageButton) {
        val req = Requests()

        req.makePostRequest(object : VolleyCallback {
            override fun onSuccessResponse(result: JSONArray) {}
            override fun onSuccessResponse(result: JSONObject) {}
            override fun onFailedResponse() {}

        }, UrlRequests().postFavorite(imageId), mContext, "bearer")

        anim_fav.progress = 0F
        anim_fav.speed = 2F
        anim_fav.visibility = View.VISIBLE
        anim_fav.playAnimation()
        anim_fav.addAnimatorListener(object : Animator.AnimatorListener {
            override fun onAnimationEnd(animation: Animator?) {
                anim_fav.visibility = View.GONE
            }

            override fun onAnimationRepeat(animation: Animator?) {}
            override fun onAnimationCancel(animation: Animator?) {}
            override fun onAnimationStart(animation: Animator?) {}
        })
        fav.setImageResource(R.drawable.ic_favorite_fill)
    }
}