package controllers

import play.api.mvc._
import play.api.db.slick.Config.driver.simple._
import play.api.db.slick._
import models._
import play.api.Logger
import play.api.libs.json.Json._
import play.api.libs.json.Json
/**
 * Created by UserPC on 14.04.2015.
 */
class Compyuters extends  Controller {

  val compyuters = TableQuery[CompyuterTable]
  val prices = TableQuery[PriceTable]

  def list = DBAction { implicit rs =>
    //    Logger.info(s"SHOW_ALL = ${cities.list}")
    val compyuterResult = (for {
      (compyuter, price) <- compyuters leftJoin prices on (_.priceId === _.id)
    } yield (compyuter, price.mode)).list
      .map { case (compyuter, priceMD) => CompyuterForDisplay(compyuter, priceMD)}

    Ok(views.html.list(compyuterResult))
  }

  def showAddForm = DBAction { implicit rs =>
    Ok(views.html.add(prices.list))
  }

  def add = DBAction { implicit request =>
    val formParams = request.body.asFormUrlEncoded
    val mode = formParams.get("mode")(0)
    val cpu = formParams.get("cpu")(0)
    val priceId = formParams.get("mode")(0).toInt

    val compyuterId = (compyuters returning compyuters.map(_.id)) += Compyuter(None, mode, cpu, priceId)
    Logger.info(s"LastId = $compyuterId")
    Redirect(routes.Compyuters.list())
  }


  def remove(id: Int) = DBAction { implicit request =>
    compyuters.filter(_.id === id).delete;
    Redirect(routes.Compyuters.list())
  }

  def update(id: Int) = DBAction { implicit rs =>
    val formParams = rs.body.asFormUrlEncoded
    val mode = formParams.get("mode")(0)
    val cpu = formParams.get("cpu")(0)
    val priceId = formParams.get("mode")(0).toInt

    val compyuter = Compyuter(Some(id), mode, cpu, priceId)
    compyuters.filter(_.id === id).update(compyuter)

    Redirect(routes.Compyuters.list())
  }

  def showEditForm(compyuterId: Int) = DBAction { implicit request =>
    val byId = compyuters.findBy(_.id)
    val compyuter = byId(compyuterId).list.head

    Ok(views.html.edit(compyuter, prices.list))
  }

}

