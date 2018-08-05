package eg.foureg.circles.ui

import android.content.Context
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v7.widget.RecyclerView
import eg.foureg.circles.R
import eg.foureg.circles.common.ui.BaseActivity
import eg.foureg.circles.contacts.ContactsRetriever
import eg.foureg.circles.ui.contacts.viewer.ContactsListAdapter
import android.support.v7.widget.LinearLayoutManager

class MainActivity : BaseActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
    }
}
