package com.facilitymanagement.View;


//import org.springframework.context.ApplicationContext;
//import org.springframework.context.support.ClassPathXmlApplicationContext;


import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.ComponentScan;

@ComponentScan("com.facilitymanagement")
@SpringBootApplication
public class Client {

	@SuppressWarnings("unused")
	public static void main(String[] args) {
		ConfigurableApplicationContext context = SpringApplication.run(Client.class,args);

		try {

			FacilityClient facilityClient = context.getBean(FacilityClient.class);
			MaintainenceClient maintenanceClient = context.getBean(MaintainenceClient.class);
			UseRequestClient userequestClient = context.getBean(UseRequestClient.class);
			InspectionClient inspectionClient = context.getBean(InspectionClient.class);
			UserClient userClient = context.getBean(UserClient.class);



			//maintenanceClient.start();
			//userequestClient.start();
			//inspectionClient.start();
			//facilityClient.start();
			userClient.start();

		} catch (Exception e) {
			System.out.println(e);
		}



	}

}
