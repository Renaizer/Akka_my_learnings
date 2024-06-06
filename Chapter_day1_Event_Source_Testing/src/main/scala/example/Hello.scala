package example

object Hello extends Greeting with App {

  case class Emp(name:String, id:String, tech:String)

  val emp1 = Emp("hamid", "1", "scala1")
  val emp2 = Emp("javid", "1", "scala2")
  val emp3 = Emp("sharu", "1", "scala3")


  def empMatcher(emp:Emp):(String,String,String) = {
    emp match {
      case a @ Emp(_,_,"scala1") => (a.id, a.name, a.tech)
      case a : Emp if a.name == "hamid" => (a.id, a.name, a.tech)
      case a @ Emp(_,_,_) => (a.id, a.name, a.tech)
    }
  }


//  println(empMatcher(emp1))
  println(empMatcher(emp2))
//  println(empMatcher(emp3))

}

trait Greeting {
  lazy val greeting: String = "hello"
}
