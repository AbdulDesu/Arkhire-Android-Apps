package com.sizdev.arkhirefortalent.homepage.item.home.project.detailedproject

import android.annotation.SuppressLint
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import com.sizdev.arkhirefortalent.R
import com.sizdev.arkhirefortalent.administration.login.LoginActivity
import com.sizdev.arkhirefortalent.administration.register.RegisterResponse
import com.sizdev.arkhirefortalent.databinding.ActivityDetailOfProjectBinding
import com.sizdev.arkhirefortalent.networking.ApiClient
import kotlinx.coroutines.*

class DetailOfProjectActivity : AppCompatActivity() {

    private lateinit var binding: ActivityDetailOfProjectBinding
    private lateinit var coroutineScope: CoroutineScope
    private lateinit var service: ProjectReplyAuthService

    @SuppressLint("SetTextI18n")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_detail_of_project)
        coroutineScope = CoroutineScope(Job() + Dispatchers.Main)
        service = ApiClient.getApiClient(this)!!.create(ProjectReplyAuthService::class.java)

        val offeringID = intent.getStringExtra("offeringID")
        val projectTitle = intent.getStringExtra("projectTitle")
        val projectSalary = intent.getStringExtra("projectSalary")
        val projectDesc = intent.getStringExtra("projectDesc")
        val projectDuration = intent.getStringExtra("projectDuration")
        val projectStatus = intent.getStringExtra("projectStatus")
        val msgForTalent = intent.getStringExtra("msgForTalent")
        val msgReply = intent.getStringExtra("msgReply")
        val repliedAt = intent.getStringExtra("repliedAt")

        binding.tvProjectTitle.text = projectTitle
        binding.tvDetailProjectSalary.text = projectSalary
        binding.tvProjectDesc.text = projectDesc
        binding.tvDuration.text = projectDuration
        binding.tvMsgForTalent.text = msgForTalent
        binding.tvReplyMsg.text = msgReply
        binding.tvRepliedAt.text = repliedAt

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
            binding.tvTitleReplyMsg.visibility = View.INVISIBLE
            binding.tvReplyMsg.visibility = View.INVISIBLE
            binding.tvRepliedAt.visibility = View.INVISIBLE
        }

        binding.btApproveProject.setOnClickListener {
            Toast.makeText(this, "$offeringID", Toast.LENGTH_SHORT).show()
            if (offeringID != null) {
                replyProject(offeringID, "Approved", "Project Approved")
            }
        }

        binding.btDeclineProject.setOnClickListener{
            if (offeringID != null) {
                replyProject(offeringID, "Declined", "Project Declined")
            }
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