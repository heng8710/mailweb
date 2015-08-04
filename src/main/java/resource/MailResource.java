package resource;

import java.util.regex.Pattern;

import javax.ws.rs.FormParam;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;

import sender.Sender;

import com.google.common.base.Strings;

@Path("")
public class MailResource {
	 
	@GET
	@Produces("text/plain")
    public boolean get(@QueryParam("to")final String _to, 
    		@QueryParam("appKey")final String appKey, 
    		@QueryParam("subject")final String subject, 
    		@QueryParam("text")final String text
    		) {
		return send(_to, appKey, subject, text);
    }
	
	
	@POST
	@Produces("text/plain")
    public boolean post(@FormParam("to")final String _to, 
    		@FormParam("appKey")final String appKey, 
    		@FormParam("subject")final String subject, 
    		@FormParam("text")final String text
    		) {
		return send(_to, appKey, subject, text);
    }
	
	
	
	private boolean send(final String _to, final String appKey, final String subject, final String text){
		final String to = Strings.nullToEmpty(_to).toLowerCase();
		//简单正则判断一下
		if(Strings.isNullOrEmpty(_to) || !to.matches("[^@]*@[a-zA-Z0-9]*\\.com")){
			//邮箱格式不对
			throw new IllegalArgumentException(String.format("to=[%s] incorrect", _to));
		}
		return Sender.send(to, appKey, subject, text);
	}
	
	
	public static void main(String... args){
		Object obj = "ab_cd@qq.com".matches(".*@\\.com");
		boolean find = Pattern.compile("[^@]*@[a-zA-Z0-9]*\\.com").matcher("ac_cd@qq.com").find();
		boolean finda = Pattern.compile(".*@").matcher("ac_cd@").find();
		System.out.println(obj);
		System.out.println(find);
		System.out.println(finda);
		System.out.println(Pattern.compile("@.*").matcher("@ac_cd").find());
		System.out.println(Pattern.compile("\\.com").matcher(".com").find());
	}
	
}
