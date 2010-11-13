package code.model

import net.liftweb.mapper._

object Vote extends Vote with LongKeyedMetaMapper[Vote]  {
  override def dbTableName = "votes"
}

class Vote extends LongKeyedMapper[Vote] with IdPK with CreatedUpdated {
  def getSingleton = Vote

  object statement extends LongMappedMapper(this, Statement)
}
