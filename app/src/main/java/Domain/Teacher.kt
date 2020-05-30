package Domain

data class Teacher(
    var id:Int,
    var username:String,
    var password:String,
    var name1:String,
    var depart:String,
    var course:String,
    var class1:String
) {
    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as Teacher

        if (id != other.id) return false
        if (username != other.username) return false
        if (password != other.password) return false
        if (name1 != other.name1) return false
        if (depart != other.depart) return false
        if (course != other.course) return false
        if (class1 != other.class1) return false

        return true
    }

    override fun hashCode(): Int {
        var result = id
        result = 31 * result + username.hashCode()
        result = 31 * result + password.hashCode()
        result = 31 * result + name1.hashCode()
        result = 31 * result + depart.hashCode()
        result = 31 * result + course.hashCode()
        result = 31 * result + class1.hashCode()
        return result
    }
}