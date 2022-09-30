package com.example.android2

import android.app.Activity
import android.database.sqlite.SQLiteDatabase
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.android2.databinding.ActivityAddBinding

class AddActivity : AppCompatActivity() {
    lateinit var binding: ActivityAddBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAddBinding.inflate(layoutInflater)
        setContentView(binding.root)

    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_add, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean =
        when (item.itemId) {
            //할일 등록 화면에서 저장버튼 클릭시 실행

            R.id.menu_add_save -> {
                //DBHelper 이용한 db객체 생성
                val intent = intent
                var todoText = binding.addEditView.text.toString()


                if (todoText.isBlank()) {
                    //할일 입력값이 없을때
                    setResult(Activity.RESULT_CANCELED, intent)
                    //toast
                    Toast.makeText(this,"할일이 생성되지 않았습니다.", Toast.LENGTH_SHORT).show()
                } else {
                    //할일 입력값이 있을때
                    //데이터 베이스 연동해서 insert문 작성
                    val db: SQLiteDatabase = DBHelper(this).writableDatabase
                    db.execSQL(
                        "insert into LIST_DB(edit) values(?)",
                        arrayOf(todoText)
                    )
                    db.close()

                    intent.putExtra("result", todoText)
                    setResult(Activity.RESULT_OK, intent)
                }
                finish()
                true
            }
            else -> true
        }
}