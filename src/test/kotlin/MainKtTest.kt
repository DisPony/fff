import org.junit.jupiter.api.Assertions.assertEquals
import java.io.File

internal class MainKtTest {

    @org.junit.jupiter.api.Test
    fun questionNumber() {
        val fileMapper = FileMapper(File("/home/nikita/IdeaProjects/fff/src/test/resources/1.txt"))
        assertEquals(fileMapper.questionNumber, 16)
    }

    @org.junit.jupiter.api.Test
    fun mapToQuestion(){
        val fileMapper = FileMapper(File("/home/nikita/IdeaProjects/fff/src/test/resources/1.txt"))

        val expected = Question(
            question = "К полноценным антигенам относятся:",
            answers = listOf(
                "белки",
                "липиды",
                "бактериальные токсины",
                "углеводы",
                "аминокислоты"
            ),
            correctAnswers = listOf(1, 3)
        )
        val obtained = fileMapper.parse().take(1).map { fileMapper.toQuestion(it) }.first()
        assertEquals(expected, obtained)
    }

    @org.junit.jupiter.api.Test
    fun parse(){
        val fileMapper = FileMapper(File("/home/nikita/IdeaProjects/fff/src/test/resources/1.txt"))
        val expected = 16
        val actual = fileMapper.parse().count()
        assertEquals(expected, actual)
    }
}