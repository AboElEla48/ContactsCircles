package eg.foureg.circles.feature.main

import android.Manifest
import android.content.pm.PackageManager
import android.os.Build
import android.os.Bundle
import android.widget.Toast
import eg.foureg.circles.R
import eg.foureg.circles.common.message.data.Message
import eg.foureg.circles.common.ui.BaseActivity


class MainActivity : BaseActivity() {
    val mainActivityFragmentsNavigator : MainActivityFragmentsNavigator = MainActivityFragmentsNavigator()
    val PERMISSION_CONTACT_ID = 15

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M ) {
            if (checkSelfPermission(Manifest.permission.READ_CONTACTS) == PackageManager.PERMISSION_GRANTED) {
                mainActivityFragmentsNavigator.setContactsListFragment(this)
            }
            else {
                requestPermissions(arrayOf(Manifest.permission.READ_CONTACTS, Manifest.permission.WRITE_CONTACTS),
                        PERMISSION_CONTACT_ID)
            }
        }
        else {
            mainActivityFragmentsNavigator.setContactsListFragment(this)
        }

    }

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)

        when(requestCode) {
            PERMISSION_CONTACT_ID -> {
                if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    mainActivityFragmentsNavigator.setContactsListFragment(this)
                }
                else {
                    Toast.makeText(this, getString(R.string.txt_permission_must_be_granted), Toast.LENGTH_LONG).show()
                }
            }
        }
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
