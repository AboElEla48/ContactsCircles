package eg.foureg.circles.common.message.data

import android.os.Bundle

/**
 * Define the message that can be exchanged among actors
 */
class Message {

    companion object {
        const val DEFAULT_PARAM_NAME : String = "DEFAULT_PARAM_NAME"
    }
    val data : Bundle = Bundle()
    var id : Int = -1
}