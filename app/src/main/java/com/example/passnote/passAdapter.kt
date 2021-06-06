package com.example.passnote

import android.content.ClipData
import android.content.ClipboardManager
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.core.content.ContextCompat.getSystemService
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.item_pass.view.*
import java.util.*

class passAdapter(private val userlist:ArrayList<fetchdata>): RecyclerView.Adapter<passAdapter.myViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): myViewHolder {
        val itemView =LayoutInflater.from(parent.context).inflate(R.layout.item_pass,parent,false)

        return myViewHolder(itemView)



    }

    override fun onBindViewHolder(holder: myViewHolder, position: Int) {

        val currentitem=userlist[position]

        holder.fireappname.text=currentitem.appname


        holder.fireepass.text=currentitem.password


        holder.enter.setOnClickListener {

            holder.pass3.requestFocus()
            val fix=holder.decrpt.text.toString()
            if(fix.isEmpty()){
                holder.decrpt.error="Enter the Code in Number Only"
                holder.decrpt.requestFocus()

            }else{
                val s= decrypto(currentitem.password.toString(),fix)
                holder.pass3.text=s.toString()
            }

        }

//        holder.copy.setOnClickListener {
//            val fix=holder.decrpt.text.toString()
//            val s= decrypto(currentitem.password.toString(),fix)
//            val copytext=s.toString()
//
//
//        }
        with(holder.itemView){
            val colors = resources.getIntArray(R.array.random_color)
            val randomColor = colors[Random().nextInt(colors.size)]

            viewColorTag.setBackgroundColor(randomColor)
        }

    }



    override fun getItemCount(): Int {
        return userlist.size
    }

    class myViewHolder(itemView: View):RecyclerView.ViewHolder(itemView){

        val enter: Button =itemView.findViewById(R.id.enter)
//        enter.setOnClickListener {
//            Toast.makeText(baseContext, "SuccessFully pressed Enter.",
//                Toast.LENGTH_SHORT).show()
//        }
        val pass3:TextView=itemView.findViewById(R.id.firepass)
        val fireappname: TextView =itemView.findViewById(R.id.fireappname)
        val  fireepass:TextView=itemView.findViewById(R.id.fireepass)
        val decrpt:EditText=itemView.findViewById(R.id.decrpt)
//        val copy:Button=itemView.findViewById(R.id.copy)

//        init {
//            itemView.copy.setOnClickListener {
//                val Clipboard = it.context.getSystemService(Context.CLIPBOARD_SERVICE) as ClipboardManager
//                val clipData = ClipData.newPlainText("text", "HIGH")
//                Toast.makeText(it.context,"Text_Copied",Toast.LENGTH_SHORT).show()
//            }
//        }




    }
}

private fun decrypto(s1: String, js: String): String? {
    // TODO Auto-generated method stub
    val j = js.toIntOrNull()
    var s5: String? = ""
    for (i in 0 until s1.length) {
        val a = s1[i]
        val v = a.code
        val k = v - j!!
        val d = k.toChar()
        s5 += d
    }
    return s5
}
