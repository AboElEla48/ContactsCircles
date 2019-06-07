package eg.foureg.circles.feature.circle.listing


import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProviders
import android.content.Context
import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.jakewharton.rxbinding2.view.RxView
import eg.foureg.circles.R
import eg.foureg.circles.common.message.data.Message
import eg.foureg.circles.common.message.server.MessageServer
import eg.foureg.circles.feature.activitymain.MainActivity
import eg.foureg.circles.feature.activitymain.MainActivityMessages
import io.reactivex.disposables.Disposable
import kotlinx.android.synthetic.main.fragment_circles_list.*
import kotlinx.android.synthetic.main.fragment_circles_list.view.*


/**
 * This fragment to view list of circles
 *
 */
class CirclesListFragment : Fragment() {

    private var circlesListViewModel = CirclesListViewModel()
    private val listOfDisposable: ArrayList<Disposable> = ArrayList()

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

        listOfDisposable.add(RxView.clicks(fragment_circles_list_add_contact_floating_button).subscribe {
            val msg = Message()

            msg.id = MainActivityMessages.MSG_ID_VIEW_CIRCLE_EDITOR
            MessageServer.getInstance().sendMessage(MainActivity::class.java, msg)
        })

        circlesListViewModel.circlesList.observe(this, Observer { list ->
            fragment_circles_list_circles_grid_view.adapter = CirclesGridAdapter(activity as Context, list)
            if(list?.size == 0) {
                view.fragment_circles_list_empty_grid_text_view.visibility = View.VISIBLE
            }
            else {
                view.fragment_circles_list_empty_grid_text_view.visibility = View.GONE
            }
        })

        listOfDisposable.add(circlesListViewModel.loadCircles(activity as Context)
                .subscribe {
                    fragment_circles_list_loading_progress_layout.visibility = View.GONE
                })
    }

    override fun onDestroy() {
        super.onDestroy()
        for (disposable in listOfDisposable) {
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
