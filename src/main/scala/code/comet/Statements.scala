package code.comet

import net.liftweb.actor._
import net.liftweb.http._
import net.liftweb.common._
import net.liftweb.util.Helpers._
import code.model._
import scala.xml._

object VoteServer extends LiftActor with ListenerManager {
  var vote: Vote = _
  def createUpdate = vote
  override def lowPriority = {
    case s: Statement =>
      vote = Vote.create.statement(s).saveMe
      updateListeners()
  }
}

class VoteCountClient extends CometActor with CometListener {
  def registerWith = VoteServer
  override def lifespan = Full(5 minutes)

  def id: Long = name map { _.toLong } openOr (throw new NullPointerException("name attribute was not specified"))
  //TODO should this just be a def instead of a lazy val?
  private lazy val statement: Statement = Statement find id openOr (throw new NullPointerException("Statement " + id + " + does not exist"))
  def render = Text(statement.voteCount.toString)

  override def lowPriority = {
    //we receive every new Vote, so only re-render if it's for our statement
    case v: Vote if (v.statement == statement) => reRender(false)
  }
}

object VoteCountClient {
  /** Returns xhtml that renders a comet-updated count of the specified statement's votes. */
  def apply(s: Statement) = <lift:comet type="VoteCountClient" name={s.id.is.toString}>-1</lift:comet>
}
