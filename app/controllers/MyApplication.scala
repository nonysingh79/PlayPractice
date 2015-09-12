package controllers

import play.api.mvc.{Action, Controller}

/**
 * Created by naveen on 10/9/15.
 */
class MyApplication extends Controller{

  def index = Action { request =>
    Ok("Welcome "+ request)
  }

}
