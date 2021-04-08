package com.muhammadfurqan.bangkit_e_class.sqlite

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.MenuItem
import android.widget.Toast
import androidx.lifecycle.lifecycleScope
import com.muhammadfurqan.bangkit_e_class.databinding.ActivityDetailBookBinding
import com.muhammadfurqan.bangkit_e_class.sqlite.db.MyBookDatabase
import kotlinx.coroutines.launch

class DetailBookActivity : AppCompatActivity() {
    private lateinit var binding: ActivityDetailBookBinding
    private lateinit var bookDb: MyBookDatabase
    private lateinit var book: BookModel
    companion object{
        const val EXTRA_BOOK = "book"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailBookBinding.inflate(layoutInflater)
        setContentView(binding.root)

        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        bookDb = MyBookDatabase(this)

        book = intent.getParcelableExtra<BookModel>(EXTRA_BOOK)
        binding.etDetailBookName.setText(book.name)

        binding.btnDeleteBook.setOnClickListener {
            onDelete(book.id)
        }
        binding.btnUpdateBook.setOnClickListener {
            val newName = binding.etDetailBookName.text.toString()
            onUpdate(book.id, newName)
        }
    }

    private fun onUpdate(id: Int, newName: String) {
        if (newName.equals(book.name)) {
            Toast.makeText(this, "Please Input New Name for The Book", Toast.LENGTH_SHORT).show()
        } else {
            lifecycleScope.launch {
                bookDb.updateBook(id.toString(), newName)
                finish()
            }
        }
    }

    private fun onDelete(id: Int) {
        lifecycleScope.launch {
            bookDb.deleteBook(id.toString())
            finish()
        }
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == android.R.id.home) {
            finish()
        }
        return true
    }
}