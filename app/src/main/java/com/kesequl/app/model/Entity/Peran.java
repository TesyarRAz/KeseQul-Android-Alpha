package com.kesequl.app.model.Entity;

import com.google.gson.annotations.SerializedName;

public class Peran {

	@SerializedName("id_peran")
	private String idPeran;

	@SerializedName("id_level")
	private String idLevel;

	@SerializedName("nama_level")
	private String namaLevel;

	public void setIdPeran(String idPeran){
		this.idPeran = idPeran;
	}

	public String getIdPeran(){
		return idPeran;
	}

	public void setIdLevel(String idLevel){
		this.idLevel = idLevel;
	}

	public String getIdLevel(){
		return idLevel;
	}

	public void setNamaLevel(String namaLevel){
		this.namaLevel = namaLevel;
	}

	public String getNamaLevel(){
		return namaLevel;
	}
}