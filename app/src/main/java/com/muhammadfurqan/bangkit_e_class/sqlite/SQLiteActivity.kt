package com.muhammadfurqan.bangkit_e_class.sqlite

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.AppCompatButton
import androidx.appcompat.widget.AppCompatEditText
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.muhammadfurqan.bangkit_e_class.R
import com.muhammadfurqan.bangkit_e_class.databinding.ActivitySqliteBinding
import com.muhammadfurqan.bangkit_e_class.sqlite.adapter.MyBookAdapter
import com.muhammadfurqan.bangkit_e_class.sqlite.db.MyBookDatabase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.launch

class SQLiteActivity : AppCompatActivity() {

    // implement recyclerview to show the book list (only name)
    // the recyclerview data must be updated every time new book added
    // item must have edit function to change the book name
    // item must have delete function to delete book

    private lateinit var etBookName: AppCompatEditText
    private lateinit var btnAdd: AppCompatButton
    private lateinit var btnRead: AppCompatButton
    private lateinit var bookAdapter: MyBookAdapter
    private lateinit var binding: ActivitySqliteBinding
    private lateinit var myBookDatabase: MyBookDatabase

    private val bookDatabase: MyBookDatabase by lazy {
        MyBookDatabase(this)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySqliteBinding.inflate(layoutInflater)
        setContentView(binding.root)

        etBookName = findViewById(R.id.et_book_name)

        binding.rvBooks.layoutManager = LinearLayoutManager(this)
        bookAdapter = MyBookAdapter(this)
        binding.rvBooks.adapter = bookAdapter

        myBookDatabase = MyBookDatabase(this)
        loadDataAsync()

        btnAdd = findViewById(R.id.btn_add)
        btnAdd.setOnClickListener {
            onAdd()
        }

        btnRead = findViewById(R.id.btn_read)
        btnRead.setOnClickListener {
            onRead()
        }
    }

    override fun onStart() {
        super.onStart()
        loadDataAsync()
    }

    private fun loadDataAsync() {
        lifecycleScope.launch {
            val books = myBookDatabase.getAllBooks()
            if (books.size > 0) {
                bookAdapter.bookList = books as ArrayList<BookModel>
            } else {
                bookAdapter.bookList.clear()
                bookAdapter.notifyDataSetChanged()
                Toast.makeText(this@SQLiteActivity, "No Book Data at This Moment", Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun onAdd() {
        val bookName = etBookName.text.toString()
        etBookName.setText("")

        if (bookName.isNotEmpty()) {
            lifecycleScope.launch {
                bookDatabase.addBook(bookName)
                loadDataAsync()
            }
            Toast.makeText(this, "Insert Book Success", Toast.LENGTH_SHORT).show()
        } else {
            Toast.makeText(this, "Please input the book name", Toast.LENGTH_SHORT).show()
        }
    }

    private fun onRead() {
        val bookList = bookDatabase.getAllBooks()
        val stringOfBookList = bookList.joinToString(separator = "\n") {
            "Book ${it.id} is ${it.name}"
        }
        if (stringOfBookList.isEmpty()) {
            Toast.makeText(this, "No Books Available", Toast.LENGTH_LONG).show()
        } else {
            Toast.makeText(this, stringOfBookList, Toast.LENGTH_LONG).show()
        }
    }
}