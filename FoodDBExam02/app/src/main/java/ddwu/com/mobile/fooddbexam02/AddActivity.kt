package ddwu.com.mobile.fooddbexam02

import android.content.ContentValues
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import ddwu.com.mobile.fooddbexam02.data.MovieDBHelper
import ddwu.com.mobile.fooddbexam02.data.MovieDto
import ddwu.com.mobile.fooddbexam02.databinding.ActivityAddBinding
import java.sql.Types.NULL

class AddActivity : AppCompatActivity() {
    val TAG = "AddActivity"

    lateinit var addBinding : ActivityAddBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        addBinding = ActivityAddBinding.inflate(layoutInflater)
        setContentView(addBinding.root)

        /*추가버튼 클릭*/
        addBinding.btnAddFood.setOnClickListener{
            val movie = addBinding.etAddMovie.text.toString()
            val director = addBinding.etAddDirector.text.toString()
            val star = addBinding.etAddStar.text.toString()
            val newDto = MovieDto(0, movie, director, star)      // 화면 값으로 dto 생성, id 는 임의의 값 0

            if (movie.isNotEmpty() && director.isNotEmpty() && star.isNotEmpty()) {
                if (addMovie(newDto) > 0) {
                    setResult(RESULT_OK)
                } else {
                    setResult(RESULT_CANCELED)
                }
                finish()
            } else {
                // 정보를 입력하지 않은 경우 처리
                Toast.makeText(this, "정보를 모두 입력해주세요.", Toast.LENGTH_SHORT).show()
                setResult(RESULT_CANCELED)
            }
        }

        /*취소버튼 클릭*/
        addBinding.btnAddCancel.setOnClickListener{
            setResult(RESULT_CANCELED)
            finish()
        }
    }


    /*추가할 정보를 담고 있는 dto 를 전달받아 DB에 추가, id 는 autoincrement 이므로 제외
    * DB추가 시 추가한 항목의 ID 값 반환, 추가 실패 시 -1 반환 */
    fun addMovie(newDto : MovieDto) : Long  {
        val helper = MovieDBHelper(this)
        val db = helper.writableDatabase

        val newValues = ContentValues()
        newValues.put(MovieDBHelper.COL_MOVIE, newDto.movie)
        newValues.put(MovieDBHelper.COL_DIRECTOR, newDto.director)
        newValues.put(MovieDBHelper.COL_STAR, newDto.star)

        /*insert 한 항목의 id 를 반환*/
        val result = db.insert(MovieDBHelper.TABLE_NAME, null, newValues)

        helper.close()      // DB 작업 후 close() 수행

        return result
    }



}