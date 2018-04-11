/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package entidades;

import com.jme3.asset.AssetManager;
import com.jme3.bullet.BulletAppState;
import com.jme3.bullet.control.RigidBodyControl;
import com.jme3.material.Material;
import com.jme3.math.ColorRGBA;
import com.jme3.math.Vector3f;
import com.jme3.scene.Geometry;
import com.jme3.scene.shape.Box;

/**
 *
 * @author Marianne
 */
public class RigidBodyBox extends Box{
    
    private RigidBodyControl rigidBodyControl;
    private float width;
    private float height;
    private float depth;
    private Material material;
    private String name;
    private Vector3f position;
    private Vector3f rotation;
    private Geometry boxGeometry;   
    private BulletAppState bulletAppState;
    
    /*Para realizar a instância dessa classe na main, basta seguir os passos abaixo como exemplo:
     floorBox = new RigidBodyBox("floor", bulletAppState, assetManager, 10f, 0.1f, 5f, "Common/MatDefs/Light/Lighting.j3md", ColorRGBA.Blue, new Vector3f(0,0,0), new Vector3f(dgx,dgy,dgz));
    
    this.rootNode.attachChild(floorBox.getBox());
    floorBox.initRigidBody(0);   
    
    */
    
    
    public RigidBodyBox() {
    }
    
    public Geometry getBox(){
        return boxGeometry;
    }
    
    public RigidBodyBox(String name,BulletAppState bulletAppState,AssetManager assetManager, float width, float height, float depth, String defName, ColorRGBA color, Vector3f position, Vector3f rotation){
        
        this.width = width;
        this.height = height;
        this.depth = depth;
        
        this.name = name;
        this.position = position;
        this.rotation = rotation;
        
        this.bulletAppState = bulletAppState;
        
        material = new Material(assetManager, defName);
        material.setBoolean("UseMaterialColors", true);
        material.setColor("Ambient", color);
        material.setColor("Diffuse", color);
        
        boxGeometry = new Geometry(name,this);
        boxGeometry.setMaterial(material);
        boxGeometry.setLocalTranslation(position);
        boxGeometry.rotate(rotation.x, rotation.y, rotation.z);
                
       updateGeometry(Vector3f.ZERO, width, height, depth);

    }
    
    public void initRigidBody(float mass){
        rigidBodyControl = new RigidBodyControl(mass);
        boxGeometry.addControl(rigidBodyControl);
        bulletAppState.getPhysicsSpace().add(rigidBodyControl);
    }
    
   
    
}
