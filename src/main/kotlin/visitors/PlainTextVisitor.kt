package visitors

import Category
import Question
import Test
import java.io.File
import java.util.concurrent.atomic.AtomicInteger

class PlainTextVisitor(private val file: File) : Visitor {

    override fun close() = Unit

    private val categoryIndex = AtomicInteger(1)
    private val questionIndex = AtomicInteger(1)
    override fun visit(test: Test) {
        file.writeText("Тест\n\n")
//        for (category in test.categories) {
//            visit(category)
//        }
    }

    override fun visit(category: Category) {
        file.appendText("\n ### Вопрос ${categoryIndex.getAndIncrement()} ### \n")
//        for (question in category.questions) {
//            visit(question)
//        }
    }

    override fun visit(question: Question) {
        file.appendText("\n ${questionIndex.getAndIncrement()}) ${question.question}")
        for ((i, answer) in question.answers.withIndex()) {
            file.appendText("\n ${i+1}. $answer")
        }
        file.appendText("\n Ответ: ${question.correctAnswers.joinToString("")}\n")
    }
}