package ddwu.com.mobile.fooddbexam02

import android.content.ContentValues
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.BaseColumns
import android.widget.Toast
import ddwu.com.mobile.fooddbexam02.data.MovieDBHelper
import ddwu.com.mobile.fooddbexam02.data.MovieDto
import ddwu.com.mobile.fooddbexam02.databinding.ActivityUpdateBinding

class UpdateActivity : AppCompatActivity() {

    lateinit var updateBinding : ActivityUpdateBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        updateBinding = ActivityUpdateBinding.inflate(layoutInflater)
        setContentView(updateBinding.root)

        /*RecyclerView 에서 선택하여 전달한 dto 를 확인*/
        val dto = intent.getSerializableExtra("dto") as MovieDto

        /*전달받은 값으로 화면에 표시*/
        updateBinding.etUpdateId.setText(dto.id.toString())
        updateBinding.etUpdateMovie.setText(dto.movie)
        updateBinding.etUpdateDirector.setText(dto.director)
        updateBinding.etUpdateStar.setText(dto.star)

        updateBinding.btnUpdateFood.setOnClickListener{
            /*dto 는 아래와 같이 기존의 dto 를 재사용할 수도 있음*/
            dto.movie = updateBinding.etUpdateMovie.text.toString()
            dto.director = updateBinding.etUpdateDirector.text.toString()
            dto.star = updateBinding.etUpdateStar.text.toString()
            if (dto.movie.isNotEmpty() && dto.director.isNotEmpty() && dto.star.isNotEmpty()) {
                if (updateFood(dto) > 0) {
                    setResult(RESULT_OK)      // update 를 수행했으므로 RESULT_OK 를 반환
                } else {
                    setResult(RESULT_CANCELED)
                }
                finish()
            }else {
                // 정보를 입력하지 않은 경우 처리
                Toast.makeText(this, "정보를 모두 수정해주세요.", Toast.LENGTH_SHORT).show()
                setResult(RESULT_CANCELED)
            }
        }
        updateBinding.btnUpdateCancel.setOnClickListener{
            setResult(RESULT_CANCELED)
            finish()
        }
    }



    /*update 정보를 담고 있는 dto 를 전달 받아 dto 의 id 를 기준으로 수정*/
    fun updateFood(dto: MovieDto): Int {
        val helper = MovieDBHelper(this)
        val db = helper.writableDatabase
        val updateValue = ContentValues()
        updateValue.put(MovieDBHelper.COL_MOVIE, dto.movie)
        updateValue.put(MovieDBHelper.COL_DIRECTOR, dto.director)
        updateValue.put(MovieDBHelper.COL_STAR, dto.star)
        val whereCaluse = "${BaseColumns._ID}=?"
        val whereArgs = arrayOf(dto.id.toString())

        /*upate 가 적용된 레코드의 개수 반환*/
        val result =  db.update(MovieDBHelper.TABLE_NAME,
            updateValue, whereCaluse, whereArgs)

        helper.close()      // DB 작업 후에는 close()

        return result
    }

}