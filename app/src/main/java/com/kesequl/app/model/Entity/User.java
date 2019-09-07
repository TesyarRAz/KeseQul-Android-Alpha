package com.kesequl.app.model.Entity;

import java.util.List;
import com.google.gson.annotations.SerializedName;

public class User {

	@SerializedName("keterangan")
	private Object keterangan;

	@SerializedName("reason_ban")
	private Object reasonBan;

	@SerializedName("peran")
	private List<Peran> peran;

	@SerializedName("uang")
	private int uang;

	@SerializedName("id_user")
	private String idUser;

	@SerializedName("email")
	private String email;

	@SerializedName("aktif")
	private String aktif;

	@SerializedName("username")
	private String username;

	private String password;

	@SerializedName("token")
	private String token;

	public void setKeterangan(Object keterangan){
		this.keterangan = keterangan;
	}

	public Object getKeterangan(){
		return keterangan;
	}

	public void setReasonBan(Object reasonBan){
		this.reasonBan = reasonBan;
	}

	public Object getReasonBan(){
		return reasonBan;
	}

	public void setPeran(List<Peran> peran){
		this.peran = peran;
	}

	public List<Peran> getPeran(){
		return peran;
	}

	public void setUang(int uang){
		this.uang = uang;
	}

	public int getUang(){
		return uang;
	}

	public void setIdUser(String idUser){
		this.idUser = idUser;
	}

	public String getIdUser(){
		return idUser;
	}

	public void setEmail(String email){
		this.email = email;
	}

	public String getEmail(){
		return email;
	}

	public void setAktif(String aktif){
		this.aktif = aktif;
	}

	public String getAktif(){
		return aktif;
	}

	public void setUsername(String username){
		this.username = username;
	}

	public String getUsername(){
		return username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public void setToken(String token){
		this.token = token;
	}

	public String getToken(){
		return token;
	}
}