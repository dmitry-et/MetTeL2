package mettel.util;

import java.io.PrintWriter;

public class MettelReport {

	private PrintWriter out = null;
	private PrintWriter err = null;
	
	private boolean quiet = false;
	
	@SuppressWarnings("unused")
	private MettelReport() {}

	public MettelReport(PrintWriter out, PrintWriter err, boolean quiet){
		super();
		this.out = out;
		this.err = err;
		this.quiet = quiet;
	}
	
	public void report(String msg){
		if(!quiet){
			out.println(msg);
			out.flush();
		}
	}
	
	public void error(String msg){
		err.println(msg);
		err.flush();
	}
}
