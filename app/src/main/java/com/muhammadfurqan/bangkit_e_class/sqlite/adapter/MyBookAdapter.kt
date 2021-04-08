package com.muhammadfurqan.bangkit_e_class.sqlite.adapter

import android.app.Activity
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.muhammadfurqan.bangkit_e_class.R
import com.muhammadfurqan.bangkit_e_class.databinding.ItemMyBookBinding
import com.muhammadfurqan.bangkit_e_class.sqlite.BookModel
import com.muhammadfurqan.bangkit_e_class.sqlite.DetailBookActivity

class MyBookAdapter(val activity: Activity): RecyclerView.Adapter<MyBookAdapter.MyBookViewHolder>() {
    var bookList = ArrayList<BookModel>()
        set(bookList) {
            if (bookList.size > 0) {
                this.bookList.clear()
            }
            this.bookList.addAll(bookList)
            notifyDataSetChanged()
        }

    inner class MyBookViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val binding = ItemMyBookBinding.bind(itemView)

        fun bind(book: BookModel) {
            binding.tvBookName.text = book.name
            itemView.setOnClickListener {
                val intent = Intent(activity, DetailBookActivity::class.java)
                intent.putExtra(DetailBookActivity.EXTRA_BOOK, book)
                activity.startActivity(intent)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyBookViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_my_book, parent, false)
        return MyBookViewHolder(view)
    }

    override fun onBindViewHolder(holder: MyBookViewHolder, position: Int) {
        return holder.bind(bookList[position])
    }

    override fun getItemCount(): Int {
        return bookList.size
    }
}