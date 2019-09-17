import visitors.Node
import visitors.Visitor

data class Question(
    val question: String,
    val answers: List<String>,
    val correctAnswers: List<Int>
) : Node {
    override fun accept(v: Visitor) = v.visit(this)
    }

/*
@param num: positional number of category, correspond to filename
 */
data class Category(val questions: List<Question>, val num: Int) : Node {
    override fun accept(v: Visitor) = v.visit(this)
}

data class Test(val categories: List<Category>) : Node, Iterable<Node> {
    override fun accept(v: Visitor) = v.visit(this)

    override operator fun iterator(): Iterator<Node> = iterator {
        yield(this@Test)
        for (category in categories){
            yield(category)
            yieldAll(category.questions)
        }
    }

}