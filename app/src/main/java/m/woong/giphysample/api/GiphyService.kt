package m.woong.giphysample.api

import m.woong.giphysample.data.source.remote.model.RemoteTrendingGiphyResponse
import retrofit2.http.GET
import retrofit2.http.Query

interface GiphyService {

    @GET(SUB_PATH_TREND)
    suspend fun requestTrendingGiphy(
        @Query("api_key") key: String = API_KEY,
        @Query("limit") limit: Int,
        @Query("offset") offset: Int,
        @Query("rating") rating: String = "g",
        @Query("lang") lang: String = "en"
    ): RemoteTrendingGiphyResponse

    companion object {
        const val API_KEY = "CAvlc5ArNMyF9U0dbm1XJ64TFS6DMsUB"
        const val SUB_PATH_TREND = "trending"
    }
}