import org.docx4j.jaxb.Context
import org.docx4j.openpackaging.packages.WordprocessingMLPackage
import org.docx4j.wml.BooleanDefaultTrue
import java.io.File
import java.nio.charset.Charset

fun main1() {
    val directory = "/home/nikita/IdeaProjects/fff/src/main/resources/"

    val files = (1..13).asSequence()
        .map { File("$directory$it.txt") }
        .toList()

    val pattern = "((.|\\s)+?)(\\s\\.(.|\\s)*?\\.)(\\s+?\\d+)".toRegex()


    val test = files
        .map { it.readText(Charset.forName("windows-1251")).dropWhile { ch -> !ch.isLetter() } }
        .mapIndexed { i, content -> Category(getQuestions(pattern, content), i) }


    val realTest = Test(test)

    val allQuestions = realTest.categories.flatMap { it.questions }

    val outputFile = File("/home/nikita/IdeaProjects/fff/src/main/resources/output5.docx")

    val wordPackage = WordprocessingMLPackage.createPackage();
    val mainDocumentPart = wordPackage.getMainDocumentPart();

    val factory = Context.getWmlObjectFactory()
    val bold = factory.createRPr()
    bold.b = BooleanDefaultTrue()


    for ((index, question) in allQuestions.withIndex()) {
        if(question.answers.size != 5)
            println("$index ${question.correctAnswers} ${question.answers.size}")
//        println(question)
    }

    for ((index, question) in allQuestions.withIndex()) {
        mainDocumentPart.p {
            r {
                text {
                    value = "${index + 1}) ${question.question} \n"
                }
            }
            question.answers.forEachIndexed { i, s ->
                r {
                    text {
                        value = "${i + 1}. $s \n"
                    }
                    if (question.correctAnswers.contains(i + 1)) rPr = bold
                }
            }
        }
    }

    wordPackage.save(outputFile)

}

private fun getQuestions(pattern: Regex, content: String): List<Question> {
    return pattern.findAll(content).asSequence().map {
        val question = it.groups[1]?.value?.trim() ?: ""
        val answersBuf = it.groups[3]?.value ?: ""
        val answers = answersBuf.replace(".", "").split(",\\s*?\n".toRegex())
            .map { it.trim() }
            .map { it.replace("\r\n", " ") }
        val correctAnswers =
            it.groups[5]?.value?.trim()?.asSequence()?.map { it.toString().toInt() }?.toList() ?: emptyList()
        Question(question, answers, correctAnswers)
    }.toList()
}

fun toQuestion(matchResult : MatchResult) : Question {
    with(matchResult){
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