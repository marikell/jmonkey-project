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
import com.jme3.bullet.collision.shapes.BoxCollisionShape;
import com.jme3.bullet.collision.shapes.CapsuleCollisionShape;
import com.jme3.bullet.collision.shapes.CollisionShape;
import com.jme3.bullet.collision.shapes.MeshCollisionShape;
import com.jme3.bullet.control.RigidBodyControl;
import com.jme3.bullet.util.CollisionShapeFactory;
import com.jme3.math.Vector3f;
import com.jme3.scene.Node;
import mygame.Main;

/**
 *
 * @author Marianne
 */
public class Model extends Node implements AnimEventListener, PhysicsCollisionListener {
    
    private float posX;
    private float posY;
    private float posZ;
    private float rotX;
    private float rotY;
    private float rotZ;
    
    private Boolean isColliding;
    
    private Node nodeToLoadMesh;
    private RigidBodyControl rigidBodyControl;
    
    private AnimControl animationControl;
    private AnimChannel animationChannel;
    private BulletAppState bulletAppState;

    public AnimChannel getAnimationChannel() {
        return animationChannel;
    }
    
      public AnimControl getAnimationControl() {
        return animationControl;
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
        this.rotX = rotation.x;
        this.rotY = rotation.y;
        this.rotZ = rotation.z;
        this.isColliding = false;
        
        this.nodeToLoadMesh = (Node) assetManager.loadModel(mesh);
        this.bulletAppState = bulletAppState;
        
        this.animationControl = this.nodeToLoadMesh.getControl(AnimControl.class);
        this.animationChannel = animationControl.createChannel();
    }
    
    public void initEngine() {
        setLocalTranslation(new Vector3f(this.posX, this.posY, this.posZ));
        rotate(this.rotX, this.rotY, this.rotZ);
     
        attachChild(this.nodeToLoadMesh);
    }
    
    public void moveModelWithRigidBody(Vector3f value) throws Exception{
        super.move(value);
        if (rigidBodyControl == null) {
            throw new Exception("Colisão não iniciada");
        }
        refreshColliderPosition(value);
    }
    
    private void refreshColliderPosition(Vector3f value){
            if(rigidBodyControl == null){
                return;
            }
          rigidBodyControl.setPhysicsLocation(this.getLocalTranslation().add(value));

    }
    
    public void startAnimation(String animation, float time){
        animationControl.addListener(this);
        animationChannel.setAnim(animation, time);
    }
    
    public void startCollider(int mass, float radiusCollision, float heightCollision) {
        /*CollisionShape collisionShape = CollisionShapeFactory.createMeshShape(this.nodeToLoadMesh);
        RigidBodyControl boxPhysicsNode = new RigidBodyControl(collisionShape);
        this.rigidBodyControl = boxPhysicsNode;
        boxPhysicsNode.setMass(mass);
        this.nodeToLoadMesh.addControl(boxPhysicsNode);
        bulletAppState.getPhysicsSpace().add(boxPhysicsNode);*/
        
        CapsuleCollisionShape shape = new CapsuleCollisionShape(radiusCollision,heightCollision);
        RigidBodyControl rigidBodyControl = new RigidBodyControl(shape,100);
        rigidBodyControl.setFriction(0);
        this.rigidBodyControl = rigidBodyControl;
        this.addControl(rigidBodyControl);
        bulletAppState.getPhysicsSpace().add(rigidBodyControl);  

        /*RigidBodyControl ridigBodyControl = new RigidBodyControl(new BoxCollisionShape(), 10);
       rigidBodyControl.setFriction(0);
       this.nodeToLoadMesh.addControl(ridigBodyControl);
        bulletAppState.getPhysicsSpace().add(rigidBodyControl);*/
}
    
    public void animate(String beforeAnimName,String afterAnimName, float time, LoopMode loopMode, float speed){}

    @Override
    public void onAnimCycleDone(AnimControl control, AnimChannel channel, String animName) {}

    @Override
    public void onAnimChange(AnimControl control, AnimChannel channel, String animName) {
    }

    
    public Boolean isColliding() {
        return isColliding; 
    }
    
    public void setIsColliding(Boolean colliding){
        isColliding = colliding;
    }

    @Override
    public void collision(PhysicsCollisionEvent event) {
        

    }
    
    
}
