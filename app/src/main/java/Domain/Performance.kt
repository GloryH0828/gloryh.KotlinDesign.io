package Domain

class Performance(
    var course:String,
    var class1:String,
    var username:String,
    var name1:String,
    var number:Int,
    var semester:Int,
    var teacher: String
) {
    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as Performance

        if (course != other.course) return false
        if (class1 != other.class1) return false
        if (username != other.username) return false
        if (name1 != other.name1) return false
        if (number != other.number) return false
        if (semester != other.semester) return false
        if (teacher != other.teacher) return false

        return true
    }

    override fun hashCode(): Int {
        var result = course.hashCode()
        result = 31 * result + class1.hashCode()
        result = 31 * result + username.hashCode()
        result = 31 * result + name1.hashCode()
        result = 31 * result + number
        result = 31 * result + semester
        result = 31 * result + teacher.hashCode()
        return result
    }
}