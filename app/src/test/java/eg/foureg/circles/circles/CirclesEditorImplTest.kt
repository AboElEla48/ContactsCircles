package eg.foureg.circles.circles

import eg.foureg.circles.circles.data.CircleData
import io.reactivex.Observable
import org.junit.Assert
import org.junit.Test
import org.junit.Before
import java.util.Arrays
import kotlin.collections.ArrayList

class CirclesEditorImplTest {

    private val circleEditor = CircleEditorImplSample()
    private val sampleCircles : ArrayList<CircleData> = ArrayList()

    @Before
    fun setupTest() {
        sampleCircles.add(CircleData(15, "C1", ArrayList(Arrays.asList("24", "25"))))
        sampleCircles.add(CircleData(16, "C2", ArrayList(Arrays.asList("34", "25"))))
        sampleCircles.add(CircleData(14, "C3", ArrayList(Arrays.asList("44", "45"))))
        sampleCircles.add(CircleData(12, "C4", ArrayList(Arrays.asList("54", "25"))))
    }

    @Test
    fun updateContactReferenceInCircles_Test() {

        val oldContactId = "25"
        val newContactId = "90"
        circleEditor.testupdateContactReferenceInCircles(sampleCircles, oldContactId, newContactId)
        Observable.fromIterable(sampleCircles)
                .subscribe { circle ->
                    Assert.assertTrue(!circle.contactsIds.contains(oldContactId))
                    Assert.assertTrue(circle.contactsIds.contains(newContactId))
                }
    }
}