package com.example.coroutinetest

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.*
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.channels.Channel.Factory.CONFLATED
import kotlinx.coroutines.channels.Channel.Factory.UNLIMITED
import kotlinx.coroutines.channels.ReceiveChannel
import kotlinx.coroutines.channels.consumeEach
import kotlinx.coroutines.channels.produce

@OptIn(ExperimentalCoroutinesApi::class)
class ExampleViewModel : ViewModel() {
//    private val channel = Channel<Language>()
    private var channel: ReceiveChannel<Language> = Channel()
    init {
        //TODO ЧЕРЕЗ withcontext можно вернуть значение
//        viewModelScope.launch {
//            val result = withContext(Dispatchers.IO){
//                delay(3000)
//                true
//            }
//            Log.d("CoroutinesRes",result.toString())
//        }


        // TODO Чтоыб вернуть значение можно испольщовать async
//        val result = viewModelScope.async {
//            delay(4000)
//            true
//        }
//
//        // TODO Чтобы использовать это значение когда оно готово invokeOnCompletition
//        result.invokeOnCompletion {
//            if (it == null){
//                Log.d("CoroutinesRes",result.getCompleted().toString())
//
//            }
//        }


//        COROUTINE 1
        viewModelScope.launch {
            // TODO Отправка данных через канал c помощью Produce, для этого класс должен быть Receive
            //  Можно указать размер буфера copasity =
            //  CONFLATED - объедененный канал, буверизует 1 канал, будет передн самый последний
            //  RENDEZVOUS - по умолчанию
            //  UNLIMITED пропусная способность неограничена
            // Test commit
            channel = produce (){
                send(Language.Kotlin)
                Log.d("sendCoroutinesTest","send Kotlin")
                send(Language.Java)
                Log.d("sendCoroutinesTest","send Java")

                send(Language.Python)
                Log.d("sendCoroutinesTest","send Python")

                send(Language.JavaScript)
                Log.d("sendCoroutinesTest","send JS")

            }



            // TODO Отправка данных через канал c помощью Send
//            channel.send(Language.Kotlin)
//            Log.d("sendCoroutinesTest","send Kotlin")
//            channel.send(Language.Java)
//            Log.d("sendCoroutinesTest","send Java")
//            channel.close()

        }
//        COROUTINE 2
        viewModelScope.launch {
            Log.d("CloseCoroutinesTest","${channel.isClosedForReceive}")

            channel.consumeEach {
                Log.d("ReceiveCoroutinesTest","${it.name}")

            }
            Log.d("CloseCoroutinesTest","${channel.isClosedForReceive}")



        }
    }

    fun test(){
        Log.d("CoroutinesRes","test")

    }
enum class Language{
    Kotlin,Java,Python,JavaScript
}
}