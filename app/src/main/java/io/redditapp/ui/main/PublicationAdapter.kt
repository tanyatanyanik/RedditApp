package io.redditapp.ui.main

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import io.redditapp.R
import io.redditapp.data.DataChildrenEntity

class PublicationAdapter(val onImageClick: (itemId: String?) -> Unit) :
    RecyclerView.Adapter<PublicationAdapter.PublicationViewHolder>() {

    val publications = mutableListOf<DataChildrenEntity>()

    fun setData(list: List<DataChildrenEntity>) {
        publications.clear()
        publications.addAll(list)
        notifyDataSetChanged()
    }

    override fun getItemId(position: Int): Long {
        return position.toLong()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PublicationViewHolder {
        return PublicationViewHolder(
            LayoutInflater.from(parent.context).inflate(R.layout.item_publication, parent, false)
        )
    }

    override fun getItemCount(): Int {
        return publications.size
    }

    override fun onBindViewHolder(holder: PublicationViewHolder, position: Int) {
        val pub = publications[position]
        val context = holder.itemView.context

        holder.tvAuthor.text = pub.authorFullname
        val comments = pub.numComments ?: 0
        if (comments == 1)
            holder.tvComments.text = context.getString(R.string.one_comment)
        else
            holder.tvComments.text = context.getString(R.string.comments, (comments).toString())

        //todo add Glide for iv

        holder.iv.setOnClickListener {
            onImageClick(pub.id)
        }
    }

    class PublicationViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val tvAuthor: TextView = itemView.findViewById(R.id.tvAuthor) as TextView
        val tvComments: TextView = itemView.findViewById(R.id.tvComments) as TextView
        val tvDate: TextView = itemView.findViewById(R.id.tvDate) as TextView
        val iv: ImageView = itemView.findViewById(R.id.iv) as ImageView
    }

}