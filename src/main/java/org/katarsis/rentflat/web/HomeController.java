package org.katarsis.rentflat.web;

import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.Locale;

import org.katarsis.rentflat.entities.Location;
import org.katarsis.rentflat.repository.FlatRepository;
import org.katarsis.rentflat.repository.LocationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class HomeController {
	
	@Autowired
	LocationRepository locationsRepos;
	@Autowired
	FlatRepository flatsRepos;
	
	@RequestMapping(value = "/index", method = RequestMethod.GET)
	public String index(Locale locale, Model model) {
		System.out.println("what the hell");
		/*for(Location locationItem: locationsRepos.findAll()){
			Double avgPrice = flatsRepos.findAvgPriceForStantion(locationItem.getDescription());
			System.out.println(avgPrice);
			model.addAttribute(locationItem.getDescription().replaceAll(" ", "_").replaceAll("-", "_"), avgPrice);
		}*/
		return "index";
	}
	
	@RequestMapping(value = "/standart/rentmap", method = RequestMethod.GET)
	public String rentmap(Locale locale, Model model,@RequestParam(required = false) Integer flatConut,@RequestParam(required = false) String type) {
		System.out.println("in rent");
		if(flatConut!=null&&type!=null){
			for(Location locationItem: locationsRepos.findAll()){
				Double avgPrice = flatsRepos.findAvgPriceForStantion(locationItem.getDescription(),flatConut,type);
				System.out.println(avgPrice);
				NumberFormat formatter = new DecimalFormat("#,###,###.##"); 
				if(avgPrice!=null){
					model.addAttribute(locationItem.getDescription().replaceAll(" ", "_").replaceAll("-", "_"), formatter.format(avgPrice));
				}
			}
		}
		return "standart/rentmap";
	}
	
	
	

}
