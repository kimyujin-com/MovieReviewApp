package ddwu.com.mobile.fooddbexam02.data

import java.io.Serializable

/*Intent에 저장하여야 하므로 Serializable 인터페이스 구현*/
data class MovieDto( var id : Int, var movie: String, var director: String, var star: String) : Serializable {
//    var imageNo : Int
//        get() {
//            return imageNo
//        }
//        set(value) {
//            imageNo = value
//        }
//
//    init {
//        imageNo = 1
//    }
    override fun toString() = "$id - $movie ( $director, $star )"
}
