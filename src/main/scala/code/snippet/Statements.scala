package code.snippet

import scala.xml._
import net.liftweb.util.Helpers._
import net.liftweb.http.SHtml._
import net.liftweb.common._
import net.liftweb.http.js.JsCmds._
import code.model._
import code.comet._

class Statements extends Logger {
  def render(xhtml: NodeSeq): NodeSeq = Statement findRecent 10 flatMap { statement => 
    bind("statement", xhtml, 
	 "text" -> statement.text.is, 
	 "voteCount" -> VoteCountClient(statement), 
	 "vote" -> a(Text("Vote")) { VoteServer ! statement; Noop })
  }
}
