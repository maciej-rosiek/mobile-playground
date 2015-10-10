package game

import com.badlogic.gdx.ApplicationAdapter
import com.badlogic.gdx.Gdx
import com.badlogic.gdx.InputProcessor
import com.badlogic.gdx.graphics.FPSLogger
import com.badlogic.gdx.graphics.GL20
import com.badlogic.gdx.graphics.OrthographicCamera
import com.badlogic.gdx.graphics.Texture
import com.badlogic.gdx.graphics.g2d.Sprite
import com.badlogic.gdx.graphics.g2d.SpriteBatch
import com.badlogic.gdx.input.GestureDetector
import com.badlogic.gdx.math.Vector2
import com.badlogic.gdx.physics.box2d.*
import com.badlogic.gdx.scenes.scene2d.InputEvent
import com.badlogic.gdx.scenes.scene2d.InputListener
import com.badlogic.gdx.scenes.scene2d.Stage
import com.badlogic.gdx.scenes.scene2d.ui.Table
import com.badlogic.gdx.utils.viewport.ScreenViewport
import kotlin.concurrent.fixedRateTimer
import kotlin.properties.Delegates

public class TheGame : ApplicationAdapter() {

    var batch:SpriteBatch by Delegates.notNull()

    var camera:OrthographicCamera by Delegates.notNull()
    var world:World by Delegates.notNull()
    var player:Player by Delegates.notNull()
    var ground:Ground by Delegates.notNull()
    var stage:Stage by Delegates.notNull()
    var renderer :Box2DDebugRenderer by Delegates.notNull()

    override fun create() {
        batch = SpriteBatch()
        camera = OrthographicCamera(Const.WIDTH.toFloat(), Const.HEIGHT.toFloat())
        world = World(Vector2(0f, -9.8f), true)
        ground = Ground(world, 50f, 140f)
        player = Player(world, 80f, 150f)
        stage = Stage(ScreenViewport(camera))
        Gdx.input.setInputProcessor(GestureDetector(Gestures(player)))
        stage.addActor(Background())
        stage.addActor(player)
        stage.addActor(ground)
        renderer = Box2DDebugRenderer()
    }

    override fun render() {
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        stage.act()
        stage.draw()
        world.step(Gdx.graphics.getDeltaTime(), 6, 2);

        camera.position.x = player.body.getPosition().x
        camera.position.y = player.body.getPosition().y + 100
        if (Const.DEBUG) {
            renderer.render(world, camera.combined)
        }
    }

    override fun dispose() {
        player.dispose()
        world.dispose()
    }

    private class Gestures(p:Player) : GestureDetector.GestureAdapter() {

        val player: Player

        init {
            player = p
        }

        override fun tap(x: Float, y: Float, count: Int, button: Int): Boolean {
            player.move()
            return false
        }
    }

}
