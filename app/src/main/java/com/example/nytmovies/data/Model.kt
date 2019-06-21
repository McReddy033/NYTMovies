package com.example.nytmovies.data

import com.google.gson.annotations.SerializedName

object Model {
    data class Result(
        /**
         * Количество ответов на запрос
         */
        @SerializedName("has_more")
        val hasMore: Boolean,
        /**
         * Есть ли у ответа на запрос продолжение
         */
        @SerializedName("num_results")
        val numResults: Int = 0,
        /**
        * Массив ответов на запрос
        */
        @SerializedName("results")
        val results: List<Movie>
    )
    data class Movie(
        /**
        * Название фильма
         */
        @SerializedName("display_title")
        val displayTitle: String = "",
        /**
         * Автор рецензии
         */
        @SerializedName("byline")
        val byline: String = "",
        /**
         * Заголовок
         */
        @SerializedName("headline")
        val headline: String = "",
        /**
         * Описание
         */
        @SerializedName("summary_short")
        val summaryShort: String = "",
        /**
         * Дата публикации
         */
        @SerializedName("publication_date")
        val publicationDate: String = "",
        /**
         * Ссылка на рецензию и ее название
         */
        @SerializedName("link")
        val link: Link,
        /**
         * Ссылка на изображение и его размеры
         */
        @SerializedName("multimedia")
        val multimedia: Multimedia?
        )

    data class Link (
        /**
         * Ссылка на рецензию
         */
        @SerializedName("url")
        val url: String = "",
        /**
         * Название рецензии
         */
        @SerializedName("suggested_link_text")
        val suggestedLinkText: String = ""
    )

    data class Multimedia(
        /**
         * Ссылка на изображение
         */
        @SerializedName("src")
        val src: String = "",
        /**
         * Ширина изображения
         */
        @SerializedName("width")
        val width: Int = 0,
        /**
         * Высота изображения
         */
        @SerializedName("height")
        val height: Int = 0
    )
}