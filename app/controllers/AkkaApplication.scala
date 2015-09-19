package controllers

import javax.inject.Inject

import play.api.i18n.{I18nSupport, MessagesApi}
import play.api.mvc._

class AkkaApplication @Inject() (val messagesApi: MessagesApi) extends Controller with I18nSupport{

  def index = Action {implicit request =>
    Ok(views.html.index("Your new application is ready."))
  }

}
