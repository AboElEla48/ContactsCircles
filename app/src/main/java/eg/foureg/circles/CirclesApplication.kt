package eg.foureg.circles

import android.app.Application
import org.koin.android.ext.android.startKoin

class CirclesApplication : Application() {
    override fun onCreate() {
        super.onCreate()
        startKoin(this, listOf(appContactsModule, appCirclesModule))
    }
}