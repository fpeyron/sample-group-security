package fr.sysf.sample

import java.util.UUID

import org.springframework.boot.SpringApplication
import org.springframework.boot.autoconfigure.{EnableAutoConfiguration, SpringBootApplication}
import org.springframework.context.annotation.Import
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableResourceServer
import org.springframework.web.bind.annotation.{RequestMapping, RestController}

/**
 * Created by florent on 05/03/2016.
 */
@SpringBootApplication
object Application extends App {
  SpringApplication.run(classOf[ApplicationConfig]);
}

@EnableAutoConfiguration
@EnableResourceServer
@Import(Array(classOf[ServiceController]))
class ApplicationConfig

@RestController
class ServiceController {

  @RequestMapping(Array("/"))
  def home(): Message = {
    return new Message(UUID.randomUUID().toString(), "Hello World")
  }

}


class Message(var id: String, var content: String) {
  def getId = id

  def getContent = content

}