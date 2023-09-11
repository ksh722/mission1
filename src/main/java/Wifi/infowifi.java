package Wifi;

import java.util.List;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

import com.google.gson.annotations.SerializedName;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class infowifi {
		@SerializedName("X_SWIFI_MGR_NO")
		private String mgr_no;
		
		@SerializedName("X_SWIFI_WRDOFC")
		private String wrdofc;
		
		@SerializedName("X_SWIFI_MAIN_NM")
		private String wifi_name;
		
		@SerializedName("X_SWIFI_ADRES1")
		private String adres_rail;
		
		@SerializedName("X_SWIFI_ADRES2")
		private String adres_detail;
		
		@SerializedName("X_SWIFI_INSTL_FLOOR")
		private String instl_floor;
		
		@SerializedName("X_SWIFI_INSTL_TY")
		private String instl_ty;

		@SerializedName("X_SWIFI_INSTL_MBY")
		private String instl_mby;
		
		@SerializedName("X_SWIFI_SVC_SE")
		private String svc_se;
		
		@SerializedName("X_SWIFI_CMCWR")
		private String cmcwr;
		
		@SerializedName("X_SWIFI_CNSTC_YEAR")
		private String cnstc_year;
		
		@SerializedName("X_SWIFI_INOUT_DOOR")
		private String inout_door;

		@SerializedName("X_SWIFI_REMARS3")
		private String remars3;
		
		@SerializedName("LNT")
		private String wifi_x;
		
		@SerializedName("LAT")
		private String wifi_y;
		
		@SerializedName("WORK_DTTM")
		private String work_dttm;
		
		private double km;
		private int year;
		private double lnt;
		private double lat;

}
