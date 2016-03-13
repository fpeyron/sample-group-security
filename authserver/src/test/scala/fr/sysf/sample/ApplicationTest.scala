package fr.sysf.sample

import org.junit.Assert._
import org.junit.Test
import org.junit.runner.RunWith
import org.springframework.beans.factory.annotation.Value
import org.springframework.boot.test.{IntegrationTest, SpringApplicationConfiguration, TestRestTemplate}
import org.springframework.http.HttpStatus
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner
import org.springframework.test.context.web.WebAppConfiguration

/**
 * Created by florent on 05/03/2016.
 */
@RunWith(classOf[SpringJUnit4ClassRunner])
@SpringApplicationConfiguration(classes = Array(classOf[ApplicationConfig]))
@WebAppConfiguration
@IntegrationTest(Array("server.port:0"))
class ApplicationTest {

  @Value("${local.server.port}")
  private val port = 0

  private val template = new TestRestTemplate()

  @Test
  def homePageProtected {
    val response = new TestRestTemplate().getForEntity("http://localhost:" + port + "/uaa", classOf[String])

    assertEquals(HttpStatus.UNAUTHORIZED, response.getStatusCode())
    val auth = response.getHeaders().getFirst("WWW-Authenticate")
    assertTrue("Wrong header: " + auth, auth.startsWith("Bearer realm=\""))
  }

  @Test
  def userEndpointProtected {
    val response = template.getForEntity("http://localhost:" + port + "/uaa/user", classOf[String])
    assertEquals(HttpStatus.UNAUTHORIZED, response.getStatusCode())
    val auth = response.getHeaders().getFirst("WWW-Authenticate")
    assertTrue("Wrong header: " + auth, auth.startsWith("Bearer realm=\""))
  }

  @Test
  def authorizationRedirects() {
    val response = template.getForEntity("http://localhost:" + port + "/uaa/oauth/authorize", classOf[String])

    assertEquals(HttpStatus.UNAUTHORIZED, response.getStatusCode())
    val auth = response.getHeaders().getFirst("WWW-Authenticate")
    assertTrue("Wrong header: " + auth, auth.startsWith("Basic realm=\""))
  }

}
