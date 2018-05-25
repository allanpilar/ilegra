package br.com.test.ilegra.schedule;

import javax.annotation.PostConstruct;
import javax.ejb.Schedule;
import javax.ejb.Singleton;
import javax.ejb.Startup;

import br.com.test.ilegra.schedule.service.FilesProcessService;

@Singleton
@Startup
public class ReadFilesScheduler {

	@PostConstruct
	public void init() {
		System.out.println("Service created");
	}
	
	/**
	 * Method for scheduling task execution
	 */
	@Schedule(hour = "*", minute = "*", second = "*/10", persistent = false)
	public void runTask() {
		try {
			
			System.out.println("Task started");
			FilesProcessService.doProcess();
			System.out.println("Task executed successfully.");
			
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
	}

}
