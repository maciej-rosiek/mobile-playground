package game

import com.badlogic.gdx.graphics.Color
import com.badlogic.gdx.graphics.g2d.Batch
import com.badlogic.gdx.graphics.glutils.ShapeRenderer
import com.badlogic.gdx.math.Vector2
import com.badlogic.gdx.physics.box2d.*
import com.badlogic.gdx.scenes.scene2d.Actor

class Ground(world: World) : Actor() {

    val renderer = ShapeRenderer();

    init {
        setWidth(Const.WIDTH.toFloat())
        setHeight(20f)
        val bodyDef = BodyDef();
        bodyDef.position.set(Vector2(0f, 10f));
        val body = world.createBody(bodyDef)
        val shape = ChainShape()
        shape.createChain(arrayOf(Vector2(0f, 0f), Vector2(getWidth(), 0f)))
        val fixtureDef = FixtureDef()
        fixtureDef.shape = shape
        fixtureDef.friction = 0.9f
        body.createFixture(fixtureDef)
        shape.dispose()
    }


    override fun draw(batch: Batch?, parentAlpha: Float) {
        batch?.end();

        renderer.setProjectionMatrix(batch?.getProjectionMatrix());
        renderer.setTransformMatrix(batch?.getTransformMatrix());
        renderer.translate(getX(), getY(), 0f);

        renderer.begin(ShapeRenderer.ShapeType.Filled);
        renderer.setColor(Color.BLUE);
        renderer.rect(0f, 0f, getWidth(), 10f);
        renderer.end();

        batch?.begin();
    }
}
