package org.katarsis.rentflat.web;

import java.util.Locale;

import org.katarsis.rentflat.repository.JobLogRepository;
import org.katarsis.rentflat.service.RefreshStatisticService;
import org.katarsis.rentflat.service.RefreshStatisticService.OperationType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class TaskController {
	
	@Autowired
	JobLogRepository taskRepository;
	@Autowired
	RefreshStatisticService refreshStatisticService;

	@RequestMapping(value="administration/tasks",method=RequestMethod.GET)
	public String showTaskLog(Locale locale, Model model){
		model.addAttribute("tasks", taskRepository.findAll());
		return "administration/tasks";
	}
	
	@RequestMapping(value="administration/manage",method=RequestMethod.GET)
	public String showManageForm(Locale locale, Model model){
		System.out.println("in manage");
		return "administration/manage";
	}
	
	@RequestMapping(value = "administration/refreshrent", method = RequestMethod.GET)
	public void refreshRent(@RequestParam("flat") String flatCount)
    {
		try{
			refreshStatisticService.refreshStatistic(OperationType.RENT, Integer.parseInt(flatCount));
		}catch(Exception exp){
			exp.printStackTrace();
		}
	
    }
	
	@RequestMapping(value = "administration/refreshprice", method = RequestMethod.GET)
	public void demoServiceMethod(@RequestParam("flat") String flatCount)
    {
		try{
			refreshStatisticService.refreshStatistic(OperationType.BUY, Integer.parseInt(flatCount));
		}catch(Exception exp){
			exp.printStackTrace();
		}
	
    }
}
