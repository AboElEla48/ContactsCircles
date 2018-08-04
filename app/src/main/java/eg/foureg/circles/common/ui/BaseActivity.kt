package eg.foureg.circles.common.ui

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import eg.foureg.circles.common.actor.Actor
import eg.foureg.circles.common.message.data.Message
import eg.foureg.circles.common.message.server.MessageServer

open class BaseActivity : AppCompatActivity(), Actor {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        MessageServer.getInstance().registerActor(this)
    }

    override fun onDestroy() {
        super.onDestroy()
        MessageServer.getInstance().unregisterActor(this)
    }

    override fun handleMessage(message: Message) {

    }
}