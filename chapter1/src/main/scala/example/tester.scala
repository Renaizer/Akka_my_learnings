package example

object  tester  extends App {

//  val altitude  = 0.0 + ((1717660575158f - 1717660574854f)/60000) * 5000.0
  val altitude = ((1717660575158d - 1717660574854d)/60000)*5000.0
  println(s"$altitude")
}
