package br.edu.ifsp.scl.sdm.conceitoservice

import android.app.Service
import android.content.Intent
import android.os.IBinder

class LifetimeStartedService : Service() {
    //contador de segundos
    private var lifetime: Int = 0

    companion object {
        // Para contar
        val  EXTRA_LIFETIME = "EXTRA_LIFETIME"
    }
    // Nossa thead de trabalho que conta segundos
    private inner class  WorkerThread: Thread() {
        var running = false

        override fun run() {
            running = true
            while (running){
                sleep(1000)

                //Envia o lifetime para Activity
                sendBroadcast(Intent("ACTION_RECEIVE_LIFETIME").also {
                    it.putExtra(EXTRA_LIFETIME, ++lifetime)
                } )
            }
            //super.run()
        }
    }
    private lateinit var workerThread: WorkerThread

    // Primeira função executada em qualquer serviço
    override fun onCreate() {
        super.onCreate()
        workerThread = WorkerThread()
    }

    //Só faz sentido se o serviço for vinculado
    override fun onBind(intent: Intent): IBinder? = null

    /*Chamando com Activity executa starService.  Executa indefinidamente até que
     seja chamado o métod stopSelf ou stopService*/
    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        if (!workerThread.running) {
            workerThread.start()
        }
        return START_STICKY
    }

    // Destroi tudo e apaga luz e vamos embora
    override fun onDestroy() {
        super.onDestroy()
        workerThread.running = false
    }
}