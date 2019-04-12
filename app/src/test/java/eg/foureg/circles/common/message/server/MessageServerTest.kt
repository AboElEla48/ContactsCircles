package eg.foureg.circles.common.message.server

import eg.foureg.circles.common.message.server.sample.data.Actor1
import eg.foureg.circles.common.message.server.sample.data.Actor2
import org.junit.Assert
import org.junit.Before
import org.junit.Test
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
        val clazz : Class<*> = Actor2::class.java
        val messageData = "This is message data"

        MessageServer.getInstance().sendMessage(clazz, 11, messageData)
        Assert.assertTrue(actor1.text.equals("This is actor 1"))
        Assert.assertTrue(actor2.text.equals(messageData))

    }

    @Test
    fun broadcastMessage() {

        val messageData = "This is a broadcast message"

        Assert.assertTrue(actor1.text.equals("This is actor 1"))
        Assert.assertTrue(actor2.text.equals("This is actor 2"))

        MessageServer.getInstance().broadcastMessage(27, messageData)
        Assert.assertTrue(actor1.text.equals(messageData))
        Assert.assertTrue(actor2.text.equals(messageData))

    }


}