package visitors

import Category
import Question
import Test
import java.util.logging.Logger

class DebuggingVisitor(private val visitor: Visitor) : Visitor by visitor {
    private val logger = Logger.getLogger("Export logger")

    override fun visit(test: Test) {
        logger.info {
            "Exporting test. It has ${test.categories.size} categories"
        }
        visitor.visit(test)
    }

    override fun visit(category: Category) {
        logger.info {
            "Exporting category number ${category.num} with ${category.questions.size} questions"
        }
        visitor.visit(category)
    }

    override fun visit(question: Question) {
        logger.info {
            "Exporting question. ${question.answers.size} answers and "
        }
        visitor.visit(question)
    }

}