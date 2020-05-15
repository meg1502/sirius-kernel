/*
 * Made with all the love in the world
 * by scireum in Remshalden, Germany
 *
 * Copyright by scireum GmbH
 * http://www.scireum.de - info@scireum.de
 */
package sirius.kernel.commons

import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import sirius.kernel.SiriusLifecycleExtension
import java.io.ByteArrayInputStream
import java.io.InputStreamReader
import kotlin.test.assertEquals

@ExtendWith(SiriusLifecycleExtension::class)
class BOMReaderTest {

    companion object {
        private val WITH_UTF8_BOM = byteArrayOf(239.toByte(), 187.toByte(), 191.toByte(), 'H'.toByte(), 'E'.toByte(), 'L'.toByte(), 'L'.toByte(), 'O'.toByte())
        private val WITHOUT_BOM = byteArrayOf('H'.toByte(), 'E'.toByte(), 'L'.toByte(), 'L'.toByte(), 'O'.toByte())
    }

    @Test
    fun `read BOM`() {
        val reader = BOMReader(InputStreamReader(ByteArrayInputStream(WITH_UTF8_BOM)))
        assertEquals('H'.toLong(), reader.read().toLong())
        assertEquals('E'.toLong(), reader.read().toLong())
    }

    @Test
    fun `read without BOM`() {
        val reader = BOMReader(InputStreamReader(ByteArrayInputStream(WITHOUT_BOM)))
        assertEquals('H'.toLong(), reader.read().toLong())
        assertEquals('E'.toLong(), reader.read().toLong())
    }

    @Test
    fun `read Array1 BOM`() {
        val reader = BOMReader(InputStreamReader(ByteArrayInputStream(WITH_UTF8_BOM)))
        val buffer = CharArray(1)
        assertEquals(1, reader.read(buffer).toLong())
        assertEquals('H'.toLong(), buffer[0].toLong())
    }

    @Test
    fun `read Array2 BOM`() {
        val reader = BOMReader(InputStreamReader(ByteArrayInputStream(WITH_UTF8_BOM)))
        val buffer = CharArray(2)
        assertEquals(2, reader.read(buffer).toLong())
        assertEquals('H'.toLong(), buffer[0].toLong())
    }

    @Test
    fun `read Array10 BOM`() {
        val reader = BOMReader(InputStreamReader(ByteArrayInputStream(WITH_UTF8_BOM)))
        val buffer = CharArray(10)
        assertEquals(5, reader.read(buffer).toLong())
        assertEquals('H'.toLong(), buffer[0].toLong())
    }

    @Test
    fun `read Array without BOM`() {
        val reader = BOMReader(InputStreamReader(ByteArrayInputStream(WITHOUT_BOM)))
        val buffer = CharArray(2)
        assertEquals(2, reader.read(buffer).toLong())
        assertEquals('H'.toLong(), buffer[0].toLong())
    }
}
