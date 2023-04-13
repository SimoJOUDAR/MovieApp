package fr.mjoudar.oqee

import android.app.Application
import dagger.hilt.android.HiltAndroidApp


/***********************************************************************************************
 * BaseApplication class - the root access point of our application
 ***********************************************************************************************/

@HiltAndroidApp
class BaseApplication : Application() {
}