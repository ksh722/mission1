package Wifi;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.google.gson.reflect.TypeToken;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.lang.reflect.Field;
import java.net.HttpURLConnection;
import java.net.URL;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.Clock;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CompletableFuture;

import javax.validation.constraints.NotNull;

import org.w3c.dom.ls.LSOutput;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.ResponseBody;

public class MyOkHttp {
	static int TOTALCNT;
	private static final String key = "56626b4e6b726c6136366c42534474";
	private static OkHttpClient client;
	private static Request.Builder builder;
	public MyOkHttp() {
		client = new OkHttpClient.Builder().build();
		builder = new Request.Builder();
	}

	public Request getRequest(int start, int end) {
		String url = "http://openapi.seoul.go.kr:8088/" + key + "/json/TbPublicWifiInfo/" + start + "/" + end + "/";
		return builder.url(url).get().build();
	}

	public Call getOkclient(Request request) {
		return client.newCall(request);
	}
	@SuppressWarnings("deprecation")
	public static int TotalCnt() throws ParseException {
		URL url = null;
		HttpURLConnection con = null;
		JsonObject result = null;
		StringBuilder sb = new StringBuilder();
		int start = 1;
		int end = 1;
		String baseurl = "http://openapi.seoul.go.kr:8088/" + key + "/json/TbPublicWifiInfo/" + start + "/" + end + "/";
		try {
			url = new URL(baseurl);
			con = (HttpURLConnection)url.openConnection();
			con.setRequestMethod("GET");
			con.setRequestProperty("Content-type", "application/json");
			con.setDoOutput(true);
			
			BufferedReader br = new BufferedReader(new InputStreamReader(con.getInputStream(), "UTF-8"));
			while(br.ready()) {
				sb.append(br.readLine());
			}
			con.disconnect();
		}catch(Exception e) {
			e.printStackTrace();
		}
		
		result = (JsonObject)new JsonParser().parse(sb.toString());
		
		StringBuilder out = new StringBuilder();
		
		JsonObject data = (JsonObject) result.get("TbPublicWifiInfo");
		int totalGet = Integer.parseInt(data.get("list_total_count").toString());
		
		return totalGet;
	}
	
	public static List<infowifi> AddWifi() throws ParseException{
		int start = 0;
		int end = 0;
		
		TOTALCNT = TotalCnt();
		int pageEnd = TOTALCNT / 1000;
		int pageEndRemain = TOTALCNT % 1000;
		List<infowifi> list = new ArrayList<>();
		
		for (int i = 0; i <= pageEnd; i++) {
			start = 1000 * i + 1;
			
			if(i == pageEnd) {
				end = start + pageEndRemain - 1;
			} else {
				end = 1000 *(i + 1);
			}
			
			String baseurl = "http://openapi.seoul.go.kr:8088/" + key + "/json/TbPublicWifiInfo/" + start + "/" + end + "/";
			
			URL url = null;
			HttpURLConnection con = null;
			JsonObject result = null;
			StringBuilder sb = new StringBuilder();
			
			try {
				url = new URL(baseurl);
				con = (HttpURLConnection) url.openConnection();
				con.setRequestMethod("GET");
				con.setRequestProperty("Content-type", "application/json");
				con.setDoOutput(true);
				
				BufferedReader br = new BufferedReader(new InputStreamReader(con.getInputStream(),"UTF-8"));
				while(br.ready()) {
					sb.append(br.readLine());
				}
				con.disconnect();
			} catch (Exception e) {
				e.printStackTrace();
			}
			
			result = (JsonObject)new JsonParser().parse(sb.toString());
			JsonObject data = (JsonObject) result.get("TbPublicWifiInfo").getAsJsonObject();
			JsonArray arr = (JsonArray) data.get("row").getAsJsonArray();
			
			Gson gson = new Gson();
			List<infowifi> list2 = gson.fromJson(arr.toString(), new TypeToken<List<infowifi>>(){}.getType());
			list.addAll(list2);
//			System.out.println(list2.size());
//			System.out.println(list2.get(0).getAdres_detail());
//			System.out.println(list2.get(0).getAdres_rail());
		}
		
		return list;
	}
	
    public static void dbInsert(List<infowifi> list){
        String url = "jdbc:mariadb://3.36.180.148:3306/mission1";
        String dbUserId = "mission1";
        String dbPassword = "mission1";

        try {
            Class.forName("org.mariadb.jdbc.Driver");
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
        
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet rs = null;
        DateTimeFormatter dB_time_Format = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        
        for (infowifi infowifi : list) {
        	try {
                connection = DriverManager.getConnection(url, dbUserId, dbPassword);

                String sql = " insert into WIFIINFO "
                		+ " values(?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?) ";

                preparedStatement = connection.prepareStatement(sql);
                // 객체 변수를 반복문으로 하나씩 얻을 때 사용
//                try {
//                	Object obj = infowifi;
//                	int idx = 1;
//                    for (Field field : obj.getClass().getDeclaredFields()) {
//    					field.setAccessible(true);
//    					Object value = field.get(obj);
//    					preparedStatement.setString(idx++, (String) value);
//    				}
//                } catch (Exception e) {
//                	e.printStackTrace();
//                }

                preparedStatement.setString(1, infowifi.getMgr_no());
                preparedStatement.setString(2, infowifi.getWrdofc());
                preparedStatement.setString(3, infowifi.getWifi_name());
                preparedStatement.setString(4, infowifi.getAdres_rail());
                preparedStatement.setString(5, infowifi.getAdres_detail());
                preparedStatement.setString(6, infowifi.getInstl_floor());
                preparedStatement.setString(7, infowifi.getInstl_ty());
                preparedStatement.setString(8, infowifi.getInstl_mby());
                preparedStatement.setString(9, infowifi.getSvc_se());
                preparedStatement.setString(10, infowifi.getCmcwr());
                preparedStatement.setInt(11, Integer.valueOf(infowifi.getCnstc_year()));
                preparedStatement.setString(12, infowifi.getInout_door());
                preparedStatement.setString(13, infowifi.getRemars3());
                preparedStatement.setDouble(14, Double.valueOf(infowifi.getWifi_x()));
                preparedStatement.setDouble(15, Double.valueOf(infowifi.getWifi_y()));
                preparedStatement.setString(16, infowifi.getWork_dttm());
                
                int affected = preparedStatement.executeUpdate();
                
            } catch (SQLException e) {
                throw new RuntimeException(e);
            } finally {
                try {
                    if(rs != null && !rs.isClosed()){
                        rs.close();
                    }
                } catch (SQLException e) {
                    throw new RuntimeException(e);
                }
                try {
                    if(preparedStatement != null && !preparedStatement.isClosed()){
                        preparedStatement.close();
                    }
                } catch (SQLException e) {
                    throw new RuntimeException(e);
                }
                try {
                    if(connection != null && !connection.isClosed()){
                        connection.close();
                    }
                } catch (SQLException e) {
                    throw new RuntimeException(e);
                }
            }
		}

        
    }
    
    public static List<infowifi> dbSelect(double lat, double lnt){
    	
    	List<infowifi> dblist = new ArrayList<>();
        String url = "jdbc:mariadb://3.36.180.148:3306/mission1";
        String dbUserId = "mission1";
        String dbPassword = "mission1";

        try {
            Class.forName("org.mariadb.jdbc.Driver");
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
        
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet rs = null;
        DateTimeFormatter dB_time_Format = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        
        
    	try {
            connection = DriverManager.getConnection(url, dbUserId, dbPassword);
            String historysql = "INSERT into HISTORY(H_x, H_y, H_date) "
            		+ "VALUES(?, ?, ?)";
            preparedStatement = connection.prepareStatement(historysql);
            preparedStatement.setDouble(1, lat);
            preparedStatement.setDouble(2, lnt);
            LocalDateTime now = LocalDateTime.now();
            String n = now.format(dB_time_Format).toString();
            preparedStatement.setString(3, n);
            
            preparedStatement.executeUpdate();
            
            
            String sql = " SELECT *, ( 6371 * acos( cos( radians(?) ) * cos( radians( wifi_x ) ) * cos( radians( wifi_y ) - radians(?) ) + sin( radians(?) ) * sin( radians( wifi_x ) ) ) ) / 1000 AS distance "
            		+ "FROM WIFIINFO WHERE cmcwr != '자가망U무선망' "
            		+ "HAVING distance <= 2 ORDER BY distance LIMIT 20; ";
            

            
            preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setDouble(1, lat);
            preparedStatement.setDouble(2, lnt);
            preparedStatement.setDouble(3, lat);
            
            rs = preparedStatement.executeQuery();
            
            while(rs.next()) {
            	double km =  rs.getDouble("distance");
            	String mgr_no = rs.getString("mgr_no");
            	String wrdofc = rs.getString("wrdofc");
            	String wifi_name = rs.getString("wifi_name");
            	String adres_rail = rs.getString("adres_rail");
            	String adres_detail = rs.getString("adres_detail");
            	String instl_floor = rs.getString("instl_floor");
            	String instl_ty = rs.getString("instl_ty");
            	String instl_mby = rs.getString("instl_mby");
            	String svc_se = rs.getString("svc_se");
            	String cmcwr = rs.getString("cmcwr");
            	int cnstc_year = rs.getInt("cnstc_year");
            	String inout_door = rs.getString("inout_door");
            	String remars3 = rs.getString("remars3");
            	double wifi_x = rs.getDouble("wifi_x");
            	double wifi_y = rs.getDouble("wifi_y");
            	String work_dttm = rs.getString("work_dttm");
             
            	infowifi infowifi = new infowifi();
            	infowifi.setKm(Math.round(km*10000)/10000.0);
            	infowifi.setMgr_no(mgr_no);
            	infowifi.setWrdofc(wrdofc);
            	infowifi.setWifi_name(wifi_name);
            	infowifi.setAdres_rail(adres_rail);
            	infowifi.setAdres_detail(adres_detail);
            	infowifi.setInstl_floor(instl_floor);
            	infowifi.setInstl_ty(instl_ty);
            	infowifi.setInstl_mby(instl_mby);
            	infowifi.setSvc_se(svc_se);
            	infowifi.setCmcwr(cmcwr);
            	infowifi.setYear(cnstc_year);
            	infowifi.setInout_door(inout_door);
            	infowifi.setRemars3(remars3);
            	infowifi.setLat(Math.round(wifi_x*10000)/10000.0);
            	infowifi.setLnt(Math.round(wifi_y*10000)/10000.0);
            	infowifi.setWork_dttm(work_dttm);
            	dblist.add(infowifi);
            }
           
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } finally {
            try {
                if(rs != null && !rs.isClosed()){
                    rs.close();
                }
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
            try {
                if(preparedStatement != null && !preparedStatement.isClosed()){
                    preparedStatement.close();
                }
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
            try {
                if(connection != null && !connection.isClosed()){
                    connection.close();
                }
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        }
	
    	
        return dblist;
    }
    
    public static List<history> historySelect(){
    	List<history> list = new ArrayList<>();
    	
        String url = "jdbc:mariadb://3.36.180.148:3306/mission1";
        String dbUserId = "mission1";
        String dbPassword = "mission1";

        try {
            Class.forName("org.mariadb.jdbc.Driver");
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet rs = null;
        
        try {
            connection = DriverManager.getConnection(url, dbUserId, dbPassword);

            String sql = " SELECT * "
            		+ " FROM HISTORY ";

            preparedStatement = connection.prepareStatement(sql);
            rs = preparedStatement.executeQuery();

            while(rs.next()){
            	history h = new history();
            	h.setId(rs.getInt("H_ID"));
                h.setH_x(rs.getDouble("H_x"));
                h.setH_y(rs.getDouble("H_y"));
                h.setH_date(rs.getString("H_date"));
                
                list.add(h);
            }

        } catch (SQLException e) {
            throw new RuntimeException(e);
        } finally {
            try {
                if(rs != null && !rs.isClosed()){
                    rs.close();
                }
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
            try {
                if(preparedStatement != null && !preparedStatement.isClosed()){
                    preparedStatement.close();
                }
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
            try {
                if(connection != null && !connection.isClosed()){
                    connection.close();
                }
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        }
        return list;
    }

    public static int dbCount() {
    	int c = 0;
    	String url = "jdbc:mariadb://3.36.180.148:3306/mission1";
        String dbUserId = "mission1";
        String dbPassword = "mission1";

        try {
            Class.forName("org.mariadb.jdbc.Driver");
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet rs = null;
        try {
            connection = DriverManager.getConnection(url, dbUserId, dbPassword);

            String sql = " SELECT count(*) as count "
            		+ " from WIFIINFO ";

            preparedStatement = connection.prepareStatement(sql);
            rs = preparedStatement.executeQuery();
            
            while(rs.next()){
                c = rs.getInt("count");
            }

        } catch (SQLException e) {
            throw new RuntimeException(e);
        } finally {
            try {
                if(rs != null && !rs.isClosed()){
                    rs.close();
                }
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
            try {
                if(preparedStatement != null && !preparedStatement.isClosed()){
                    preparedStatement.close();
                }
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
            try {
                if(connection != null && !connection.isClosed()){
                    connection.close();
                }
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        }
        return c;
    }

}