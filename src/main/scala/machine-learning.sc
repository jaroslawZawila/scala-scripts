//import $ivy.`org.apache.opennlp:opennlp-tools:1.6.0`
import $ivy.`org.apache.opennlp:opennlp-tools:1.9.0`

import opennlp.tools.tokenize._
import java.io.ByteArrayInputStream
import opennlp.tools.namefind._
import scala.collection.JavaConverters._

val tokenDataUrl = "http://opennlp.sourceforge.net/models-1.5/en-token.bin"

val tokenData = scalaj.http.Http(tokenDataUrl).asBytes

val str = "Hi. How are you? This is Mike. Did you see book about Peter Smith? Do you know anything about Adam?"

val tokenModel = new TokenizerModel(new ByteArrayInputStream(tokenData.body))

val tokenizer = new TokenizerME(tokenModel)

val tokens = tokenizer.tokenize(str)

val nameDataUrl = "http://opennlp.sourceforge.net/models-1.5/en-ner-person.bin"

val nameData = scalaj.http.Http(nameDataUrl).timeout(50000,50000).asBytes

val nameModel = new TokenNameFinderModel(new ByteArrayInputStream(nameData.body))

val nameFinder = new NameFinderME(nameModel)

val names = nameFinder.find(tokens)

val tokenss: Array[String] = opennlp.tools.util.Span.spansToStrings(names, tokens)

tokenss.toList.foreach(println(_))




