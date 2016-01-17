package org.katarsis.rentflat.service;

import java.io.IOException;
import java.sql.Timestamp;
import java.util.Date;

import org.hibernate.jpa.criteria.expression.BinaryArithmeticOperation.Operation;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.katarsis.rentflat.entities.Flat;
import org.katarsis.rentflat.entities.JobLog;
import org.katarsis.rentflat.entities.Location;
import org.katarsis.rentflat.repository.FlatRepository;
import org.katarsis.rentflat.repository.JobLogRepository;
import org.katarsis.rentflat.repository.LocationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Repository;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

@Repository
public class RefreshStatisticService {

	public enum OperationType {BUY,RENT}
	
	
	@Autowired
    FlatRepository repos;
	@Autowired
	LocationRepository locationRepo;
	@Autowired
	JobLogRepository jobRepo;
	
	@Scheduled(cron="0 21 21 11 * ?")
	public void demoCron(){
		refreshStatistic(OperationType.BUY,1);
		refreshStatistic(OperationType.BUY,2);
		refreshStatistic(OperationType.RENT,1);
		refreshStatistic(OperationType.RENT,2);
	}
	
    public void refreshStatistic(OperationType operationType, int flatCount)
    {
		Document doc;
		
		try {
			String baseUrl = "https://www.avito.ru/moskva/kvartiry";
			if(operationType == OperationType.BUY && flatCount ==1){
				baseUrl+="/prodam/1-komnatnye/vtorichka";
			}else if(operationType == OperationType.BUY && flatCount ==2){
				baseUrl+="/prodam/2-komnatnye/vtorichka";
			}else if(operationType == OperationType.RENT && flatCount ==1){
				baseUrl+="/sdam/na_dlitelnyy_srok/1-komnatnye";
			}else if(operationType == OperationType.RENT && flatCount ==2){
				baseUrl+="/sdam/na_dlitelnyy_srok/2-komnatnye";
			}
			String defaultUrlAddress = baseUrl;
			doc = Jsoup.connect(defaultUrlAddress).get();
			String endPage = "";
			// find last page index
			Elements address = doc.select("a.pagination__page");
			for (Element link : address) {
				if(link.text().equals("Последняя")){
			        endPage = link.attr("href").substring(link.attr("href").indexOf("=")+1);
				}
			}
			Integer maxPage = Integer.valueOf(endPage);
			
			// for all page
			for(int pageNumber =1;pageNumber<maxPage;pageNumber++){
				doc = Jsoup.connect(defaultUrlAddress+"?p="+pageNumber).get();
				System.out.println(defaultUrlAddress+"?p="+pageNumber);
				Elements flatItems = doc.select("div.item");
				for (Element flat : flatItems) {
					
					try{
					Element price = flat.select("div.popup-prices").first();
					JsonObject jsonCurrencyMeta = null;
					if(operationType == OperationType.BUY){
						jsonCurrencyMeta = new JsonParser().parse(price.attr("data-prices").substring(1,price.attr("data-prices").length()-1).split("}},")[0]+"}}").getAsJsonObject();
					}else {
						jsonCurrencyMeta = new JsonParser().parse(price.attr("data-prices").substring(1,price.attr("data-prices").length()-1)).getAsJsonObject();
					}
					JsonObject jsonPrice = (JsonObject) jsonCurrencyMeta.get("currencies");
					String priceString = jsonPrice.get("RUB").getAsString(); 
					
					Element stantion = flat.select("p.address").first();
					String stantionWithDest = stantion.text().split(",")[0];
					String stantionStr = stantionWithDest.split("\\d+")[0];	
					String  destantionStr = stantion.select("span.c-2").first().text();
					//String destantionStrDesc = stantion.select("span.c-2").first().text().split("\\d+")[0];
					Double destantionInMetrs = Double.valueOf(destantionStr.split(" ")[0].replaceAll("\\+", ""));
					if(destantionStr.contains("км")){
						destantionInMetrs = destantionInMetrs *1000;
					}
					
					Element data = flat.select("div.date").first();
					String dataStr = data.text();
					
					Element desc = flat.select("h3.title").first();
					String descStr = desc.text();
					Location location = locationRepo.findStantion(stantionStr.trim());
					if(location==null){
						location = new Location();
						location.setDescription(stantionStr.trim());
						locationRepo.saveAndFlush(location);
					}
					
					Flat newFlat = new Flat();
					newFlat.setDistantion(destantionInMetrs.intValue());
					newFlat.setPrice(Integer.parseInt(priceString));
					newFlat.setLocation(location);
					newFlat.setRoom_count(flatCount);
					if(operationType==OperationType.BUY){
						newFlat.setOperationType("buy");
					}else if(operationType == OperationType.RENT){
						newFlat.setOperationType("rent");
					}
					Timestamp currentDate = new Timestamp((new Date()).getTime());
					newFlat.setDateCreation(currentDate);
					repos.saveAndFlush(newFlat);
					}catch(Exception es){
						System.out.println("ERROR: "+flat.text());
						//es.printStackTrace();
					}
				}
			}
			JobLog finishedJob = new JobLog();
			finishedJob.setDateRunning(new Date());
			if(operationType==OperationType.BUY){
				finishedJob.setOperationType("refresh buying info");
			}else if(operationType == OperationType.RENT){
				finishedJob.setOperationType("refresh rent info");
			}
			finishedJob.setParameters("flat room: "+flatCount);
			jobRepo.save(finishedJob);
		} catch (IOException e) {
			e.printStackTrace();
		}
    }
}
