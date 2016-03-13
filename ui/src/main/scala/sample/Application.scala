package fr.sysf.sample

import javax.servlet.http.{Cookie, HttpServletRequest, HttpServletResponse}
import javax.servlet.{Filter, FilterChain}

import org.springframework.boot.SpringApplication
import org.springframework.boot.autoconfigure.security.oauth2.client.EnableOAuth2Sso
import org.springframework.boot.autoconfigure.{EnableAutoConfiguration, SpringBootApplication}
import org.springframework.cloud.netflix.zuul.EnableZuulProxy
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter
import org.springframework.security.web.csrf.CsrfFilter
import org.springframework.security.web.csrf.CsrfToken
import org.springframework.security.web.csrf.CsrfTokenRepository
import org.springframework.security.web.csrf.HttpSessionCsrfTokenRepository
import org.springframework.web.filter.OncePerRequestFilter
import org.springframework.web.util.WebUtils

/**
 * Created by florent on 05/03/2016.
 */
@SpringBootApplication
object Application extends App {
  SpringApplication.run(classOf[ApplicationConfig]);
}

@EnableAutoConfiguration
@EnableZuulProxy
@EnableOAuth2Sso
class ApplicationConfig extends WebSecurityConfigurerAdapter {

  override def configure(http: HttpSecurity): Unit = {
    http.antMatcher("/**").authorizeRequests.antMatchers("/index.html", "/home.html", "/")
      .permitAll.anyRequest.authenticated
      .and.csrf.csrfTokenRepository(csrfTokenRepository)
      .and.addFilterAfter(csrfHeaderFilter, classOf[CsrfFilter])
  }

  def csrfHeaderFilter: Filter = {

     new OncePerRequestFilter {

      override def doFilterInternal(httpServletRequest: HttpServletRequest, httpServletResponse: HttpServletResponse, filterChain: FilterChain): Unit = {

        val csrf = httpServletRequest.getAttribute(classOf[CsrfToken].getName).asInstanceOf[CsrfToken]

        if (csrf != null) {
          val myToken = csrf.getToken
          var cookie: Cookie = WebUtils.getCookie(httpServletRequest, "XSRF-TOKEN")

          if (cookie == null || myToken != null && !myToken.equals(cookie.getValue)) {
            cookie = new Cookie("XSRF-TOKEN", myToken)
            cookie.setPath("/")
            httpServletResponse.addCookie(cookie)
          }
        }
        filterChain.doFilter(httpServletRequest, httpServletResponse)
      }
    }

  }


  private def csrfTokenRepository: CsrfTokenRepository = {
    val repository = new HttpSessionCsrfTokenRepository
    repository.setHeaderName("X-XSRF-TOKEN")
    repository
  }
}
