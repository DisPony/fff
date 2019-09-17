package visitors

import Test
import Category
import Question

class PrintVisitor : Visitor {
    override fun visit(test: Test) {
        println("НАЧАЛО ТЕСТА")
    }

    override fun visit(category: Category) {
        println("НАЧАЛО КАТЕГОРИИ")
    }

    override fun visit(question: Question) {
        println("ВОПРОС")
    }

    override fun close() = Unit
}