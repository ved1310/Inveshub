package com.example.investhub.investordashboard.home

import android.view.LayoutInflater
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.example.investhub.common.BaseFragment
import com.example.investhub.common.BaseViewBindingFragment
import com.example.investhub.databinding.FragmentChatBinding
import com.example.investhub.databinding.FragmentHomeBinding
import okhttp3.OkHttp
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.WebSocket

class ChatFragment:BaseFragment<FragmentChatBinding>(){

    override fun inflateLayout(layoutInflater: LayoutInflater): FragmentChatBinding {
        return FragmentChatBinding.inflate(layoutInflater)
    }
    lateinit var  webSocketListener: WebSocketListener
    lateinit var viewModel: ChatViewModel
    private val  okHttpClient=OkHttpClient()
    private var  webSocket: WebSocket?=null

    override fun initViews() {
        super.initViews()
        viewModel=ViewModelProvider(this)[ChatViewModel::class.java]
        webSocketListener=WebSocketListener(viewModel)
        viewModel.socketStatus.observe(this, Observer {
            binding.tvMessage.text=if (it)"connedcted" else "Disconnected"
        })
        var text=""
        viewModel.message.observe(this, Observer {
            text += "${ if(it.first)"You: " else "Server: "} ${it.second}\n"
            binding.tvMessage.text=text
        })
        binding.btnConnect.setOnClickListener{
            webSocket=okHttpClient.newWebSocket(createRequest(),webSocketListener)
        }
        binding.btnDissconnect.setOnClickListener {
            webSocket?.close(100,"Cancled Mannually")
        }
        binding.btnSend.setOnClickListener {
            if(binding.etMessage.toString().isNotEmpty())
            {
                webSocket?.send(binding.etMessage.text.toString())
                viewModel.setMessage(Pair(true,binding.etMessage.text.toString()))
            }

        }

    }
    private  fun createRequest():Request{
        val webSocketUrl="wss://s11427.nyc1.piesocket.com/v3/1?api_key=Sd4z0LYO62drKWdHeiUw1vbalfjcO6ByZTAp8l8o&notify_self=1"
        return Request.Builder().url(webSocketUrl).build()

    }
}