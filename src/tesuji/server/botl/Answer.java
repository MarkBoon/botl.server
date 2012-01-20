package tesuji.server.botl;

import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.PathParam;

import com.avatar_reality.ai.RemoteBotInterface;

// POJO, no interface no extends

// The class registers its methods for the HTTP GET request using the @GET annotation. 
// Using the @Produces annotation, it defines that it can deliver several MIME types,
// text, XML and HTML. 

// The browser requests per default the HTML MIME type.

//Sets the path to base URL + /hello
@Path("/answer/{name}/{input}")
public class Answer
{
	// This method is called if TEXT_PLAIN is request
	@GET
	@Produces(MediaType.TEXT_PLAIN)
	public String sayPlainTextHello(@PathParam("name") String name, @PathParam("input") String input)
	{
		return say(name,input);
	}

	// This method is called if XML is request
	@GET
	@Produces(MediaType.TEXT_XML)
	public String sayXMLHello(@PathParam("name") String name,@PathParam("input") String input)
	{
		return "<?xml version=\"1.0\"?>" + "<say> " + say(name,input) + "</say>";
	}

	// This method is called if HTML is request
	@GET
	@Produces(MediaType.TEXT_HTML)
	public String sayHtmlHello(@PathParam("name") String name,@PathParam("input") String input)
	{
		return "<html> " + "<title>" + "Hello" + "</title>"
				+ "<body><h1>" + say(name,input) + "</body></h1>" + "</html> ";
	}

	private String say(String name, String input)
	{
//		return input;
//		 if (System.getSecurityManager() == null)
//		 {
//			 System.setSecurityManager(new SecurityManager());
//	     }
	     try 
	     {
            Registry registry = LocateRegistry.getRegistry("localhost",1098);
            RemoteBotInterface bot = (RemoteBotInterface) registry.lookup("RemoteBot");
            return bot.say(name, input);
            
	     } 
	     catch (Exception e)
	     {
	    	 return "RemoteBot exception:"+e.getMessage();
	     }
	}
}