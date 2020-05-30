package Domain

class Depart(
    var id:Int,
    var name1:String,
    var type:Int
) {
    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as Depart

        if (id != other.id) return false
        if (name1 != other.name1) return false
        if (type != other.type) return false

        return true
    }

    override fun hashCode(): Int {
        var result = id
        result = 31 * result + name1.hashCode()
        result = 31 * result + type
        return result
    }
}