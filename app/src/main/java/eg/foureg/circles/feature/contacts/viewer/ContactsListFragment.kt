package eg.foureg.circles.feature.contacts.viewer


import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProviders
import android.content.Context
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

import eg.foureg.circles.R
import eg.foureg.circles.common.ui.BaseFragment
import eg.foureg.circles.contacts.ContactData

/**
 * A simple [Fragment] subclass.
 * Use the [ContactsListFragment.newInstance] factory method to
 * create an instance of this fragment.
 *
 */
class ContactsListFragment : BaseFragment() {

    var viewModel: ContactsListViewModel = ContactsListViewModel()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {}
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_contacts_list, container, false)

        val recyclerView: RecyclerView = view.findViewById(R.id.fragment_contacts_list_recycler_view)
        val context = getActivity() as Context

        recyclerView.setHasFixedSize(true)
        recyclerView.layoutManager = LinearLayoutManager(context)

        viewModel = ViewModelProviders.of(this).get(ContactsListViewModel::class.java)
        viewModel.contacts.observe(this, Observer { contactsList: List<ContactData>? ->
            recyclerView.adapter = ContactsListAdapter(context, contactsList)
        })

        viewModel.loadContacts(context)

        return view
    }

    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @return A new instance of fragment ContactsListFragment.
         */
        @JvmStatic
        fun newInstance() =
                ContactsListFragment().apply {
                    arguments = Bundle().apply {}
                }
    }
}
