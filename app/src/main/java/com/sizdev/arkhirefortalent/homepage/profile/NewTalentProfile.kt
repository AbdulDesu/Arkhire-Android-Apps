package com.sizdev.arkhirefortalent.homepage.profile

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.widget.PopupMenu
import android.widget.Toast
import com.sizdev.arkhirefortalent.R
import kotlinx.android.synthetic.main.activity_new_talent_profile.*

class NewTalentProfile : AppCompatActivity() {
    @SuppressLint("SetTextI18n")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_new_talent_profile)

        //Ge User Input
        val talentTitle = et_newProfileJobTitle.text.toString()
        val talentLocation = et_newProfileLocation.text.toString()
        val talentWorkTime = et_newProfileWorkTime.text.toString()
        val talentDescription = et_newProfileDesc.text.toString()
        val talentGithub = et_newProfileGithub.text.toString()

        //Job Tittle Pop Up Menu
        val jobTitle = PopupMenu(this, et_newProfileJobTitle)

        jobTitle.menu.add(Menu.NONE, 0 ,0, "Android Developer")
        jobTitle.menu.add(Menu.NONE, 1 ,1, "Fullstack Mobile")
        jobTitle.menu.add(Menu.NONE, 2 ,2, "Fullstack Web")
        jobTitle.menu.add(Menu.NONE, 3 ,3, "DevOps Engineer")

        //Talent City Pop Up Menu
        val city = PopupMenu(this, et_newProfileLocation)

        city.menu.add(Menu.NONE, 0 ,0, "Jakarta")
        city.menu.add(Menu.NONE, 1 ,1, "Bandung")
        city.menu.add(Menu.NONE, 2 ,2, "Lampung")
        city.menu.add(Menu.NONE, 3 ,3, "Bali")
        city.menu.add(Menu.NONE, 4 ,4, "Aceh")
        city.menu.add(Menu.NONE, 5 ,5, "Cimahi")
        city.menu.add(Menu.NONE, 6 ,6, "Nagreg")
        city.menu.add(Menu.NONE, 7 ,7, "Cicalengka")

        //Work Time Pop Up Menu
        val workTime = PopupMenu(this, et_newProfileWorkTime)

        workTime.menu.add(Menu.NONE, 0 ,0, "Full Time")
        workTime.menu.add(Menu.NONE, 1 ,1, "Freelancer")

        //Skill Choose Pop Up Menu
        val talentSKill1 = PopupMenu(this, et_newProfileSkill1)

        talentSKill1.menu.add(Menu.NONE, 0 ,0, "JavaScript")
        talentSKill1.menu.add(Menu.NONE, 1 ,1, "HTML & CSS")
        talentSKill1.menu.add(Menu.NONE, 2 ,2, "Java")
        talentSKill1.menu.add(Menu.NONE, 3 ,3, "Kotlin")
        talentSKill1.menu.add(Menu.NONE, 4 ,4, "React Native")
        talentSKill1.menu.add(Menu.NONE, 5 ,5, "Python")
        talentSKill1.menu.add(Menu.NONE, 6 ,6, "Golang")

        val talentSKill2 = PopupMenu(this, et_newProfileSkill2)

        talentSKill2.menu.add(Menu.NONE, 0 ,0, "JavaScript")
        talentSKill2.menu.add(Menu.NONE, 1 ,1, "HTML & CSS")
        talentSKill2.menu.add(Menu.NONE, 2 ,2, "Java")
        talentSKill2.menu.add(Menu.NONE, 3 ,3, "Kotlin")
        talentSKill2.menu.add(Menu.NONE, 4 ,4, "React Native")
        talentSKill2.menu.add(Menu.NONE, 5 ,5, "Python")
        talentSKill2.menu.add(Menu.NONE, 6 ,6, "Golang")

        val talentSKill3 = PopupMenu(this, et_newProfileSkill3)

        talentSKill3.menu.add(Menu.NONE, 0 ,0, "JavaScript")
        talentSKill3.menu.add(Menu.NONE, 1 ,1, "HTML & CSS")
        talentSKill3.menu.add(Menu.NONE, 2 ,2, "Java")
        talentSKill3.menu.add(Menu.NONE, 3 ,3, "Kotlin")
        talentSKill3.menu.add(Menu.NONE, 4 ,4, "React Native")
        talentSKill3.menu.add(Menu.NONE, 5 ,5, "Python")
        talentSKill3.menu.add(Menu.NONE, 6 ,6, "Golang")

        val talentSKill4 = PopupMenu(this, et_newProfileSkill4)

        talentSKill4.menu.add(Menu.NONE, 0 ,0, "JavaScript")
        talentSKill4.menu.add(Menu.NONE, 1 ,1, "HTML & CSS")
        talentSKill4.menu.add(Menu.NONE, 2 ,2, "Java")
        talentSKill4.menu.add(Menu.NONE, 3 ,3, "Kotlin")
        talentSKill4.menu.add(Menu.NONE, 4 ,4, "React Native")
        talentSKill4.menu.add(Menu.NONE, 5 ,5, "Python")
        talentSKill4.menu.add(Menu.NONE, 6 ,6, "Golang")

        val talentSKill5 = PopupMenu(this, et_newProfileSkill5)

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
                0 -> {et_newProfileJobTitle.text = "Android Developer"}
                1 -> {et_newProfileJobTitle.text = "Fullstack Mobile"}
                2 -> {et_newProfileJobTitle.text = "Fullstack Web"}
                3 -> {et_newProfileJobTitle.text = "DevOps Engineer"}
            }

            false
        }

        //City Listener
        city.setOnMenuItemClickListener { menuItem ->
            val id = menuItem.itemId

            when (id) {
                0 -> {et_newProfileLocation.text = "Jakarta"}
                1 -> {et_newProfileLocation.text = "Bandung"}
                2 -> {et_newProfileLocation.text = "Lampung"}
                3 -> {et_newProfileLocation.text = "Bali"}
                4 -> {et_newProfileLocation.text = "Aceh"}
                5 -> {et_newProfileLocation.text = "Cimahi"}
                6 -> {et_newProfileLocation.text = "Nagreg"}
                7 -> {et_newProfileLocation.text = "Cicalengka"}
            }

            false
        }

        //Work Time Listener
        workTime.setOnMenuItemClickListener { menuItem ->
            val id = menuItem.itemId

            when (id) {
                0 -> {et_newProfileWorkTime.text = "Full Time"}
                1 -> {et_newProfileWorkTime.text = "Freelancer"}

            }

            false
        }

        //Talent Skill 1 Listener
        talentSKill1.setOnMenuItemClickListener { menuItem ->
            val id = menuItem.itemId

            when (id) {
                0 -> {et_newProfileSkill1.text = "JavaScript"}
                1 -> {et_newProfileSkill1.text = "HTML & CSS"}
                2 -> {et_newProfileSkill1.text = "Java"}
                3 -> {et_newProfileSkill1.text = "Kotlin"}
                4 -> {et_newProfileSkill1.text = "React Native"}
                5 -> {et_newProfileSkill1.text = "Python"}
                6 -> {et_newProfileSkill1.text = "Golang"}
            }

            false
        }

        //Talent Skill 2 Listener
        talentSKill2.setOnMenuItemClickListener { menuItem ->
            val id = menuItem.itemId

            when (id) {
                0 -> {et_newProfileSkill2.text = "JavaScript"}
                1 -> {et_newProfileSkill2.text = "HTML & CSS"}
                2 -> {et_newProfileSkill2.text = "Java"}
                3 -> {et_newProfileSkill2.text = "Kotlin"}
                4 -> {et_newProfileSkill2.text = "React Native"}
                5 -> {et_newProfileSkill2.text = "Python"}
                6 -> {et_newProfileSkill2.text = "Golang"}
            }

            false
        }

        //Talent Skill 3 Listener
        talentSKill3.setOnMenuItemClickListener { menuItem ->
            val id = menuItem.itemId

            when (id) {
                0 -> {et_newProfileSkill3.text = "JavaScript"}
                1 -> {et_newProfileSkill3.text = "HTML & CSS"}
                2 -> {et_newProfileSkill3.text = "Java"}
                3 -> {et_newProfileSkill3.text = "Kotlin"}
                4 -> {et_newProfileSkill3.text = "React Native"}
                5 -> {et_newProfileSkill3.text = "Python"}
                6 -> {et_newProfileSkill3.text = "Golang"}
            }

            false
        }

        //Talent Skill 4 Listener
        talentSKill4.setOnMenuItemClickListener { menuItem ->
            val id = menuItem.itemId

            when (id) {
                0 -> {et_newProfileSkill4.text = "JavaScript"}
                1 -> {et_newProfileSkill4.text = "HTML & CSS"}
                2 -> {et_newProfileSkill4.text = "Java"}
                3 -> {et_newProfileSkill4.text = "Kotlin"}
                4 -> {et_newProfileSkill4.text = "React Native"}
                5 -> {et_newProfileSkill4.text = "Python"}
                6 -> {et_newProfileSkill4.text = "Golang"}
            }

            false
        }

        //Talent Skill 5 Listener
        talentSKill5.setOnMenuItemClickListener { menuItem ->
            val id = menuItem.itemId

            when (id) {
                0 -> {et_newProfileSkill5.text = "JavaScript"}
                1 -> {et_newProfileSkill5.text = "HTML & CSS"}
                2 -> {et_newProfileSkill5.text = "Java"}
                3 -> {et_newProfileSkill5.text = "Kotlin"}
                4 -> {et_newProfileSkill5.text = "React Native"}
                5 -> {et_newProfileSkill5.text = "Python"}
                6 -> {et_newProfileSkill5.text = "Golang"}
            }

            false
        }

        //Show Pop Up when clicked
        et_newProfileJobTitle.setOnClickListener {
            jobTitle.show()
        }

        et_newProfileLocation.setOnClickListener {
            city.show()
        }

        et_newProfileWorkTime.setOnClickListener {
           workTime.show()
        }

        et_newProfileSkill1.setOnClickListener {
            talentSKill1.show()
        }

        et_newProfileSkill2.setOnClickListener {
            talentSKill2.show()
        }

        et_newProfileSkill3.setOnClickListener {
            talentSKill3.show()
        }

        et_newProfileSkill4.setOnClickListener {
            talentSKill4.show()
        }

        et_newProfileSkill5.setOnClickListener {
            talentSKill5.show()
        }

        bt_newProfileDone.setOnClickListener {
                val intent = Intent(this, TalentProfileActivity::class.java)
                saveProfile()
                newTalentStatus()
                startActivity(intent)
                Toast.makeText(this, "Profile Updated", Toast.LENGTH_LONG).show()
                finish()
        }


    }

    private fun saveProfile() {
        val talentTitle = et_newProfileJobTitle.text.toString()
        val talentLocation = et_newProfileLocation.text.toString()
        val talentWorkTime = et_newProfileWorkTime.text.toString()
        val talentDescription = et_newProfileDesc.text.toString()
        val talentSkill1 = et_newProfileSkill1.text.toString()
        val talentSkill2 = et_newProfileSkill2.text.toString()
        val talentSkill3 = et_newProfileSkill3.text.toString()
        val talentSkill4 = et_newProfileSkill4.text.toString()
        val talentSkill5 = et_newProfileSkill5.text.toString()
        val talentGithub = et_newProfileGithub.text.toString()
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