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
import com.jakewharton.rxbinding2.view.RxView

import eg.foureg.circles.R
import eg.foureg.circles.circles.data.CircleData
import eg.foureg.circles.common.message.server.MessageServer
import eg.foureg.circles.contacts.data.ContactData
import eg.foureg.circles.feature.activitycircles.CirclesActivity
import eg.foureg.circles.feature.activitycircles.CirclesActivityMessages
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers
import kotlinx.android.synthetic.main.fragment_circle_add_contacts.view.*


/**
 * This Fragment to add contacts to circle
 *
 */
class CircleAddContactsFragment : Fragment() {

    lateinit var circleData: CircleData
    lateinit var circleAddContactsViewModel : CircleAddContactsViewModel
    lateinit var circleContactsList: ArrayList<ContactData>
    lateinit var allContactsNamesList: ArrayList<String>

    val listOfDisposables : ArrayList<Disposable> = ArrayList()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {bundle->
            circleData = bundle.getParcelable(PARAM_CIRCLE_DATA)!!
            circleContactsList = bundle.getParcelableArrayList<ContactData>(PARAM_CIRCLE_CONTACTS_DATA)!!

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

        // display contacts into list
        circleAddContactsViewModel.contacts.observe(this, Observer {contacts ->
            allContactsNamesList = ArrayList()

            Observable.fromIterable(contacts)
                    .blockingSubscribe {contactData ->
                        allContactsNamesList.add(contactData.name)
                    }

            // Change adapter
            view.fragment_circle_add_contacts_list_view.adapter = CircleAddContactsListAdapter(activity as Context, allContactsNamesList)

            initContactsSelection(view)

            view.fragment_circle_add_contacts_loading_progress.visibility = View.GONE
            view.fragment_circle_add_contacts_list_view.visibility = View.VISIBLE
            view.fragment_circle_add_contacts_navigation_layout.visibility = View.VISIBLE
        })

        // Handle save button
        listOfDisposables.add(RxView.clicks(view.fragment_circle_add_contacts_save_btn)
                .subscribe {

                    val selectedContactsPhones : ArrayList<String> = ArrayList()

                    for(index : Int in 0 until view.fragment_circle_add_contacts_list_view.count) {
                        if (view.fragment_circle_add_contacts_list_view.isItemChecked(index)) {
                            val circleData = circleAddContactsViewModel.contacts.value?.get(index)!!
                            for(phoneNumber in circleData.phones!!){
                                if(phoneNumber.phoneNumber.isNotEmpty()) {
                                    selectedContactsPhones.add(phoneNumber.phoneNumber)
                                }
                            }

                        }
                    }

                    // save contacts into circle
                    view.fragment_circle_add_contacts_loading_progress.visibility = View.VISIBLE
                    view.fragment_circle_add_contacts_navigation_layout.visibility = View.GONE
                    circleAddContactsViewModel.updateCircleContacts(activity as Activity,
                            circleData.circleID,
                            selectedContactsPhones)
                            .subscribeOn(Schedulers.io())
                            .observeOn(AndroidSchedulers.mainThread())
                            .subscribe {
                                // send message to close activity
                                MessageServer.getInstance().sendMessage(CirclesActivity::class.java,
                                        CirclesActivityMessages.MSG_ID_SHOW_CIRCLE_CONTACTS, "")
                            }


                })

        // Handle cancel button
        listOfDisposables.add(RxView.clicks(view.fragment_circle_add_contacts_cancel_btn)
                .subscribe {
                    MessageServer.getInstance().sendMessage(CirclesActivity::class.java,
                            CirclesActivityMessages.MSG_ID_SHOW_CIRCLE_CONTACTS, "")
                })

        circleAddContactsViewModel.initContacts(activity as Activity)

    }

    private fun initContactsSelection(view: View) {

        val circleContactsNames : ArrayList<String> = ArrayList()
        Observable.fromIterable(circleContactsList)
                .blockingSubscribe {contactData ->
                    circleContactsNames.add(contactData.name)
                }

        for(index : Int in 0 until allContactsNamesList.size) {

            if(circleContactsNames.contains(allContactsNamesList.get(index))) {
                view.fragment_circle_add_contacts_list_view.setItemChecked(index, true)
            }
        }
    }

    override fun onDestroy() {
        super.onDestroy()

        for (itemDisposable in listOfDisposables) {
            itemDisposable.dispose()
        }
    }


    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @return A new instance of fragment CircleAddContactsFragment.
         */
        @JvmStatic
        fun newInstance(circleData: CircleData, contactsList: ArrayList<ContactData>) =
                CircleAddContactsFragment().apply {
                    arguments = Bundle().apply {
                        putParcelable(PARAM_CIRCLE_DATA, circleData)
                        putParcelableArrayList(PARAM_CIRCLE_CONTACTS_DATA, contactsList)
                    }
                }

        const val PARAM_CIRCLE_DATA : String = "PARAM_CIRCLE_DATA"
        const val PARAM_CIRCLE_CONTACTS_DATA : String = "PARAM_CIRCLE_CONTACTS_DATA"
    }
}
