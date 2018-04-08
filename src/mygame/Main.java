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
public class Main extends SimpleApplication implements AnimEventListener, PhysicsCollisionListener, ActionListener {
    
    private float posX = 0f;
    private float posY = 0f;
    private float posZ = -600f;
    
    private float rotateSide = 80f;
        
    private AnimChannel channel;
    private AnimControl control;
    
    private BulletAppState bulletAppState;
    
    private Ninja playerA;
    private Ninja playerB;
    
    private Ninja NinjaKey;
    
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
    
     private DirectionalLight createLight(ColorRGBA color){
        viewPort.setBackgroundColor(color);
        DirectionalLight light = new DirectionalLight();
        light.setDirection(new Vector3f(-0.1f,-1f,-1).normalizeLocal());
        rootNode.addLight(light);
        return light;
    }

    @Override
    public void simpleInitApp() {
        initBulletAppState();
        createLight(ColorRGBA.Gray);
        createKeys();
        NinjaKey = createNinja("ninjaKey",posX,posY,posZ,new Vector3f(0,0,0),this);
        //playerA = createNinja("ninja1", posX+150, posY, posZ,new Vector3f(0,-80f,0) ,this);
        //playerB = createNinja("ninja2", posX-150, posY, posZ,new Vector3f(0,80f,0), this);
    }
    
    public void createKeys(){
        inputManager.addMapping("Right", new KeyTrigger(KeyInput.KEY_0));
        inputManager.addMapping("Left",new KeyTrigger(KeyInput.KEY_Q));
        inputManager.addMapping("Up", new KeyTrigger(KeyInput.KEY_W));
        inputManager.addMapping("Down", new KeyTrigger(KeyInput.KEY_S));
    }

    private Ninja createNinja(String name, float posX, float posY, float posZ,Vector3f rotation,Main listener){
      
        Ninja ninja = new Ninja(name, new Vector3f(posX,posY,posZ),rotation,bulletAppState, "Models/Ninja/Ninja.mesh.xml", assetManager);
       
        //Já inicializa uma animação
        //ninja.animationListener(listener, "Walk", 0.02f);
        
        rootNode.attachChild(ninja);
        return ninja;
    }
    

    
    @Override
    public void simpleUpdate(float tpf) {
        /*if(!colision(playerA)){
            playerA.move(-0.01f,0,0);
        }
        if(!colision(playerB)){
            playerB.move(0.01f,0,0);
        }*/
    }
    
   
    @Override
    public void simpleRender(RenderManager rm) {
        //TODO: add render code
    }

    @Override
    public void onAnimCycleDone(AnimControl control, AnimChannel channel, String animName) {
        /*if(colision(playerA)){
         playerA.animate("Walk", "Attack3", 0.0f, LoopMode.DontLoop, 1f);
        } */      
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
        
        /*if(event.getNodeA().getName().equals("ninja1")){
            playerACollider++;
            System.out.println("NINJA1");
            
        }
        else if(event.getNodeB().getName().equals("ninja2")){
            playerBCollider++;
            System.out.println("NINJA2");
        }*/
        
    }

    @Override
    public void onAction(String name, boolean isPressed, float tpf) {

        if(name.equals("Right")){
            NinjaKey.move(0.01f,0,0);
            System.out.println("right");

        }
        else if(name.equals("Left")){
        NinjaKey.move(-0.01f,0,0);
        //ninjaWalk(playerA, -0.01f);
         System.out.println("left");


        }
       /* else if(name.equals(""))
        {
            
        }
        else if(name.equals("")){
            
        }*/
        
    }
}
