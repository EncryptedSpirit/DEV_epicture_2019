package tekproject.dev_epicture.epicture.ApiRequests

import android.content.Context
import android.widget.Toast
import com.android.volley.Request.Method.GET
import com.android.volley.toolbox.Volley
import com.android.volley.*
import com.android.volley.Request.Method.POST
import com.android.volley.toolbox.JsonObjectRequest
import org.json.JSONObject
import tekproject.dev_epicture.epicture.Interface.VolleyCallback
import tekproject.dev_epicture.epicture.R
import java.lang.Exception

class Requests {
    fun makeGetRequest(callback: VolleyCallback, url: String, context: Context, header: String = "client") {
        val headerType: Map<String, String> = mapOf(
            "client" to context.getString(R.string.header_clientId_value),
            "bearer" to context.getString(R.string.header_bearer_value)
        )
        val queue = Volley.newRequestQueue(context)
        queue.cancelAll(GET)
        
        try {
            val request = object : JsonObjectRequest(GET, url, null,
                Response.Listener { response ->
                    if (response.get("data") is JSONObject) {
                        callback.onSuccessResponse(response.getJSONObject("data"))
                    } else
                        callback.onSuccessResponse(response.getJSONArray("data"))
                },
                Response.ErrorListener {
                    callback.onFailedResponse()
                }) {
                @Throws(AuthFailureError::class)
                override fun getHeaders(): Map<String, String> {
                    val headers = HashMap<String, String>()
                    headers[context.getString(R.string.headerAuth)] = headerType[header].toString()
                    return headers
                }
            }
            queue.add(request)
        } catch (e: Exception) {
            //error
        }
    }

    fun makePostRequest(callback: VolleyCallback, url: String, context: Context, header: String = "client") {
        val headerType: Map<String, String> = mapOf(
            "client" to context.getString(R.string.header_clientId_value),
            "bearer" to context.getString(R.string.header_bearer_value)
        )
        val queue = Volley.newRequestQueue(context)
        queue.cancelAll(POST)

        try {
            val request = object : JsonObjectRequest(POST, url, null,
                Response.Listener { response -> },
                Response.ErrorListener { callback.onFailedResponse() }) {
                @Throws(AuthFailureError::class)
                override fun getHeaders(): Map<String, String> {
                    val headers = HashMap<String, String>()
                    headers[context.getString(R.string.headerAuth)] = headerType[header].toString()
                    return headers
                }
            }
            queue.add(request)
        } catch (e: Exception) {
            //error
        }
    }
}