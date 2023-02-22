package NucleicAcidTesting.game;

public interface Config {
    int WINDOW_HEIGHT=520;
    int WINDOW_WIDTH =960;
    String VERSION="0.9";
    String TITLE="Nucleic Acid Testing";

    boolean ONLINE = false;

    // Player
    double Velocity=300;

    // Building
    double GAP_X =175*2;
    double GAP_Y =175*2;
    int MAX_CYCLE_TIME=1000;

    int GAP_TO_WINDOW=130;

    double SPAWNING_X_GAP=20;

    // Window
    int WINDOW_MIN_X= -WINDOW_WIDTH;
    int WINDOW_MAX_X= (int) (WINDOW_WIDTH*1.5);
    int WINDOW_MIN_Y= -WINDOW_HEIGHT;
    int WINDOW_MAX_Y= (int) (WINDOW_HEIGHT*1.5);

    int BACKGROUND_LEVEL=-1;

    int MAX_FOLLOW_NUM=20;

}
