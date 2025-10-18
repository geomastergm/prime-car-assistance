package com.example.myapplication.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.myapplication.R
import com.example.myapplication.data.model.ProviderRegistration
import java.text.SimpleDateFormat
import java.util.*

class RegistrationRequestsAdapter(
    private var registrations: List<ProviderRegistration>,
    private val onApprove: (ProviderRegistration) -> Unit,
    private val onReject: (ProviderRegistration) -> Unit
) : RecyclerView.Adapter<RegistrationRequestsAdapter.RegistrationViewHolder>() {

    inner class RegistrationViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val tvFullName: TextView = itemView.findViewById(R.id.tv_full_name)
        val tvPhone: TextView = itemView.findViewById(R.id.tv_phone)
        val tvEmail: TextView = itemView.findViewById(R.id.tv_email)
        val tvCompany: TextView = itemView.findViewById(R.id.tv_company)
        val tvServices: TextView = itemView.findViewById(R.id.tv_services)
        val tvLicense: TextView = itemView.findViewById(R.id.tv_license)
        val tvExperience: TextView = itemView.findViewById(R.id.tv_experience)
        val tvAreas: TextView = itemView.findViewById(R.id.tv_areas)
        val tvStatus: TextView = itemView.findViewById(R.id.tv_status)
        val tvSubmittedAt: TextView = itemView.findViewById(R.id.tv_submitted_at)
        val btnApprove: Button = itemView.findViewById(R.id.btn_approve)
        val btnReject: Button = itemView.findViewById(R.id.btn_reject)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RegistrationViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_registration_request, parent, false)
        return RegistrationViewHolder(view)
    }

    override fun onBindViewHolder(holder: RegistrationViewHolder, position: Int) {
        val registration = registrations[position]
        
        holder.tvFullName.text = "სახელი: ${registration.fullName}"
        holder.tvPhone.text = "ტელეფონი: ${registration.phoneNumber}"
        holder.tvEmail.text = "ელ-ფოსტა: ${registration.email}"
        holder.tvCompany.text = "კომპანია: ${registration.companyName}"
        holder.tvServices.text = "სერვისები: ${registration.serviceTypes.joinToString(", ")}"
        holder.tvLicense.text = "ლიცენზია: ${registration.licenseNumber}"
        holder.tvExperience.text = "გამოცდილება: ${registration.experienceYears} წელი"
        holder.tvAreas.text = "სამუშაო რეგიონები: ${registration.workingAreas.joinToString(", ")}"
        holder.tvStatus.text = "სტატუსი: ${registration.status}"
        
        // Date formatting
        val dateFormat = SimpleDateFormat("dd/MM/yyyy HH:mm", Locale.getDefault())
        holder.tvSubmittedAt.text = "გაგზავნილია: ${dateFormat.format(Date(registration.submittedAt))}"
        
        // Buttons visibility based on status
        if (registration.status == "PENDING") {
            holder.btnApprove.visibility = View.VISIBLE
            holder.btnReject.visibility = View.VISIBLE
            
            holder.btnApprove.setOnClickListener { onApprove(registration) }
            holder.btnReject.setOnClickListener { onReject(registration) }
        } else {
            holder.btnApprove.visibility = View.GONE
            holder.btnReject.visibility = View.GONE
        }
    }

    override fun getItemCount(): Int = registrations.size

    fun updateRegistrations(newRegistrations: List<ProviderRegistration>) {
        registrations = newRegistrations
        notifyDataSetChanged()
    }
}