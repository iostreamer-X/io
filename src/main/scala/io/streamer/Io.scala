package io.streamer

import java.io.{DataInputStream,DataOutputStream}
import scala.concurrent.ExecutionContext.Implicits.global

object Io extends App {
  new Server(9000, {
      socket =>
        val inputStream = new DataInputStream(socket.getInputStream)
        var isQuit=false
        val inData = inputStream.readLine match {
            case null => "quit"
            case validString:String => validString
          }
        if(inData == "quit")
          isQuit = true 
        if(inData == "ex")
          throw new Exception("dayum") 

        inputStream.close
        socket.close
        inData
        
      }
  ).start() foreach println

  Thread.sleep(3000)
  new Client("localhost",9000,{
      socket =>
        val outputStream = new DataOutputStream(socket.getOutputStream)
        outputStream.writeUTF("hello")
        outputStream.flush()
        outputStream.close
        socket.close
        
    }
  ).beam()
}
