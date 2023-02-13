package NucleicAcidTesting.game;

public interface Config {
    int WINDOW_HEIGHT=520;
    int WINDOW_WIDTH =960;
    String VERSION="0.1";
    String TITLE="Nucleic Acid Testing";

    boolean ONLINE = false;

    // Player
    double Velocity=300;

    // Building
    double SIZE_X=80;
    double SIZE_Y=160;

    double GAP_X =SIZE_X+30;
    double GAP_Y =SIZE_Y+30;
    int MAX_CYCLE_TIME=100;

    double OFFSET_X=-SIZE_X/2;
    double OFFSET_Y=-SIZE_Y/2;

    int GAP_TO_WINDOW=30;

    // Window
    int WINDOW_MIN_X=-WINDOW_WIDTH;
    int WINDOW_MAX_X=WINDOW_WIDTH;
    int WINDOW_MIN_Y=-WINDOW_HEIGHT;
    int WINDOW_MAX_Y=WINDOW_HEIGHT;

    int BACKGROUND_LEVEL=-1;

    int MAX_FOLLOW_NUM=20;

}
