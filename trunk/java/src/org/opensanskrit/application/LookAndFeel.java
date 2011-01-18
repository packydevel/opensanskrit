package org.opensanskrit.application;

import java.util.TreeMap;

import javax.swing.UIManager;

import org.opensanskrit.exception.NotAvailableLookAndFeelException;
/**
 *
 * @author luca
 */
public class LookAndFeel {
    private final String JAVA_METAL = "Metal";
    private final String JAVA_MOTIF = "Motif";
    private final String JAVA_SYSTEM = "System";
    private final String JAVA_CROSS_PLATFORM = "Cross Platform";
    private final String JAVA_NIMBUS = "Nimbus";
    private final String JAVA_WINDOWS = "Windows";
    private final String JAVA_MAC = "Mac";
    private final String JTATTOO_ACRYL = "JTattoo Acryl";
    private final String JTATTOO_AERO = "JTattoo Aero";
    private final String JTATTOO_ALUMINIUM = "JTattoo Aluminium";
    private final String JTATTOO_BERNSTEIN = "JTattoo Bernstein";
    private final String JTATTOO_FAST = "JTatoo Fast";
    private final String JTATTOO_GRAPHITE = "JTattoo Graphite";
    private final String JTATTOO_HIFI = "JTattoo HiFi";
    private final String JTATTOO_LUNA = "JTattoo Luna";
    private final String JTATTOO_MCWIN = "JTattoo_McWin";
    private final String JTATTOO_MINT = "JTattoo Mint";
    private final String JTATTOO_NOIRE = "JTattoo Noire";
    private final String JTATTOO_SMART = "JTattoo Smart";
    private final String JTATTOO_CUSTOM = "JTattoo Custom";
    private final String SYNTHETICA_STANDARD = "Synthetica Standard";
    private final String SYNTHETICA_BLACKMOON = "Synthetica BlackMoon";
    private final String SYNTHETICA_BLACKSTAR = "Synthetica BlackStar";
    private final String SYNTHETICA_BLUEICE = "Synthetica BlueIce";
    private final String SYNTHETICA_BLUESTEEL = "Synthetica BlueSteel";
    private final String SYNTHETICA_GREENDREAM = "Synthetica GreenDream";
    private final String SYNTHETICA_SILVERMOON = "Synthetica SilverMoon";
    private final String SYNTHETICA_BLACKEYE = "Synthetica BlackEye";
    private final String SYNTHETICA_BLUEMOON = "Synthetica BlueMoon";
    private final String SYNTHETICA_MAOUVEMETALLIC = "Synthetica MaouveMetallic";
    private final String SYNTHETICA_ORANGEMETALLIC = "Synthetica OrangeMetallic";
    private final String SYNTHETICA_SIMPLE2D = "Synthetica Simple2D";
    private final String SYNTHETICA_SKYMETALLIC = "Synthetica SkyMetallic";
    private final String SYNTHETICA_WHITEVISION = "Synthetica WhiteVision";

    private TreeMap<String, String> LAFClassMap = new TreeMap<String, String>();
    private String actualLAF;
    
    public void addJavaLAF() {
    	LAFClassMap.put(JAVA_METAL, "javax.swing.plaf.metal.MetalLookAndFeel");
    	LAFClassMap.put(JAVA_MOTIF, "com.sun.java.swing.plaf.motif.MotifLookAndFeel");
        LAFClassMap.put(JAVA_SYSTEM, UIManager.getSystemLookAndFeelClassName());
        LAFClassMap.put(JAVA_CROSS_PLATFORM, UIManager.getCrossPlatformLookAndFeelClassName());
        LAFClassMap.put(JAVA_NIMBUS, "com.sun.java.swing.plaf.nimbus.NimbusLookAndFeel");
        LAFClassMap.put(JAVA_WINDOWS, "com.sun.java.swing.plaf.windows.WindowsLookAndFeel");
        LAFClassMap.put(JAVA_MAC, "com.sun.java.swing.plaf.mac.MacLookAndFeel");
    }    
    
    public void addJTattooLAF() {
    	LAFClassMap.put(JTATTOO_ACRYL, "com.jtattoo.plaf.acryl.AcrylLookAndFeel");
    	LAFClassMap.put(JTATTOO_AERO , "com.jtattoo.plaf.aero.AeroLookAndFeel");
    	LAFClassMap.put(JTATTOO_ALUMINIUM, "com.jtattoo.plaf.aluminium.AluminiumLookAndFeel");
    	LAFClassMap.put(JTATTOO_BERNSTEIN, "com.jtattoo.plaf.bernstein.BernsteinLookAndFeel");
    	LAFClassMap.put(JTATTOO_FAST, "com.jtattoo.plaf.fast.FastLookAndFeel");
    	LAFClassMap.put(JTATTOO_GRAPHITE, "com.jtattoo.plaf.graphite.GraphiteLookAndFeel");
    	LAFClassMap.put(JTATTOO_HIFI, "com.jtattoo.plaf.hifi.HiFiLookAndFeel");
    	LAFClassMap.put(JTATTOO_LUNA, "com.jtattoo.plaf.luna.LunaLookAndFeel");
    	LAFClassMap.put(JTATTOO_MCWIN, "com.jtattoo.plaf.mcwin.McWinLookAndFeel");
    	LAFClassMap.put(JTATTOO_MINT, "com.jtattoo.plaf.mint.MintLookAndFeel");
    	LAFClassMap.put(JTATTOO_NOIRE, "com.jtattoo.plaf.noire.NoireLookAndFeel");
    	LAFClassMap.put(JTATTOO_SMART, "com.jtattoo.plaf.smart.SmartLookAndFeel");
    	LAFClassMap.put(JTATTOO_CUSTOM, "de.pnwvi.plaf.PNWLookAndFeel");
    }

    public void addSyntheticaStandardLAF(){
        LAFClassMap.put(SYNTHETICA_STANDARD, "de.javasoft.plaf.synthetica.SyntheticaStandardLookAndFeel");
    }
    
    public void addSyntheticaFreeLAF(){
        LAFClassMap.put(SYNTHETICA_BLACKMOON, "de.javasoft.plaf.synthetica.SyntheticaBlackMoonLookAndFeel");
        LAFClassMap.put(SYNTHETICA_BLACKSTAR, "de.javasoft.plaf.synthetica.SyntheticaBlackStarLookAndFeel");
        LAFClassMap.put(SYNTHETICA_BLUEICE, "de.javasoft.plaf.synthetica.SyntheticaBlueIceLookAndFeel");
        LAFClassMap.put(SYNTHETICA_BLUEMOON, "de.javasoft.plaf.synthetica.SyntheticaBlueMoonLookAndFeel");
        LAFClassMap.put(SYNTHETICA_BLUESTEEL, "de.javasoft.plaf.synthetica.SyntheticaBlueSteelLookAndFeel");
        LAFClassMap.put(SYNTHETICA_GREENDREAM, "de.javasoft.plaf.synthetica.SyntheticaGreenDreamLookAndFeel");
        LAFClassMap.put(SYNTHETICA_SILVERMOON, "de.javasoft.plaf.synthetica.SyntheticaSilverMoonLookAndFeel");
    }

    public void addSyntheticaNotFreeLAF(){
        LAFClassMap.put(SYNTHETICA_BLACKEYE, "de.javasoft.plaf.synthetica.SyntheticaBlackEyeLookAndFeel");
        LAFClassMap.put(SYNTHETICA_MAOUVEMETALLIC, "de.javasoft.plaf.synthetica.SyntheticaMaouveMetallicLookAndFeel");
        LAFClassMap.put(SYNTHETICA_ORANGEMETALLIC, "de.javasoft.plaf.synthetica.SyntheticaOrangeMetallicLookAndFeel");
        LAFClassMap.put(SYNTHETICA_SIMPLE2D, "de.javasoft.plaf.synthetica.SyntheticaSimple2DLookAndFeel");
        LAFClassMap.put(SYNTHETICA_SKYMETALLIC, "de.javasoft.plaf.synthetica.SyntheticaSkyMetallicLookAndFeel");
        LAFClassMap.put(SYNTHETICA_WHITEVISION, "de.javasoft.plaf.synthetica.SyntheticaWhiteVisionLookAndFeel");
    }

    public String[] getAvailableLAF() {
        return LAFClassMap.navigableKeySet().toArray(new String[LAFClassMap.size()]);
    }

    public void setLookAndFeel(String key) throws NotAvailableLookAndFeelException{
        try{
            UIManager.setLookAndFeel(LAFClassMap.get(key));
            actualLAF = key;
        } catch (Exception e) {
            throw new NotAvailableLookAndFeelException();
        }
    }
    public void setLookAndFeel() {
        try{
            UIManager.setLookAndFeel(LAFClassMap.get(JAVA_CROSS_PLATFORM));
            actualLAF = JAVA_CROSS_PLATFORM;
        } catch (Exception e) {}
    }
}