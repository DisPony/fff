package visitors

import Category
import Question
import Test
import java.io.Closeable
import kotlin.random.Random

interface Visitor : Closeable {
    fun visit(test: Test)
    fun visit(category: Category)
    fun visit(question: Question)
}

interface Node {
    fun accept(v: Visitor)
}