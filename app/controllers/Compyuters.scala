package controllers

import play.api.mvc._
import play.api.db.slick.Config.driver.simple._
import play.api.db.slick._
import models._
import play.api.Logger

import scala.slick.lifted.TableQuery

class Compyuters extends Controller{

  val compyuters = TableQuery[CompyuterTable]

  def list = DBAction{ implicit rs =>
    Logger.info(s"SHOW_ALL = ${compyuters.list}")
    Ok(views.html.list(compyuters.list))
  }


  def showAddForm = Action{
    Ok(views.html.add())
  }

  def add = DBAction { implicit request =>
    val formParams = request.body.asFormUrlEncoded
    val mode = formParams.get("mode")(0)
    val cpu = formParams.get("cpu")(0)
    val mark = formParams.get("mark")(0)

    val compyuterId = (compyuters returning compyuters.map(_.id) ) += Compyuter(None, mode, cpu, mark= 0 )
    Logger.info(s"LastId = $compyuterId")
    Redirect(routes.Compyuters.list())
  }


  def remove(id: Int) = DBAction { implicit request =>
    compyuters.filter(_.id === id).delete;
    Redirect(routes.Compyuters.list())
  }
}
