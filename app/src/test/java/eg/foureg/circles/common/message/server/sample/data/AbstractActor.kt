package eg.foureg.circles.common.message.server.sample.data

import eg.foureg.circles.common.actor.Actor
import eg.foureg.circles.common.message.data.Message

open class AbstractActor : Actor {

    override fun handleMessage(message: Message) {
        text = message.data.get(Message.DEFAULT_PARAM_NAME) as String
    }

    var text : String = ""

}