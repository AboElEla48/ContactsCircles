package eg.foureg.circles.feature.circle.models

import eg.foureg.circles.circles.CirclesEditor
import eg.foureg.circles.circles.CirclesRetriever

class CirclesModel(cRetriver : CirclesRetriever, cEditor : CirclesEditor) {

    var circlesRetriever : CirclesRetriever
    var circlesEditor : CirclesEditor

    init {
        circlesEditor = cEditor
        circlesRetriever = cRetriver
    }


}