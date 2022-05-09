package com.example.firebasedatatorecyclerviewusingkotlin

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.*

    class UserlistActivity : AppCompatActivity() {

        private lateinit var dbref : DatabaseReference
        private lateinit var userRecyclerview : RecyclerView
        private lateinit var userArrayList : ArrayList<User>


        override fun onCreate(savedInstanceState: Bundle?) {
            super.onCreate(savedInstanceState)
            setContentView(R.layout.activity_userlist)

            userRecyclerview = findViewById(R.id.userList)
            userArrayList = arrayListOf<User>()

            userRecyclerview.layoutManager = LinearLayoutManager(this)
            userRecyclerview.setHasFixedSize(true)

            getUserData()

            val adapter = MyAdapter(userArrayList)
            userRecyclerview.adapter = adapter

        }

        private fun getUserData() {

            dbref = FirebaseDatabase.getInstance().getReference("Users")

            dbref.addValueEventListener(object : ValueEventListener{
                override fun onDataChange(snapshot: DataSnapshot) {

                    if (snapshot.exists()){
                        for (userSnapshot in snapshot.children){

                            val user = userSnapshot.getValue(User::class.java)
                            userArrayList.add(user!!)
                        }
                        return
                    }
                }

                override fun onCancelled(error: DatabaseError) {
                    TODO("Not yet implemented")
                }
            })

        }
    }
