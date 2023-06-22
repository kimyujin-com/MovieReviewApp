package ddwu.com.mobile.finalreport.data

import java.io.Serializable
import java.util.Date

/*Intent에 저장하여야 하므로 Serializable 인터페이스 구현*/
data class MovieDto(var id: Int, var movie: String, var director: String, var star: String, var date: String) : Serializable {
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
