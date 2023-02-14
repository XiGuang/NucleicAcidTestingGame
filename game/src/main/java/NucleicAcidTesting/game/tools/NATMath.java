package NucleicAcidTesting.game.tools;

public class NATMath {
    /**
     * 插值实现连续平滑的变化效果
     * @param begin 开始数值
     * @param end   目标值
     * @param speed 变化速度
     * @return 插值量
     */
    public static double InterpolationD(double begin, double end, double speed){
        return begin+(end-begin)*speed;
    }
}
