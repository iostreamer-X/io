package io.streamer
import java.net.Socket
import scala.concurrent.ExecutionContext.Implicits.global
import scala.concurrent.Future

class Client[T](ip:String, port:Int, execute:Socket => T) {
	def beam():Future[T] ={
		val socket = new Socket(ip,port)
		Future{
			execute(socket)
		}	
	}	
}