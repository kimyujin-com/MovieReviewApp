package ddwu.com.mobile.fooddbexam02.data

import ddwu.com.mobile.fooddbexam02.R

/*데이터베이스가 아닌 list 로 간단하게 구현 DAO*/
class SimpleMovieDao {
    private val movies = ArrayList<MovieDto>()

    init {
        movies.add( MovieDto(0,"스파이더맨", "조아킴", "샤메익 무어"))
        movies.add( MovieDto(1, "엘리멘탈", "피터 손", "레아 루이스") )
        movies.add( MovieDto( 2, "범죄도시3", "이상용", "마동석") )
        movies.add( MovieDto( 3, "명탐정코난", "미야시타", "타카야마") )
        movies.add( MovieDto( 4, "플래시", "안드레스", "에즈라") )
        movies.add( MovieDto(5, "트랜스포머", "스티븐", "안소니") )
    }

    fun getAllmovies() : ArrayList<MovieDto> {
        return movies
    }

    fun addNewMovie(newMovieDto : MovieDto) {
        movies.add(newMovieDto)
    }

    fun modifyMovie(pos: Int, modifyMovieDto : MovieDto) {
        movies.set(pos, modifyMovieDto)
    }

    fun removeMovie(removeMovieDto : MovieDto) {
        val index = movies.indexOf(removeMovieDto)
        movies.removeAt(index)
    }

    fun getMovieByPos(pos : Int) = movies.get(pos)
}