package models

/**
 * Created by naveen on 10/9/15.
 */
case class Invoice(taxId: String, product: String, salesAmount: BigDecimal, taxCategory: String) {

}

object Invoice{
  var invoices= Set(
  Invoice("ABC","ABC",100.00, "Cat1"),
    Invoice("BCD","BCD",200.00, "Cat1"),
      Invoice("CDE","CDE",300.00,"Cat2")
  )

  def findAll=invoices.toList.sortBy(_.salesAmount)
  def findByTaxId(taxId:String)=invoices.find(_.taxId == taxId)
  def add(invoice: Invoice): Unit ={
    invoices=invoices+invoice
  }

}