package com.kesequl.app.model.Entity;

import com.google.gson.annotations.SerializedName;

public class Spp{

	@SerializedName("keterangan")
	private Object keterangan;

	@SerializedName("id_spp")
	private String idSpp;

	@SerializedName("waktu")
	private String waktu;

	@SerializedName("bayar")
	private String bayar;

	@SerializedName("bulan")
	private String bulan;

	@SerializedName("id_siswa")
	private String idSiswa;

	public void setKeterangan(Object keterangan){
		this.keterangan = keterangan;
	}

	public Object getKeterangan(){
		return keterangan;
	}

	public void setIdSpp(String idSpp){
		this.idSpp = idSpp;
	}

	public String getIdSpp(){
		return idSpp;
	}

	public void setWaktu(String waktu){
		this.waktu = waktu;
	}

	public String getWaktu(){
		return waktu;
	}

	public void setBayar(String bayar){
		this.bayar = bayar;
	}

	public String getBayar(){
		return bayar;
	}

	public void setBulan(String bulan){
		this.bulan = bulan;
	}

	public String getBulan(){
		return bulan;
	}

	public void setIdSiswa(String idSiswa){
		this.idSiswa = idSiswa;
	}

	public String getIdSiswa(){
		return idSiswa;
	}
}