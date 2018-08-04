package eg.foureg.circles.common.message.server

import eg.foureg.circles.common.actor.Actor
import eg.foureg.circles.common.message.data.Message
import io.reactivex.Observable
import io.reactivex.functions.Consumer
import io.reactivex.functions.Predicate
import kotlin.jvm.internal.ClassReference
import kotlin.reflect.KClass

/**
 * This is the message server that communicate messages among different actors
 */
class MessageServer private constructor(){

    /**
     * Make this class as singleton
     */
    companion object {
        private val messageServer = MessageServer()

        fun getInstance(): MessageServer {
            return messageServer
        }

    }

    /**
     * Add actor to active message listeners
     */
    fun registerActor(actor: Actor) {
        actorsList.add(actor)
    }

    /**
     * Remove actor from active message liteners
     */
    fun unregisterActor(actor: Actor) {
        actorsList.remove(actor)
    }

    /**
     * Helper function to quickly send message of type text
     */
    fun sendMessage(clazz: KClass<Any>, id : Int, text : String) {
        val message = Message()
        message.id = id
        message.data.put(Message.DEFAULT_PARAM_NAME, text)
        sendMessage(clazz, message)
    }

    /**
     * send message to actor
     */
    fun sendMessage(clazz: KClass<Any>, message: Message) {

        Observable.fromIterable(actorsList)
                .filter(Predicate { actor: Actor ->  (actor::class as ClassReference).jClass.name.equals((clazz as ClassReference).jClass.name)})
                .subscribe(Consumer { actor: Actor -> actor.handleMessage(message) })
    }

    // Hold list of actors
    var actorsList: ArrayList<Actor> = ArrayList()
}