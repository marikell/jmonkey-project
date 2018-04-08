/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package entidades;

import com.jme3.animation.AnimChannel;
import com.jme3.animation.AnimControl;
import com.jme3.animation.LoopMode;
import com.jme3.asset.AssetManager;
import com.jme3.bullet.BulletAppState;
import com.jme3.math.Vector3f;
import com.jme3.scene.Node;

/**
 *
 * @author Marianne
 */
public class Ninja extends Model{
    
    public Ninja(String name, Vector3f position, Vector3f rotation, BulletAppState bulletAppState, String mesh, AssetManager assetManager) {
        super(name, position, rotation, bulletAppState, mesh, assetManager);
    }
    
    @Override
    public void onAnimCycleDone(AnimControl control, AnimChannel channel, String animName) {
        
        animate(animName,"Attack3", 0f, LoopMode.DontLoop,1f);
         
    }
    @Override
    public void animate(String beforeAnimName,String afterAnimName, float time, LoopMode loopMode, float speed){
        
         if (beforeAnimName.equals("Walk")) {
            getAnimationChannel().setAnim(afterAnimName, 0.04f);
            getAnimationChannel().setLoopMode(LoopMode.DontLoop);            
            
        }
        
    }
    
   
    
    
}
