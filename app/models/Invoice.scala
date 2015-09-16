package models

import org.squeryl.PrimitiveTypeMode._
import org.squeryl.{KeyedEntity, Schema}
import play.api.Logger

/**
 * Created by naveen on 10/9/15.
 */
case class Invoice(taxId: String, product: String, salesAmount: BigDecimal, taxCategory: String) extends KeyedEntity[Long] {
  override def id: Long = 0
}

object Invoice{


  var invoices= Set(
  Invoice("ABC","ABC",100.00, "Cat1"),
    Invoice("BCD","BCD",200.00, "Cat1"),
      Invoice("CDE","CDE",300.00,"Cat2")
  )

  def allInvoices=from(AppDB.invoiceTable) {
    invoice => select(invoice) orderBy (invoice.taxId desc)
  }

  def findAllInvoicesFromDb= inTransaction {
   allInvoices.toList
  }


  def findAll=invoices.toList.sortBy(_.salesAmount)

  def findByTaxId(taxId:String)=
    {

     val inv:Option[Invoice]=findByTaxIdDB(taxId)
      Logger.info(inv.toString)
      inv
    }

  def findByTaxIdDB(taxId:String)={
    inTransaction(from(AppDB.invoiceTable)(
    invoice => where(invoice.taxId===taxId) select(invoice)).headOption
  )}

  def add(invoice: Invoice): Unit ={
    invoices=invoices+invoice
  }

  def insert(invoice: Invoice): Invoice = {
    inTransaction(

      AppDB.invoiceTable insert invoice)


  }
}

object AppDB extends Schema {
  val invoiceTable = table[Invoice]("invoice")
}