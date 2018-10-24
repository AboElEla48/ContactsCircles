package eg.foureg.circles.feature.main

import android.os.Bundle
import eg.foureg.circles.R
import eg.foureg.circles.common.message.data.Message
import eg.foureg.circles.common.ui.BaseActivity

class MainActivity : BaseActivity() {
    val mainActivityFragmentsNavigator : MainActivityFragmentsNavigator = MainActivityFragmentsNavigator()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        mainActivityFragmentsNavigator.setContactsListFragment(this)
    }

    override fun handleMessage(message: Message) {
        super.handleMessage(message)

        when(message.id) {
            MainActivityMessages.MSG_ID_VIEW_CONTACTS_List -> {
                mainActivityFragmentsNavigator.setContactsListFragment(this)
            }

            MainActivityMessages.MSG_ID_VIEW_CONTACT_DETAILS -> {
                val contactIndex = message.data.get(MainActivityMessages.DATA_PARAM_CONTACT_INDEX) as Int
                mainActivityFragmentsNavigator.setContactViewFragment(this, contactIndex)
            }

            MainActivityMessages.MSG_ID_EDIT_CONTACT_DETAILS -> {
                val contactIndex = message.data.get(MainActivityMessages.DATA_PARAM_CONTACT_INDEX) as Int
                mainActivityFragmentsNavigator.setContactEditorFragment(this, contactIndex)
            }
        }
    }
}
