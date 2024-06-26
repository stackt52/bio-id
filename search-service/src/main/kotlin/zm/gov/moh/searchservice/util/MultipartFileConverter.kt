package zm.gov.moh.searchservice.util

import org.springframework.core.convert.converter.Converter
import org.springframework.core.io.buffer.DataBufferUtils
import org.springframework.http.codec.multipart.FilePart
import org.springframework.web.multipart.MultipartFile
import java.io.*
import java.nio.channels.Channels
import java.nio.channels.WritableByteChannel


class MultipartFileConverter : Converter<FilePart, MultipartFile> {

    companion object {
        class ByteArrayMultipartFile(private val name: String, private val content: ByteArray) : MultipartFile {
            override fun getName(): String {
                return name
            }

            override fun getOriginalFilename(): String {
                return name
            }

            override fun getContentType(): String? {
                return null
            }

            override fun isEmpty(): Boolean {
                return content.isEmpty()
            }

            override fun getSize(): Long {
                return content.size.toLong()
            }

            override fun getBytes(): ByteArray {
                return content
            }

            override fun getInputStream(): InputStream {
                return ByteArrayInputStream(content)
            }

            override fun transferTo(dest: File) {
                FileOutputStream(dest).use { out ->
                    out.write(content)
                }
            }
        }
    }

    override fun convert(filePart: FilePart): MultipartFile {
        // Create a ByteArrayOutputStream to hold the file data
        val byteArrayOutputStream = ByteArrayOutputStream()
        val writableByteChannel: WritableByteChannel = Channels.newChannel(byteArrayOutputStream)

        filePart.content().subscribe { dataBuffer ->
            try {
                writableByteChannel.write(dataBuffer.readableByteBuffers().next())
            } catch (e: IOException) {
                e.printStackTrace()
            } finally {
                DataBufferUtils.release(dataBuffer)
            }
        }

        val fileBytes = byteArrayOutputStream.toByteArray()


        return ByteArrayMultipartFile(filePart.filename(), fileBytes)
    }
}