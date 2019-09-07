package com.kesequl.app.model.Entity;

import com.google.gson.annotations.SerializedName;

public class VotingTeam{

	@SerializedName("id_wakil")
	private String idWakil;

	@SerializedName("misi")
	private String misi;

	@SerializedName("keterangan")
	private Object keterangan;

	@SerializedName("nama_ketua")
	private String namaKetua;

	@SerializedName("kelas_wakil")
	private String kelasWakil;

	@SerializedName("password")
	private String password;

	@SerializedName("nama")
	private String nama;

	@SerializedName("tanggal_selesai")
	private String tanggalSelesai;

	@SerializedName("id_pembuat")
	private String idPembuat;

	@SerializedName("kelas_ketua")
	private String kelasKetua;

	@SerializedName("tanggal_mulai")
	private String tanggalMulai;

	@SerializedName("id_ketua")
	private String idKetua;

	@SerializedName("id_event_voting")
	private String idEventVoting;

	@SerializedName("id_nominasi_team")
	private String idNominasiTeam;

	@SerializedName("nama_wakil")
	private String namaWakil;

	@SerializedName("visi")
	private String visi;

	@SerializedName("status")
	private String status;

	public void setIdWakil(String idWakil){
		this.idWakil = idWakil;
	}

	public String getIdWakil(){
		return idWakil;
	}

	public void setMisi(String misi){
		this.misi = misi;
	}

	public String getMisi(){
		return misi;
	}

	public void setKeterangan(Object keterangan){
		this.keterangan = keterangan;
	}

	public Object getKeterangan(){
		return keterangan;
	}

	public void setNamaKetua(String namaKetua){
		this.namaKetua = namaKetua;
	}

	public String getNamaKetua(){
		return namaKetua;
	}

	public void setKelasWakil(String kelasWakil){
		this.kelasWakil = kelasWakil;
	}

	public String getKelasWakil(){
		return kelasWakil;
	}

	public void setPassword(String password){
		this.password = password;
	}

	public String getPassword(){
		return password;
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

	public void setKelasKetua(String kelasKetua){
		this.kelasKetua = kelasKetua;
	}

	public String getKelasKetua(){
		return kelasKetua;
	}

	public void setTanggalMulai(String tanggalMulai){
		this.tanggalMulai = tanggalMulai;
	}

	public String getTanggalMulai(){
		return tanggalMulai;
	}

	public void setIdKetua(String idKetua){
		this.idKetua = idKetua;
	}

	public String getIdKetua(){
		return idKetua;
	}

	public void setIdEventVoting(String idEventVoting){
		this.idEventVoting = idEventVoting;
	}

	public String getIdEventVoting(){
		return idEventVoting;
	}

	public void setIdNominasiTeam(String idNominasiTeam){
		this.idNominasiTeam = idNominasiTeam;
	}

	public String getIdNominasiTeam(){
		return idNominasiTeam;
	}

	public void setNamaWakil(String namaWakil){
		this.namaWakil = namaWakil;
	}

	public String getNamaWakil(){
		return namaWakil;
	}

	public void setVisi(String visi){
		this.visi = visi;
	}

	public String getVisi(){
		return visi;
	}

	public void setStatus(String status){
		this.status = status;
	}

	public String getStatus(){
		return status;
	}
}