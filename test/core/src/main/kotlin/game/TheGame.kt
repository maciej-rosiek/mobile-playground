package game

import com.badlogic.gdx.ApplicationAdapter
import com.badlogic.gdx.Gdx
import com.badlogic.gdx.graphics.FPSLogger
import com.badlogic.gdx.graphics.GL20
import com.badlogic.gdx.graphics.OrthographicCamera
import com.badlogic.gdx.graphics.Texture
import com.badlogic.gdx.graphics.g2d.Sprite
import com.badlogic.gdx.graphics.g2d.SpriteBatch
import com.badlogic.gdx.math.Vector2
import com.badlogic.gdx.physics.box2d.*
import com.badlogic.gdx.scenes.scene2d.Stage
import com.badlogic.gdx.utils.viewport.ScreenViewport
import kotlin.concurrent.fixedRateTimer
import kotlin.properties.Delegates

public class TheGame : ApplicationAdapter() {

    val fpsLogger = FPSLogger()

    var batch: SpriteBatch by Delegates.notNull()

    var camera: OrthographicCamera by Delegates.notNull()
    var world: World by Delegates.notNull()
    var player: Player by Delegates.notNull()
    var ground: Ground by Delegates.notNull()
    var stage :Stage by Delegates.notNull()
    var renderer :Box2DDebugRenderer by Delegates.notNull()

    override fun create() {
        batch = SpriteBatch()
        camera = OrthographicCamera(Const.WIDTH.toFloat(), Const.HEIGHT.toFloat())
        camera.setToOrtho(false, Gdx.graphics.getWidth().toFloat(), Gdx.graphics.getHeight().toFloat())
        camera.position.set(camera.viewportWidth / 2f, camera.viewportHeight / 2f, 0f)
        world = World(Vector2(0f, -9.8f), true)
        ground = Ground(world)
        player = Player(world, 40f, 46f)
        stage = Stage(ScreenViewport(camera))
        Gdx.input.setInputProcessor(stage)
        stage.addActor(player)
        stage.addActor(ground)
        renderer = Box2DDebugRenderer()
    }

    var lastTouch:Long? = null

    override fun render() {
        //player.updatePosition()
        //camera.update()
        //Gdx.gl.glClearColor(1f, 1f, 1f, 1f);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        //debugRenderer.render(world, camera.combined);

        if (Const.DEBUG) {
            fpsLogger.log()
        }

        /*
        if (Gdx.input.justTouched()) {
            println("touch")
            if (System.currentTimeMillis() - (lastTouch ?: 0L) <= 1000) {
                player.move()
            }
            lastTouch = System.currentTimeMillis()
        }
        */


        stage.act()
        stage.draw()
        world.step(Gdx.graphics.getDeltaTime(), 6, 2);

        renderer.render(world, camera.combined)
    }

    override fun dispose() {
        player.dispose()
        world.dispose()
    }

}
