package eg.foureg.circles.common.ui

import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import eg.foureg.circles.common.actor.Actor
import eg.foureg.circles.common.message.data.Message
import eg.foureg.circles.common.message.server.MessageServer

open class BaseFragment : Fragment(), Actor{

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        MessageServer.getInstance().registerActor(this)
        return super.onCreateView(inflater, container, savedInstanceState)
    }

    override fun onDestroy() {
        super.onDestroy()
        MessageServer.getInstance().unregisterActor(this)
    }


    override fun handleMessage(message: Message) {

    }
}