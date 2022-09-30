package com.example.android2

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat.startActivity
import androidx.recyclerview.widget.RecyclerView
import com.example.android2.databinding.ItemRecyclerviewBinding
import com.example.android2.UpdateActivity


class MyViewHolder(val binding: ItemRecyclerviewBinding) : RecyclerView.ViewHolder(binding.root)

class MyAdapter(val datas: MutableList<String>?) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    lateinit var db: DBHelper
    lateinit var context: Context

    override fun getItemCount(): Int {
        return datas?.size ?: 0
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        //DB 사용을 위한 사전 준비
        context = parent.context
        db = DBHelper(context)

        return MyViewHolder(
            ItemRecyclerviewBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }


    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val binding = (holder as MyViewHolder).binding
        binding.itemData.text = datas!![position]

        //삭제 이벤트 추가
        binding.deleteBtn.setOnClickListener {
            try {
                val data = binding.itemData.text.toString()
                val delDB = db.writableDatabase

                delDB.execSQL("delete from LIST_DB where edit=?", arrayOf(data))
                delDB.close()

                //삭제 후 화면 표시
                datas.remove(datas!![position])
                notifyDataSetChanged()
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
        //수정 이벤트 추가
        binding.itemData.setOnClickListener {
            if(binding.updateBtn.visibility == View.GONE){
                binding.updateBtn.visibility = View.VISIBLE
                binding.updateBtn.setOnClickListener {
                    val intent = Intent(binding.itemData.context, UpdateActivity::class.java)
                    intent.putExtra("todo",binding.itemData.text.toString())
                    startActivity(context, intent, null)
                }
            }else{
                binding.updateBtn.visibility = View.GONE
            }
        }
    }
}



