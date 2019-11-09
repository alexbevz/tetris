package Info;

import com.badlogic.gdx.Gdx;

public interface Info {
    float WIDTH = 480;
    float HEIGHT = 800;
    float REAL_WIDTH = Gdx.graphics.getWidth();
    float REAL_HEIGHT = Gdx.graphics.getHeight();
    float BB_HEIGHT = (Gdx.graphics.getHeight() - Gdx.graphics.getWidth() * 1.6666667f) / 2;  //black bar height
    float RIGHT_EDGE_X = 319;
    float LEFT_EDGE_X = 19;
    float BOTTOM_EDGE_Y = 180;
    float TOP_EDGE_Y = 780;
    float SQ_W = 30;//
    float CENTER_X = 169;
    float FRAMES_PER_SECOND = 60.098f;
    float NEXTFIGURE_BL_X = 351;
    float NEXTFIGURE_BL_Y = 630;
}
