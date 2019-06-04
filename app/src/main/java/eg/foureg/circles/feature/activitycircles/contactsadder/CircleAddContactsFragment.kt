package eg.foureg.circles.feature.activitycircles.contactsadder


import android.app.Activity
import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProviders
import android.content.Context
import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ListView

import eg.foureg.circles.R
import eg.foureg.circles.circles.data.CircleData
import eg.foureg.circles.feature.contacts.models.ContactsModel
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import kotlinx.android.synthetic.main.fragment_circle_add_contacts.view.*


/**
 * This Fragment to add contacts to circle
 *
 */
class CircleAddContactsFragment : Fragment() {

    lateinit var circleData: CircleData
    lateinit var circleAddContactsViewModel : CircleAddContactsViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {bundle->
            circleData = bundle.getParcelable(PARAM_CIRCLE_DATA)!!

            circleAddContactsViewModel = ViewModelProviders.of(this).get(CircleAddContactsViewModel::class.java)
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_circle_add_contacts, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        view.fragment_circle_add_contacts_list_view.choiceMode = ListView.CHOICE_MODE_MULTIPLE

        // display contacts into list
        circleAddContactsViewModel.contacts.observe(this, Observer {contacts ->
            val contactsNames: ArrayList<String> = ArrayList()

            Observable.fromIterable(contacts)
                    .blockingSubscribe {contactData ->
                        contactsNames.add(contactData.name)
                    }

            // Change adapter
            view.fragment_circle_add_contacts_list_view.adapter = CircleAddContactsListAdapter(activity as Context, contactsNames)

            view.fragment_circle_add_contacts_loading_progress.visibility = View.GONE
        })

        circleAddContactsViewModel.initContacts(activity as Activity)
    }


    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @return A new instance of fragment CircleAddContactsFragment.
         */
        @JvmStatic
        fun newInstance(circleData: CircleData) =
                CircleAddContactsFragment().apply {
                    arguments = Bundle().apply {
                        putParcelable(PARAM_CIRCLE_DATA, circleData)
                    }
                }

        const val PARAM_CIRCLE_DATA : String = "PARAM_CIRCLE_DATA"
    }
}
