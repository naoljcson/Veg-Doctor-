package com.example.vegdoc.view

import android.annotation.SuppressLint
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.vegdoc.adapter.NotificationAdapter
import com.example.vegdoc.databinding.FragmentNotificationBinding
import com.example.vegdoc.model.Notification
import com.example.vegdoc.model.Notifications
import com.example.vegdoc.viewModel.NotificationViewModel
import kotlinx.coroutines.DelicateCoroutinesApi
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch




class NotificationFragment : Fragment() {
    private  var _binding: FragmentNotificationBinding? = null
    private val binding get() = _binding!!
    private lateinit var notificationViewModel: NotificationViewModel

    @SuppressLint("NotifyDataSetChanged")
    @OptIn(DelicateCoroutinesApi::class)
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentNotificationBinding.inflate(inflater,container,false)
        notificationViewModel = ViewModelProvider(this)[NotificationViewModel::class.java]
        val notificationRecyclerView: RecyclerView = binding.notificationRecyclerView
        notificationRecyclerView.layoutManager =
            LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)

        GlobalScope.launch(Dispatchers.Main) {
            val notifications = notificationViewModel.allNotifications()
            Log.d("NotificationFragment", "MessagingService () notifications ${notifications.size}")
            notificationRecyclerView.adapter = NotificationAdapter(notifications)
        }
        return binding.root
    }
}