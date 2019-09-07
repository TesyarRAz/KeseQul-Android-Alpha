package com.kesequl.app.model.Entity;

import com.google.gson.annotations.SerializedName;
import com.kesequl.app.R;

public class TU implements DaoData{

	@SerializedName("keterangan")
	private Object keterangan;

	@SerializedName("id_tu")
	private String idTu;

	@SerializedName("nip")
	private String nip;

	@SerializedName("nama")
	private String nama;

	@SerializedName("gender")
	private String gender;

	@SerializedName("id_user")
	private String idUser;

	@SerializedName("tanggal_lahir")
	private String tanggalLahir;

	public void setKeterangan(Object keterangan){
		this.keterangan = keterangan;
	}

	public Object getKeterangan(){
		return keterangan;
	}

	public void setIdTu(String idTu){
		this.idTu = idTu;
	}

	public String getIdTu(){
		return idTu;
	}

	public void setNip(String nip){
		this.nip = nip;
	}

	public String getNip(){
		return nip;
	}

	public void setNama(String nama){
		this.nama = nama;
	}

	public String getNama(){
		return nama;
	}

	@Override
	public int getResIdText() {
		return R.array.aksi_tu_text;
	}

	@Override
	public int getResIdImage() {
		return R.array.aksi_tu_image;
	}

	public void setGender(String gender){
		this.gender = gender;
	}

	public String getGender(){
		return gender;
	}

	public void setIdUser(String idUser){
		this.idUser = idUser;
	}

	public String getIdUser(){
		return idUser;
	}

	public void setTanggalLahir(String tanggalLahir){
		this.tanggalLahir = tanggalLahir;
	}

	public String getTanggalLahir(){
		return tanggalLahir;
	}
}