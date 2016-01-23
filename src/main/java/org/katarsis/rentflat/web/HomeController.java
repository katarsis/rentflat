package org.katarsis.rentflat.web;

import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.List;
import java.util.Locale;

import javax.persistence.EntityManager;
import javax.persistence.Query;

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
	@Autowired
	EntityManager em;
	
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
		Query query = em.createNativeQuery("select avg(price),loc.description from flat join location loc on loc.id=id_location where  operation_type=?1 and room_count=?2 group by loc.description");
		
		if(flatConut!=null&&type!=null){
			query.setParameter(1, type);
			query.setParameter(2, flatConut);
			List<Object[]> list = query.getResultList();
			for(Object[] locationItem: list){
				Double avgPrice = Double.parseDouble(((BigDecimal)locationItem[0]).toString());
				System.out.println(avgPrice);
				NumberFormat formatter = new DecimalFormat("#,###,###.##"); 
				if(avgPrice!=null){
					model.addAttribute(((String)locationItem[1]).replaceAll(" ", "_").replaceAll("-", "_"), formatter.format(avgPrice));
				}
			}
		}
		return "standart/rentmap";
	}
	
	
	

}
