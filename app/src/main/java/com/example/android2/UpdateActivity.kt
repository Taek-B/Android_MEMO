package com.example.android2

import android.app.Activity
import android.content.Intent
import android.database.sqlite.SQLiteDatabase
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast
import com.example.android2.databinding.ActivityUpdateBinding


class UpdateActivity : AppCompatActivity() {
    lateinit var binding : ActivityUpdateBinding
    lateinit var todo:String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityUpdateBinding.inflate(layoutInflater)
        setContentView(binding.root)
        val intent = intent
        todo = intent.getStringExtra("todo").toString()
        binding.updateEditView.setText(todo)
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_add, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean{
        return when (item.itemId) {
            //할일 등록 화면에서 저장버튼 클릭시 실행

            R.id.menu_add_save -> {

                var inputText = binding.updateEditView.text.toString()
                if(inputText.isBlank()){
                    //할일이 비어져 있을 때
                    Toast.makeText(this,"할일이 비어져 있습니다. 확인해주세요.", Toast.LENGTH_SHORT).show()
                    return false
                }else{
                    //할일이 있을 때
                    val db: SQLiteDatabase = DBHelper(this).writableDatabase
                    db.execSQL(
                        "update LIST_DB set edit = ? where edit =?", arrayOf(inputText,todo)
                    )
                    db.close()

                    val intent = Intent(this,MainActivity::class.java)
                    intent.putExtra("result",inputText)
                    startActivity(intent)
                }
                return true
            }
            else -> return true
        }
    }
}