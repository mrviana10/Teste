package br.edu.ifsp.scl.sdm.conceitoservice

import android.app.Service
import android.content.Intent
import android.os.Binder
import android.os.IBinder

class LifetimeBoundService : Service() {
    var lifetime: Int = 0
    private set

    private inner class  WorkerThread: Thread() {
        var running = false

        override fun run() {
            running = true
            while (running){
                sleep(1000)
                lifetime++
            }
            //super.run()
        }
    }
    private lateinit var workerThread: WorkerThread

    inner class lifetimeBoundservice: Binder(){
        fun getService() = @LifetimeSoundService
    }

    override fun onBind(intent: Intent): IBinder {
        if (!workerThread.running) {
            workerThread.start()
        }
        return lifetimeBoundservice()
    }
    }
}