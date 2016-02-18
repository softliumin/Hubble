package junit;

/**
 * Created by lizhitao on 16-1-19.
 */
public class MyDriver implements Driver{
    @Override
    public void drive(Car car) {
        car.run();
    }
}
