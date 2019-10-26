package tekproject.dev_epicture.epicture.ApiRequests

import android.graphics.Bitmap
import org.json.JSONArray
import org.json.JSONObject
import tekproject.dev_epicture.epicture.Model.HomeImagesModel
import tekproject.dev_epicture.epicture.Model.UserDataModel
import tekproject.dev_epicture.epicture.Model.UserPostModel

class ParseRequests {
    fun getHomeImages(jsonResponse: JSONArray, index: Int): HomeImagesModel? {
        return HomeSeatchRequests(jsonResponse, index)
    }

    fun getSearchImage(jsonResponse: JSONArray, index: Int): HomeImagesModel? {
        return HomeSeatchRequests(jsonResponse, index)
    }

    fun getAccountPostImage(jsonResponse: JSONArray, index: Int): UserPostModel {
        val obj = jsonResponse.getJSONObject(index)

        val imageId     = obj.getString("id")
        val imageUrl    = obj.getString("link")

        return UserPostModel(imageId, imageUrl)
    }

    fun getAccountData(jsonResponse: JSONObject, index: Int = 0): UserDataModel {
        val obj = jsonResponse

        val avatarUrl = obj.getString("avatar")
        val coverUrl = obj.getString("cover")
        val username = obj.getString("url")
        val userBio = obj.getString("bio")
        val userRep = obj.getInt("reputation").toString()
        val userRepName = obj.getString("reputation_name")
        val userCreation = obj.getInt("created").toString()

        return UserDataModel(avatarUrl, coverUrl, username, userBio, userRep, userRepName, userCreation)
    }

    fun getFavoriteImgId(jsonResponse: JSONArray, index: Int): String {
        return jsonResponse.getJSONObject(index).getString("cover")
    }

    fun getFavorite(jsonResponse: JSONObject, index: Int): String {
        return jsonResponse.getString("link")
    }

    fun isFavorite(jsonResponse: JSONObject, index: Int): Boolean {
        if (jsonResponse.getString("favorite") == "true")
            return true
        return false
    }

    private fun HomeSeatchRequests(jsonResponse: JSONArray, index: Int): HomeImagesModel? {
        val obj = jsonResponse.getJSONObject(index)

        if (obj.has("images")) {
            val imageUrl = obj.getJSONArray("images").getJSONObject(0).getString("link")
            if (imageUrl.endsWith(".png") or imageUrl.endsWith(".jpg") or imageUrl.endsWith(".gif")) {
                val imageTitle  = obj.getString("title")
                val username    = obj.getString("account_url")
                val isFavorite  = obj.getJSONArray("images").getJSONObject(0).getString("favorite")
                val imageId     = obj.getString("cover")
                val userId      = obj.getString("account_id")
                val avatar      = "https://i.imgur.com/Ero0SiK_d.png?maxwidth=290&fidelity=grand"

                return HomeImagesModel(imageId, userId, imageTitle, imageUrl, avatar, username, isFavorite)
            }
        }
        return null
    }


}