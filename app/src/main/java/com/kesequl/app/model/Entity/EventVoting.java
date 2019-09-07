package com.kesequl.app.model.Entity;

import com.google.gson.annotations.SerializedName;

public class EventVoting{

	@SerializedName("password")
	private String password;

	@SerializedName("keterangan")
	private Object keterangan;

	@SerializedName("nama")
	private String nama;

	@SerializedName("tanggal_selesai")
	private String tanggalSelesai;

	@SerializedName("id_pembuat")
	private String idPembuat;

	@SerializedName("tanggal_mulai")
	private String tanggalMulai;

	@SerializedName("id_event_voting")
	private String idEventVoting;

	@SerializedName("status")
	private String status;

	public void setPassword(String password){
		this.password = password;
	}

	public String getPassword(){
		return password;
	}

	public void setKeterangan(Object keterangan){
		this.keterangan = keterangan;
	}

	public Object getKeterangan(){
		return keterangan;
	}

	public void setNama(String nama){
		this.nama = nama;
	}

	public String getNama(){
		return nama;
	}

	public void setTanggalSelesai(String tanggalSelesai){
		this.tanggalSelesai = tanggalSelesai;
	}

	public String getTanggalSelesai(){
		return tanggalSelesai;
	}

	public void setIdPembuat(String idPembuat){
		this.idPembuat = idPembuat;
	}

	public String getIdPembuat(){
		return idPembuat;
	}

	public void setTanggalMulai(String tanggalMulai){
		this.tanggalMulai = tanggalMulai;
	}

	public String getTanggalMulai(){
		return tanggalMulai;
	}

	public void setIdEventVoting(String idEventVoting){
		this.idEventVoting = idEventVoting;
	}

	public String getIdEventVoting(){
		return idEventVoting;
	}

	public void setStatus(String status){
		this.status = status;
	}

	public String getStatus(){
		return status;
	}
}