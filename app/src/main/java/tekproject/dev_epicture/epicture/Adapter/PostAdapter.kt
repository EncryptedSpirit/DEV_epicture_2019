package tekproject.dev_epicture.epicture.Adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import tekproject.dev_epicture.epicture.Model.PostModel
import tekproject.dev_epicture.epicture.R

class PostAdapter(private val mContext: Context, private val mData: MutableList<PostModel>) : RecyclerView.Adapter<PostAdapter.PostItemView>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PostItemView {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.rcv_tabpost_layout, parent, false)
        return PostItemView(view)
    }

    override fun onBindViewHolder(holder: PostItemView, position: Int) {
        val requestOptions = RequestOptions().placeholder(R.drawable.ic_launcher_background)

        Glide.with(mContext)
            .load(mData[position].mImageUrl)
            .apply(requestOptions)
            .into(holder.image)
    }

    override fun getItemCount(): Int {
        return mData.size
    }

    inner class PostItemView(itemView: View) : RecyclerView.ViewHolder(itemView) {
        internal var image: ImageView = itemView.findViewById(R.id.img)
//        internal var name: TextView = itemView.findViewById(R.id.txt)
    }
}