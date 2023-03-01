package NucleicAcidTesting.game.components;

import NucleicAcidTesting.game.ui.LoadingWin;
import com.almasb.fxgl.entity.component.Component;

public class GameLoadControllerComponent extends Component {

    final double DELAY = 1.5;
    static boolean INITED = false;


    double initDelay = DELAY;
    boolean showLoading = false;
    LoadingWin loadingWin;
    @Override
    public void onAdded() {
        INITED = false;
        initDelay = DELAY;
        showLoading = false;
    }

    @Override
    public void onUpdate(double tpf) {
        double max_time = 0.016;
        if (!INITED) {
            initDelay -= tpf;
            if (tpf <= max_time && initDelay <0) {
                if (showLoading) {
                    if(loadingWin.isShowing())
                        loadingWin.closeLoading();
                    showLoading = false;
                    initDelay = DELAY;
                }
                INITED = true;
            }
            else if (!showLoading) {
                loadingWin = new LoadingWin();
                showLoading = true;
            }
            else if(!loadingWin.isShowing()&& initDelay <0){
                showLoading = false;
                initDelay = DELAY;
                INITED = true;
            }
        }
    }

    public static boolean isInited() {
        return INITED;
    }
}
