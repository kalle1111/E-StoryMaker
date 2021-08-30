package com.eStory.route


import com.eStory.table.FileEntity
import io.ktor.application.*
import io.ktor.auth.*
import io.ktor.http.content.*
import io.ktor.locations.*
import io.ktor.request.*
import io.ktor.response.*
import io.ktor.routing.*
import org.jetbrains.exposed.sql.transactions.transaction
import java.io.File

@Location(UPLOAD_FILE_POST)
class UploadFile

@Location(DOWNLOAD_FILE_POST)
class DownloadFile

@OptIn(KtorExperimentalLocationsAPI::class)
fun Route.uploadRoutes() {
    var fileDescription = ""
    var fileName = ""
    // authenticate("jwt") {
    post<UploadFile> {
        val multipartData = call.receiveMultipart()

        multipartData.forEachPart { part ->
            when (part) {
                is PartData.FormItem -> {
                    fileDescription = part.value
                }
                is PartData.FileItem -> {
                    fileName = part.originalFileName as String
                    var fileBytes = part.streamProvider().readBytes()
                    File("uploads/$fileName").writeBytes(fileBytes)

                    transaction {
                        FileEntity.new {
                            this.binaryFile = fileBytes
                        }
                    }
                }
            }
        }

        call.respondText("$fileDescription is uploaded to 'uploads/$fileName'")
    }


    get<DownloadFile> {
        val arrabyte = transaction { FileEntity.all().first().binaryFile }

        call.respondBytes(arrabyte)
    }
    // }
}
