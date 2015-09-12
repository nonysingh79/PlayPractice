package controllers

import models.Invoice
import play.api.data._
import play.api.data.Forms._
import play.api.mvc.{Flash, Action, Controller}
import play.i18n.Messages



/**
 * Created by naveen on 10/9/15.
 */
@javax.inject.Inject
class Invoices extends Controller {

  def list = Action { implicit request =>
    val invoices = Invoice.findAll
    Ok(views.html.listInvoice(invoices))

  }

  def showVAT(taxId: String) = Action { implicit request =>

    val invoice=Invoice.findByTaxId(taxId)
    invoice.map { invoice =>
        Ok(views.html.vat(invoice,invoice.salesAmount*0.2))
    }.getOrElse(NotFound)

  }

  def save  = Action { implicit request =>
  val newInvoiceForm = invoiceForm.bindFromRequest()
    newInvoiceForm.fold(
    hasErrors = { form =>
    Redirect(routes.Invoices.newInvoice()).flashing(Flash(form.data)+
      ("error"-> Messages.get("validation.errors")))
    },
    success={ newInvoice => Invoice.add(newInvoice)
      val message= Messages.get("invoice.new.success", newInvoice.taxId)
      Redirect(routes.Invoices.showVAT(newInvoice.taxId)).
        flashing("success"-> message)

    }
    )


  }

  def newInvoice = Action {implicit request =>
    val form = if (request.flash.get("error").isDefined)
      invoiceForm.bind(request.flash.data)
    else
      invoiceForm
    Ok(views.html.editInvoice(form))

  }

  private val invoiceForm: Form[Invoice]= Form(
  mapping(
  "taxId"->nonEmptyText,
  "product"->nonEmptyText,
  "salesAmount"->bigDecimal(10,2),
  "taxCategory"->nonEmptyText
  )(Invoice.apply)(Invoice.unapply)
  )

}
