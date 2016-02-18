package junit;

import junit.framework.TestCase;
import org.jmock.Expectations;
import org.jmock.Mockery;
import org.jmock.integration.junit4.JUnit4Mockery;
import org.junit.Test;

/**
 * Created by lizhitao on 16-1-19.
 */
public class TestDriver extends TestCase {
    Mockery mockery = new JUnit4Mockery();

    @Test
    public void testDriver() {
        final Car car = mockery.mock(Car.class);
        Driver driver = new MyDriver();

        mockery.checking(new Expectations() {{
            oneOf(car).run();
        }});

        driver.drive(car);
    }
}
