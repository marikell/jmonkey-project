/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package entidades;

import com.jme3.animation.AnimChannel;
import com.jme3.animation.AnimControl;
import com.jme3.animation.AnimEventListener;
import com.jme3.animation.LoopMode;
import com.jme3.asset.AssetManager;
import com.jme3.bullet.BulletAppState;
import com.jme3.bullet.collision.PhysicsCollisionEvent;
import com.jme3.bullet.collision.PhysicsCollisionListener;
import com.jme3.math.Vector3f;
import com.jme3.scene.Node;
import mygame.Main;

/**
 *
 * @author Marianne
 */
public class Model extends Node implements AnimEventListener, PhysicsCollisionListener {
    
    private String name;
    private float posX;
    private float posY;
    private float posZ;
    private final AnimControl animationControl;
    private final AnimChannel animationChannel;            

    public String getName() {
        return name;
    }
    
    public AnimChannel getAnimationChannel() {
        return animationChannel;
    }
    
      public AnimControl getAnimationControl() {
        return animationControl;
    }

    public void setName(String name) {
        this.name = name;
    }

    public float getPosX() {
        return posX;
    }

    public void setPosX(float posX) {
        this.posX = posX;
    }

    public float getPosY() {
        return posY;
    }

    public void setPosY(float posY) {
        this.posY = posY;
    }

    public float getPosZ() {
        return posZ;
    }

    public void setPosZ(float posZ) {
        this.posZ = posZ;
    }
    
    public Model(String name,Vector3f position,Vector3f rotation, BulletAppState bulletAppState,String mesh, AssetManager assetManager){
        
        super(name);
        this.name = name;
        this.posX = position.x;
        this.posY = position.y;
        this.posZ = position.z;
        Node model = (Node) assetManager.loadModel(mesh);
        setLocalTranslation(position);
        rotate(rotation.x, rotation.y,rotation.z);
        animationControl = model.getControl(AnimControl.class);
        animationChannel = animationControl.createChannel();
        attachChild(model);

    }
    
    public void animationListener(Main listener, String animation, float time){
        animationControl.addListener(listener);
        animationChannel.setAnim(animation, time);
    }
    
    public void animate(String beforeAnimName,String afterAnimName, float time, LoopMode loopMode, float speed){}

    @Override
    public void onAnimCycleDone(AnimControl control, AnimChannel channel, String animName) {}

    @Override
    public void onAnimChange(AnimControl control, AnimChannel channel, String animName) {
    }

    @Override
    public void collision(PhysicsCollisionEvent event) {
    }
    
    
    
}
