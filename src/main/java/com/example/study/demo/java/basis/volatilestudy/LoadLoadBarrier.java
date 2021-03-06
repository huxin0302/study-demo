package com.example.study.demo.java.basis.volatilestudy;

/** 为什么使用volatile ? volatile是什么?有什么作用?
 * 下列描述来自维基百科
 * 当 volatile 用于一个作用域时，Java保证如下：
 * 1.（适用于Java所有版本）读和写一个 volatile 变量有全局的排序。也就是说每个线程访问一个 volatile 作用域时会在继续执行之前读取它的当前值，而不是（可能）使用一个缓存的值。（但是并不保证经常读写 volatile 作用域时读和写的相对顺序，也就是说通常这并不是有用的线程构建）。
 * 2.（适用于Java5及其之后的版本） volatile 的读和写创建了一个happens-before关系
 *
 * 重排序一定会带来可见性问题
 *
 *为了实现volatile的内存语义，面临2个问题：可见性问题，重排序（也会导致内存可见性问题）
 *
 */
public class LoadLoadBarrier {
    /**
     * 然而volatile是怎么实现上面的作用的呢？ 1.内存可见性 （对应作用1）  2.禁止一定程度上的指令重排序（读操作禁止重排序之后的操作，写操作禁止重排序之前的操作）（对应作用2）
     * volatile 1.内存可见性的实现 --->+ (cpu的缓存一致性协议MESI + ring bus)
     *
     *   cpu的缓存一致性协议MESI + ring bus 只能保证单条cpu指令对共享变量的赋值 是可见性的（解决了cpu缓存层面上的可见性问题）
     *
     * 在并发模型下，重排序还是可能会引发问题，比较经典的就是"单例模式失效"问题.那么如何保证多线程下，volatile的读写 happens-before关系呢？  内存屏障
        * Lock:前缀指令(有mfence--storeLoad语义的指令) （volatile在反编译成汇编语言的时候，会出现Lock: 前缀的指令）
     *
     *
     *  "单例模式失效"  【见Singleton类-双重检验锁】因重排序的原因，一条java代码 instance = new Singleton()并非一个原子操作，
     *  它实际分解下面这三个操作，被反编译为3条cpu指令：
     *  memory = allocate();    //1：分配对象的内存空间
     *  ctorInstance(memory);  //2：初始化对象
     *  instance = memory;     //3：设置instance指向刚分配的内存地址
     *
     * 进行指令重排序后：
     * memory = allocate();    //1：分配对象的内存空间
     * instance = memory;     //3：instance指向刚分配的内存地址，此时对象还未初始化
     * ctorInstance(memory);  //2：初始化对象
     *
     * 第二句instance = memory; 确实满足内存可见性，但是多线程的情况下，会导致NullPointException  因为这个分配的内存地址还没有初始化对象
     * 这个空指针异常是如何出现的呢？cpu指令重排序  如何去解决这个呢？禁止一定程度的指令重排序----内存屏障
     *
     *
     * 顺序是：volatile声明的变量，在生成cpu指令时
     *      *      1.先在volatile变量写/读前后加入内存屏障阻止了指令的重排，并生成Lock:前缀的CPU指令
     *      *      2.再利用CPU硬件上的支持MESI缓存一致性协议 + ring bus
     *      *     结合上述2点确保了内存的可见性 和 多线程并发下volatile变量写读的happens-before原则
     *      *     参考BLOG  https://www.jianshu.com/p/6745203ae1fe
     *
     *
     *
     * 正题：
     * 我们都知道  【数据依赖】的是不允许重排序的 见as-if-serial。而【控制依赖】是允许重排序的，但执行过程中会使用“猜测执行”来保证单线程执行的结果正确性
     * 这个测试用例目的是验证：多线程下，控制依赖进行重排序，猜测执行会出现不正确的结果
     *
     */


/**什么是内存屏障？
 *
 * 总结下所有可能发生乱序执行的情况:
     * 现代处理器采用指令并行技术,在不存在数据依赖性的前提下,处理器可以改变语句对应的机器指令的执行顺序来提高处理器执行速度
     * 现代处理器采用内部缓存技术,导致数据的变化不能及时反映在主存所带来的乱序.
     * 现代编译器为优化而重新安排语句的执行顺序
 *
 * 尽管我们看到乱序执行初始目的是为了提高效率,但是它看来其好像在这多核时代不尽人意,
 * 其中的某些”自作聪明”的优化导致多线程程序产生各种各样的意外.因此有必要存在一种机制来消除乱序执行带来的坏影响,
 * 也就是说应该允许程序员显式的告诉处理器对某些地方禁止乱序执行.这种机制就是所谓内存屏障.不同架构的处理器在其指令集中提供了不同的指令来发起内存屏障,比如x86的lock指令 任何带有lock前缀的指令以及CPUID等指令都有内存屏障的作用。
 * 对应在编程语言当中就是提供特殊的关键字来调用处理器相关的指令.
 *
 *
 *https://juejin.im/entry/58ad75908d6d810058c88989
 *https://juejin.im/post/5a52cfdc518825733b0eb69a
 *
 *
 *
 *
 *
 *
 *
 *
 */








}
