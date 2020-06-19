package at.fhj.ima.forumengine.forumengine

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer

@SpringBootApplication
class ForumengineApplication : SpringBootServletInitializer() {
}
fun main(args: Array<String>) {
	runApplication<ForumengineApplication>(*args)
}
