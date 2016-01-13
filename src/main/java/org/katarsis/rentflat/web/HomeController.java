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
import org.katarsis.rentflat.repository.LocationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

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
	public String rentmap(Locale locale, Model model) {
		System.out.println("in rent");
		return "standart/rentmap";
	}
	
	
	

}
