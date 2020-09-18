package com.example.study.demo.java.basis.classloadstudy;

/**
 * 类加载过程中，加载--- 连接（验证 准备 解析）--初始化
 * <p>
 * <p>
 * tips:
 * 1)在同一个类加载器下面只能初始化类一次,如果已经初始化了就不必要初始化了.
 * 这里多说一点，为什么只初始化一次呢？因为我们上面讲到过类加载的最终结果就是在堆中存有唯一一个Class对象，我们通过Class对象找到
 * 类的相关信息。唯一一个Class对象说明了类只需要初始化一次即可，如果再次初始化就会出现多个Class对象，这样和唯一相违背了。
 * 2)在编译的时候能确定下来的静态变量(编译常量),不会对类进行初始化;
 * 3)在编译时无法确定下来的静态变量(运行时常量),会对类进行初始化;
 * 4)如果这个类没有被加载和连接的话,那就需要进行加载和连接
 * 5)如果这个类有父类并且这个父类没有被初始化,则先初始化父类.
 * 6)如果类中存在初始化语句,依次执行初始化语句
 * <p>
 * 在连接阶-准备阶段，给静态变量赋予默认初始值
 * 属性初始值为 null,0,0
 * 调用静态方法时候，首先会先Class初始化，静态属性一个个顺序执行，
 * 1.调用构造方法  counter1 =1   counter2 = 1
 * 2. counter1 初始化。 没有初始化值，所以无法初始化  counter1 = 1;
 * 3. counter2  初始化赋值为0   counter2 = 0
 * <p>
 * 初始化完毕之后就会执行静态方法Singleton.getSingleton(); 我们知道返回的singleton已经初始化了。
 * 那么输出的内容也就理所当然的是1和0了
 */
public class Singleton4ClassLoad {


    private static Singleton4ClassLoad signleton = new Singleton4ClassLoad();
    public static int counter1;
    public static int counter2 = 0;


    public Singleton4ClassLoad() {
        counter1++;
        counter2++;
    }

    public static Singleton4ClassLoad getSignleton() {
        return signleton;
    }
}
/**
 * 初始化时机:
 * <p>
 * 类的加载时机我们提到了“首次主动使用”这个词语，那什么是“主动使用”呢？
 * <p>
 * 主动初始化的6种方式
 * (1)创建对象的实例：我们new对象的时候，会引发类的初始化，前提是这个类没有被初始化。
 * (2)调用类的静态属性或者为静态属性赋值
 * (3)调用类的静态方法
 * (4)通过class文件反射创建对象
 * (5)初始化一个类的子类：使用子类的时候先初始化父类
 * (6)java虚拟机启动时被标记为启动类的类：就是我们的main方法所在的类
 * (7)对类使用动态代理的时候，需要先被初始化
 * 只有上面6种情况才是主动使用，也只有上面六种情况的发生才会引发类的初始化。
 * <p>
 * <p>
 * <p>
 * 类初始化步骤：
 * <p>
 * 没有父类的情况：
 * <p>
 * 类的静态属性
 * <p>
 * 类的静态代码块
 * <p>
 * 类的非静态属性
 * <p>
 * 类的非静态代码块
 * <p>
 * 构造方法
 * <p>
 * 有父类的情况:
 * <p>
 * 父类的静态属性
 * <p>
 * 父类的静态代码块
 * <p>
 * 子类的静态属性
 * <p>
 * 子类的静态代码块
 * <p>
 * 父类的非静态属性
 * <p>
 * 父类的非静态代码块
 * <p>
 * 父类构造方法
 * <p>
 * 子类非静态属性
 * <p>
 * 子类非静态代码块
 * <p>
 * 子类构造方法
 * <p>
 * 代码块和属性是等价的，他们是按照代码顺序执行的
 * <p>
 * <p>
 * https://juejin.im/entry/58d89c9c61ff4b0060689114
 */