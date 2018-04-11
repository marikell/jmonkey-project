/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package entidades;

import com.jme3.animation.AnimChannel;
import com.jme3.animation.AnimControl;
import com.jme3.asset.AssetManager;
import com.jme3.bullet.BulletAppState;
import com.jme3.bullet.control.RigidBodyControl;
import com.jme3.math.Vector3f;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import com.jme3.shape.box;

/**
 *
 * @author lucas
 */

interface Command {
    void runCommand();
}

public class Labirinto extends Model {
    
    private int[][] map;
    private ArrayList<Model> components;
    private Map<String, Command> creationMap = new HashMap<>();
    
    private final Vector3f position = new Vector3f(0f,-20f,0);
    private final Vector3f rotation = new Vector3f(0f,0,0);
    
    public Labirinto(String name, Vector3f position, Vector3f rotation,
            BulletAppState bulletAppState, String mesh, AssetManager assetManager, int[][] map) {
        super(name, position, rotation, bulletAppState, mesh, assetManager);
        this.map = map;
        this.components = new ArrayList();
        
        creationMap.put("Cubo", new Command(){
            // Criação do cubo
            @Override
            public void runCommand() {
                
            }
        });
        
        creationMap.put("Pontos", new Command(){
            // Criação dos pontos
            @Override
            public void runCommand() {
                
            }
        });
     
    }
    
    public void CreateMap() {
        for(Model next: this.components) {
            creationMap.get(next.getName()).runCommand();
        }
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
