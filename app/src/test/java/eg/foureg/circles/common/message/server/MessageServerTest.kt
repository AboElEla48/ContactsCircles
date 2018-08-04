package eg.foureg.circles.common.message.server

import eg.foureg.circles.common.actor.Actor
import eg.foureg.circles.common.message.server.sample.data.Actor1
import eg.foureg.circles.common.message.server.sample.data.Actor2
import io.reactivex.Observable
import io.reactivex.functions.Consumer
import io.reactivex.functions.Predicate
import org.junit.Assert
import org.junit.Before
import org.junit.Test
import kotlin.jvm.internal.ClassReference
import kotlin.reflect.KClass


class MessageServerTest {

    val actor1 : Actor1 = Actor1()
    val actor2 : Actor2 = Actor2()

    @Before
    fun prepareActors() {
        MessageServer.getInstance().actorsList.clear()

        MessageServer.getInstance().registerActor(actor1)
        MessageServer.getInstance().registerActor(actor2)
    }

    @Test
    fun registerActor() {
        // Assure items are registered successfully
        Assert.assertEquals(MessageServer.getInstance().actorsList.size, 2)

        Assert.assertTrue(MessageServer.getInstance().actorsList.get(0) is Actor1)
        Assert.assertTrue(MessageServer.getInstance().actorsList.get(1) is Actor2)
    }

    @Test
    fun unregisterActor() {

        // Assure there is one item removed
        MessageServer.getInstance().unregisterActor(actor2)
        Assert.assertEquals(MessageServer.getInstance().actorsList.size, 1)
        Assert.assertTrue(MessageServer.getInstance().actorsList.get(0) is Actor1)
    }



    @Test
    fun sendMessage() {
        val clazz : KClass<Any> = Actor2::class as KClass<Any>
        var result = false
        var className = ""

        Observable.fromIterable(MessageServer.getInstance().actorsList)
                .filter(Predicate { actor: Actor ->  (actor::class as ClassReference).jClass.name.equals((clazz as ClassReference).jClass.name)})
                .subscribe(Consumer {
                    actor: Actor -> className = actor.javaClass.name
                    result = true})

        Assert.assertTrue(result)
        Assert.assertTrue(!className.equals(""))

    }
}