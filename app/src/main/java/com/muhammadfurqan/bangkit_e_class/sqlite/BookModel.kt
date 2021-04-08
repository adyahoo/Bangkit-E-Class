package com.muhammadfurqan.bangkit_e_class.sqlite

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class BookModel(
    val id: Int,
    val name: String
):Parcelable