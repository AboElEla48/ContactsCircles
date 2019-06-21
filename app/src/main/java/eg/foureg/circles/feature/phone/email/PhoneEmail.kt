package eg.foureg.circles.feature.phone.email

import android.content.Context
import android.content.Intent

object PhoneEmail {
    fun sendEmail(context: Context, email: String) {
        val intent = Intent(Intent.ACTION_SEND)
        intent.type = "text/html"
        intent.putExtra(Intent.EXTRA_EMAIL, email)

        context.startActivity(Intent.createChooser(intent, "Send Email"))
    }
}