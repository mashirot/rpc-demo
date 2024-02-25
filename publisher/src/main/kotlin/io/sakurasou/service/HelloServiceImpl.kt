package io.sakurasou.service

/**
 * @author mashirot
 * 2024/2/24 23:37
 */
class HelloServiceImpl : HelloService {
    override fun hello(name: String): String {
        return "HelloWorld, Hello $name"
    }

    override fun hello2(name: String): String {
        return "HelloWorld2, Hello $name"
    }

    override fun hello3(name: String): String {
        return "HelloWorld3, Hello $name"
    }

    override fun sum(nums: List<Int>): Int {
        return nums.sum()
    }
}