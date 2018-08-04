package eg.foureg.circles.common.message.data

import android.os.Bundle

/**
 * Define the message that can be exchanged among actors
 */
class Message {

    companion object {
        const val DEFAULT_PARAM_NAME : String = "DEFAULT_PARAM_NAME"
    }
    val data : HashMap<String, Any> = HashMap()
    var id : Int = -1
}