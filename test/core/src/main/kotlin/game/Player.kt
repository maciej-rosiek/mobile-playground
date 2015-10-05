package game

import com.badlogic.gdx.Gdx
import com.badlogic.gdx.graphics.Texture
import com.badlogic.gdx.graphics.g2d.Batch
import com.badlogic.gdx.graphics.g2d.Sprite
import com.badlogic.gdx.math.Vector2
import com.badlogic.gdx.physics.box2d.*
import com.badlogic.gdx.scenes.scene2d.Actor
import com.badlogic.gdx.scenes.scene2d.InputEvent
import com.badlogic.gdx.scenes.scene2d.InputListener
import kotlin.properties.Delegates

class Player(world: World, x: Float, y: Float) : Actor() {

    var sprite: Sprite by Delegates.notNull()
    var body: Body by Delegates.notNull()
    var moving = false
    var fixture: Fixture by Delegates.notNull()

    init {
        setWidth(64f)
        setHeight(64f)
        sprite = Sprite(Texture("char.png"))
        sprite.setSize(getWidth(), getHeight())
        sprite.setPosition(x, y)
        val bodyDef = BodyDef()
        bodyDef.type = BodyDef.BodyType.DynamicBody
        bodyDef.position.set(x, y)
        bodyDef.fixedRotation = true
        body = world.createBody(bodyDef)
        val shape = PolygonShape()
        shape.setAsBox(getWidth() / 2, getHeight() / 2, Vector2(getWidth() / 2, getHeight() / 2), 0f)
        val fixtureDef = FixtureDef()
        fixtureDef.shape = shape
        fixtureDef.friction = 2000f
        fixtureDef.density = 1f
        fixture = body.createFixture(fixtureDef)
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
        }
    }

    var movingTime = 0f

    override fun act(delta: Float) {
        super.act(delta)
        if (body.getLinearVelocity().x > 330) {
            body.setLinearVelocity(330f, body.getLinearVelocity().y)
        }

        if (moving) {
            if (body.getLinearVelocity().x == 0f) {
                body.setLinearVelocity(100f, body.getLinearVelocity().y)
            }
            movingTime += delta
            fixture.setFriction(0.2f)
            body.applyLinearImpulse(1200f, 0f, body.getPosition().x, body.getPosition().y, true)
            if (movingTime > 2) {
                moving = false
                movingTime = 0f
                println("done moving")
            }
        }
        else {
            fixture.setFriction(3000f)
        }
        sprite.setPosition(body.getPosition().x, body.getPosition().y)
    }

}
