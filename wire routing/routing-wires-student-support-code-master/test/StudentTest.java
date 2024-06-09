import org.junit.jupiter.api.Test;
import java.time.Duration;
import static org.junit.jupiter.api.Assertions.assertTimeout;

public class StudentTest {

    @Test
    public void testWire0() {
        assertTimeout(Duration.ofMillis(1000), () -> {
            Utilities.test("./test/inputs/wire0.in");
        });
    }

    @Test void publicWire1(){
        assertTimeout(Duration.ofMillis(1000), ()->{
            Utilities.test("./test/inputs/wire1.in");
        });
    }

    @Test void publicWire2(){
        assertTimeout(Duration.ofMillis(1000), ()->{
            Utilities.test("./test/inputs/wire2.in");
        });
    }

    @Test void publicWire3(){
        assertTimeout(Duration.ofMillis(1000), ()->{
            Utilities.test("./test/inputs/wire3.in");
        });
    }

    @Test
    public void publicWire0_0(){
        assertTimeout(Duration.ofMillis(1000), ()->{
            Utilities.test("./test/inputs/wire0_0.in");
        });
    }

    @Test
    public void publicWire4(){
        assertTimeout(Duration.ofMillis(1000), ()->{
            Utilities.test("./test/inputs/wire4.in");
        });
    }

    @Test
    public void publicWire5(){
        assertTimeout(Duration.ofMillis(1000), ()->{
            Utilities.test("./test/inputs/wire5.in");
        });
    }

    @Test
    public void publicWire6(){
        assertTimeout(Duration.ofMillis(1000), ()->{
            Utilities.test("./test/inputs/wire6.in");
        });
    }
}
