package eg.foureg.circles.circles

import eg.foureg.circles.circles.data.CircleData
import org.junit.Assert
import org.junit.Test
import org.junit.Before
import java.util.Arrays
import kotlin.collections.ArrayList

class CirclesEditorImplTest {

    private val circleEditor = CircleEditorImplSample()
    private val sampleCircles: ArrayList<CircleData> = ArrayList()

    @Before
    fun setupTest() {
        sampleCircles.add(CircleData(14, "C3", ArrayList(Arrays.asList("44", "45"))))
        sampleCircles.add(CircleData(12, "C4", ArrayList(Arrays.asList("54", "25"))))
        sampleCircles.add(CircleData(15, "C1", ArrayList(Arrays.asList("24", "25"))))
        sampleCircles.add(CircleData(16, "C2", ArrayList(Arrays.asList("34", "25"))))

    }

    @Test
    fun updateContactReferenceInCircles_Test() {

        val oldContactId = "25"
        val newContactId = "90"
        circleEditor.test_updateContactReferenceInCircles(sampleCircles, oldContactId, newContactId)
        for(circle in sampleCircles) {
            Assert.assertTrue(!circle.phones.contains(oldContactId))
        }

        Assert.assertTrue(sampleCircles.get(1).phones.contains(newContactId))
        Assert.assertTrue(sampleCircles.get(2).phones.contains(newContactId))
        Assert.assertTrue(sampleCircles.get(3).phones.contains(newContactId))

    }

    @Test
    fun deleteContactReferenceInCircles_Test() {
        val oldContactId = "25"
        circleEditor.test_deleteContactReferenceInCircles(sampleCircles, oldContactId)

        for(circle in sampleCircles) {
            Assert.assertTrue(!circle.phones.contains(oldContactId))
        }
    }

    @Test
    fun generateNewCircleId_AsEmpty_List_Test() {
        val list = ArrayList<CircleData>()

        val newId = circleEditor.test_generateNewCircleId(list)
        Assert.assertTrue(newId == 1)
    }

    @Test
    fun generateNewCircleId_AsNonEmpty_List_Test() {
        val newId = circleEditor.test_generateNewCircleId(sampleCircles)
        Assert.assertTrue(newId == 17)
    }

    @Test
    fun findCircle_As_Existing_Test() {
        val circleId = 12
        Assert.assertTrue(circleEditor.test_findCircle(circleId, sampleCircles) != null)
    }

    @Test
    fun findCircle_As_NonExisting_Test() {
        val circleId = 2
        Assert.assertNull(circleEditor.test_findCircle(circleId, sampleCircles))
    }

    @Test
    fun isContactIdExistInCircle_AsTrue_Test() {
        val circle = CircleData(14, "C3", ArrayList(Arrays.asList("44", "45")))
        Assert.assertTrue(circleEditor.test_isContactIdExistInCircle(circle, "44"))
    }

    @Test
    fun isContactIdExistInCircle_AsFalse_Test() {
        val circle = CircleData(14, "C3", ArrayList(Arrays.asList("44", "45")))
        Assert.assertFalse(circleEditor.test_isContactIdExistInCircle(circle, "60"))
    }
}