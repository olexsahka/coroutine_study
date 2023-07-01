package com.example.coroutinetest

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.example.coroutinetest.databinding.FragmentFirstBinding
import kotlinx.coroutines.*

/**
 * A simple [Fragment] subclass as the default destination in the navigation.
 */
@Suppress("UNREACHABLE_CODE")
class FirstFragment : Fragment() {

    private  val scope = CoroutineScope( CoroutineName("MyScope"))

    private var _binding: FragmentFirstBinding? = null
    private val viewModel: ExampleViewModel by viewModels()

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        _binding = FragmentFirstBinding.inflate(inflater, container, false)
        val mainJob = scope.launch {
            val job1 = launch {
                while (true){
                    delay(1)
                    Log.d("testCoroutine","job 1 running")
                }
            }
            val job2 = launch {
                Log.d("testCoroutine","job 1 running")
            }
            delay(500)
            Log.d("testCoroutine","Cancelling")
            job2.cancelAndJoin()
            Log.d("testCoroutine","job 2 canceled")
        }
        runBlocking {
            delay(1000)
            Log.d("testCoroutine","Cancelling")
            mainJob.cancelAndJoin()
            Log.d("testCoroutine","main  job canceled")

        }
        return binding.root


    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.buttonFirst.setOnClickListener {
            findNavController().navigate(R.id.action_FirstFragment_to_SecondFragment)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}