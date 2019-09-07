package com.kesequl.app.model.Entity;

import com.google.gson.annotations.SerializedName;
import com.kesequl.app.R;

public class Siswa implements DaoData {

	@SerializedName("keterangan")
	private Object keterangan;

	@SerializedName("nama")
	private String nama;

	@SerializedName("gender")
	private String gender;

	@SerializedName("nisn")
	private String nisn;

	@SerializedName("kelas")
	private String kelas;

	@SerializedName("id_user")
	private String idUser;

	@SerializedName("index_jurusan")
	private String indexJurusan;

	@SerializedName("id_jurusan")
	private String idJurusan;

	@SerializedName("tanggal_lahir")
	private String tanggalLahir;

	@SerializedName("id_siswa")
	private String idSiswa;

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

	@Override
	public int getResIdText() {
		return R.array.aksi_siswa_text;
	}

	@Override
	public int getResIdImage() {
		return R.array.aksi_siswa_image;
	}

	public void setGender(String gender){
		this.gender = gender;
	}

	public String getGender(){
		return gender;
	}

	public void setNisn(String nisn){
		this.nisn = nisn;
	}

	public String getNisn(){
		return nisn;
	}

	public void setKelas(String kelas){
		this.kelas = kelas;
	}

	public String getKelas(){
		return kelas;
	}

	public void setIdUser(String idUser){
		this.idUser = idUser;
	}

	public String getIdUser(){
		return idUser;
	}

	public void setIndexJurusan(String indexJurusan){
		this.indexJurusan = indexJurusan;
	}

	public String getIndexJurusan(){
		return indexJurusan;
	}

	public void setIdJurusan(String idJurusan){
		this.idJurusan = idJurusan;
	}

	public String getIdJurusan(){
		return idJurusan;
	}

	public void setTanggalLahir(String tanggalLahir){
		this.tanggalLahir = tanggalLahir;
	}

	public String getTanggalLahir(){
		return tanggalLahir;
	}

	public void setIdSiswa(String idSiswa){
		this.idSiswa = idSiswa;
	}

	public String getIdSiswa(){
		return idSiswa;
	}
}