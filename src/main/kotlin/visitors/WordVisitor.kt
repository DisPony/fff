package visitors

import Category
import Question
import Test
import org.docx4j.jaxb.Context
import org.docx4j.openpackaging.packages.WordprocessingMLPackage
import org.docx4j.wml.BooleanDefaultTrue
import org.docx4j.wml.RPr
import p
import r
import text
import java.io.File
import java.util.concurrent.atomic.AtomicInteger

class WordVisitor(val output: File) : Visitor {

    private val wordPackage = WordprocessingMLPackage.createPackage();
    private val mainDocumentPart = wordPackage.getMainDocumentPart();
    private val bold : RPr
    private var index: AtomicInteger

    init {
        val factory = Context.getWmlObjectFactory()
        bold = factory.createRPr()
        bold.b = BooleanDefaultTrue()
        index = AtomicInteger(0)
    }


    override fun visit(test: Test) {
//        test.categories.forEach {
//            visit(it)
//        }
//        wordPackage.save(output)
    }

    override fun visit(category: Category) {
//        category.questions.forEach {
//            visit(it)
//        }
    }

    override fun visit(question: Question) {
        mainDocumentPart.p {
            r {
                text {
                    value = "${index.getAndIncrement() + 1}) ${question.question.replace("\r\n", " ")} \n"
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

    override fun close() {
        wordPackage.save(output)
    }
}