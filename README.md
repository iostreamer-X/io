Well with Grid(http://amethystlabs.org/grid), I had 11 TCP servers, and I managed to screw them all.
The design I implemented(a year ago when I was sorta rookie) was to start a server, let client connect to it,
when done close the server and then start again. I think you can see what is happening here, I closed the whole
ServerSocket when the client was finished and started it again to listen for new connections. Screwed up stuff,
I know. 

And now finally when I am done with the network(99.999999%), here is the updated Rx approach. 
The io.streamer.Server class, takes 2 parameters, a port number, and a function.
The start() method creates a thread which listens for incoming client connections, and returns an
Observable which emits the result of the function you passed to start(). That function must take
one argument af Socket type.

Here is an example:

```scala
import java.io.{DataInputStream,DataOutputStream}
import scala.concurrent.ExecutionContext.Implicits.global
import io.streamer.Server
import io.streamer.Client

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
```
