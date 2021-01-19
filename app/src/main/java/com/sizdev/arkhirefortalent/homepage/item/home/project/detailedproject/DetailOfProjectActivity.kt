package com.sizdev.arkhirefortalent.homepage.item.home.project.detailedproject

import android.annotation.SuppressLint
import android.content.ActivityNotFoundException
import android.content.Intent
import android.graphics.Bitmap
import android.net.Uri
import android.os.Bundle
import android.os.Environment
import android.view.View
import android.view.WindowManager
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import com.sizdev.arkhirefortalent.R
import com.sizdev.arkhirefortalent.databinding.ActivityDetailOfProjectBinding
import com.sizdev.arkhirefortalent.networking.ArkhireApiClient
import com.sizdev.arkhirefortalent.networking.ArkhireApiService
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.alert_reply_confirmation.view.*
import kotlinx.coroutines.*
import java.io.File
import java.io.FileOutputStream
import java.text.NumberFormat
import java.util.*


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

        // Set Status Bar
        window.setFlags(
            WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS,
            WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS
        )

        val format = NumberFormat.getCurrencyInstance()
        format.maximumFractionDigits = 0
        format.currency = Currency.getInstance("IDR")

        val projectTitle = intent.getStringExtra("projectTitle")
        val offeredSalary = format.format(intent.getStringExtra("projectSalary")?.toDouble())
        val projectDesc = intent.getStringExtra("projectDesc")
        val projectDuration = intent.getStringExtra("projectDuration")
        val projectStatus = intent.getStringExtra("projectStatus")
        val projectImage = intent.getStringExtra("projectImage")
        val msgReply = intent.getStringExtra("msgReply")

        binding.tvProjectTitle.text = projectTitle
        binding.tvDetailProjectSalary.text = offeredSalary
        binding.tvProjectDesc.text = projectDesc
        binding.tvDuration.text = projectDuration
        binding.tvReplyMsg.text = msgReply

        Picasso.get()
                .load("http://54.82.81.23:911/image/$projectImage")
                .resize(1280, 720)
                .centerCrop()
                .into(binding.ivProjectCompanyPhoto)

        binding.backButton.setOnClickListener {
            finish()
        }

        binding.shareButton.setOnClickListener {
            val intent = Intent(Intent.ACTION_SEND)
            intent.putExtra(Intent.EXTRA_TEXT, "$projectTitle \n$offeredSalary\nThis Project Is Waiting, Install Arkhire Now")
            intent.type="text/plain"
            startActivity(Intent.createChooser(intent, "Share to:"))
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

    private fun replyProject(offeringID: String, hiringStatus: String, replyMsg: String) {
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