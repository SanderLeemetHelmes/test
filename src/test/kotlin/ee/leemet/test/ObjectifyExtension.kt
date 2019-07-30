package ee.leemet.test

import com.googlecode.objectify.ObjectifyFactory
import com.googlecode.objectify.ObjectifyService
import com.googlecode.objectify.util.Closeable
import org.junit.jupiter.api.extension.AfterEachCallback
import org.junit.jupiter.api.extension.BeforeEachCallback
import org.junit.jupiter.api.extension.ExtensionContext

class ObjectifyExtension : BeforeEachCallback, AfterEachCallback {

    companion object {
        private val NAMESPACE = ExtensionContext.Namespace.create(ObjectifyExtension::class.java)
    }

    override fun beforeEach(context: ExtensionContext) {
        val datastore = LocalDatastoreExtension.getHelper(context).options.service

        ObjectifyService.init(ObjectifyFactory(datastore))

        val rootService = ObjectifyService.begin()

        context.getStore(NAMESPACE).put(Closeable::class.java, rootService)
    }

    override fun afterEach(context: ExtensionContext) {
        val rootService = context.getStore(NAMESPACE).get(Closeable::class.java, Closeable::class.java)
        rootService.close()
    }

}