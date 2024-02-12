import java.nio.file.Files
import java.nio.file.Path
import java.nio.file.Paths

object CheckPermissions {
    @JvmStatic
    fun main(args: Array<String>) {
        val path: Path = Paths.get("model/my_model")
        val isDirectory = Files.isDirectory(path)
        val isWritable = Files.isWritable(path)
        val isReadable = Files.isReadable(path)

        println("Is directory?: $isDirectory")
        println("Is writable?: $isWritable")
        println("Is readable?: $isReadable")
    }
}