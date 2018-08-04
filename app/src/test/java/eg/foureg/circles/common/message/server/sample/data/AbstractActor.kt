package eg.foureg.circles.common.message.server.sample.data

import eg.foureg.circles.common.actor.Actor
import eg.foureg.circles.common.message.data.Message

open class AbstractActor : Actor {

    companion object {
        const val PARAM = "PARAM_NAME"
    }

    override fun handleMessage(message: Message) {
        text = message.data.getString(PARAM)
    }

    var text : String = ""

}