package Domain

class Semester(
    var id:Int,
    var name1:String
) {
    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as Semester

        if (id != other.id) return false
        if (name1 != other.name1) return false

        return true
    }

    override fun hashCode(): Int {
        var result = id
        result = 31 * result + name1.hashCode()
        return result
    }
}