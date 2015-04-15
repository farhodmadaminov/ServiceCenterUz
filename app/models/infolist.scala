package models


import com.sun.org.glassfish.gmbal.Description
import play.api.db.slick.Config.driver.simple._

case class Compyuter(id: Option[Int],
                     mode: String,
                     cpu: String,
                     priceId: Int)

case class CompyuterForDisplay(compyuter: Compyuter,
                            priceMD: String)

case class Price(id: Option[Int],
                  mode: String)


class CompyuterTable(tag: Tag) extends Table[Compyuter](tag, "COMP"){

  val prices = TableQuery[PriceTable]

  def id  = column[Int]("ID", O.PrimaryKey, O.AutoInc)

  def mode = column[String]("MODE", O.Default(""))

  def cpu = column[String]("CPU", O.Default(""))

  def priceId = column[Int]("PRICE_ID", O.NotNull)

  def * = (id.?, mode, cpu,  priceId) <> (Compyuter.tupled, Compyuter.unapply _)

  def price = foreignKey("PRICE_ID", priceId, prices)(_.id)

}
class PriceTable(tag: Tag) extends Table[Price](tag, "PRICE"){

  def id = column[Int]("ID", O.PrimaryKey, O.AutoInc)

  def mode = column[String]("MODE", O.Default(""))

  def * = (id.?, mode) <> (Price.tupled, Price.unapply)

}


