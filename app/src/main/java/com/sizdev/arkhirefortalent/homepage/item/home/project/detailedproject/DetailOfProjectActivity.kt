package com.sizdev.arkhirefortalent.homepage.item.home.project.detailedproject

import android.annotation.SuppressLint
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.databinding.DataBindingUtil
import com.sizdev.arkhirefortalent.R
import com.sizdev.arkhirefortalent.databinding.ActivityDetailOfProjectBinding
import com.sizdev.arkhirefortalent.networking.ArkhireApiClient
import com.sizdev.arkhirefortalent.networking.ArkhireApiService
import kotlinx.android.synthetic.main.alert_reply_confirmation.view.*
import kotlinx.coroutines.*

class DetailOfProjectActivity : AppCompatActivity() {

    private lateinit var binding: ActivityDetailOfProjectBinding
    private lateinit var coroutineScope: CoroutineScope
    private lateinit var service: ArkhireApiService
    private lateinit var dialog: AlertDialog

    @SuppressLint("SetTextI18n")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_detail_of_project)
        coroutineScope = CoroutineScope(Job() + Dispatchers.Main)
        service = ArkhireApiClient.getApiClient(this)!!.create(ArkhireApiService::class.java)

        val projectTitle = intent.getStringExtra("projectTitle")
        val projectSalary = intent.getStringExtra("projectSalary")
        val projectDesc = intent.getStringExtra("projectDesc")
        val projectDuration = intent.getStringExtra("projectDuration")
        val projectStatus = intent.getStringExtra("projectStatus")
        val msgReply = intent.getStringExtra("msgReply")

        binding.tvProjectTitle.text = projectTitle
        binding.tvDetailProjectSalary.text = projectSalary
        binding.tvProjectDesc.text = projectDesc
        binding.tvDuration.text = projectDuration
        binding.tvReplyMsg.text = msgReply

        binding.backButton.setOnClickListener {
            finish()
        }

        binding.shareButton.setOnClickListener {
            val intent = Intent(Intent.ACTION_SEND)
            intent.putExtra(Intent.EXTRA_SUBJECT, "Arkhire")
            intent.putExtra(Intent.EXTRA_TEXT, "Join Arkhire Now!")
            startActivity(Intent.createChooser(intent, "Share This Project"))
        }

        when (projectStatus) {
            "Approved" -> binding.ivHiringStatus.setImageResource(R.drawable.ic_approved)
            "Declined" -> binding.ivHiringStatus.setImageResource(R.drawable.ic_declined)
            else -> binding.ivHiringStatus.setImageResource(R.drawable.ic_waiting)
        }

        if(projectStatus == "Waiting"){
            binding.btDeclineProject.visibility = View.VISIBLE
            binding.btApproveProject.visibility = View.VISIBLE
            binding.tvTitleReplyMsg.visibility = View.GONE
            binding.tvReplyMsg.visibility = View.GONE
        }

        binding.btApproveProject.setOnClickListener {
            showReplyConfirmation("Approved")
            dialog.show()
        }

        binding.btDeclineProject.setOnClickListener{
            showReplyConfirmation("Declined")
            dialog.show()
        }
    }

    private fun showReplyConfirmation(projectStatus: String) {
        val view: View = layoutInflater.inflate(R.layout.alert_reply_confirmation, null)

        dialog = AlertDialog.Builder(this)
                .setView(view)
                .setCancelable(false)
                .create()

        view.bt_sendReply.setOnClickListener {
            dialog.dismiss()
            val offeringID = intent.getStringExtra("offeringID")
            val replyMsg = view.et_replyMsg.text.toString()
            if (offeringID != null) {
                replyProject(offeringID, projectStatus, replyMsg)
                Toast.makeText(this, "Project $projectStatus", Toast.LENGTH_LONG).show()
                finish()
            }
        }
        view.bt_cancelReply.setOnClickListener {
            dialog.cancel()
        }
    }

    private fun replyProject(offeringID: String, hiringStatus: String, replyMsg:String) {
        coroutineScope.launch {

            val result = withContext(Dispatchers.IO) {
                try {
                    service.updateProjectByOfferingID(offeringID, hiringStatus, replyMsg)
                } catch (e: Throwable) {
                    e.printStackTrace()
                }
            }

            if (result is ProjectReplyResponse) {
                Toast.makeText(this@DetailOfProjectActivity, "Project Replied Successfully", Toast.LENGTH_LONG).show()
                finish()
            }
        }
    }

}