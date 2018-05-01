package com.admin.invoice.vo;

public class InvoiceSearchCriteria{
	
	private String messenger;
	
	private String status;
	
	private String invoicedatefrom;
	
	private String invoicedateto;

	public String getMessenger() {
		return messenger;
	}

	public void setMessenger(String messenger) {
		this.messenger = messenger;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getInvoicedatefrom() {
		return invoicedatefrom;
	}

	public void setInvoicedatefrom(String invoicedatefrom) {
		this.invoicedatefrom = invoicedatefrom;
	}

	public String getInvoicedateto() {
		return invoicedateto;
	}

	public void setInvoicedateto(String invoicedateto) {
		this.invoicedateto = invoicedateto;
	}

	
	
}
