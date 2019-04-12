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
import eg.foureg.circles.feature.circle.models.CirclesModel
import io.reactivex.disposables.Disposable
import kotlinx.android.synthetic.main.fragment_circle_edit.*
import kotlinx.android.synthetic.main.fragment_circle_edit.view.*
import org.koin.android.ext.android.inject

/**
 * A fragment to add/edit circle
 *
 */
class CircleEditFragment : Fragment() {

    var viewModel = CircleEditViewModel()
    val listOfDisposable: ArrayList<Disposable> = ArrayList()
    var editCircle: CircleData? = null

    val circlesModel: CirclesModel by inject()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            editCircle = it.getParcelable<CircleData>(CircleEditFragment.CIRCLE_DATA_PARAM)
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

        viewModel.circleContacts.observe(this, Observer { contacts: ArrayList<String>? ->
            // TODO: set circle contacts
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

    private fun saveCircle(circleNameEditor : EditText) {
        viewModel.circleName.value = circleNameEditor.text.toString()
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
                        putParcelable(CircleEditFragment.CIRCLE_DATA_PARAM, circleData)
                    }
                }

        const val CIRCLE_DATA_PARAM = "CIRCLE_DATA_PARAM"
    }
}
