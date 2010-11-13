package code.model

import net.liftweb.mapper._

object Statement extends Statement with LongKeyedMetaMapper[Statement] {
  override def dbTableName = "statements"

  def findRecent(max: Long = 10) = findAll(OrderBy(Statement.createdAt, Descending), MaxRows(max))
}

class Statement extends LongKeyedMapper[Statement] with IdPK with CreatedUpdated {
  def getSingleton = Statement

  object text extends MappedPoliteString(this, 255)

  def voteCount = Vote.count(By(Vote.statement, this))
}
