package tekproject.dev_epicture.epicture.Adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import tekproject.dev_epicture.epicture.Model.HomeImagesModel
import tekproject.dev_epicture.epicture.R

class SearchAdapter(context: Context, data: MutableList<HomeImagesModel>): RecyclerView.Adapter<SearchAdapter.SearchViewHolder>() {
    private val mContext = context
    private val mData = data

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SearchViewHolder {
        val view: View = LayoutInflater.from(mContext).inflate(R.layout.rcv_search_layout, parent, false)
        return SearchViewHolder(view)
    }

    override fun getItemCount(): Int {
        return mData.size
    }

    override fun onBindViewHolder(holder: SearchViewHolder, position: Int) {
        val currentItem = mData[position] ?: return

        val imageUrl: String = currentItem.imageUrl

        if (imageUrl.endsWith(".gif"))
            Glide.with(mContext).asGif().load(imageUrl).into(holder.image)
        else
            Glide.with(mContext).load(imageUrl).into(holder.image)
    }

    inner class SearchViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val image = itemView.findViewById<ImageView>(R.id.img_g)
    }
}