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
import com.jme3.bullet.control.RigidBodyControl;
import com.jme3.math.Vector3f;
import com.jme3.scene.Node;

/**
 *
 * @author Marianne
 */
public class Ninja extends Model{
    
    private Boolean walkToLeft;
    private final Vector3f ninjaScale = new Vector3f(0f,0f,0f);
    private int numLife;
    
    public Ninja(String name, Vector3f position, Vector3f rotation, BulletAppState bulletAppState, String mesh, AssetManager assetManager, Boolean walkToLeft,int numLife) {
        super(name, position, rotation, bulletAppState, mesh, assetManager);
        this.walkToLeft = walkToLeft;
        this.numLife = numLife;
    }
    
    @Override
    public void initEngine(){
        super.initEngine();       
    
    }
    
    @Override
    public void onAnimCycleDone(AnimControl control, AnimChannel channel, String animName) {
        animate(animName,"Attack3", 0f, LoopMode.DontLoop,1f);
    }

    public int getNumLife() {
        return numLife;
    }

    public void setNumLife(int numLife) {
        this.numLife = numLife;
    }
    @Override
    public void animate(String beforeAnimName,String afterAnimName, float time, LoopMode loopMode, float speed){
        
       if (beforeAnimName.equals("Walk") && isColliding()) {
            getAnimationChannel().setAnim(afterAnimName, time);
            getAnimationChannel().setLoopMode(loopMode);            
            
        }
        
    }
    
    public void automaticWalkWhenIsNotColliding(Vector3f value) throws Exception {
        if(super.isColliding()) {
           return; 
        }
        
        if(this.walkToLeft) {
            value = new Vector3f(-value.x, value.y, value.z);
        }
        
        super.moveModelWithRigidBody(value);
    }
    
    public void startCollider(){
       startCollider(0,70,80f);
    }

    public AnimChannel getAnimationChannel() {
        return animationChannel;
    }

    public AnimControl getAnimationControl() {
        return animationControl;
    }

    public RigidBodyControl getRigidBodyControl() {
        return rigidBodyControl;
    }
   
    
    
}
