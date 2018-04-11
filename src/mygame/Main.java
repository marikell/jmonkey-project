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
import entidades.Model;
import entidades.Ninja;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Random;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;

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
    
    private int playerACollider = 0;
    private int playerBCollider = 0;

    private Boolean isRunning = true;
    
    private ArrayList<Model> ninjaList;
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
    public void simpleInitApp(){
        ninjaList = new ArrayList<>();
        initBulletAppState();
        createLight(ColorRGBA.Gray);
        createKeys();
        playerA = createNinja("ninja1", posX+150, posY, posZ,new Vector3f(0,-80f,0) ,this, true,15);
        playerB = createNinja("ninja2", posX-150, posY, posZ,new Vector3f(0,80f,0), this, false,15);
        initNinjas();
    }
    
    private void initNinjas(){
        for (Model next : ninjaList){
            if(!isNinja(next.getClass())){
                continue;
            }
            Ninja aux = (Ninja) next;
            aux.initEngine();
            aux.startAnimation("Walk", 0.005f);
            aux.startCollider();
        }
    }
    
    public void createKeys(){
        inputManager.addMapping("Right", new KeyTrigger(KeyInput.KEY_0));
        inputManager.addMapping("Left",new KeyTrigger(KeyInput.KEY_Q));
        inputManager.addMapping("Up", new KeyTrigger(KeyInput.KEY_W));
        inputManager.addMapping("Down", new KeyTrigger(KeyInput.KEY_S));
    }
    
    private Boolean isNinja(Class<?> classType) {
        return classType.equals(Ninja.class);
    }

    private Ninja createNinja(String name, float posX, float posY, float posZ, Vector3f rotation, Main listener, boolean walkToLeft, int par2){
        Ninja ninja = new Ninja(name, new Vector3f(posX,posY,posZ),rotation, this.bulletAppState, "Models/Ninja/Ninja.mesh.xml", assetManager, walkToLeft,15);
        rootNode.attachChild(ninja);
        ninjaList.add(ninja);
        return ninja;
    }
    
    @Override
    public void simpleUpdate(float tpf) {
        if(isRunning){
            for (Model next : ninjaList) {
            if (isNinja(next.getClass())) {
                Ninja aux = (Ninja)next;
                try {
                    if(!isColliding){
                        aux.automaticWalkWhenIsNotColliding(new Vector3f(tpf*15,0f,0f));
                    }
                    else{
                        //se os ninjas ainda estão vivos
                        if(aux.getNumLife()>0){
                            Random random = new Random();
                            int life = aux.getNumLife()-random.nextInt(3);
                            aux.setNumLife(life);
                        }
                        //ninja morreu
                        else{
                            String animName = aux.getName().equals("ninja1") ? "Attack3" : "Attack2";
                            aux.animate(animName, "Climb", 0.004f, LoopMode.DontLoop, 0.01f);
                            aux.animate("Climb", "Death1", 0.004f, LoopMode.DontLoop, 0.01f);
                            isRunning = false;
                            
                        }
                    }
                    
                } catch (Exception ex) {
                    Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        }
        }
    }
    
   
    @Override
    public void simpleRender(RenderManager rm) {
        //TODO: add render code
    }

    @Override
    public void onAnimCycleDone(AnimControl control, AnimChannel channel, String animName) {
             
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
        
        if(event.getNodeA().getName().startsWith("ninja") && event.getNodeB().getName().startsWith("ninja")) {
            
            Ninja ninja1 = (Ninja)event.getNodeA();
            Ninja ninja2 = (Ninja)event.getNodeB();
            
            ninja1.setIsColliding(true);
            ninja2.setIsColliding(true);
            
            ninja1.getRigidBodyControl().setKinematic(true);
            ninja2.getRigidBodyControl().setKinematic(true);

            
            isColliding = ninja1.isColliding() && ninja2.isColliding();
            
            ninja1.animate("Walk", "Attack3", 0.005f, LoopMode.Loop, 0.01f);
            ninja2.animate("Walk", "Attack2", 0.01f, LoopMode.Loop, 0.04f);
            
        }
        
       
        
    }
   
    private Boolean isColliding = false;
    
    @Override
    public void onAction(String name, boolean isPressed, float tpf) {
        

      /*  if(name.equals("Right")){
            NinjaKey.move(0.01f,0,0);
            System.out.println("right");

        }
        else if(name.equals("Left")){
        NinjaKey.move(-0.01f,0,0);
        //ninjaWalk(playerA, -0.01f);
         System.out.println("left");


        }*/
       /* else if(name.equals(""))
        {
            
        }
        else if(name.equals("")){
            
        }*/
        
    }
}
