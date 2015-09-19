package io.streamer

import akka.actor._
import scala.concurrent.Future
import scala.concurrent.ExecutionContext.Implicits.global
import java.net.{ServerSocket, Socket}
import rx.lang.scala.{JavaConversions, Observable}

class Server[T](port:Int, execute:Socket => T) {
	def start()={
		Observable[T](
			observer => {
				new Thread {
					override def run() = {
						val serverSocket = new ServerSocket(port)
						while(true){
							val socket = serverSocket.accept()
							new Thread{
								override def run() = {
									try{
										observer.onNext(execute(socket))
									}
									catch{
										case t:Throwable =>
											socket.close
									}
								}
							}.start
						}
					}
				}.start()
			}
		)
		
	}
}