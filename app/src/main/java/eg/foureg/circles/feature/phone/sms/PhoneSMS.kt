package eg.foureg.circles.feature.phone.caller

import android.content.Context
import android.content.Intent
import android.net.Uri

object PhoneSMS {
    fun sendSMS(context: Context, phoneNumber: String) {
        context.startActivity(Intent(Intent.ACTION_VIEW, Uri.fromParts("sms", phoneNumber, null)))
    }
}

