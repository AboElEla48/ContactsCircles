package eg.foureg.circles.common

import android.content.Context
import android.widget.Toast
import eg.foureg.circles.BuildConfig

class ToastHolder {
    companion object {
        fun showToast(context: Context, msg : String) {
            Toast.makeText(context, msg, Toast.LENGTH_LONG).show()
        }

        fun showDebugToast(context: Context, msg : String) {
            if(BuildConfig.DEBUG) {
                Toast.makeText(context, msg, Toast.LENGTH_LONG).show()
            }
        }
    }
}