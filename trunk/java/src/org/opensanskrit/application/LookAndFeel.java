package org.opensanskrit.application;

import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author luca
 */
class LookAndFeel {
    private List<String> availableLafs = new ArrayList<String>();

    public void addSyntheticaFree(){
        availableLafs.add("Synthetica BlackMoon");
        availableLafs.add("Synthetica BlackStar");
        availableLafs.add("Synthetica BlueIce");
        availableLafs.add("Synthetica BlueSteel");
        availableLafs.add("Synthetica GreenDream");
        availableLafs.add("Synthetica SilverMoon");
    }

    public void addSyntheticaStandard(){
        availableLafs.add("Synthetica Standard");
    }

    public void addSyntheticaNotFree(){
        availableLafs.add("Synthetica BlackEye");
        availableLafs.add("Synthetica BlueMoon");
        availableLafs.add("Synthetica MaouveMetallic");
        availableLafs.add("Synthetica OrangeMetallic");
        availableLafs.add("Synthetica Simple2D");
        availableLafs.add("Synthetica SkyMetallic");
        availableLafs.add("Synthetica WhiteVision");
    }

    public void addOtherLAF() {
        availableLafs.add("System");
        availableLafs.add("Cross Platform");
        availableLafs.add("Nimbus");
    }

    public List<String> getAvailableLafs() {
        return availableLafs;
    }
}