package game

import com.badlogic.gdx.graphics.Color
import com.badlogic.gdx.graphics.g2d.Batch
import com.badlogic.gdx.graphics.glutils.ShapeRenderer
import com.badlogic.gdx.math.Vector2
import com.badlogic.gdx.physics.box2d.BodyDef
import com.badlogic.gdx.physics.box2d.PolygonShape
import com.badlogic.gdx.physics.box2d.World
import com.badlogic.gdx.scenes.scene2d.Actor

class Ground(world: World) : Actor() {

    val renderer = ShapeRenderer();

    init {
        setWidth(Const.WIDTH.toFloat())
        setHeight(20f)
        val bodyDef = BodyDef();
        bodyDef.position.set(Vector2(0f, 10f));
        val body = world.createBody(bodyDef)
        val shape = PolygonShape()
        shape.setAsBox(getWidth(), getHeight())
        body.createFixture(shape, 0f)
        shape.dispose()
    }


    override fun draw(batch: Batch?, parentAlpha: Float) {
        batch?.end();

        renderer.setProjectionMatrix(batch?.getProjectionMatrix());
        renderer.setTransformMatrix(batch?.getTransformMatrix());
        renderer.translate(getX(), getY(), 0f);

        renderer.begin(ShapeRenderer.ShapeType.Filled);
        renderer.setColor(Color.BLUE);
        renderer.rect(0f, 0f, getWidth(), getHeight());
        renderer.end();

        batch?.begin();
    }
}
