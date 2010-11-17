package org.opensanskrit.application;

import java.util.TreeMap;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;

/**
 *
 * @author luca
 */
public class MyLookAndFeel {
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
    private final String CROSS_PLATFORM = "Cross Platform";
    private final String NIMBUS = "Nimbus";

    private TreeMap<String, String> lafs = new TreeMap<String, String>();
    private String actualLaf;

    public void addSyntheticaFree(){
        lafs.put(SYNTHETICA_BLACKMOON, "de.javasoft.plaf.synthetica.SyntheticaBlackMoonLookAndFeel");
        lafs.put(SYNTHETICA_BLACKSTAR, "de.javasoft.plaf.synthetica.SyntheticaBlackStarLookAndFeel");
        lafs.put(SYNTHETICA_BLUEICE, "de.javasoft.plaf.synthetica.SyntheticaBlueIceLookAndFeel");
        lafs.put(SYNTHETICA_BLUESTEEL, "de.javasoft.plaf.synthetica.SyntheticaBlueSteelLookAndFeel");
        lafs.put(SYNTHETICA_GREENDREAM, "de.javasoft.plaf.synthetica.SyntheticaGreenDreamLookAndFeel");
        lafs.put(SYNTHETICA_SILVERMOON, "de.javasoft.plaf.synthetica.SyntheticaSilverMoonLookAndFeel");
    }

    public void addSyntheticaStandard(){
        lafs.put(SYNTHETICA_STANDARD, "de.javasoft.plaf.synthetica.SyntheticaStandardLookAndFeel");
    }

    public void addSyntheticaNotFree(){
        lafs.put(SYNTHETICA_BLACKEYE, "de.javasoft.plaf.synthetica.SyntheticaBlackEyeLookAndFeel");
        lafs.put(SYNTHETICA_BLUEMOON, "de.javasoft.plaf.synthetica.SyntheticaBlueMoonLookAndFeel");
        lafs.put(SYNTHETICA_MAOUVEMETALLIC, "de.javasoft.plaf.synthetica.SyntheticaMaouveMetallicLookAndFeel");
        lafs.put(SYNTHETICA_ORANGEMETALLIC, "de.javasoft.plaf.synthetica.SyntheticaOrangeMetallicLookAndFeel");
        lafs.put(SYNTHETICA_SIMPLE2D, "de.javasoft.plaf.synthetica.SyntheticaSimple2DLookAndFeel");
        lafs.put(SYNTHETICA_SKYMETALLIC, "de.javasoft.plaf.synthetica.SyntheticaSkyMetallicLookAndFeel");
        lafs.put(SYNTHETICA_WHITEVISION, "de.javasoft.plaf.synthetica.SyntheticaWhiteVisionLookAndFeel");
    }

    public void addOtherLAF() {
        //availableLafs.add("System");
        lafs.put(CROSS_PLATFORM, UIManager.getCrossPlatformLookAndFeelClassName());
        lafs.put(NIMBUS, "com.sun.java.swing.plaf.nimbus.NimbusLookAndFeel");
    }

    public String[] getAvailableLafs() {
        return lafs.navigableKeySet().toArray(new String[lafs.size()]);
    }

    public void setLookAndFeel(String key) throws NotAvailableLookAndFeelException{
        try{
            UIManager.setLookAndFeel(lafs.get(key));
            actualLaf = key;
        //} catch (ClassNotFoundException e) {
        //} catch (IllegalAccessException e) {
        //} catch (InstantiationException e) {
        //} catch (UnsupportedLookAndFeelException e) {
        } catch (Exception e) {
            throw new NotAvailableLookAndFeelException();
        }
    }
    public void setLookAndFeel() {
        try{
            UIManager.setLookAndFeel(lafs.get(CROSS_PLATFORM));
            actualLaf = CROSS_PLATFORM;
        //} catch (ClassNotFoundException e) {
        //} catch (IllegalAccessException e) {
        //} catch (InstantiationException e) {
        //} catch (UnsupportedLookAndFeelException e) {
        } catch (Exception e) {}
    }
}