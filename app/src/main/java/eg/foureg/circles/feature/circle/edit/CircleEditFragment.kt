package eg.foureg.circles.feature.circle.edit


import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProviders
import android.content.Context
import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import com.jakewharton.rxbinding2.view.RxView
import eg.foureg.circles.R
import eg.foureg.circles.circles.data.CircleData
import eg.foureg.circles.common.ToastHolder
import eg.foureg.circles.common.message.data.Message
import eg.foureg.circles.common.message.server.MessageServer
import eg.foureg.circles.feature.activitymain.content.ContentActivity
import eg.foureg.circles.feature.activitymain.content.ContentActivityMessages
import io.reactivex.disposables.Disposable
import kotlinx.android.synthetic.main.fragment_circle_edit.*
import kotlinx.android.synthetic.main.fragment_circle_edit.view.*

/**
 * A fragment to add/edit circle
 *
 */
class CircleEditFragment : Fragment() {

    var viewModel = CircleEditViewModel()
    val listOfDisposable: ArrayList<Disposable> = ArrayList()
    var editCircle: CircleData? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            editCircle = it.getParcelable<CircleData>(CIRCLE_DATA_PARAM)
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_circle_edit, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel = ViewModelProviders.of(this).get(CircleEditViewModel::class.java)

        viewModel.circleName.observe(this, Observer { name: String? ->
            fragment_circle_edit_circle_name_editor.setText(name)
        })

        viewModel.progressVisibility.observe(this, Observer { visibility: Int? ->
            fragment_circle_edit_loading_progress.visibility = visibility!!
        })

        listOfDisposable.add(RxView.clicks(fragment_circle_edit_save_btn)
                .subscribe {
                    saveCircle(view.fragment_circle_edit_circle_name_editor)
                })

        viewModel.initCircle(activity as Context, editCircle)
    }

    override fun onDestroy() {
        super.onDestroy()
        for (dispose in listOfDisposable) {
            dispose.dispose()
        }
    }

    private fun saveCircle(circleNameEditor: EditText) {
        viewModel.circleName.value = circleNameEditor.text.toString()

        // TODO: save circle contacts

        // save circle
        listOfDisposable.add(viewModel.addNewCircle(activity as Context)
                .subscribe {
                    viewModel.progressVisibility.value = View.GONE

                    ToastHolder.showToast(activity as Context, getString(R.string.txt_circle_saved_message))

                    exitCirclesEdit()
                })
    }

    private fun exitCirclesEdit() {

        val msg = Message()
        msg.id = ContentActivityMessages.MSG_ID_CLOSE_CONTENT_ACTIVITY_AND_REFRESH_CONTACTS
        MessageServer.getInstance().sendMessage(ContentActivity::class.java, msg)
    }


    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @return A new instance of fragment CircleEditFragment.
         */
        @JvmStatic
        fun newInstance(circleData: CircleData?) =
                CircleEditFragment().apply {
                    arguments = Bundle().apply {
                        putParcelable(CIRCLE_DATA_PARAM, circleData)
                    }
                }

        const val CIRCLE_DATA_PARAM = "CIRCLE_DATA_PARAM"
    }
}
