package Wifi;

import java.io.IOException;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.concurrent.CompletableFuture;

import org.hibernate.validator.internal.engine.constraintvalidation.PredefinedScopeConstraintValidatorManagerImpl;

import Wifi.*;
public class pracC {
	public static void methodd() throws ParseException, IOException{
//		MyOkHttp ok = new MyOkHttp();
//		
//		int cnt = ok.TotalCnt();
//		
//		List<infowifi.row> list = new ArrayList<>();
//		List<infowifi> wl = new ArrayList<infowifi>();
////		List<CompletableFuture<infowifi>> listcf = new ArrayList<>();
//		for(int i = 0; i < cnt + 1; i += 1000) {
//			wl.add(ok.getApiResult(1 + i, Math.min(1000 + i, cnt)));
//		}
//		System.out.println(wl.size());
//		for (infowifi cf : wl) {
//			list.addAll(cf.getRow());
//			
//			if(list.size() == cnt) {
//			}				
//		}
//		System.out.println("s");
//		System.out.println(list.size());
//		
////		for (infowifi cf : w) {
////			cf.thenAccept(result -> {list.addAll(result.getlistrow());
////			
////			if(list.size() == cnt) {
////				
////			}
////			
////			});
		
		
	}
	
	public static void main(String[] args) throws ParseException, IOException {
		MyOkHttp ok = new MyOkHttp();
		List<infowifi> list = ok.AddWifi();
		ok.dbInsert(list);
	}
}
