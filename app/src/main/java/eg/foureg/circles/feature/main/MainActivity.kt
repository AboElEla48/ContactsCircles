package eg.foureg.circles.feature.main

import android.Manifest
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Build
import android.os.Bundle
import android.widget.Toast
import eg.foureg.circles.R
import eg.foureg.circles.circles.data.CircleData
import eg.foureg.circles.common.message.data.Message
import eg.foureg.circles.common.ui.BaseActivity
import eg.foureg.circles.contacts.data.ContactData
import eg.foureg.circles.feature.circle.models.CirclesModel
import eg.foureg.circles.feature.contacts.models.ContactsModel
import eg.foureg.circles.feature.main.content.ContentActivity
import eg.foureg.circles.feature.main.content.ContentActivityMessages
import org.koin.android.ext.android.inject


class MainActivity : BaseActivity() {
    private val mainActivityFragmentsNavigator : MainActivityFragmentsNavigator = MainActivityFragmentsNavigator()
    private var tempContactData : ContactData? = null
    private var tempCircleData : CircleData? = null

    val contactsModel : ContactsModel by inject()
    val circlesModel : CirclesModel by inject()

    companion object {
        const val PERMISSION_READ_CONTACT_ID = 150
        const val PERMISSION_WRITE_CONTACT_ID = 151

        const val PERMISSION_WRITE_CIRCLE_ID = 152
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

    }

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)

        when(requestCode) {
            PERMISSION_READ_CONTACT_ID -> {
                if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    mainActivityFragmentsNavigator.setContactsListFragment(this)
                    mainActivityFragmentsNavigator.setHeaderCirclesListFragment(this)
                }
                else {
                    Toast.makeText(this, getString(R.string.txt_permission_must_be_granted), Toast.LENGTH_LONG).show()
                }
            }

            PERMISSION_WRITE_CONTACT_ID -> {
                if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    viewContactEditor(tempContactData)
                }
                else {
                    Toast.makeText(this, getString(R.string.txt_permission_must_be_granted), Toast.LENGTH_LONG).show()
                }
            }

            PERMISSION_WRITE_CIRCLE_ID -> {
                if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    viewCircleEditor(tempCircleData)
                }
                else {
                    Toast.makeText(this, getString(R.string.txt_permission_must_be_granted), Toast.LENGTH_LONG).show()
                }
            }
        }
    }

    override fun onResume() {
        super.onResume()

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M ) {
            if (checkSelfPermission(Manifest.permission.READ_CONTACTS) == PackageManager.PERMISSION_GRANTED) {
                mainActivityFragmentsNavigator.removeContactListFragment(this)
                mainActivityFragmentsNavigator.setContactsListFragment(this)
                mainActivityFragmentsNavigator.setHeaderCirclesListFragment(this)
            }
            else {
                requestPermissions(arrayOf(Manifest.permission.READ_CONTACTS, Manifest.permission.WRITE_CONTACTS),
                        PERMISSION_READ_CONTACT_ID)
            }
        }
        else {
            mainActivityFragmentsNavigator.removeContactListFragment(this)
            mainActivityFragmentsNavigator.setContactsListFragment(this)
            mainActivityFragmentsNavigator.setHeaderCirclesListFragment(this)
        }

    }

    override fun handleMessage(message: Message) {
        super.handleMessage(message)

        when(message.id) {

            MainActivityMessages.MSG_ID_VIEW_CONTACT_DETAILS -> {
                val contact = message.data.get(MainActivityMessages.DATA_PARAM_CONTACT_DATA) as ContactData
                val intent = Intent(this, ContentActivity::class.java)
                intent.putExtra(ContentActivity.CONTENT_MODEL_PARAM, ContentActivityMessages.MSG_ID_VIEW_CONTACT_DETAILS)
                intent.putExtra(ContentActivity.CONTENT_MSG_DATA1, contact)
                startActivity(intent)

            }

            MainActivityMessages.MSG_ID_ADD_NEW_CONTACT -> {
                viewContactEditor(null)
            }

            MainActivityMessages.MSG_ID_EDIT_CONTACT_DETAILS -> {
                val contact = message.data.get(MainActivityMessages.DATA_PARAM_CONTACT_DATA) as ContactData
                viewContactEditor(contact)
            }

            MainActivityMessages.MSG_ID_VIEW_CIRCLE_EDITOR -> {
                viewCircleEditor(null)
            }
        }
    }

    private fun viewCircleEditor(circleData: CircleData?) {
        val intent = Intent(this, ContentActivity::class.java)
        if(circleData == null) {
            intent.putExtra(ContentActivity.CONTENT_MODEL_PARAM, ContentActivityMessages.MSG_ID_ADD_NEW_CIRCLE)
        }
        else {
            intent.putExtra(ContentActivity.CONTENT_MSG_DATA1, circleData)
            intent.putExtra(ContentActivity.CONTENT_MODEL_PARAM, ContentActivityMessages.MSG_ID_EDIT_CIRCLE)
        }

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M ) {
            if (checkSelfPermission(Manifest.permission.WRITE_CONTACTS) == PackageManager.PERMISSION_GRANTED) {
                startActivity(intent)
            }
            else {
                tempCircleData = circleData
                requestPermissions(arrayOf(Manifest.permission.WRITE_CONTACTS),
                        PERMISSION_WRITE_CIRCLE_ID)
            }
        }
        else {
            startActivity(intent)
        }
    }

    private fun viewContactEditor(contact: ContactData?) {

        val intent = Intent(this, ContentActivity::class.java)
        if(contact == null) {
            intent.putExtra(ContentActivity.CONTENT_MODEL_PARAM, ContentActivityMessages.MSG_ID_ADD_NEW_CONTACT)
        }
        else {
            intent.putExtra(ContentActivity.CONTENT_MSG_DATA1, contact)
            intent.putExtra(ContentActivity.CONTENT_MODEL_PARAM, ContentActivityMessages.MSG_ID_EDIT_CONTACT_DETAILS)
        }



        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M ) {
            if (checkSelfPermission(Manifest.permission.WRITE_CONTACTS) == PackageManager.PERMISSION_GRANTED) {
                startActivity(intent)
            }
            else {
                tempContactData = contact
                requestPermissions(arrayOf(Manifest.permission.WRITE_CONTACTS),
                        PERMISSION_WRITE_CONTACT_ID)
            }
        }
        else {
            startActivity(intent)
        }
    }
}
