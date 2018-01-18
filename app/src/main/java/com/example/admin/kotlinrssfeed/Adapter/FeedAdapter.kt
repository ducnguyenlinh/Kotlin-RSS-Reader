package com.example.admin.kotlinrssfeed.Adapter

import android.content.Context
import android.content.Intent
import android.net.Uri
import android.support.v7.widget.RecyclerView
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import com.example.admin.kotlinrssfeed.Interface.ItemClickListener
import com.example.admin.kotlinrssfeed.Model.RSSObject
import com.example.admin.kotlinrssfeed.R

class FeedViewHolder(itemView: View):RecyclerView.ViewHolder(itemView), View.OnClickListener,
        View.OnLongClickListener {

    var tvTitle: TextView
    var tvPubdate: TextView
    var tvContent: TextView

    private var itemClickListener: ItemClickListener?= null //Add privite here

    init {
        tvTitle = itemView.findViewById(R.id.tvTitle) as TextView
        tvPubdate = itemView.findViewById(R.id.tvPubdate) as TextView
        tvContent = itemView.findViewById(R.id.tvContent) as TextView

        itemView.setOnClickListener(this)
        itemView.setOnLongClickListener(this)
    }

    fun setItemClickListener(itemClickListener: ItemClickListener) {
        this.itemClickListener = itemClickListener
    }

    override fun onClick(v: View?) {
        itemClickListener!!.onClick(v, adapterPosition, false)
        //add "!!" here, this will return NON-NULL value of this variable
    }

    override fun onLongClick(v: View?): Boolean {
        itemClickListener!!.onClick(v, adapterPosition, true)
        return true //add return true here
    }
}

class FeedAdapter(private val rssObject: RSSObject, private val mContext: Context):RecyclerView.Adapter<FeedViewHolder>()
{
    private val inflater:LayoutInflater

    init {
        inflater = LayoutInflater.from(mContext)
    }
    override fun onCreateViewHolder(parent: ViewGroup?, viewType: Int): FeedViewHolder {
        val itemView = inflater.inflate(R.layout.item_recyclerview, parent, false)

        return FeedViewHolder(itemView)
    }

    override fun getItemCount(): Int {
        return rssObject.items.size
    }

    override fun onBindViewHolder(holder: FeedViewHolder, position: Int) {
        holder.tvTitle.text = rssObject.items[position].title
        holder.tvPubdate.text = rssObject.items[position].pubDate
        holder.tvContent.text = rssObject.items[position].content

        holder.setItemClickListener(ItemClickListener { view, position, isLongClick ->
            //when user click on item of RecyclerView, we will call WebBrowser to show this link
            if (!isLongClick){
                val browserIntent = Intent(Intent.ACTION_VIEW, Uri.parse(rssObject.items[position].link))
                mContext.startActivity(browserIntent)
        }
        })
    }

}

