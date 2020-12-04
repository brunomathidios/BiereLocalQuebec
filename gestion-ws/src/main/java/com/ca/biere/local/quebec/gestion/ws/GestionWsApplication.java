package com.ca.biere.local.quebec.gestion.ws;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

import com.ca.biere.local.quebec.commons.ws.repository.base.BaseRepositoryImpl;

@SpringBootApplication
@ComponentScan( {"com.ca.biere.local.quebec.gestion.ws", "com.ca.biere.local.quebec.commons.ws"} )

/** caso exista entity no projeto Gestion:
 * será necessario declarar o pacote aqui tb
 * (exemplo: com.ca.biere.local.quebec.gestion.ws.entite) **/
@EntityScan( {"com.ca.biere.local.quebec.commons.ws"} )

/** foi necessário informar dois pacotes de repositórios porque
 * existem repositórios declarados nos projetos Commons e Gestion **/
@EnableJpaRepositories(
		value = {"com.ca.biere.local.quebec.commons.ws.repository.base",
				"com.ca.biere.local.quebec.gestion.ws"}, 
		repositoryBaseClass = BaseRepositoryImpl.class)
public class GestionWsApplication {
	
	public static void main(String[] args) {
		SpringApplication.run(GestionWsApplication.class, args);
	}

}
