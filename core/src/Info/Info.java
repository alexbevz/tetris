package Info;

import com.badlogic.gdx.Gdx;

public interface Info {
    float WIDTH = 1080;
    float HEIGHT = 1920;
    float REAL_WIDTH = Gdx.graphics.getWidth();
    float REAL_HEIGHT = Gdx.graphics.getHeight();
    float BB_HEIGHT = (Gdx.graphics.getHeight() - Gdx.graphics.getWidth() * 1.77777778f) / 2;  //black bar height
    float RIGHT_EDGE_X = 758;
    float LEFT_EDGE_X = 38;
    float BOTTOM_EDGE_Y = 438;
    float TOP_EDGE_Y = 1878;
    float SQ_W = 72;//
    float CENTER_X = 326;
    float FRAMES_PER_SECOND = 60.098f;
    float NEXTFIGURE_BL_X = 804;
    float NEXTFIGURE_BL_Y = 1138;
}
