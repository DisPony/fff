import com.github.ajalt.clikt.core.CliktCommand
import com.github.ajalt.clikt.output.CliktConsole
import com.github.ajalt.clikt.parameters.options.option
import com.github.ajalt.clikt.parameters.options.required
import com.github.ajalt.clikt.parameters.options.validate
import com.github.ajalt.clikt.parameters.types.file
import visitors.WordVisitor
import java.io.File



class Cli : CliktCommand() {
    private val directory by option().file()
        .required()
        .validate {
            require(it.isDirectory) {
                "Given path isn't a directory"
            }
        }

    private val extension by option()
        .required()



    override fun run() {

        val files = directory.listFiles { file -> file.extension.toLowerCase() == extension.toLowerCase() }
            ?.filterNot { it.name.substringBeforeLast(".") == "0" }
            ?.also { println(it) }
            ?.sortedBy { it.name.substringBeforeLast(".").toIntOrNull() }
            ?.also { println(it) }
            ?.map { FileMapper(it) } ?: error("Can't obtain files from given path")

        if (files.isEmpty()) error("No files with given extension")
        val test = files
            .mapIndexed { i, file -> Category(file.questionList(), i) }
            .let { Test(it) }
        val output = File("C:\\Users\\nikit\\Desktop\\test\\new.docx")
        val exporter = WordVisitor(output)

        exporter.use {
            for (item in test){
                item.accept(it)
            }
        }
    }
}

fun main() = Cli().main(
    listOf(
        "--directory", "C:\\Users\\nikit\\Desktop\\test",
        "--extension", "txt"
    )
)