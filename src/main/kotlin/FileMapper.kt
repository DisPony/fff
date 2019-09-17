import java.io.File
import java.nio.charset.Charset

class FileMapper(file: File) {

    private val pattern = "((.|\\s)+?)(\\s\\.(.|\\s)*?\\.)(\\s+?\\d+)".toRegex()
    val questionNumber: Int
    private val questionsPart: String

    init {
        val content = file.readText(Charset.forName("windows-1251"))
        questionNumber = content.takeWhile { c: Char -> c.isDigit() }.toInt()
        questionsPart = content.dropWhile { ch -> !ch.isLetter() }
    }

    fun parse() : Sequence<MatchResult> {
        return pattern.findAll(questionsPart).asSequence()
    }

    fun toQuestion(result: MatchResult): Question {
        with(result){
            val question = this.groups[1]?.value?.trim() ?: ""
            val answersBuf = this.groups[3]?.value ?: ""
            val answers = answersBuf.replace(".", "").split(",\\s*?\n".toRegex())
                .map { it.trim() }
                .map { it.replace("\r\n", " ") }
            val correctAnswers =
                this.groups[5]?.value?.trim()?.asSequence()?.map { it.toString().toInt() }?.toList() ?: emptyList()
            return Question(question, answers, correctAnswers)
        }
    }

    fun questionList() : List<Question> {
        return parse().map { toQuestion(it) }.toList()
    }
}
