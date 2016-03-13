package fr.sysf.sample

import java.security.Principal

import org.springframework.boot.SpringApplication
import org.springframework.boot.autoconfigure.{EnableAutoConfiguration, SpringBootApplication}
import org.springframework.context.annotation.Import
import org.springframework.security.oauth2.config.annotation.web.configuration.{EnableAuthorizationServer, EnableResourceServer}
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
@EnableAuthorizationServer
@Import(Array(classOf[AuthserverController]))
class ApplicationConfig


@RestController
class AuthserverController {

  @RequestMapping(Array("/user"))
  def user(user: Principal): Principal = user

}

/*
@Configuration
@EnableAuthorizationServer
class AuthorizationServerConfig extends AuthorizationServerConfigurerAdapter {

  @Autowired
  private val authenticationManager: AuthenticationManager = null


  override def configure(security: AuthorizationServerSecurityConfigurer): Unit = {
    super.configure(security)
  }

  override def configure(endpoints: AuthorizationServerEndpointsConfigurer): Unit = {
    endpoints.authenticationManager(authenticationManager)
  }

  override def configure(clients: ClientDetailsServiceConfigurer): Unit = {
    clients.inMemory()
      .withClient("acme")
      .secret("acmesecret")
      .authorizedGrantTypes("authorization_code", "refresh_token", "password")
      .scopes("openid")
  }

}

@Configuration
@RestController
@EnableResourceServer
@Order(-20)
class ResourceServiceConfig extends WebSecurityConfigurerAdapter {

  @Autowired
   override val authenticationManager: AuthenticationManager = null

  override def configure(auth: AuthenticationManagerBuilder): Unit = {
    auth.parentAuthenticationManager(authenticationManager)
  }

  override def configure(http: HttpSecurity): Unit = {
    http
      .formLogin().loginPage("/login").permitAll()
      .and()
      .requestMatchers().antMatchers("/login", "/oauth/authorize", "/oauth/confirm_access")
      .and()
      .authorizeRequests().anyRequest().authenticated()
  }

  @RequestMapping(Array("/user"))
  def user(user: Principal): Principal = user

}
*/