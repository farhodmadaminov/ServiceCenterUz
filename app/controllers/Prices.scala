package controllers

import models._
import play.api.Logger
import play.api.db.slick.Config.driver.simple._
import play.api.db.slick._
import play.api.mvc._
import play.api.libs.json.Json._
import play.api.libs.json.Json

import scala.slick.lifted.TableQuery

class Prices extends Controller {

  val prices = TableQuery[PriceTable]

  def list = DBAction { implicit rs =>
    Ok(views.html.listPrices(prices.list))
  }

  def showAddForm = DBAction { implicit rs =>
    Ok(views.html.addPrice())
  }


  def add = DBAction { implicit request =>
    val formParams = request.body.asFormUrlEncoded
    val mode = formParams.get("mode")(0)
    val compyuterId = (prices returning prices.map(_.id)) += Price(None, mode)
    Logger.info(s"LastId = $compyuterId")
    Redirect(routes.Prices.list())
  }


  def remove(id: Int) = DBAction { implicit request =>
    prices.filter(_.id === id).delete;
    Redirect(routes.Prices.list())
  }

  def update(id: Int) = DBAction { implicit rs =>
    val formParams = rs.body.asFormUrlEncoded
    val modern = formParams.get("modern")(0)

    val price = Price(Some(id), modern)
    prices.filter(_.id === id).update(price)

    Redirect(routes.Prices.list())
  }

  def showEditForm(priceId: Int) = DBAction { implicit request =>
    val byId = prices.findBy(_.id)
    val price = byId(priceId).list.head

    Ok(views.html.editPrice(price))
  }

}