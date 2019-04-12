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
            return Gson().fromJson<List<String>>(PreferenceManager.getDefaultSharedPreferences(context).getString(key, ""),
                    ArrayList::class.java)
        }
    }
}