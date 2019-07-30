package ee.leemet.test

import com.google.cloud.datastore.testing.LocalDatastoreHelper
import org.junit.jupiter.api.extension.BeforeAllCallback
import org.junit.jupiter.api.extension.BeforeEachCallback
import org.junit.jupiter.api.extension.ExtensionContext

class LocalDatastoreExtension(private val consistency: Double = 1.0) : BeforeAllCallback, BeforeEachCallback {
    override fun beforeAll(context: ExtensionContext) {
        if (getHelper(context) == null) {
            println("Creating new LocalDatastoreHelper")
            val helper = LocalDatastoreHelper.create(consistency)
            context.root.getStore(ExtensionContext.Namespace.GLOBAL).put(LocalDatastoreHelper::class.java, helper)
            helper.start()
        }
    }

    override fun beforeEach(context: ExtensionContext) {
        val helper = getHelper(context)
        helper.reset()
    }

    companion object {
        fun getHelper(context: ExtensionContext) =
                context.root.getStore(ExtensionContext.Namespace.GLOBAL).get(LocalDatastoreHelper::class.java, LocalDatastoreHelper::class.java)
    }

}