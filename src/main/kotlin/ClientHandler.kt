import java.io.BufferedReader
import java.io.InputStreamReader
import java.io.PrintWriter
import java.net.Socket

class ClientHandler(private val socket: Socket) {

    suspend fun hanlde() {
        val reader = BufferedReader(InputStreamReader(socket.getInputStream()))
        val writer = PrintWriter(socket.getOutputStream(), true)
        while (true) {
            val line = reader.readLine() ?: break
            writer.write("$line\n")
            writer.flush()
        }
        reader.close()
        writer.close()
        socket.close()
    }
}