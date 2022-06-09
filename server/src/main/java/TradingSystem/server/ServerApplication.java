package TradingSystem.server;

import TradingSystem.server.DAL.Repo;
import TradingSystem.server.Service.ManyTrying;
import TradingSystem.server.Service.Trying;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;

import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@SpringBootApplication (exclude = { SecurityAutoConfiguration.class })
public class ServerApplication {

	public static void main(String[] args) {
		Trying trying = new Trying("hihihihi");
		Trying try2 = new Trying("t2");
		Trying try3 = new Trying("t3");
		List<Trying> lst = new ArrayList<>();

		lst.add(trying);
		lst.add(try2);
		lst.add(try3);

		ManyTrying manyTrying = new ManyTrying();
		ManyTrying manyTrying2 = new ManyTrying();
		ManyTrying manyTrying3 = new ManyTrying();
		manyTrying.setLst(lst);

		trying.setOne_to_one(manyTrying);
		try2.setOne_to_one(manyTrying2);
		try3.setOne_to_one(manyTrying3);

		List<ManyTrying> manyTryingList = new ArrayList<>();
		manyTryingList.add(manyTrying);
		trying.setManyTryingList(manyTryingList);


		Map<Integer,ManyTrying> map_many_trying = new HashMap<>();
		map_many_trying.put(1,manyTrying);
		map_many_trying.put(2,manyTrying2);
		map_many_trying.put(3,manyTrying3);

		trying.setManyTryingMap(map_many_trying);

		trying.add_many_trying(manyTrying2,manyTrying3);

		trying.add_to_map_complex_non(manyTrying3,5);

		Repo.persist(manyTrying);
		Repo.persist(manyTrying2);
		Repo.persist(manyTrying3);
		Repo.persist(trying);
		Repo.persist(try2);
		Repo.persist(try3);

		SpringApplication.run(ServerApplication.class, args);
	}
//	@Bean
//	public ServletWebServerFactory servletContainer() {
//		// Enable SSL Trafic
//		TomcatServletWebServerFactory tomcat = new TomcatServletWebServerFactory() {
//			@Override
//			protected void postProcessContext(Context context) {
//				SecurityConstraint securityConstraint = new SecurityConstraint();
//				securityConstraint.setUserConstraint("CONFIDENTIAL");
//				SecurityCollection collection = new SecurityCollection();
//				collection.addPattern("/*");
//				securityConstraint.addCollection(collection);
//				context.addConstraint(securityConstraint);
//			}
//		};
//
//		// Add HTTP to HTTPS redirect
//		tomcat.addAdditionalTomcatConnectors(httpToHttpsRedirectConnector());
//
//		return tomcat;
//	}
//
//	/*
//    We need to redirect from HTTP to HTTPS. Without SSL, this application used
//    port 8082. With SSL it will use port 8443. So, any request for 8082 needs to be
//    redirected to HTTPS on 8443.
//     */
//	private Connector httpToHttpsRedirectConnector() {
//		Connector connector = new Connector(TomcatServletWebServerFactory.DEFAULT_PROTOCOL);
//		connector.setScheme("http");
//		connector.setPort(8080);
//		connector.setSecure(false);
//		connector.setRedirectPort(8443);
//		return connector;
//	}
}
