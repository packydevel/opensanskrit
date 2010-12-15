package org.opensanskrit.application;

import org.opensanskrit.exception.AlreadyStartedApplicationException;
import org.opensanskrit.exception.UnableRestartApplicationException;
import it.sauronsoftware.junique.AlreadyLockedException;
import it.sauronsoftware.junique.JUnique;

import java.awt.Image;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.net.URLDecoder;
import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.jfacility.java.lang.JVM;
import org.jfacility.java.lang.MySystem;

public class Application {

    public static Application APPLICATION;    
    public static String BUILD_NUMBER, ROOT_DIRECTORY, HOME_DIRECTORY;
    public static boolean IS_JAR;

    private static Class<?> CLASS;
    private static boolean DEBUG_FLAG;

    public String JAR_FILENAME;

    private String name, author;
    private Image ICON;    
    private Boolean singleInstance = false;
    private LookAndFeel applicationLookAndFeel;

    private Application() {
        Throwable t = new Throwable();
        StackTraceElement[] trace = t.getStackTrace();
        try {
            Class<?> caller = Class.forName(trace[trace.length - 1].getClassName());
            if (DEBUG_FLAG)
                System.out.println(caller.getCanonicalName());
            CLASS = caller;
        } catch (Exception e) {
        }
        IS_JAR = isJar();
        if (DEBUG_FLAG)
            System.out.println("JAR:" + IS_JAR);
        ROOT_DIRECTORY = retrieveRootDirectory();        
        JAR_FILENAME = retrieveJarFileName();
        applicationLookAndFeel = new LookAndFeel();
    }

    public static Application getInstance(boolean debugFlag) {
        if (APPLICATION == null) {
            DEBUG_FLAG = debugFlag;
            return new Application();
        }
        return APPLICATION;
    }

    public static String getBuildNumber() {
        return CLASS.getPackage().getImplementationVersion();
    }

    public String getHomeDirectory() {
        try {
            return getClass().getProtectionDomain().getCodeSource().getLocation().getPath();
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    private boolean isJar() {
        String name = CLASS.getName().replaceAll("\\.", "/") + ".class";
        URL classURL = Thread.currentThread().getContextClassLoader().getResource(name);
        /*
         * caller is null in case the ressource is not found or not enough
         * rights, in that case we assume its not jared
         */
        if (DEBUG_FLAG)
            System.out.println("The classURL is:" + classURL);
        if (classURL == null)
            return false;

        return classURL.toString().matches("jar\\:.*\\.jar\\!.*");
    }

    private String retrieveJarFileName() {
        if (IS_JAR) {
            String classFileName = CLASS.getName().replaceAll("\\.", "/")
                    + ".class";
            URL classFileNameURL = Thread.currentThread().getContextClassLoader().getResource(classFileName);
            String classFileNameDecodedURL;

            try {
                classFileNameDecodedURL = URLDecoder.decode(
                        classFileNameURL.toString(), "UTF-8");
            } catch (Exception e) {
                e.printStackTrace();
                classFileNameDecodedURL = "";
            }
            
            Pattern p = Pattern.compile("/[^/]*\\.jar");
			
            Matcher m = p.matcher(classFileNameDecodedURL);
            m.find();
            
            if (DEBUG_FLAG) {
            	System.out.println("classFileNameDecodedURL is: " + classFileNameDecodedURL);
                System.out.println(classFileNameDecodedURL.substring(m.start() + 1, m.end()));   
            }
            
            return (classFileNameDecodedURL.substring(m.start() + 1, m.end()));
        }
        return "";
    }

    private String retrieveRootDirectory() {
        String loc;
        String rootDirectory;

        if (IS_JAR) { // this is the jar file
            try {
                String temp = CLASS.getProtectionDomain().getCodeSource().getLocation().getFile();
                loc = URLDecoder.decode(temp, "UTF-8");
                if (DEBUG_FLAG)
                    System.out.println("loc:" + loc);
            } catch (Exception e) {
                loc = CLASS.getProtectionDomain().getCodeSource().getLocation().getFile();
                System.err.println("failed urldecoding Location: " + loc);
            }

            File appRoot = new File(loc);
            if (appRoot.isFile())
                appRoot = appRoot.getParentFile();
            rootDirectory = appRoot.getAbsolutePath();
        } else
            rootDirectory = MySystem.getUserHome() + File.separator + HOME_DIRECTORY
                    + File.separator;
        if (DEBUG_FLAG)
            System.out.println(rootDirectory);
        return rootDirectory;
    }

    public void start() throws AlreadyStartedApplicationException {
        try {
            if (singleInstance) {
                String temp = name;
                if (IS_JAR)
                    temp += "JAR";
                JUnique.acquireLock(temp);
            }
        } catch (AlreadyLockedException e) {
            throw new AlreadyStartedApplicationException();
        }
    }

    public void restart() throws UnableRestartApplicationException {
        ArrayList<String> commands = new ArrayList<String>();
        commands.add(JVM.getCommand());
        commands.add("-jar");
        commands.add(ROOT_DIRECTORY + File.separator + JAR_FILENAME);

        if (DEBUG_FLAG)
            for (int i=0; i<commands.size(); i++)
                System.out.println(commands.get(i));

        ProcessBuilder pb = new ProcessBuilder(commands);
        pb.redirectErrorStream(true);
        JUnique.releaseLock(name);
        try {
            pb.start();
        } catch (IOException e) {
            throw new UnableRestartApplicationException();
        }
        shutdown();
    }

    public void shutdown() {
        System.exit(0);
    }

    public String getName() {
        return name;
    }

    public void setName(String _name) {
        name = _name;
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

    public void enableSingleInstance(boolean flag) {
        this.singleInstance = flag;
    }

    public LookAndFeel getIstanceLAF(){
        return applicationLookAndFeel;
    }
}