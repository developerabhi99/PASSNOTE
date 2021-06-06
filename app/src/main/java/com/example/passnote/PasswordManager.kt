package com.example.passnote

import android.content.Intent
import android.graphics.*
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.TextView

import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.*
import kotlinx.android.synthetic.main.activity_password_manager.*
import kotlinx.android.synthetic.main.activity_password_manager.view.*
import kotlinx.android.synthetic.main.activity_scheduler.*
import kotlinx.android.synthetic.main.item_pass.view.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import java.util.*


class PasswordManager : AppCompatActivity() {
    lateinit var auth: FirebaseAuth
    lateinit var myRef:DatabaseReference
    private lateinit var userrecyclerview:RecyclerView
    private lateinit var userArraylist:ArrayList<fetchdata>
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_password_manager)


        userrecyclerview=findViewById(R.id.passrve)
        userrecyclerview.layoutManager=LinearLayoutManager(this)
        userrecyclerview.setHasFixedSize(true)

        userArraylist= arrayListOf<fetchdata>()

//        userArraylist.add(fetchdata("WHATSAPP","abivjkwwjw"))
//        userArraylist.add(fetchdata("FACEBOOK","abivjkwwjw"))
//        userArraylist.add(fetchdata("FACEBOOK","abivjkwwjw"))
//        userArraylist.add(fetchdata("FACEBOOK","abivjkwwjw"))
//        userArraylist.add(fetchdata("FACEBOOK","abivjkwwjw"))
//        userArraylist.add(fetchdata("FACEBOOK","abivjkwwjw"))


        getuserdata()
        initSwipe()

    }
    fun initSwipe() {
        val simpleItemTouchCallback = object : ItemTouchHelper.SimpleCallback(
            0,
            ItemTouchHelper.LEFT
        ) {
            override fun onMove(
                recyclerView: RecyclerView,
                viewHolder: RecyclerView.ViewHolder,
                target: RecyclerView.ViewHolder
            ): Boolean = false

            override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
                val position = viewHolder.adapterPosition
                val current=userArraylist[position]
                if (direction == ItemTouchHelper.LEFT) {
                    auth= FirebaseAuth.getInstance()
                    val user=auth.currentUser

                    val userid=user?.uid.toString()
                     myRef=FirebaseDatabase.getInstance().reference
                    GlobalScope.launch(Dispatchers.IO) {
                        myRef.child("PASSWORD MANAGER").child(userid).child(current.appname.toString()).removeValue().addOnCompleteListener{

                            Toast.makeText(this@PasswordManager,"DELETED",Toast.LENGTH_SHORT).show()
                            Log.e("TAG","DELETED")
                            startActivity(Intent(this@PasswordManager,PasswordManager::class.java))
                        }
                    }

                }
            }

            override fun onChildDraw(
                canvas: Canvas,
                recyclerView: RecyclerView,
                viewHolder: RecyclerView.ViewHolder,
                dX: Float,
                dY: Float,
                actionState: Int,
                isCurrentlyActive: Boolean
            ) {
                if (actionState == ItemTouchHelper.ACTION_STATE_SWIPE) {
                    val itemView = viewHolder.itemView

                    val paint = Paint()
                    val icon: Bitmap

                    if (dX > 0) {

                        icon = BitmapFactory.decodeResource(resources, R.mipmap.ic_delete_white_png)

                        paint.color = Color.parseColor("#388E3C")

                        canvas.drawRect(
                            itemView.left.toFloat(), itemView.top.toFloat(),
                            itemView.left.toFloat() + dX, itemView.bottom.toFloat(), paint
                        )

                        canvas.drawBitmap(
                            icon,
                            itemView.left.toFloat(),
                            itemView.top.toFloat() + (itemView.bottom.toFloat() - itemView.top.toFloat() - icon.height.toFloat()) / 2,
                            paint
                        )


                    } else {
                        icon = BitmapFactory.decodeResource(resources, R.mipmap.ic_delete_white_png)

                        paint.color = Color.parseColor("#D32F2F")

                        canvas.drawRect(
                            itemView.right.toFloat() + dX, itemView.top.toFloat(),
                            itemView.right.toFloat(), itemView.bottom.toFloat(), paint
                        )

                        canvas.drawBitmap(
                            icon,
                            itemView.right.toFloat() - icon.width,
                            itemView.top.toFloat() + (itemView.bottom.toFloat() - itemView.top.toFloat() - icon.height.toFloat()) / 2,
                            paint
                        )
                    }
                    viewHolder.itemView.translationX = dX


                } else {
                    super.onChildDraw(
                        canvas,
                        recyclerView,
                        viewHolder,
                        dX,
                        dY,
                        actionState,
                        isCurrentlyActive
                    )
                }
            }


        }

        val itemTouchHelper = ItemTouchHelper(simpleItemTouchCallback)
        itemTouchHelper.attachToRecyclerView(passrve)
    }

    private fun getuserdata() {
        auth= FirebaseAuth.getInstance()
        val user=auth.currentUser

        val userid=user?.uid.toString()
        Log.e("abc",userid)




        myRef=FirebaseDatabase.getInstance().reference
        myRef.child("PASSWORD MANAGER").child(userid).addValueEventListener(object :ValueEventListener{
            override fun onDataChange(snapshot: DataSnapshot) {
                if(snapshot.exists()){
                    for (usersnap in snapshot.children){

                        val us=usersnap.getValue(fetchdata::class.java)
//                        val app=usersnap.getValue(String?)
//                        val password=usersnap.getValue(String::class.java)
                        userArraylist.add(us!!)

                    }

                   // userrecyclerview.adapter=passAdapter(userArraylist)
                    //userArraylist.add(fetchdata("FACEBOOK","Ghnopoz"))
                    userrecyclerview.adapter=passAdapter(userArraylist)
                }
            }

            override fun onCancelled(error: DatabaseError) {
                TODO("Not yet implemented")
            }

        })

    }


    override fun onBackPressed() {

        startActivity(Intent(this,MainActivity::class.java))
    }
    fun openNewTask(view: View) {

        startActivity(Intent(this,Scheduler::class.java))
    }
}




