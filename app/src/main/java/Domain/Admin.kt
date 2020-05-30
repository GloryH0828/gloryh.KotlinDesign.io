package Domain

data class Admin(

    var id:Int,
    var username:String,
    var password:String,
    var name1:String
) {
    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as Admin

        if (id != other.id) return false
        if (username != other.username) return false
        if (password != other.password) return false
        if (name1 != other.name1) return false

        return true
    }

    override fun hashCode(): Int {
        var result = id
        result = 31 * result + username.hashCode()
        result = 31 * result + password.hashCode()
        result = 31 * result + name1.hashCode()
        return result
    }


}