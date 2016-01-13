package org.katarsis.rentflat.web;

import java.io.IOException;
import java.util.Locale;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.katarsis.rentflat.entities.Flat;
import org.katarsis.rentflat.entities.Location;
import org.katarsis.rentflat.repository.FlatRepository;
import org.katarsis.rentflat.repository.JobLogRepository;
import org.katarsis.rentflat.repository.LocationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

@Controller
public class TaskController {
	
	@Autowired
	LocationRepository locationsRepos;
	@Autowired
	FlatRepository flatsRepos;
	@Autowired
	JobLogRepository taskRepository;

	@RequestMapping(value="administration/tasks",method=RequestMethod.GET)
	public String showTaskLog(Locale locale, Model model){
		model.addAttribute("tasks", taskRepository.findAll());
		return "administration/tasks";
	}
	
	@RequestMapping(value="administration/manage",method=RequestMethod.GET)
	public String showManageForm(Locale locale, Model model){
		return "administration/manage";
	}
	
	@RequestMapping(value = "administration/refreshrent", method = RequestMethod.POST)
	public void demoServiceMethod(@RequestParam("flat") String flatCount)
    {
		Document doc;
		try {
			System.out.println("Flat count:" + flatCount);
			String defaultUrlAddress = "https://www.avito.ru/moskva/kvartiry/sdam/na_dlitelnyy_srok/2-komnatnye";
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
				JsonObject jsonCurrencyMeta = new JsonParser().parse(price.attr("data-prices").substring(1,price.attr("data-prices").length()-1)).getAsJsonObject();
				JsonObject jsonPrice = (JsonObject) jsonCurrencyMeta.get("currencies");
				String priceString = jsonPrice.get("RUB").getAsString(); 
				
				Element stantion = flat.select("p.address").first();
				String stantionWithDest = stantion.text().split(",")[0];
				String stantionStr = stantionWithDest.split("\\d+")[0];	
				String destantionStr = stantion.select("span.c-2").first().text();
				//String destantionStrDesc = stantion.select("span.c-2").first().text().split("\\d+")[0];
				Double destantionInMetrs = Double.valueOf(destantionStr.split(" ")[0].replaceAll("\\+", ""));
				if(destantionStr.contains("км")){
					destantionInMetrs = destantionInMetrs *1000;
				}
				
				Element data = flat.select("div.date").first();
				String dataStr = data.text();
				
				Element desc = flat.select("h3.title").first();
				String descStr = desc.text();
				Location location = locationsRepos.findStantion(stantionStr.trim());
				if(location==null){
					location = new Location();
					location.setDescription(stantionStr.trim());
					locationsRepos.saveAndFlush(location);
				}
				
				Flat newFlat = new Flat();
				newFlat.setDistantion(destantionInMetrs.intValue());
				newFlat.setPrice(Integer.parseInt(priceString));
				newFlat.setLocation(location);
				
				flatsRepos.saveAndFlush(newFlat);
				}catch(Exception es){
					System.out.println("ERROR: "+flat.text());
					//es.printStackTrace();
				}
			}
		}

	} catch (IOException e) {
		e.printStackTrace();
	}
    }
}