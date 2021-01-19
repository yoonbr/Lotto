package com.yoond.lotto

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.TextUtils
import android.widget.Toast
import kotlinx.android.synthetic.main.activity_name.*
import java.text.SimpleDateFormat
import java.util.*
import kotlin.collections.ArrayList

class NameActivity : AppCompatActivity() {

    // 별자리 문자열을 받아서 6개의 정수 List를 만들어서 리턴하는 메소드
    fun getLottoNumberFromHash(str:String):MutableList<Int>{
        // 정수 저장 List
        var list = mutableListOf<Int>()

        // 정수 1~45를 List에 저장
        for(number in 1..45){
            list.add(number)
        }

        // 오늘 날짜와 문자열을 합쳐서 새로운 문자열 만들기
        // 날짜만 입력하면 날짜마다 다르고, 시간까지 입력하면 시간마다 번호가 랜덤하게 생성됨
        val targetString = SimpleDateFormat("yyyy-MM-dd").format(Date()) + str

        // list의 숫자 섞기
        // 문자열의 해시코드를 Long으로 변환해서 Random의 seed를 생성해서 list의 숫자를 섞기
        list.shuffle(Random(targetString.hashCode().toLong()))

        return list.subList(0,6)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_name)

        // 버튼의 클릭 이벤트 처리
        goButton.setOnClickListener{
            val intent = Intent(this, ResultActivity::class.java)

            // 입력된 이름이 없으면 토스트를 출력
            if(TextUtils.isEmpty(editText.text.toString())){
                Toast.makeText(this@NameActivity, "이름을 입력하세요.",
                    Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            // 데이터 만들기
            val list = getLottoNumberFromHash(editText.text.toString())

            // 데이터 저장
            intent.putIntegerArrayListExtra("result", ArrayList(list))

            // 이름 전달
            intent.putExtra("name", editText.text.toString())
            startActivity(intent)
        }

        backButton.setOnClickListener{
            finish()
        }
    }
}