package com.muhammadfurqan.bangkit_e_class.sqlite.db

import android.content.ContentValues
import android.content.Context
import com.muhammadfurqan.bangkit_e_class.sqlite.BookModel
import kotlinx.coroutines.CoroutineScope

/**
 * @author by furqan on 08/04/2021
 */
class MyBookDatabase(context: Context) {

    private val openHelper: MyBookOpenHelper by lazy {
        MyBookOpenHelper(context)
    }

    private val readableDb by lazy {
        openHelper.readableDatabase
    }

    fun addBook(name: String) {
        val writeableDb = openHelper.writableDatabase

        val values = ContentValues()
        values.put(MyBookOpenHelper.FIELD_NAME, name)

        writeableDb.insert(MyBookOpenHelper.TABLE_BOOK, null, values)
        writeableDb.close()
    }

    // create update function
    fun updateBook(id: String, newName: String) {
        val writeableDb = openHelper.writableDatabase

        val values = ContentValues()
        values.put(MyBookOpenHelper.FIELD_NAME, newName)

        writeableDb.update(MyBookOpenHelper.TABLE_BOOK, values, "${MyBookOpenHelper.FIELD_ID} = ?", arrayOf(id))
        writeableDb.close()
    }

    // create delete function
    fun deleteBook(id: String) {
        val writeableDb = openHelper.writableDatabase

        writeableDb.delete(MyBookOpenHelper.TABLE_BOOK, "${MyBookOpenHelper.FIELD_ID} = ?", arrayOf(id))
        writeableDb.close()
    }

    fun getAllBooks(): List<BookModel> {
        val bookList: MutableList<BookModel> = mutableListOf()

        val cursor = readableDb.rawQuery(
            "SELECT * FROM ${MyBookOpenHelper.TABLE_BOOK}",
            null
        )

        cursor?.apply {
            while (moveToNext()) {
                val book = BookModel(
                    getInt(getColumnIndexOrThrow(MyBookOpenHelper.FIELD_ID)),
                    getString(getColumnIndexOrThrow(MyBookOpenHelper.FIELD_NAME))
                )
                bookList.add(book)
            }
        }

        return bookList
    }

}