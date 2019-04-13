package eg.foureg.circles.common

import android.content.Context
import android.preference.PreferenceManager
import com.google.gson.Gson

class PrefHelper {
    companion object {

        fun saveString(context: Context, key: String, value: String) {
            PreferenceManager.getDefaultSharedPreferences(context).edit().putString(key, value).apply()
        }

        fun loadString(context: Context, key: String): String? {
            return PreferenceManager.getDefaultSharedPreferences(context).getString(key, "")
        }

        fun saveStringArray(context: Context, key: String, values: List<String>) {
            PreferenceManager.getDefaultSharedPreferences(context).edit().putString(key, Gson().toJson(values)).apply()
        }

        fun loadStringArray(context: Context, key: String): List<String> {
            val str = PreferenceManager.getDefaultSharedPreferences(context).getString(key, "")
            if(str?.length!! > 0) {
                return Gson().fromJson<List<String>>(str, ArrayList::class.java)
            }

            return ArrayList()
        }
    }
}