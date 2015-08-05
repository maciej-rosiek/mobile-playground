package game

import com.badlogic.gdx.Gdx
import com.badlogic.gdx.graphics.Texture
import com.badlogic.gdx.graphics.g2d.Batch
import com.badlogic.gdx.graphics.g2d.Sprite
import com.badlogic.gdx.math.Vector2
import com.badlogic.gdx.physics.box2d.*
import com.badlogic.gdx.scenes.scene2d.Actor
import kotlin.properties.Delegates

class Player(world: World, x: Float, y: Float) : Actor() {

    var sprite: Sprite by Delegates.notNull()
    var body: Body by Delegates.notNull()
    var moving = false

    init {
        setWidth(16f)
        setHeight(16f)
        sprite = Sprite(Texture("badlogic.jpg"))
        sprite.setSize(16f, 16f)
        sprite.setPosition(x, y)
        val player = BodyDef()
        player.type = BodyDef.BodyType.DynamicBody
        player.position.set(x, y + 100)
        body = world.createBody(player)
        val shape = PolygonShape()
        shape.setAsBox(getWidth(), getHeight())
        val fixtureDef = FixtureDef()
        fixtureDef.shape = shape
        fixtureDef.density = 1f
        body.createFixture(fixtureDef)
        shape.dispose()
    }

    override fun draw(batch: Batch?, parentAlpha: Float) {
        sprite.draw(batch, parentAlpha)
    }

    fun dispose() {
    }

    override fun positionChanged() {
        println("Changing position")
        sprite.setPosition(body.getPosition().x, body.getPosition().y)
        super.positionChanged()
    }

    fun move() {
        if (!moving) {
            moving = true
            println("moving")
            body.applyForceToCenter(-10f, -10f, true)
        }
    }

    var movingTime = 0f

    override fun act(delta: Float) {
        super.act(delta)
        if (moving) {
            movingTime += delta
            if (movingTime > 3) {
                moving = false
                movingTime = 0f
                println("done moving")
            }
        }
    }
}
