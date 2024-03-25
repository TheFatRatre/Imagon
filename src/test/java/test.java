/**
 * ClassName: test
 * Package: PACKAGE_NAME
 * Description:
 *
 * @Author CYC
 * @Create 2024/3/25 15:30
 * @Version 1.0
 */
public class test {
    private static test instance;
    private test(){

    }
    public static test getInstance(){
        if(instance==null){
            instance=new test();
        }
        return instance;
    }
}
