package com.sizdev.arkhirefortalent.homepage.profile

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.widget.PopupMenu
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import com.sizdev.arkhirefortalent.R
import com.sizdev.arkhirefortalent.databinding.ActivityProfileEditBinding

class EditProfileActivity : AppCompatActivity() {

    lateinit var binding: ActivityProfileEditBinding

    @SuppressLint("SetTextI18n")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_profile_edit)

        //Ge User Input
        val talentTitle = binding.etNewProfileJobTitle.text.toString()
        val talentLocation = binding.etNewProfileLocation.text.toString()
        val talentWorkTime = binding.etNewProfileWorkTime.text.toString()
        val talentDescription = binding.etNewProfileDesc.text.toString()
        val talentGithub = binding.etNewProfileGithub.text.toString()

        //Job Tittle Pop Up Menu
        val jobTitle = PopupMenu(this, binding.etNewProfileJobTitle)

        jobTitle.menu.add(Menu.NONE, 0 ,0, "Android Developer")
        jobTitle.menu.add(Menu.NONE, 1 ,1, "Fullstack Mobile")
        jobTitle.menu.add(Menu.NONE, 2 ,2, "Fullstack Web")
        jobTitle.menu.add(Menu.NONE, 3 ,3, "DevOps Engineer")

        //Talent City Pop Up Menu
        val city = PopupMenu(this, binding.etNewProfileLocation)

        city.menu.add(Menu.NONE, 0 ,0, "Jakarta")
        city.menu.add(Menu.NONE, 1 ,1, "Bandung")
        city.menu.add(Menu.NONE, 2 ,2, "Lampung")
        city.menu.add(Menu.NONE, 3 ,3, "Bali")
        city.menu.add(Menu.NONE, 4 ,4, "Aceh")
        city.menu.add(Menu.NONE, 5 ,5, "Cimahi")
        city.menu.add(Menu.NONE, 6 ,6, "Nagreg")
        city.menu.add(Menu.NONE, 7 ,7, "Cicalengka")

        //Work Time Pop Up Menu
        val workTime = PopupMenu(this, binding.etNewProfileWorkTime)

        workTime.menu.add(Menu.NONE, 0 ,0, "Full Time")
        workTime.menu.add(Menu.NONE, 1 ,1, "Freelancer")

        //Skill Choose Pop Up Menu
        val talentSKill1 = PopupMenu(this, binding.etNewProfileSkill1)

        talentSKill1.menu.add(Menu.NONE, 0 ,0, "JavaScript")
        talentSKill1.menu.add(Menu.NONE, 1 ,1, "HTML & CSS")
        talentSKill1.menu.add(Menu.NONE, 2 ,2, "Java")
        talentSKill1.menu.add(Menu.NONE, 3 ,3, "Kotlin")
        talentSKill1.menu.add(Menu.NONE, 4 ,4, "React Native")
        talentSKill1.menu.add(Menu.NONE, 5 ,5, "Python")
        talentSKill1.menu.add(Menu.NONE, 6 ,6, "Golang")

        val talentSKill2 = PopupMenu(this, binding.etNewProfileSkill2)

        talentSKill2.menu.add(Menu.NONE, 0 ,0, "JavaScript")
        talentSKill2.menu.add(Menu.NONE, 1 ,1, "HTML & CSS")
        talentSKill2.menu.add(Menu.NONE, 2 ,2, "Java")
        talentSKill2.menu.add(Menu.NONE, 3 ,3, "Kotlin")
        talentSKill2.menu.add(Menu.NONE, 4 ,4, "React Native")
        talentSKill2.menu.add(Menu.NONE, 5 ,5, "Python")
        talentSKill2.menu.add(Menu.NONE, 6 ,6, "Golang")

        val talentSKill3 = PopupMenu(this, binding.etNewProfileSkill3)

        talentSKill3.menu.add(Menu.NONE, 0 ,0, "JavaScript")
        talentSKill3.menu.add(Menu.NONE, 1 ,1, "HTML & CSS")
        talentSKill3.menu.add(Menu.NONE, 2 ,2, "Java")
        talentSKill3.menu.add(Menu.NONE, 3 ,3, "Kotlin")
        talentSKill3.menu.add(Menu.NONE, 4 ,4, "React Native")
        talentSKill3.menu.add(Menu.NONE, 5 ,5, "Python")
        talentSKill3.menu.add(Menu.NONE, 6 ,6, "Golang")

        val talentSKill4 = PopupMenu(this, binding.etNewProfileSkill4)

        talentSKill4.menu.add(Menu.NONE, 0 ,0, "JavaScript")
        talentSKill4.menu.add(Menu.NONE, 1 ,1, "HTML & CSS")
        talentSKill4.menu.add(Menu.NONE, 2 ,2, "Java")
        talentSKill4.menu.add(Menu.NONE, 3 ,3, "Kotlin")
        talentSKill4.menu.add(Menu.NONE, 4 ,4, "React Native")
        talentSKill4.menu.add(Menu.NONE, 5 ,5, "Python")
        talentSKill4.menu.add(Menu.NONE, 6 ,6, "Golang")

        val talentSKill5 = PopupMenu(this, binding.etNewProfileSkill5)

        talentSKill5.menu.add(Menu.NONE, 0 ,0, "JavaScript")
        talentSKill5.menu.add(Menu.NONE, 1 ,1, "HTML & CSS")
        talentSKill5.menu.add(Menu.NONE, 2 ,2, "Java")
        talentSKill5.menu.add(Menu.NONE, 3 ,3, "Kotlin")
        talentSKill5.menu.add(Menu.NONE, 4 ,4, "React Native")
        talentSKill5.menu.add(Menu.NONE, 5 ,5, "Python")
        talentSKill5.menu.add(Menu.NONE, 6 ,6, "Golang")

        //Job Title Listener
        jobTitle.setOnMenuItemClickListener { menuItem ->
            val id = menuItem.itemId

            when (id) {
                0 -> {binding.etNewProfileJobTitle.text = "Android Developer"}
                1 -> {binding.etNewProfileJobTitle.text = "Fullstack Mobile"}
                2 -> {binding.etNewProfileJobTitle.text = "Fullstack Web"}
                3 -> {binding.etNewProfileJobTitle.text = "DevOps Engineer"}
            }

            false
        }

        //City Listener
        city.setOnMenuItemClickListener { menuItem ->
            val id = menuItem.itemId

            when (id) {
                0 -> {binding.etNewProfileLocation.text = "Jakarta"}
                1 -> {binding.etNewProfileLocation.text = "Bandung"}
                2 -> {binding.etNewProfileLocation.text = "Lampung"}
                3 -> {binding.etNewProfileLocation.text = "Bali"}
                4 -> {binding.etNewProfileLocation.text = "Aceh"}
                5 -> {binding.etNewProfileLocation.text = "Cimahi"}
                6 -> {binding.etNewProfileLocation.text = "Nagreg"}
                7 -> {binding.etNewProfileLocation.text = "Cicalengka"}
            }

            false
        }

        //Work Time Listener
        workTime.setOnMenuItemClickListener { menuItem ->
            val id = menuItem.itemId

            when (id) {
                0 -> {binding.etNewProfileWorkTime.text = "Full Time"}
                1 -> {binding.etNewProfileWorkTime.text = "Freelancer"}

            }

            false
        }

        //Talent Skill 1 Listener
        talentSKill1.setOnMenuItemClickListener { menuItem ->
            val id = menuItem.itemId

            when (id) {
                0 -> {binding.etNewProfileSkill1.text = "JavaScript"}
                1 -> {binding.etNewProfileSkill1.text = "HTML & CSS"}
                2 -> {binding.etNewProfileSkill1.text = "Java"}
                3 -> {binding.etNewProfileSkill1.text = "Kotlin"}
                4 -> {binding.etNewProfileSkill1.text = "React Native"}
                5 -> {binding.etNewProfileSkill1.text = "Python"}
                6 -> {binding.etNewProfileSkill1.text = "Golang"}
            }

            false
        }

        //Talent Skill 2 Listener
        talentSKill2.setOnMenuItemClickListener { menuItem ->
            val id = menuItem.itemId

            when (id) {
                0 -> {binding.etNewProfileSkill2.text = "JavaScript"}
                1 -> {binding.etNewProfileSkill2.text = "HTML & CSS"}
                2 -> {binding.etNewProfileSkill2.text = "Java"}
                3 -> {binding.etNewProfileSkill2.text = "Kotlin"}
                4 -> {binding.etNewProfileSkill2.text = "React Native"}
                5 -> {binding.etNewProfileSkill2.text = "Python"}
                6 -> {binding.etNewProfileSkill2.text = "Golang"}
            }

            false
        }

        //Talent Skill 3 Listener
        talentSKill3.setOnMenuItemClickListener { menuItem ->
            val id = menuItem.itemId

            when (id) {
                0 -> {binding.etNewProfileSkill3.text = "JavaScript"}
                1 -> {binding.etNewProfileSkill3.text = "HTML & CSS"}
                2 -> {binding.etNewProfileSkill3.text = "Java"}
                3 -> {binding.etNewProfileSkill3.text = "Kotlin"}
                4 -> {binding.etNewProfileSkill3.text = "React Native"}
                5 -> {binding.etNewProfileSkill3.text = "Python"}
                6 -> {binding.etNewProfileSkill3.text = "Golang"}
            }

            false
        }

        //Talent Skill 4 Listener
        talentSKill4.setOnMenuItemClickListener { menuItem ->
            val id = menuItem.itemId

            when (id) {
                0 -> {binding.etNewProfileSkill4.text = "JavaScript"}
                1 -> {binding.etNewProfileSkill4.text = "HTML & CSS"}
                2 -> {binding.etNewProfileSkill4.text = "Java"}
                3 -> {binding.etNewProfileSkill4.text = "Kotlin"}
                4 -> {binding.etNewProfileSkill4.text = "React Native"}
                5 -> {binding.etNewProfileSkill4.text = "Python"}
                6 -> {binding.etNewProfileSkill4.text = "Golang"}
            }

            false
        }

        //Talent Skill 5 Listener
        talentSKill5.setOnMenuItemClickListener { menuItem ->
            val id = menuItem.itemId

            when (id) {
                0 -> {binding.etNewProfileSkill5.text = "JavaScript"}
                1 -> {binding.etNewProfileSkill5.text = "HTML & CSS"}
                2 -> {binding.etNewProfileSkill5.text = "Java"}
                3 -> {binding.etNewProfileSkill5.text = "Kotlin"}
                4 -> {binding.etNewProfileSkill5.text = "React Native"}
                5 -> {binding.etNewProfileSkill5.text = "Python"}
                6 -> {binding.etNewProfileSkill5.text = "Golang"}
            }

            false
        }

        //Show Pop Up when clicked
        binding.etNewProfileJobTitle.setOnClickListener {
            jobTitle.show()
        }

        binding.etNewProfileLocation.setOnClickListener {
            city.show()
        }

        binding.etNewProfileWorkTime.setOnClickListener {
           workTime.show()
        }

        binding.etNewProfileSkill1.setOnClickListener {
            talentSKill1.show()
        }

        binding.etNewProfileSkill2.setOnClickListener {
            talentSKill2.show()
        }

        binding.etNewProfileSkill3.setOnClickListener {
            talentSKill3.show()
        }

        binding.etNewProfileSkill4.setOnClickListener {
            talentSKill4.show()
        }

        binding.etNewProfileSkill5.setOnClickListener {
            talentSKill5.show()
        }

        binding.btNewProfileDone.setOnClickListener {
                val intent = Intent(this, TalentProfileActivity::class.java)
                saveProfile()
                newTalentStatus()
                startActivity(intent)
                Toast.makeText(this, "Profile Updated", Toast.LENGTH_LONG).show()
                finish()
        }
    }

    private fun saveProfile() {
        val talentTitle = binding.etNewProfileJobTitle.text.toString()
        val talentLocation = binding.etNewProfileLocation.text.toString()
        val talentWorkTime = binding.etNewProfileWorkTime.text.toString()
        val talentDescription = binding.etNewProfileDesc.text.toString()
        val talentSkill1 = binding.etNewProfileSkill1.text.toString()
        val talentSkill2 = binding.etNewProfileSkill2.text.toString()
        val talentSkill3 = binding.etNewProfileSkill3.text.toString()
        val talentSkill4 = binding.etNewProfileSkill4.text.toString()
        val talentSkill5 = binding.etNewProfileSkill5.text.toString()
        val talentGithub = binding.etNewProfileGithub.text.toString()

        val sharedPrefData = getSharedPreferences("profileData", Context.MODE_PRIVATE)
        val editor = sharedPrefData.edit()
        editor.apply {
            putString("talentTitle", talentTitle)
            putString("talentLocation", talentLocation)
            putString("talentWorkTime", talentWorkTime)
            putString("talentDescription", talentDescription)
            putString("talentSkill1", talentSkill1)
            putString("talentSkill2", talentSkill2)
            putString("talentSkill3", talentSkill3)
            putString("talentSkill4", talentSkill4)
            putString("talentSkill5", talentSkill5)
            putString("talentGithub", talentGithub)
        }.apply()
    }

    private fun newTalentStatus() {
        val sharedPref = this.getSharedPreferences("newTalent", Context.MODE_PRIVATE)
        val editor = sharedPref.edit()
        editor.putBoolean("updatedProfile", true)
        editor.apply()
    }
}