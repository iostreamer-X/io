package io.streamer

import akka.actor._
import java.net.{ServerSocket, Socket}

class ServerConnection(execute:Socket => Unit) extends Actor{
	def receive={
		case socket:Socket =>
			execute(socket)

		case _ =>
	}
}

class Server(port:Int, system:ActorSystem, execute:Socket => Unit) {
	def start()={
		new Thread {
			override def run() = {
				val serverSocket = new ServerSocket(port)
				while(true){
					val socket = serverSocket.accept()
					system.actorOf(Props(new ServerConnection(execute))) ! socket
				}
			}
		}.start()
	}
}