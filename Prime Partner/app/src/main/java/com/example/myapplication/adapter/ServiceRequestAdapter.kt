package com.example.myapplication.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.myapplication.R
import com.example.myapplication.data.model.ServiceRequest

class ServiceRequestAdapter(
    private var requests: List<ServiceRequest>,
    private val onAcceptClick: (ServiceRequest) -> Unit,
    private val onRejectClick: (ServiceRequest) -> Unit,
    private val onItemClick: (ServiceRequest) -> Unit
) : RecyclerView.Adapter<ServiceRequestAdapter.ServiceRequestViewHolder>() {

    inner class ServiceRequestViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val tvServiceType: TextView = itemView.findViewById(R.id.tv_service_type)
        val tvClientInfo: TextView = itemView.findViewById(R.id.tv_client_info)
        val tvLocation: TextView = itemView.findViewById(R.id.tv_location)
        val tvDescription: TextView = itemView.findViewById(R.id.tv_description)
        val tvStatus: TextView = itemView.findViewById(R.id.tv_status)
        val btnAccept: Button = itemView.findViewById(R.id.btn_accept)
        val btnReject: Button = itemView.findViewById(R.id.btn_reject)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ServiceRequestViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_service_request, parent, false)
        return ServiceRequestViewHolder(view)
    }

    override fun onBindViewHolder(holder: ServiceRequestViewHolder, position: Int) {
        val request = requests[position]
        
        holder.tvServiceType.text = "სერვისი: ${request.serviceType}"
        holder.tvClientInfo.text = "კლიენტი: ${request.clientName}\nტელ: ${request.clientPhone}"
        holder.tvLocation.text = "მდებარეობა: ${request.address}"
        holder.tvDescription.text = "აღწერა: ${request.description}"
        holder.tvStatus.text = "სტატუსი: ${request.status}"
        
        // Item Click - Map-ზე გადასვლა
        holder.itemView.setOnClickListener { onItemClick(request) }
        
        // Accept/Reject ღილაკები მხოლოდ PENDING სტატუსის დროს
        if (request.status == "PENDING") {
            holder.btnAccept.visibility = View.VISIBLE
            holder.btnReject.visibility = View.VISIBLE
            
            holder.btnAccept.setOnClickListener { onAcceptClick(request) }
            holder.btnReject.setOnClickListener { onRejectClick(request) }
        } else {
            holder.btnAccept.visibility = View.GONE
            holder.btnReject.visibility = View.GONE
        }
    }

    override fun getItemCount(): Int = requests.size

    fun updateRequests(newRequests: List<ServiceRequest>) {
        android.util.Log.d("Firebase", "ServiceRequestAdapter: updateRequests called with ${newRequests.size} requests")
        requests = newRequests
        notifyDataSetChanged()
        android.util.Log.d("Firebase", "ServiceRequestAdapter: notifyDataSetChanged() called")
    }
}