package async;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;

@WebListener
public class AppContextListener implements ServletContextListener{
	private static final String EXECUTOR="EXECUTOR";
	@Override
	public void contextInitialized(ServletContextEvent sce) {
		int coreSize = Runtime.getRuntime().availableProcessors();
		ThreadPoolExecutor executor = new ThreadPoolExecutor(
				coreSize,
				coreSize*2,
				60,
				TimeUnit.SECONDS,
				new ArrayBlockingQueue<Runnable>(100));
		sce.getServletContext().setAttribute(EXECUTOR, executor);
	}
	@Override
	public void contextDestroyed(ServletContextEvent sce) {
		ThreadPoolExecutor executor = (ThreadPoolExecutor) sce.getServletContext().getAttribute(EXECUTOR);
		executor.shutdown();
	}

}
