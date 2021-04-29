package com.all.all

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class adapter : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    var mList: ArrayList<String> = ArrayList()

    fun setup (list: ArrayList<String>){
        mList.clear()
        mList.addAll(list)
        notifyDataSetChanged()
    }

    fun del(position: Int){
        mList.removeAt(position)
        notifyDataSetChanged()
    }




    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {

            val layoutInflater = LayoutInflater.from(parent.context)
            val itemView = layoutInflater.inflate(R.layout.recycler_items, parent,false)
            return HeaderViewHolder(itemView = itemView)


    }

    override fun getItemCount(): Int {
        return mList.count()
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {

            if(holder is HeaderViewHolder){
                holder.bindHeader(mList[position])
            }





    }

    class HeaderViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView){
        var categoryHeader: TextView = itemView.findViewById(R.id.text_items)
        fun bindHeader(model: String){
            categoryHeader.text = "${model}"
        }
    }

}