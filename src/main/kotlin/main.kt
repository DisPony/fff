import visitors.PlainTextVisitor
import org.docx4j.jaxb.Context
import java.io.File
import org.docx4j.openpackaging.parts.WordprocessingML.MainDocumentPart
import org.docx4j.wml.*
import visitors.PrintVisitor

fun main(){
    val directory = "C:\\Users\\nikit\\IdeaProjects\\fff\\src\\main\\resources\\"
    val files = (1..22).asSequence()
        .map { FileMapper(File("$directory$it.BH3")) }
        .toList()

    val test = files
        .mapIndexed { i, file -> Category(file.questionList(), i) }
        .let { Test(it) }

    val exporter = PrintVisitor()
//    val exporter = PlainTextVisitor(File("/home/nikita/IdeaProjects/fff/src/main/resources/test.test"))
    exporter.use {
        for (item in test){
            item.accept(it)
        }
    }

}

fun MainDocumentPart.p(f: P.() -> Unit) {
    val p = Context.getWmlObjectFactory().createP()
    p.apply(f)
    content.add(p)
}

fun P.r(f: R.() -> Unit) {
    val r = Context.getWmlObjectFactory().createR()
    r.apply(f)
    content.add(r)
}

fun R.text(f: Text.() -> Unit) {
    val text = Context.getWmlObjectFactory().createText()
    text.apply(f)
    this.content.add(text)
}

