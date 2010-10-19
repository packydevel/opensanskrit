package org.opensanskrit.application;

import it.sauronsoftware.junique.AlreadyLockedException;
import it.sauronsoftware.junique.JUnique;

import java.awt.Image;
import java.io.File;
import java.net.URL;
import java.net.URLDecoder;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.swing.LookAndFeel;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;

import org.jfacility.lang.MySystem;

import com.sun.java.swing.plaf.nimbus.NimbusLookAndFeel;

import de.javasoft.plaf.synthetica.SyntheticaBlackEyeLookAndFeel;
import de.javasoft.plaf.synthetica.SyntheticaBlackMoonLookAndFeel;
import de.javasoft.plaf.synthetica.SyntheticaBlackStarLookAndFeel;
import de.javasoft.plaf.synthetica.SyntheticaBlueIceLookAndFeel;
import de.javasoft.plaf.synthetica.SyntheticaBlueMoonLookAndFeel;
import de.javasoft.plaf.synthetica.SyntheticaBlueSteelLookAndFeel;
import de.javasoft.plaf.synthetica.SyntheticaGreenDreamLookAndFeel;
import de.javasoft.plaf.synthetica.SyntheticaMauveMetallicLookAndFeel;
import de.javasoft.plaf.synthetica.SyntheticaOrangeMetallicLookAndFeel;
import de.javasoft.plaf.synthetica.SyntheticaSilverMoonLookAndFeel;
import de.javasoft.plaf.synthetica.SyntheticaSimple2DLookAndFeel;
import de.javasoft.plaf.synthetica.SyntheticaSkyMetallicLookAndFeel;
import de.javasoft.plaf.synthetica.SyntheticaStandardLookAndFeel;
import de.javasoft.plaf.synthetica.SyntheticaWhiteVisionLookAndFeel;

public class Application {

	public static Application APPLICATION;
	private static Class<?> CLASS;
	public static String BUILD_NUMBER;
	public static String ROOT_DIRECTORY;
	public static String HOME_DIRECTORY;
	private String name;
	private String author;
	private Image ICON;
	private LookAndFeel applicationLookAndFeel;
	public static boolean IS_JAR;
	public String JAR_FILENAME;

	private Boolean singleInstance = false;

	private Application() {
		Throwable t = new Throwable();
		StackTraceElement[] trace = t.getStackTrace();
		try {
			Class<?> caller = Class.forName(trace[trace.length - 1]
					.getClassName());
			System.out.println(caller.getCanonicalName());
			CLASS = caller;
		} catch (Exception e) {
		}

		ROOT_DIRECTORY = retrieveRootDirectory();
		IS_JAR = isJar();
		JAR_FILENAME = retrieveJarFileName();
	}

	public static Application getInstance() {
		if (APPLICATION == null) {
			return new Application();
		}
		return APPLICATION;
	}

	public static String getBuildNumber() {
		return CLASS.getPackage().getImplementationVersion();
	}

	public String getHomeDirectory() {
		try {
			return getClass().getProtectionDomain().getCodeSource()
					.getLocation().getPath();
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	private boolean isJar() {
		String name = CLASS.getName().replaceAll("\\.", "/") + ".class";
		URL classURL = Thread.currentThread().getContextClassLoader()
				.getResource(name);
		/*
		 * caller is null in case the ressource is not found or not enough
		 * rights, in that case we assume its not jared
		 */
		System.out.println("The classURL is:" + classURL);
		if (classURL == null) {
			return false;
		}

		return classURL.toString().matches("jar\\:.*\\.jar\\!.*");
	}

	private String retrieveJarFileName() {
		if (isJar()) {
			String classFileName = CLASS.getName().replaceAll("\\.", "/")
					+ ".class";
			URL classFileNameURL = Thread.currentThread()
					.getContextClassLoader().getResource(classFileName);
			String classFileNameDecodedURL;

			try {
				classFileNameDecodedURL = URLDecoder.decode(
						classFileNameURL.toString(), "UTF-8");
			} catch (Exception e) {
				e.printStackTrace();
				classFileNameDecodedURL = "";
			}

			Pattern p = Pattern.compile(System.getProperty("file.separator")
					+ "[^" + System.getProperty("file.separator") + "]*\\.jar");

			Matcher m = p.matcher(classFileNameDecodedURL);
			m.find();
			System.out.println(classFileNameDecodedURL.substring(m.start() + 1,
					m.end()));
			return (classFileNameDecodedURL.substring(m.start() + 1, m.end()));

		}
		return "";
	}

	private String retrieveRootDirectory() {
		String loc;
		String rootDirectory;

		if (isJar()) {
			// this is the jar file

			try {
				loc = URLDecoder.decode(CLASS.getProtectionDomain()
						.getCodeSource().getLocation().getFile(), "UTF-8");
			} catch (Exception e) {
				loc = CLASS.getProtectionDomain().getCodeSource().getLocation()
						.getFile();
				System.err.println("failed urldecoding Location: " + loc);
			}
			File appRoot = new File(loc);
			if (appRoot.isFile()) {
				appRoot = appRoot.getParentFile();
			}
			rootDirectory = appRoot.getAbsolutePath();
		} else {
			rootDirectory = System.getProperty("user.home")
					+ System.getProperty("file.separator") + HOME_DIRECTORY
					+ System.getProperty("file.separator");
		}
		System.out.println(rootDirectory);
		return rootDirectory;
	}

	public void start() throws AlreadyStartedApplicationException {
		try {
			if (singleInstance) {
				JUnique.acquireLock(name);
			}
		} catch (AlreadyLockedException e) {
			throw new AlreadyStartedApplicationException();
		}
	}

	public void restart() {
		ArrayList<String> commands = new ArrayList<String>();
		commands.add(MySystem.getJavaHome() + File.separator + "bin"
				+ File.separator + "java");
		commands.add("-jar");
		commands.add(ROOT_DIRECTORY + File.separator + JAR_FILENAME);
		ProcessBuilder pb = new ProcessBuilder(commands);
		// ProcessBuilder pb = new ProcessBuilder("/usr/bin/java", "-jar",
		// ROOT_DIRECTORY + File.separator + JAR_FILENAME);
		pb.redirectErrorStream(true);
		try {
			JUnique.releaseLock(name);
			Process p = pb.start();
		} catch (Exception e) {
			e.printStackTrace();
		}
		shutdown();
	}

	public void shutdown() {
		java.lang.System.exit(0);
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getAuthor() {
		return author;
	}

	public void setAuthor(String author) {
		this.author = author;
	}

	public Image getIcon() {
		return ICON;
	}

	public void setIcon(Image icon) {
		this.ICON = icon;
	}
	
	public List<String> getAvailableLookAndFeel() {
		List<String> l = new ArrayList<String>();
		
		l.add("System");
		l.add("Cross Platform");
		l.add("Nimbus");
		l.add("Synthetica Standard");
		l.add("Synthetica BlackEye");
		l.add("Synthetica BlackMoon");
		l.add("Synthetica BlackStar");
		l.add("Synthetica BlueIce");
		l.add("Synthetica BlueMoon");
		l.add("Synthetica BlueSteel");
		l.add("Synthetica GreenDream");
		l.add("Synthetica MaouveMetallic");
		l.add("Synthetica OrangeMetallic");
		l.add("Synthetica SilverMoon");
		l.add("Synthetica Simple2D");
		l.add("Synthetica SkyMetallic");
		l.add("Synthetica WhiteVision");
		
		return l;
	}

	public void setApplicationLookAndFeel(String lookAndFeelName) {
		LookAndFeel laf = null;

		// String[] li = {"Licensee=AppWork UG",
		// "LicenseRegistrationNumber=289416475", "Product=Synthetica",
		// "LicenseType=Small Business License", "ExpireDate=--.--.----",
		// "MaxVersion=2.999.999"};
		// UIManager.put("Synthetica.license.info", li);
		// UIManager.put("Synthetica.license.key",
		// "C1410294-61B64AAC-4B7D3039-834A82A1-37E5D695");

		try {
			if (lookAndFeelName.equalsIgnoreCase("System")) {
				UIManager.setLookAndFeel(UIManager
						.getSystemLookAndFeelClassName());
				laf = UIManager.getLookAndFeel();
			} else if (lookAndFeelName.equalsIgnoreCase("Cross Platform")) {
				UIManager.setLookAndFeel(UIManager
						.getCrossPlatformLookAndFeelClassName());
				laf = UIManager.getLookAndFeel();
			} else if (lookAndFeelName.equalsIgnoreCase("Nimbus")) {
				laf = new NimbusLookAndFeel();
			} else if (lookAndFeelName.equalsIgnoreCase("Synthetica Standard")) {
				laf = new SyntheticaStandardLookAndFeel();
			} else if (lookAndFeelName.equalsIgnoreCase("Synthetica BlackEye")) {
				laf = new SyntheticaBlackEyeLookAndFeel();
			} else if (lookAndFeelName.equalsIgnoreCase("Synthetica BlackMoon")) {
				laf = new SyntheticaBlackMoonLookAndFeel();
			} else if (lookAndFeelName.equalsIgnoreCase("Synthetica BlackStar")) {
				laf = new SyntheticaBlackStarLookAndFeel();
			} else if (lookAndFeelName.equalsIgnoreCase("Synthetica BlueIce")) {
				laf = new SyntheticaBlueIceLookAndFeel();
			} else if (lookAndFeelName.equalsIgnoreCase("Synthetica BlueMoon")) {
				laf = new SyntheticaBlueMoonLookAndFeel();
			} else if (lookAndFeelName.equalsIgnoreCase("Synthetica BlueSteel")) {
				laf = new SyntheticaBlueSteelLookAndFeel();
			} else if (lookAndFeelName
					.equalsIgnoreCase("Synthetica GreenDream")) {
				laf = new SyntheticaGreenDreamLookAndFeel();
			} else if (lookAndFeelName
					.equalsIgnoreCase("Synthetica MaouveMetallic")) {
				laf = new SyntheticaMauveMetallicLookAndFeel();
			} else if (lookAndFeelName
					.equalsIgnoreCase("Synthetica OrangeMetallic")) {
				laf = new SyntheticaOrangeMetallicLookAndFeel();
			} else if (lookAndFeelName
					.equalsIgnoreCase("Synthetica SilverMoon")) {
				laf = new SyntheticaSilverMoonLookAndFeel();
			} else if (lookAndFeelName.equalsIgnoreCase("Synthetica Simple2D")) {
				laf = new SyntheticaSimple2DLookAndFeel();
			} else if (lookAndFeelName
					.equalsIgnoreCase("Synthetica SkyMetallic")) {
				laf = new SyntheticaSkyMetallicLookAndFeel();
			} else if (lookAndFeelName
					.equalsIgnoreCase("Synthetica WhiteVision")) {
				laf = new SyntheticaWhiteVisionLookAndFeel();
			}
		} catch (ClassNotFoundException e) {
			// error.launch(e, getClass());
		} catch (UnsupportedLookAndFeelException e) {
			// error.launch(e, getClass());
		} catch (IllegalAccessException e) {
			// error.launch(e, getClass());
		} catch (InstantiationException e) {
			// error.launch(e, getClass());
		} catch (ParseException e) {
			// error.launch(e, getClass());
		}

		this.applicationLookAndFeel = laf;
	}

	public void enableSingleInstance(boolean flag) {
		this.singleInstance = flag;
	}
}