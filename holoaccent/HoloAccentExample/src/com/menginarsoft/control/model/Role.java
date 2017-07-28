package com.menginarsoft.control.model;

import java.util.ArrayList;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.menginarsoft.control.function.Hata;
import com.menginarsoft.control.function.Sabit;

public class Role {

	public int Id;
	public String SaatAraligi;
	public String Tip;
	Hata Sonuc = new Hata();
	public Role() {
		this.Id = -1;
		this.SaatAraligi = "";
		this.Tip = "";
	}
	
	public String getTip() {
		return Tip;
	}

	public void setTip(String tip) {
		Tip = tip;
	}

	public Role(String SaatAraligi) {
		this.SaatAraligi = SaatAraligi;
	}

	public int getId() {
		return Id;
	}

	public void setId(int id) {
		Id = id;
	}

	public String getSaatAraligi() {
		return SaatAraligi;
	}

	public void setSaatAraligi(String saatAraligi) {
		SaatAraligi = saatAraligi;
	}
	
	public Hata KaydetRole() {
		SQLiteDatabase DtBs = Sabit.MDbBaglanti.getWritableDatabase();
		try {
			
			DtBs.beginTransaction();
			if (Sabit.mTut != -1) {
				DtBs.execSQL("DELETE FROM TBLNROLE WHERE ID="
						+ Integer.toString(Sabit.mTut));
			}
			
			ContentValues Values = new ContentValues();
			Values.put("SAATARALIGI", this.getSaatAraligi());
			Values.put("TIP", this.getTip());
			
			DtBs.insert("TBLNROLE", null, Values);
			DtBs.setTransactionSuccessful();
			
		} catch (Exception e) {
			Sonuc = new Hata();
			Sonuc.setBASLIK("Role");
			Sonuc.setHATAMI(true);
			Sonuc.setMESAJ("Kay�t Ba�ar�s�z!");
		}
		
		DtBs.endTransaction();
		DtBs.close();
		return Sonuc;
	}
	
	public void GetirRoleById(String _Id) {
		try {
			SQLiteDatabase DtBs = Sabit.MDbBaglanti.getReadableDatabase();
			String SCumle = "SELECT SAATARALIGI, TIP FROM TBLNROLE WHERE ID=?";
			
			Cursor Crs = DtBs.rawQuery(SCumle, new String [] {_Id});
			while (Crs.moveToNext()) {
				this.setSaatAraligi(Crs.getString(0));
				this.setTip(Crs.getString(1));
			}
			DtBs.close();
		} catch (Exception e) {
			Sonuc = new Hata();
			Sonuc.setBASLIK("Role");
			Sonuc.setHATAMI(true);
			Sonuc.setMESAJ("Getirme Ba�ar�s�z!");
		}
	}
	
	public Hata AralikSil(int _Id) {
		SQLiteDatabase DtBs = Sabit.MDbBaglanti.getWritableDatabase();
		try {
			DtBs.beginTransaction();
			DtBs.execSQL("DELETE FROM TBLNROLE WHERE ID="
					+ Integer.toString(_Id));

			DtBs.setTransactionSuccessful();
			Sonuc.setHATAMI(false);
		} catch (Exception Ex) {
			Sonuc = new Hata();
			Sonuc.setBASLIK("Cari Talep ��lemleri");
			Sonuc.setMESAJ("Silme ��leminde Hata Olu�tu * Hata : "
					+ Ex.getMessage());
			Sonuc.setHATAMI(true);
		}
		DtBs.endTransaction();
		DtBs.close();
		return Sonuc;
	}
	
	public ArrayList<String> GetirRole(String _Tip) {
		ArrayList<String> SaatAraliklari = new ArrayList<String>();
		try {
			SQLiteDatabase DtBs = Sabit.MDbBaglanti.getReadableDatabase();
			String SCumle = "SELECT SAATARALIGI, TIP FROM TBLNROLE WHERE TIP=?";
			
			Cursor Crs = DtBs.rawQuery(SCumle, new String [] {_Tip});
			while (Crs.moveToNext()) {
				this.setSaatAraligi(Crs.getString(0));
				this.setTip(Crs.getString(1));
				
				SaatAraliklari.add(this.getSaatAraligi());
			}
			DtBs.close();
			
		} catch (Exception e) {
			Sonuc = new Hata();
			Sonuc.setBASLIK("Role");
			Sonuc.setHATAMI(true);
			Sonuc.setMESAJ("Getirme Ba�ar�s�z!");
		}
		return SaatAraliklari;
	}
	
}
