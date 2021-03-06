package debop4s.core.json

import debop4s.core.AbstractValueObject
import debop4s.core.utils.{ToStringHelper, Hashs}
import scala.beans.BeanProperty

/**
 * 객체를 JSON 직렬화를 수행하여, 그 결과를 저장하려고 할 때 사용한다.
 *
 * @author 배성혁 sunghyouk.bae@gmail.com
 * @since 2014. 2. 24. 오후 4:54
 */
@SerialVersionUID(2934074553940326254L)
class JsonTextObject(@BeanProperty val className: String,
                     @BeanProperty val jsonText: String) extends AbstractValueObject {

  override def hashCode(): Int = Hashs.compute(className, jsonText)

  override protected def buildStringHelper: ToStringHelper =
    super.buildStringHelper
      .add("className", className)
      .add("jsonText", jsonText)
}

object JsonTextObject {

  lazy val serializer = JacksonSerializer()

  val Empty = apply()

  def apply(): JsonTextObject = new JsonTextObject(null, null)

  def apply(graph: Any): JsonTextObject = {
    graph match {
      case null => Empty
      case src: JsonTextObject => apply(src)
      case _ => new JsonTextObject(graph.getClass.getName, serializer.serializeToText(graph))
    }
  }

  def apply(className: String, jsonText: String): JsonTextObject =
    new JsonTextObject(className, jsonText)

  def apply(src: JsonTextObject): JsonTextObject = {
    require(src != null)
    new JsonTextObject(src.className, src.jsonText)
  }
}
