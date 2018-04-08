package mygame;

import com.jme3.app.SimpleApplication;
import com.jme3.light.DirectionalLight;
import com.jme3.material.Material;
import com.jme3.math.ColorRGBA;
import com.jme3.math.Vector3f;
import com.jme3.renderer.RenderManager;
import com.jme3.scene.Geometry;
import com.jme3.scene.Node;
import com.jme3.scene.shape.Box;
import com.jme3.animation.AnimChannel;
import com.jme3.animation.AnimControl;
import com.jme3.animation.AnimEventListener;
import com.jme3.animation.LoopMode;
import com.jme3.app.SimpleApplication;
import com.jme3.bullet.BulletAppState;
import com.jme3.bullet.collision.PhysicsCollisionEvent;
import com.jme3.bullet.collision.PhysicsCollisionListener;
import com.jme3.bullet.collision.shapes.CollisionShape;
import com.jme3.bullet.control.RigidBodyControl;
import com.jme3.bullet.util.CollisionShapeFactory;
import com.jme3.input.KeyInput;
import com.jme3.input.controls.ActionListener;
import com.jme3.input.controls.KeyTrigger;
import com.jme3.light.DirectionalLight;
import com.jme3.math.ColorRGBA;
import com.jme3.math.FastMath;
import com.jme3.math.Vector3f;
import com.jme3.scene.Node;
import com.jme3.scene.Spatial;
import entidades.Ninja;
import java.util.List;
import java.util.Set;

/**
 * This is the Main Class of your Game. You should only do initialization here.
 * Move your Logic into AppStates or Controls
 * @author normenhansen
 */
public class Main extends SimpleApplication implements AnimEventListener, PhysicsCollisionListener {
    
    private float posX = 0f;
    private float posY = 0f;
    private float posZ = -600f;
    
    private float rotateSide = 80f;
        
    private AnimChannel channel;
    private AnimControl control;
    
    private BulletAppState bulletAppState;
    
    private Node playerA;
    private Node playerB;
    
    private int playerACollider = 0;
    private int playerBCollider = 0;

    private int lose = 10;
    
    public static void main(String[] args) {
        Main app = new Main();
        app.start();
    }
    
    public void initBulletAppState(){
        bulletAppState = new BulletAppState();
        stateManager.attach(bulletAppState);
        bulletAppState.setDebugEnabled(true);
        bulletAppState.getPhysicsSpace().addCollisionListener(this);


    }

    @Override
    public void simpleInitApp() {
        initBulletAppState();
        createLight(ColorRGBA.Gray);
        playerA = createNinja("ninja1", posX+150, posY, posZ, false, this);
        playerB = createNinja("ninja2", posX-150, posY, posZ, true, this);
    }

    private Node createNinja(String name, float posX, float posY, float posZ, boolean leftSide, Main listener){
        //Node ninja = (Node)assetManager.loadModel("Models/Ninja/Ninja.mesh.xml");
        Ninja ninja = new Ninja(name,posX,posY,posZ);
        ninja.Initialize(assetManager.loadModel("Models/Ninja/Ninja.mesh.xml"));
        //ninja.setLocalTranslation(posX, posY, posZ);
       //ninja.setName(name);
        //ninja.scale(0.025f);
        
        if (leftSide) {
            rotateNinja(ninja,0f,rotateSide,0f);
        } else {
            rotateNinja(ninja,0f,-rotateSide,0f);
        }
        animateNinja(ninja, listener);
        
        rootNode.attachChild(ninja);
        //colisionNinja(ninja);
        return ninja;
    }
    
    private DirectionalLight createLight(ColorRGBA color){
        viewPort.setBackgroundColor(color);
        DirectionalLight light = new DirectionalLight();
        light.setDirection(new Vector3f(-0.1f,-1f,-1).normalizeLocal());
        rootNode.addLight(light);
        return light;
    }
    
    private void rotateNinja(Node ninja, float dgX, float dgY, float dgZ) {
        ninja.rotate(dgX, dgY, dgZ);
    }
    
    private void animateNinja(Node ninja, Main listener) {
        control = ninja.getControl(AnimControl.class);
        control.addListener(listener);
        channel = control.createChannel();
        channel.setAnim("Walk", 0.005f);
    }
    
    private void colisionNinja(Node ninja) {
        //CollisionShape collisionShape = new ;
       // RigidBodyControl boxPhysicsNode = new RigidBodyControl(collisionShape);
        //boxPhysicsNode.setMass(0);
        
        //ninja.addControl(boxPhysicsNode);
        //bulletAppState.getPhysicsSpace().add(boxPhysicsNode);
    }
    
    @Override
    public void simpleUpdate(float tpf) {
        if(!colision(playerA)){
            ninjaWalk(playerA, -0.01f);
            colisionNinja(playerA);
        }
        if(!colision(playerB)){
            ninjaWalk(playerB, 0.01f);
            colisionNinja(playerB);
        }
    }
    
    private void ninjaWalk(Node ninja, float updateWalk) {        
       
        ninja.move(updateWalk, 0, 0);                
    }
    
    

    @Override
    public void simpleRender(RenderManager rm) {
        //TODO: add render code
    }

    @Override
    public void onAnimCycleDone(AnimControl control, AnimChannel channel, String animName) {
       ninjaAnimation(control, channel, animName, playerA);
       ninjaAnimation(control, channel, animName, playerB);
    }
    
    public void ninjaAnimation(AnimControl control, AnimChannel channel, String animName,Node player){
         if (animName.equals("Walk") && colision(player)) {
            channel.setAnim("Attack3", 0.0f);
            channel.setLoopMode(LoopMode.DontLoop);
            channel.setSpeed(1f);
            channel.setAnim("Attack1",0.0f);
            channel.setLoopMode(LoopMode.Loop);
            channel.setSpeed(1f);
             
            
        }
         
       /* if (animName.equals("Attack3") && colision()) {
            channel.setAnim("Death1", 0.0f);
            channel.setLoopMode(LoopMode.DontLoop);
            channel.setSpeed(1f);
        } */

        if (animName.equals("Death1") && !isAlive(player)) {
            rootNode.detachChild(player);
        }
    }
    
    private boolean colision(Node ninja){
     return false;   
    }
    
    private boolean isAlive(Node ninja){
        return true;
    }

    @Override
    public void onAnimChange(AnimControl control, AnimChannel channel, String animName) {
     //   throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void collision(PhysicsCollisionEvent event) {
        
        if(event.getNodeA().getName().equals("ninja1")){
            playerACollider++;
            System.out.println("NINJA1");
            
        }
        else if(event.getNodeB().getName().equals("ninja2")){
            playerBCollider++;
            System.out.println("NINJA2");
        }
        
    }
}
