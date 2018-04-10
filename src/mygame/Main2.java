package prova;

import com.jme3.animation.AnimChannel;
import com.jme3.animation.AnimControl;
import com.jme3.animation.AnimEventListener;
import com.jme3.animation.LoopMode;
import com.jme3.app.SimpleApplication;
import com.jme3.asset.plugins.ZipLocator;
import com.jme3.bullet.BulletAppState;
import com.jme3.bullet.collision.PhysicsCollisionEvent;
import com.jme3.bullet.collision.PhysicsCollisionListener;
import com.jme3.bullet.collision.shapes.BoxCollisionShape;
import com.jme3.bullet.collision.shapes.CapsuleCollisionShape;
import com.jme3.bullet.collision.shapes.CollisionShape;
import com.jme3.bullet.control.BetterCharacterControl;
import com.jme3.bullet.control.RigidBodyControl;
import com.jme3.bullet.util.CollisionShapeFactory;
import com.jme3.input.KeyInput;
import com.jme3.input.controls.ActionListener;
import com.jme3.input.controls.KeyTrigger;
import com.jme3.light.AmbientLight;
import com.jme3.light.DirectionalLight;
import com.jme3.material.Material;
import com.jme3.math.ColorRGBA;
import com.jme3.math.FastMath;
import com.jme3.math.Vector3f;
import com.jme3.renderer.RenderManager;
import com.jme3.scene.Geometry;
import com.jme3.scene.Node;
import com.jme3.scene.Spatial;
import com.jme3.scene.shape.Box;
import java.util.Random;

/**
 * Prova
 *
 * @author Glauco
 */
public class Main2 extends SimpleApplication
        implements PhysicsCollisionListener, AnimEventListener {

    public static void main(String[] args) {
        Main2 app = new Main2();
        app.showSettings = false;
        app.start();
    }
    private BulletAppState bulletAppState;
    private Node ninja1, ninja2;
    private RigidBodyControl r1;
    private RigidBodyControl r2;
    private boolean colisao;
    private AnimChannel channelNinja1;
    private AnimChannel channelNinja2;
    float vidaNinja1 = 500, vidaNinja2 = 500;
    private boolean fim;

    @Override
    public void simpleInitApp() {

        bulletAppState = new BulletAppState();
        stateManager.attach(bulletAppState);
        bulletAppState.setDebugEnabled(true);
        createLigth();
        createCity();

        cam.getLocation().z += 10;
        cam.getLocation().y -= 3;

        ninja1 = (Node) assetManager.loadModel("Models/Ninja/Ninja.mesh.xml");
        ninja1.setName("Ninja1");
        ninja1.rotate(0, -FastMath.HALF_PI, 0);
        ninja1.setLocalTranslation(-10, -5, 0);
        ninja1.setLocalScale(0.0125f);
        rootNode.attachChild(ninja1);

        r1 = new RigidBodyControl(new CapsuleCollisionShape(1, 0.25f), 10);
        r1.setFriction(0);
        ninja1.addControl(r1);

        AnimControl controlNinja1 = ninja1.getControl(AnimControl.class);
        controlNinja1.addListener(this);
        channelNinja1 = controlNinja1.createChannel();
        channelNinja1.setAnim("Walk");

        /**
         * *********************************************************************
         */
        ninja2 = (Node) assetManager.loadModel("Models/Ninja/Ninja.mesh.xml");
        ninja2.setName("Ninja2");
        ninja2.rotate(0, FastMath.HALF_PI, 0);
        ninja2.setLocalTranslation(10, -5, 0);
        ninja2.setLocalScale(0.0125f);
        rootNode.attachChild(ninja2);

        r2 = new RigidBodyControl(new CapsuleCollisionShape(1, 0.25f), 10);;
        r2.setFriction(0);
        ninja2.addControl(r2);

        AnimControl controlNinja2 = ninja2.getControl(AnimControl.class);
        controlNinja2.addListener(this);
        channelNinja2 = controlNinja2.createChannel();
        channelNinja2.setAnim("Walk");

        bulletAppState.getPhysicsSpace().add(r2);
        bulletAppState.getPhysicsSpace().add(r1);
//        bulletAppState.setDebugEnabled(true);

        bulletAppState.getPhysicsSpace().addCollisionListener(this);
    }

    @Override
    public void simpleUpdate(float tpf) {

        if (!fim) {
            if (!colisao) {
                r1.activate();
                r2.activate();
                r1.setPhysicsLocation(ninja1.getLocalTranslation().add(0.01f, 0, 0));
                r2.setPhysicsLocation(ninja2.getLocalTranslation().add(-0.01f, 0, 0));
            } else {
                if (vidaNinja1 > 0 && vidaNinja2 > 0) {
                    vidaNinja1 -= Math.random();
                    vidaNinja2 -= Math.random();
                } else {
                    if (vidaNinja1 > vidaNinja2) {
                        channelNinja1.setAnim("Climb");
                        channelNinja2.setAnim("Death1");
                        ninja1.rotate(0, -FastMath.HALF_PI, 0);
                    } else {
                        ninja2.rotate(0, FastMath.HALF_PI, 0);
                        channelNinja2.setAnim("Climb");
                        channelNinja1.setAnim("Death1");
                    }
                    fim = true;
                }
            }
        }
    }

    @Override
    public void simpleRender(RenderManager rm) {

    }

    private void createLigth() {

        DirectionalLight l1 = new DirectionalLight();
        l1.setDirection(new Vector3f(1, -0.7f, 0));
        rootNode.addLight(l1);

        DirectionalLight l2 = new DirectionalLight();
        l2.setDirection(new Vector3f(-1, 0, 0));
        rootNode.addLight(l2);

        DirectionalLight l3 = new DirectionalLight();
        l3.setDirection(new Vector3f(0, 0, -1.0f));
        rootNode.addLight(l3);

        DirectionalLight l4 = new DirectionalLight();
        l4.setDirection(new Vector3f(0, 0, 1.0f));
        rootNode.addLight(l4);

        AmbientLight ambient = new AmbientLight();
        ambient.setColor(ColorRGBA.White);
        rootNode.addLight(ambient);

    }

    private void createCity() {
        assetManager.registerLocator("town.zip", ZipLocator.class);
        Spatial scene = assetManager.loadModel("main.scene");
        scene.setLocalTranslation(0, -5.2f, 0);
        rootNode.attachChild(scene);

        RigidBodyControl cityPhysicsNode = new RigidBodyControl(CollisionShapeFactory.createMeshShape(scene), 0);
        scene.addControl(cityPhysicsNode);
        bulletAppState.getPhysicsSpace().add(cityPhysicsNode);
    }

    @Override
    public void collision(PhysicsCollisionEvent event) {

        if (event.getNodeA().getName().startsWith("Ninja")
                && event.getNodeB().getName().startsWith("Ninja")
                && !colisao) {
            colisao = true;
            channelNinja1.setAnim("Attack3");
            channelNinja2.setAnim("Attack3");
            r1.setKinematic(true);
            r2.setKinematic(true);

        }
    }

    @Override
    public void onAnimCycleDone(AnimControl control, AnimChannel channel, String animName) {

        if (animName.equals("Death1")) {
            channel.setLoopMode(LoopMode.DontLoop);
        }
    }

    @Override
    public void onAnimChange(AnimControl control, AnimChannel channel, String animName) {

    }
}