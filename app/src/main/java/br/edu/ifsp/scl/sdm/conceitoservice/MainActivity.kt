package br.edu.ifsp.scl.sdm.conceitoservice

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import br.edu.ifsp.scl.sdm.conceitoservice.LifetimeStartedService.Companion.EXTRA_LIFETIME
import br.edu.ifsp.scl.sdm.conceitoservice.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    private val activityMainBinding: ActivityMainBinding by lazy {
        ActivityMainBinding.inflate(layoutInflater)
    }
    private val lifetimeServiceIntent: Intent by lazy {
        Intent(this,LifetimeStartedService::class.java)
    }

    private  val receiveLifetimeBr: BroadcastReceiver by lazy {
        object:BroadcastReceiver() {
            override fun onReceive(p0: Context?, intent: Intent?) {
                intent?.getIntExtra(EXTRA_LIFETIME, 0).also {
                    activityMainBinding.serviceLifetimeTv.text = it.toString()
                }
            }
        }
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(activityMainBinding.root)
        with(activityMainBinding) {
            iniciarServicoBt.setOnClickListener {
                startService(lifetimeServiceIntent)
            }
            finilizarServicoBt.setOnClickListener {
                stopService(lifetimeServiceIntent)
            }
        }
    }

    override fun onStart() {
        super.onStart()
        registerReceiver(receiveLifetimeBr, IntentFilter("ACTION_RECEIVE_LIFETIME"))
    }

    override fun onStop() {
        super.onStop()
        unregisterReceiver(receiveLifetimeBr)
    }
}