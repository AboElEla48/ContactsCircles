package eg.foureg.circles.feature.main.content


import android.os.Bundle
import eg.foureg.circles.R
import eg.foureg.circles.common.message.data.Message
import eg.foureg.circles.common.message.server.MessageServer
import eg.foureg.circles.common.ui.BaseActivity
import eg.foureg.circles.contacts.data.ContactData
import eg.foureg.circles.feature.main.MainActivity
import eg.foureg.circles.feature.main.MainActivityMessages
import kotlin.reflect.KClass

class ContentActivity : BaseActivity() {

    val contentFragmentNavigator = ContentActivityFragmentsNavigator()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_content)

        val contentMode = intent.extras?.getInt(CONTENT_MODEL_PARAM)
        when (contentMode) {

            // add new contact
            ContentActivityMessages.MSG_ID_ADD_NEW_CONTACT -> {
                contentFragmentNavigator.setContactEditorFragment(this, null)
            }

            // edit existing contact
            ContentActivityMessages.MSG_ID_EDIT_CONTACT_DETAILS -> {
                val contact = intent.extras?.getParcelable<ContactData>(CONTENT_MSG_DATA1)
                contentFragmentNavigator.setContactEditorFragment(this, contact)

            }

            // view contact details
            ContentActivityMessages.MSG_ID_VIEW_CONTACT_DETAILS -> {
                val contact = intent.extras?.getParcelable<ContactData>(CONTENT_MSG_DATA1)
                contentFragmentNavigator.setContactViewFragment(this, contact!!)
            }

            // add new circle
            ContentActivityMessages.MSG_ID_ADD_NEW_CIRCLE -> {
                contentFragmentNavigator.setCircleEditorFragment(this, null)
            }

            // edit existing circle
            ContentActivityMessages.MSG_ID_EDIT_CIRCLE -> {

            }
        }
    }

    override fun handleMessage(message: Message) {
        super.handleMessage(message)
        when (message.id) {
            ContentActivityMessages.MSG_ID_CLOSE_CONTENT_ACTIVITY_AND_REFRESH_CONTACTS -> {
                finish()
            }
        }
    }

    companion object {
        const val CONTENT_MODEL_PARAM = "CONTENT_MODEL_PARAM"
        const val CONTENT_MSG_DATA1 = "CONTENT_MSG_DATA1"
    }
}
