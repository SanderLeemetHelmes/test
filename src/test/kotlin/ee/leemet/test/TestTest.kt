package ee.leemet.test

import com.googlecode.objectify.ObjectifyService
import com.googlecode.objectify.ObjectifyService.ofy
import com.googlecode.objectify.annotation.Entity
import com.googlecode.objectify.annotation.Id
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import org.junit.jupiter.api.extension.Extensions

@Extensions(
        ExtendWith(LocalDatastoreExtension::class),
        ExtendWith(ObjectifyExtension::class)
)
class TestTest {

    @Test
    fun testTest() {
        ObjectifyService.register(TestEntity::class.java)
        val k = ObjectifyService.run {
            TestEntity(a = "A", b = true).save()
        }
        println("SAVED")
        val e = ObjectifyService.run {
            ofy().load().key(k).now()
        }

        println(e)
    }
}

@Entity(name = "test-entity")
data class TestEntity(
        @Id var id: Long? = null,
        var a: String = "",
        var b: Boolean = false
) {
    fun save() = ofy().save().entity(this).now()
}