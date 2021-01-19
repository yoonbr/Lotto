package com.yoond.lotto

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.CalendarView
import android.widget.DatePicker
import kotlinx.android.synthetic.main.activity_constellation.*
import java.text.SimpleDateFormat
import java.time.Month
import java.util.*
import kotlin.collections.ArrayList

class ConstellationActivity : AppCompatActivity() {

    // 월과 일을 넘겨받아서 별자리를 리턴해주는 메소드
    fun makeConstellationString(month: Int, day:Int):String{
        // 월과 일을 합쳐서 하나의 숫자 만들기
        // 월은 0부터 시작하기 떄문에 + 1 해주기
        // 일은 두자리 숫자로 만들기 위해서 String.format 사용
        val target = "${month + 1}${String.format("%02d", day)}".toInt()
        // 별자리 생성
        when(target) {
            in 101..119 -> return "염소자리"
            in 120..218 -> return "물병자리"
            in 219..320 -> return "물고기자리"
            in 321..419 -> return "양자리"
            in 420..520 -> return "황소자리"
            in 521..621 -> return "쌍둥이자리"
            in 622..722 -> return "게자리"
            in 723..822 -> return "사자자리"
            in 823..923 -> return "처녀자리"
            in 924..1022 -> return "천칭자리"
            in 1023..1122 -> return "전갈자리"
            in 1123..1224 -> return "사수자리"
            in 1225..1231 -> return "염소자리"
            else -> return "기타별자리"
        }
    }

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
        val targetString = SimpleDateFormat("yyyy-MM-dd hh:mm:ss").format(Date()) + str

        // list의 숫자 섞기
        // 문자열의 해시코드를 Long으로 변환해서 Random의 seed를 생성해서 list의 숫자를 섞기
        list.shuffle(Random(targetString.hashCode().toLong()))

        return list.subList(0,6)
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_constellation)

        goResultButton.setOnClickListener{
            val intent = Intent(this, ResultActivity::class.java)

            // 데이터 생성
            val lottoList =
                getLottoNumberFromHash(makeConstellationString(
                    datePicker.month, datePicker.dayOfMonth))

            // 인텐트에 저장
            intent.putIntegerArrayListExtra("result", ArrayList(lottoList))

            // 별자리 이름도 전달 -> resultActivity 전달할때 이름 기억하기
            intent.putExtra("constellation",
                makeConstellationString(datePicker.month, datePicker.dayOfMonth))


            startActivity(intent)
    }
        backButton.setOnClickListener{
        finish()
        }
        // 텍스트뷰에 현재 datePicker의 월 일을 가지고 별자리 문자열을 생성해서 출력

        textView.text = makeConstellationString(
            datePicker.month, datePicker.dayOfMonth)

        // DatePicker 초기화
        val calendar = GregorianCalendar()
        datePicker.init(calendar.get(Calendar.YEAR),
            calendar.get(Calendar.MONTH),
            calendar.get(Calendar.DAY_OF_MONTH),
            object: CalendarView.OnDateChangeListener,
                DatePicker.OnDateChangedListener{

                override fun onSelectedDayChange(
                    view: CalendarView, year: Int, month: Int, dayOfMonth: Int
                ) {

                }

                override fun onDateChanged(
                    view: DatePicker?, year: Int, monthOfYear: Int, dayOfMonth: Int
                ) {
                    textView.text = makeConstellationString(
                        datePicker.month, datePicker.dayOfMonth)
                }


            }

        )

    }
}