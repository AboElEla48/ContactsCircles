package eg.foureg.circles.feature.phone.caller

import android.content.Context
import android.content.Intent
import android.net.Uri

object PhoneCaller {

    fun callPhone(context: Context, phoneNumber: String) {
        val intent = Intent(Intent.ACTION_CALL, Uri.fromParts("tel", phoneNumber, null))
        context.startActivity(intent)
    }
}