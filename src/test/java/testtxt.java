import com.cyc.imagon.service.CountTxt;
import org.junit.jupiter.api.Test;

/**
 * ClassName: testtxt
 * Package: PACKAGE_NAME
 * Description:
 *
 * @Author CYC
 * @Create 2024/4/10 20:29
 * @Version 1.0
 */
public class testtxt {
    @Test
    public void test(){
        CountTxt countTxt = new CountTxt();
        System.out.println(countTxt.readTxt());
        countTxt.writeTxt(12);
        System.out.println(countTxt.readTxt());
    }
}
