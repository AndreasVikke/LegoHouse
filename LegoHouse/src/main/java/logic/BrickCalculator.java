package logic;

import data.models.BrickLayer;
import data.models.BrickSide;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Andreas Vikke
 */
public class BrickCalculator {
    private static boolean doorCheck;
    private static boolean windowCheck;
    private static boolean bound;
    
    public static List<BrickLayer> calcBricks(int length, int width, int height, boolean dc, boolean wc, boolean b) {
        List<BrickLayer> brickLayers = new ArrayList();
        doorCheck = dc;
        windowCheck = wc;
        bound = b;
        
        for(int i = 1; i <= height; i++)
            brickLayers.add(calcLayer(length, width, i));
            
        return brickLayers;
    }
    
    private static BrickLayer calcLayer(int length, int width, int layer) {
        BrickLayer brickLayer = new BrickLayer();
        boolean doorLayer, windowLayer;
        
        // if layer is 1, 2 or 3 add window
        if(layer == 1 || layer == 2 || layer == 3)
            doorLayer = true;
        else
            doorLayer = false;
        
        // if Layer is 2 or 3 add window
        if(layer == 2 || layer == 3)
            windowLayer = true;
        else
            windowLayer = false;
        
        // Create 4 sides, 2 length and 2 width
        brickLayer.addSide(calcSide(length, layer, doorLayer, windowLayer, 1));
        brickLayer.addSide(calcSide(length, layer, doorLayer, windowLayer, 2));
        brickLayer.addSide(calcSide(width, layer, doorLayer, windowLayer, 0));
        brickLayer.addSide(calcSide(width, layer, doorLayer, windowLayer, 0));
        
        return brickLayer;
    }
    
    private static BrickSide calcSide(int value, int layer, boolean doorLayer, boolean windowLayer, int type) {
        // Checks if side is length or width and change where to remove the 4 dots
        if (layer % 2 > 0 && type != 0 && bound)
            value -= 4;
        else if (layer % 2 == 0 && type == 0 && bound)
            value -= 4;
        else if(!bound && type == 0)
            value -= 4;
         
        // Removes dots for door if door is checked
        if(doorLayer && doorCheck && type == 1)
            value -= 2;
        
        // Removes dots for window if door is checked
         if(windowLayer && windowCheck && type == 2)
            value -= 2;
        
         // Calculate brick amount
        int calc2x4 = value / 4;
        int calc2x2 = (value % 4) / 2;
        int calc2x1 = (value % 4) % 2;
        
        return new BrickSide(calc2x4, calc2x2, calc2x1);
    }
}
