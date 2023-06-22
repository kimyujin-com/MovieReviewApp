package ddwu.com.mobile.fooddbexam02

//import ddwu.com.mobile.fooddbexam02.data.MovieDBHelper
//import ddwu.com.mobile.fooddbexam02.data.MovieDto
//import ddwu.com.mobile.fooddbexam02.databinding.ActivityMainBinding
import android.annotation.SuppressLint
import android.content.DialogInterface
import android.content.Intent
import android.os.Bundle
import android.provider.BaseColumns
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import ddwu.com.mobile.fooddbexam02.data.MovieDBHelper
import ddwu.com.mobile.fooddbexam02.data.MovieDto
import ddwu.com.mobile.fooddbexam02.databinding.ActivityMainBinding

//과제명: 영화 정보 관리 앱
//분반: 모바일소프트웨어 1분반
//학번: 20210768 성명: 김유진
//제출일: 2023년 06월 22일
class MainActivity : AppCompatActivity() {

    val TAG = "MainActivity"
    val REQ_ADD = 100
    val REQ_UPDATE = 200


    lateinit var binding : ActivityMainBinding
    lateinit var adapter : MovieAdapter
    lateinit var movies : ArrayList<MovieDto>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        /*RecyclerView 의 layoutManager 지정*/
        binding.rvmovies.layoutManager = LinearLayoutManager(this).apply {
            orientation = LinearLayoutManager.VERTICAL
        }

        movies = getAllmovies()               // DB 에서 모든 Movie를 가져옴
        adapter = MovieAdapter(movies)        // adapter 에 데이터 설정
        binding.rvmovies.adapter = adapter   // RecylcerView 에 adapter 설정

        /*RecyclerView 항목 클릭 시 실행할 객체*/
        val onClickListener = object : MovieAdapter.OnItemClickListener {
            override fun onItemClick(view: View, position: Int) {
                /*클릭 항목의 dto 를 intent에 저장 후 UpdateActivity 실행*/
                val intent = Intent(this@MainActivity, UpdateActivity::class.java)
                intent.putExtra("dto", movies.get(position) )
                startActivityForResult(intent, REQ_UPDATE)
            }
        }

        adapter.setOnItemClickListener(onClickListener)


        /*RecyclerView 항목 롱클릭 시 실행할 객체*/
        val onLongClickListener = object: MovieAdapter.OnItemLongClickListener {
            override fun onItemLongClick(view: View, position: Int) {
                AlertDialog.Builder(this@MainActivity).run {
                    setTitle("영화 삭제")
                    setMessage("${movies[position].movie}를 삭제하시겠습니까?")
                    setPositiveButton("삭제", object : DialogInterface.OnClickListener {
                        override fun onClick(p0: DialogInterface?, p1: Int) {
                            /*롱클릭 항목의 dto 에서 id 확인 후 함수에 전달*/
                            if ( deleteMovie(movies.get(position).id) > 0) {
                                refreshList(RESULT_OK)
                                Toast.makeText(this@MainActivity, "영화 삭제 완료", Toast.LENGTH_SHORT).show()
                            }
                        }
                    })
                    setNegativeButton("취소") { dialog, which ->
                        dialog.dismiss()
                    }
                    show()
                }
            }
        }
        adapter.setOnItemLongClickListener(onLongClickListener)
    }
    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_main, menu)
        return super.onCreateOptionsMenu(menu)
    }
    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when(item.itemId) {
            R.id.addAddress -> { val intent = Intent(this, AddActivity::class.java)
            startActivityForResult(intent, REQ_ADD)}
            R.id.introducing -> AlertDialog.Builder(this@MainActivity).run {
                setTitle("개발자 소개")
                setIcon(R.mipmap.pic)
                setMessage("저는 모바일 소프트웨어 1분반 \n 20210768 김유진입니다.")
                setNegativeButton("닫기") { dialog, which ->
                    dialog.dismiss()
                }
                show()
            }
            R.id.endApp -> AlertDialog.Builder(this@MainActivity).run {
                setTitle("앱 종료")
                setMessage("앱을 종료하시겠습니까?")
                setPositiveButton("종료") { dialog, which ->
                    finish() // 액티비티 종료
                }
                setNegativeButton("취소") { dialog, which ->
                    dialog.dismiss()
                }
                show()
            }
        }
        return super.onOptionsItemSelected(item)
    }
    /*화면이 보일 때마다 화면을 갱신하고자 할 경우에는 onResume()에 갱신작업 추가*/
//    override fun onResume() {
//        super.onResume()
//        adapter.notifyDataSetChanged()
//    }


    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        when (requestCode) {
            REQ_UPDATE -> {
                refreshList(resultCode)
            }
            REQ_ADD -> {
                refreshList(resultCode)
            }
        }
    }

    /*다른 액티비티에서 DB 변경 시 DB 내용을 읽어와 Adapter 의 list 에 반영하고 RecyclerView 갱신*/
    private fun refreshList(resultCode: Int) {
        if (resultCode == RESULT_OK) {
            movies.clear()
            movies.addAll(getAllmovies())
            adapter.notifyDataSetChanged()
        } else {
            Toast.makeText(this@MainActivity, "취소됨", Toast.LENGTH_SHORT).show()
        }
    }


    /*전체 레코드를 가져와 dto 로 저장한 후 dto를 저장한 list 반환*/
    @SuppressLint("Range")
    fun getAllmovies() : ArrayList<MovieDto> {
        val helper = MovieDBHelper(this)
        val db = helper.readableDatabase

//        val cursor = db.rawQuery("SELECT * FROM ${MovieDBHelper.TABLE_NAME}", null)
        val cursor = db.query(MovieDBHelper.TABLE_NAME, null, null, null, null, null, null)

        val movies = arrayListOf<MovieDto>()

        with (cursor) {
            while (moveToNext()) {
                val id = getInt( getColumnIndex(BaseColumns._ID) )//
                val movie = getString ( getColumnIndex(MovieDBHelper.COL_MOVIE) )
                val director = getString ( getColumnIndex(MovieDBHelper.COL_DIRECTOR) )
                val star = getString ( getColumnIndex(MovieDBHelper.COL_STAR) )
                val dto = MovieDto(id, movie, director, star)
                movies.add(dto)
            }
        }

        cursor.close()      // cursor 사용을 종료했으므로 close()
        helper.close()      // DB 사용이 끝났으므로 close()

        return movies
    }


    /*ID 에 해당하는 레코드를 삭제 후 삭제된 레코드 개수 반환*/
    fun deleteMovie(id: Int) : Int {
        val helper = MovieDBHelper(this)
        val db = helper.writableDatabase

        val whereClause = "${BaseColumns._ID}=?"
        val whereArgs = arrayOf(id.toString())

        val result = db.delete(MovieDBHelper.TABLE_NAME, whereClause, whereArgs)

        helper.close()
        return result
    }


}