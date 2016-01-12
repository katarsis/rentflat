package org.katarsis.rentflat.service;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.TimeUnit;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;

import org.katarsis.rentflat.entities.Flat;
import org.katarsis.rentflat.repository.FlatRepository;
import org.springframework.beans.factory.annotation.Autowired;

@WebListener("application context listener")
public class ContextInitializer implements ServletContextListener  {

	public static boolean isInitalizationSucceed = true;
	public static String contextInitalizationErrorMessage ="";
    private final ScheduledExecutorService scheduler = Executors.newScheduledThreadPool(1);
	
    /*@Autowired
    FlatRepository repos;*/
    
	@Override
	public void contextDestroyed(ServletContextEvent arg0) {
	}

	@Override
	public void contextInitialized(ServletContextEvent arg0) {
		/*final Runnable beeper = new Runnable() {
            public void run() { 
            	System.out.println("ss");
            	for(Flat item:repos.findAll()){
            		System.out.println(item.getLocation().getDescription());
            	}
            }
        };
        final ScheduledFuture<?> beeperHandle =
        scheduler.scheduleAtFixedRate(beeper, 10, 10, TimeUnit.SECONDS);
        scheduler.schedule(new Runnable() {
            public void run() { beeperHandle.cancel(true); }
        }, 60 * 60, TimeUnit.SECONDS);
		*/
	}

}
