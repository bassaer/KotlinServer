import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import java.io.BufferedReader
import java.io.IOException
import java.io.InputStreamReader
import java.io.PrintWriter
import java.net.ServerSocket
import java.net.Socket


fun handle(socket: Socket): Boolean {
    lateinit var reader: BufferedReader
    lateinit var writer: PrintWriter
    try {
        reader = BufferedReader(InputStreamReader(socket.getInputStream()))
        writer = PrintWriter(socket.getOutputStream(), true)

        while (true) {
            val line = reader.readLine() ?: break
            if (line.isBlank()) {
                break
            }
            println(line)
            writer.write("$line\n")
            writer.flush()
        }

    } catch (e: Exception) {
        println(e.localizedMessage)
        return false
    } finally {
        reader.close()
        writer.close()
        socket.close()
    }
    return true
}

fun main() {
    val serverSocket = ServerSocket(8080)
    var running = true
    while (running) {
        runBlocking {
            running = handle(serverSocket.accept())
        }
    }
}