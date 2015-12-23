package labs.amethyst.TheAmethyst

import java.net.{ServerSocket, Socket}

import rx.lang.scala.Observable

class Server[T](port: Int, execute: Socket => T) {
	def start() = {
		val serverSocket = new ServerSocket(port)
		Observable[T](
			observer => {
				new Thread {
					override def run() = {
						while (true) {
							val socket = serverSocket.accept()
							new Thread {
								override def run() = {
									try {
										observer.onNext(execute(socket))
									}
									catch {
										case t: Throwable =>
											socket.close()
									}
								}
							}.start()
						}
					}
				}.start()
			}
		)

	}
}