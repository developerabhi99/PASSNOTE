package com.example.passnote

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import androidx.core.text.isDigitsOnly

import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.item_pass.view.*
import java.util.*

class noteAdapter(private val usernotelist: ArrayList<fetchnote>):RecyclerView.Adapter<noteAdapter.mynoteAdapter>(){

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): mynoteAdapter {

        val itemView=LayoutInflater.from(parent.context).inflate(R.layout.item_notes,parent,false)

       return  mynoteAdapter(itemView)
    }

    override fun onBindViewHolder(holder: mynoteAdapter, position: Int) {
        val current=usernotelist[position]

        holder.notessubject.text=current.subject
        holder.encrpytnote.text=current.notes
        holder.enternote.setOnClickListener {
            //holder.originalnote.
            holder.originalnote.requestFocus()
            val fix=holder.decrpytnote.text.toString()
            if(fix.isEmpty() ){
                holder.decrpytnote.error="Enter the Code in Number Only"
                holder.decrpytnote.requestFocus()

            }else{
                val s= decrypto(current.notes.toString(),fix)
                holder.originalnote.text=s.toString()
            }


        }


        with(holder.itemView){
            val colors = resources.getIntArray(R.array.random_color)
            val randomColor = colors[Random().nextInt(colors.size)]

            viewColorTag.setBackgroundColor(randomColor)
        }


    }

    override fun getItemCount(): Int {
        return usernotelist.size
    }

    class mynoteAdapter(itemView: View):RecyclerView.ViewHolder(itemView){
        val notessubject:TextView=itemView.findViewById(R.id.notessubject)
        val encrpytnote:TextView=itemView.findViewById(R.id.encrptnote)
        val decrpytnote:EditText=itemView.findViewById(R.id.decrptnote)
        val enternote:Button=itemView.findViewById(R.id.enternote)
        val originalnote:TextView=itemView.findViewById(R.id.originalnote)


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