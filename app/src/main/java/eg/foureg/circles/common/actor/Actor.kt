package eg.foureg.circles.common.actor

import eg.foureg.circles.common.message.data.Message

/**
 * Define how actor can be defined
 */
interface Actor {
    fun handleMessage(message : Message)
 }