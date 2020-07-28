package com.example.movieapp.model

import com.google.gson.annotations.SerializedName

data class Movie(@SerializedName("overview") var overview:String="Overview",
                 @SerializedName("title") var title:String="Title",
                 @SerializedName("vote_average")var ratings:String="0") {


    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as Movie

        if (overview != other.overview) return false
        if (title != other.title) return false
        if (ratings != other.ratings) return false

        return true
    }

    override fun hashCode(): Int {
        var result = overview.hashCode()
        result = 31 * result + title.hashCode()
        result = 31 * result + ratings.hashCode()
        return result
    }

    override fun toString(): String {
        return "Movie(overview='$overview', title='$title', ratings=$ratings)"
    }
}