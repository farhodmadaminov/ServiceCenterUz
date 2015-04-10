package models



import play.api.db.slick.Config.driver.simple._
case class Compyuter(id: Option[Int],
                     mode: String,
                     cpu: String,
                     mark: Double)



class CompyuterTable(tag: Tag) extends Table[Compyuter](tag, "COMP"){

  def id  = column[Int]("ID", O.PrimaryKey, O.AutoInc)

  def mode = column[String]("MODE", O.Default(""))

  def cpu = column[String]("CPU", O.Default(""))

  def mark = column[Double]("MARK", O.Default(0))

  def * = (id.?, mode, cpu, mark) <> (Compyuter.tupled, Compyuter.unapply _)

}


