package byfo.evtconf

import android.app.Application
import com.facebook.drawee.backends.pipeline.Fresco

/**
 * Created by Vic on 2/28/2018.
 */
class EvtConfApplication : Application() {

    override fun onCreate() {
        super.onCreate()
        Fresco.initialize(this)
    }
}