/**
 * Created by zouzhiyi on 05/06/17.
 */

public class TestGC {
    public static TestGC sTestGC = null;

    @Override
    protected void finalize() throws Throwable {
        super.finalize();
        System.out.println("finalize is called");
        TestGC.sTestGC = this;
    }

    public static void alive(){
        System.out.println("yes,i am still alive");
    }

    public static void main(String[] args)throws Throwable{
        System.out.println("running....");
        sTestGC = new TestGC();
        sTestGC = null;
        System.gc();
        Thread.sleep(500);
        if (sTestGC != null){
            alive();
        }else {
            System.out.println("no,i am dead");
        }

        sTestGC = null;
        System.gc();
        Thread.sleep(500);
        if (sTestGC != null){
            alive();
        }else {
            System.out.println("no,i am dead");
        }
    }

    @Override
    public String toString() {
        String a = "avv"+"fsfsf"+"fdfdf";
        return super.toString();
    }

    @Override
    public int hashCode() {
        return super.hashCode();
    }
}
