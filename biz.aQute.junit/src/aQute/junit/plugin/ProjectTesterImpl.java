	package aQute.junit.plugin;

import java.util.*;

import aQute.bnd.build.*;
import aQute.bnd.service.*;
import aQute.junit.constants.*;
import aQute.lib.osgi.*;

public class ProjectTesterImpl extends ProjectTester implements TesterConstants, EclipseJUnitTester {
	int		port	= -1;
	String	host;
	Project	project;
	String	report;
	boolean	prepared;

	public ProjectTesterImpl(Project project) throws Exception {
		super(project);
		this.project = project;
	}

	public boolean prepare() throws Exception {
		if (!prepared) {
			prepared = true;
			ProjectLauncher launcher = getProjectLauncher();
			launcher.setReport(true);
			if (port > 0) {
				launcher.getRunProperties().put(TESTER_PORT, "" + port);
				if (host != null)
					launcher.getRunProperties().put(TESTER_HOST, "" + host);

			}
			launcher.getRunProperties().put(TESTER_DIR, getReportDir().getAbsolutePath());
			launcher.getRunProperties().put(TESTER_CONTINUOUS, ""+getContinuous());

			Collection<String> testnames = getTests();
			if (testnames.size() > 0) {
				launcher.getRunProperties().put(TESTER_NAMES, Processor.join(testnames));
			}
			launcher.prepare();
		}
		return true;
	}

	public int test() throws Exception {
		prepare();
		return getProjectLauncher().launch();
	}

	public void setHost(String host) {
		this.host = host;
	}

	public void setPort(int port) {
		this.port = port;
	}

}
