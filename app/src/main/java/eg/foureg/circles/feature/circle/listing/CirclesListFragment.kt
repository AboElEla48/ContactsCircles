package eg.foureg.circles.feature.circle.listing


import android.arch.lifecycle.ViewModelProviders
import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.jakewharton.rxbinding2.view.RxView

import eg.foureg.circles.R
import eg.foureg.circles.common.message.data.Message
import eg.foureg.circles.common.message.server.MessageServer
import eg.foureg.circles.feature.main.MainActivity
import eg.foureg.circles.feature.main.MainActivityMessages
import io.reactivex.disposables.Disposable
import kotlinx.android.synthetic.main.fragment_circles_list.*
import kotlin.reflect.KClass


/**
 * This fragment to view list of circles
 *
 */
class CirclesListFragment : Fragment() {

    private var circlesListViewModel = CirclesListViewModel()
    private val listOfDisposable : ArrayList<Disposable> = ArrayList()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_circles_list, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        circlesListViewModel = ViewModelProviders.of(this).get(CirclesListViewModel::class.java)

        listOfDisposable.add(RxView.clicks(fragment_circles_list_add_circle_image_view).subscribe {
            val msg = Message()

            msg.id = MainActivityMessages.MSG_ID_VIEW_CIRCLE_EDITOR
            MessageServer.getInstance().sendMessage(MainActivity::class.java, msg)
        })
    }

    override fun onDestroy() {
        super.onDestroy()
        for (disposable in listOfDisposable){
            disposable.dispose()
        }
    }


    companion object {
        /**
         * Factory method to create fragment instance
         * @return A new instance of fragment CirclesListFragment.
         */
        @JvmStatic
        fun newInstance() =
                CirclesListFragment().apply {
                    arguments = Bundle().apply {

                    }
                }
    }
}