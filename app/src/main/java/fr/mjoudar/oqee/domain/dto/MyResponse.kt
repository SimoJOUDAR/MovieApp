package fr.mjoudar.oqee.domain.dto


import com.squareup.moshi.Json
import fr.mjoudar.oqee.domain.model.CastCrew
import fr.mjoudar.oqee.domain.model.Movie


data class MoviesItem(@Json(name = "page")
                      val page: Page,
                      @Json(name = "heros")
                      val heros: Heros,
                      @Json(name = "details")
                      val details: Details,
                      @Json(name = "clips")
                      val clips: List<ClipsItem> = listOf()) {

    private fun getTrailer() : String {
        return if (clips.isNotEmpty()) clips[0].versions?.enus?.sizes?.sd?.srcAlt ?: "" else ""
    }

    fun toMovie() = Movie(
        page.movieTitle,
        details.locale?.en?.synopsis ?: "",
        heros.zero?.imageUrl ?: "",
        details.genres.map { it.name },
        page.releaseDate,
        details.runTime,
        getTrailer(),
        details.officialUrl,
        page.copyright,
        CastCrew(
            details.locale?.en?.castcrew?.writers?.map { it.name } ?: listOf(),
            details.locale?.en?.castcrew?.directors?.map { it.name } ?: listOf(),
            details.locale?.en?.castcrew?.actors?.map { it.name } ?: listOf()
        )
    )
}

data class Page(@Json(name = "copyright")
                val copyright: String = "",
                @Json(name = "release_date")
                val releaseDate: String = "",
                @Json(name = "movie_title")
                val movieTitle: String = "")

data class Heros(@Json(name = "0")
                 val zero: Zero? = null)

data class Zero(@Json(name = "imageurl")
            val imageUrl: String = "")

data class Details(@Json(name = "run_time")
                   val runTime: String = "",
                   @Json(name = "genres")
                   val genres: List<GenresItem> = listOf(),
                   @Json(name = "official_url")
                   val officialUrl: String = "",
                   @Json(name = "locale")
                   val locale: Locale1? = null)

data class GenresItem(@Json(name = "name")
                      val name: String = "")

data class Locale1(@Json(name = "en")
                  val en: En1? = null)

data class En1(@Json(name = "synopsis")
              val synopsis: String = "",
              @Json(name = "castcrew")
              val castcrew: Castcrew? = null)

data class Castcrew(@Json(name = "actors")
                    val actors: List<ActorsItem>?,
                    @Json(name = "directors")
                    val directors: List<DirectorsItem>?,
                    @Json(name = "writers")
                    val writers: List<WritersItem>?)

data class ActorsItem(@Json(name = "name")
                      val name: String = "")

data class DirectorsItem(@Json(name = "name")
                         val name: String = "")

data class WritersItem(@Json(name = "name")
                       val name: String = "")


data class ClipsItem(@Json(name = "versions")
                     val versions: Versions? = null)

data class Versions(@Json(name = "enus")
                    val enus: Enus)

data class Enus(@Json(name = "sizes")
                val sizes: Sizes)

data class Sizes(@Json(name = "sd")
                 val sd: Sd)

data class Sd(@Json(name = "srcAlt")
              val srcAlt: String = "")






