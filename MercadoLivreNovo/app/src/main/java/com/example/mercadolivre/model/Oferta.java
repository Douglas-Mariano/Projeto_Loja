package com.example.mercadolivre.model;

import java.util.ArrayList;

public class Oferta {
	private String title, codOferta, thumbnailUrl;
	private int quant;
	private double price;
	private ArrayList<String> condition;

	public Oferta() {
	}

	public Oferta(String name,String codOferta, String thumbnailUrl, int quant, double price,
				  ArrayList<String> condition) {
		this.title = name;
		this.codOferta = codOferta;
		this.thumbnailUrl = thumbnailUrl;
		this.quant = quant;
		this.price = price;
		this.condition = condition;
		this.codOferta = codOferta;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String name) {
		this.title = name;
	}

	public void setCodOferta(String codOferta) { this.codOferta = codOferta; }

	public String getThumbnailUrl() {
		return thumbnailUrl;
	}

	public void setThumbnailUrl(String thumbnailUrl) {
		this.thumbnailUrl = thumbnailUrl;
	}

	public int getQuant() {
		return quant;
	}

	public void setQuant(int quant) {
		this.quant = quant;
	}

	public double getPrice() {
		return price;
	}

	public void setPrice(double price) {
		this.price = price;
	}

	public ArrayList<String> getCondition() {
		return condition;
	}

	public void setCondition(ArrayList<String> condition) {
		this.condition = condition;
	}

	public String getCodOferta() { return codOferta; }

	public void setCodOferta(double CodOferta) { this.codOferta = codOferta;}
}
