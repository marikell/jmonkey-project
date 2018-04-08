/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package entidades;

import com.jme3.animation.AnimChannel;
import com.jme3.animation.AnimControl;
import com.jme3.animation.AnimEventListener;
import com.jme3.asset.AssetManager;
import com.jme3.bullet.collision.PhysicsCollisionEvent;
import com.jme3.bullet.collision.PhysicsCollisionListener;
import com.jme3.scene.Node;

/**
 *
 * @author Marianne
 */
public class Model extends Node implements AnimEventListener, PhysicsCollisionListener {
    
    private String name;
    private float posX;
    private float posY;
    private float posZ;
    private AnimControl control;
            
    
    public Model(String name,float posX, float posY, float posZ){
        
        super(name);
        this.name = name;
        this.posX = posX;
        this.posY = posY;
        this.posZ = posZ;
        
        
    }
    
    public void Initialize(Object model){
        setUserData(name, model);
        setLocalTranslation(posX, posY, posZ);
        control = getControl(AnimControl.class);

    }

    @Override
    public void onAnimCycleDone(AnimControl control, AnimChannel channel, String animName) {
    }

    @Override
    public void onAnimChange(AnimControl control, AnimChannel channel, String animName) {
    }

    @Override
    public void collision(PhysicsCollisionEvent event) {
    }
    
    
    
}
