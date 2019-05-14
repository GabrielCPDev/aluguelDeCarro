package model.services;

import model.entities.CarRental;
import model.entities.Invoice;

public class RentalService extends model.entities.Invoice{
	private Double pricePerDay;
	private Double pricePerHour;
	
	private BrazilTaxService taxService;
	
	public RentalService(Double pricePerDay, Double pricePerHour, BrazilTaxService taxService) {
		this.pricePerDay = pricePerDay;
		this.pricePerHour = pricePerHour;
		this.taxService = taxService;
	}
	
	public void processInvoice (CarRental carRental) {
		long t1 = carRental.getStart().getTime();
		long t2 = carRental.getFinish().getTime();
		double hours =(double)(t2 - t1)/ 1000 /60/ 60;
		
		if(hours <= 12) {
			double basicPayment = Math.ceil(hours) * pricePerHour;
		}
		else {
			double basicPayment = Math.ceil(hours / 24) * pricePerDay;
		}
		double tax = taxService.tax(getBasicPayment());
		
		carRental.setInvoice(new Invoice(getBasicPayment(),tax));
	}

}
