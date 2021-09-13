package async;
import java.io.IOException;

import javax.servlet.AsyncEvent;
import javax.servlet.AsyncListener;
import javax.servlet.annotation.WebListener;

@WebListener
public class AppAsyncListener implements AsyncListener {

	@Override
	public void onComplete(AsyncEvent asyncEvent) throws IOException {
		System.out.println("AppAsyncListener onComplete :"+Thread.currentThread().getName());
		//cleanup
	}

	@Override
	public void onError(AsyncEvent asyncEvent) throws IOException {
		System.out.println("AppAsyncListener onError :"+Thread.currentThread().getName());
		//error response to client
	}

	@Override
	public void onStartAsync(AsyncEvent asyncEvent) throws IOException {
		System.out.println("AppAsyncListener onStartAsync: "+Thread.currentThread().getName());
		//log the event here
	}

	@Override
	public void onTimeout(AsyncEvent asyncEvent) throws IOException {
		
		System.out.println("AppAsyncListener onTimeout: "+Thread.currentThread().getName());
		//send appropriate response to client

	}
}