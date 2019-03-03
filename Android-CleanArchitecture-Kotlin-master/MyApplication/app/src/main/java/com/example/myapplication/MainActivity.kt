package com.example.myapplication

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.provider.Contacts
import android.util.Log
import kotlinx.coroutines.*
import kotlin.coroutines.CoroutineContext

class MainActivity : AppCompatActivity(), CoroutineScope{

    lateinit var job: Job

    override val coroutineContext: CoroutineContext
        get() = job + Dispatchers.Main

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        job = Job()
        launch { test() }
    }

    override fun onDestroy() {
        super.onDestroy()
        job.cancel()
    }

    private suspend fun test() {
        var defered = async(Dispatchers.Default) {
            for(i in 1..100) {
                Log.d(this.javaClass.canonicalName, "Thread name : ${Thread.currentThread().name}");
                delay(500)
            }
            32
        }

        launch(Dispatchers.Main) {
            Log.d(this.javaClass.canonicalName, "Before await");
            val hui = defered.await()
            Log.d(this.javaClass.canonicalName, "Before another suspend");
            anotherSuspend(hui)
            Log.d(this.javaClass.canonicalName, "After another suspend");
        }

        Log.d(this.javaClass.canonicalName, "First");
    }

    private suspend fun anotherSuspend(kek: Int){
        for(i in 1..100) {
            Log.d(this.javaClass.canonicalName, "Thread name : ${Thread.currentThread().name}");
            delay(500)
            Log.d(this.javaClass.canonicalName,"Result : " + kek)
        }
    }
}
