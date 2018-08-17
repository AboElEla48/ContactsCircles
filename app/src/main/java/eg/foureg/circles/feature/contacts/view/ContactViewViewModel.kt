package eg.foureg.circles.feature.contacts.view

import android.arch.lifecycle.MutableLiveData
import android.arch.lifecycle.ViewModel
import android.graphics.Bitmap

class ContactViewViewModel : ViewModel() {
    var contactName : MutableLiveData<String> = MutableLiveData()
    var phones : MutableLiveData<List<String>> = MutableLiveData()
    var emails : MutableLiveData<List<String>> = MutableLiveData()
    var image : MutableLiveData<Bitmap> = MutableLiveData()
}