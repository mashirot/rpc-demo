package io.sakurasou.service

/**
 * @author mashirot
 * 2024/2/24 23:37
 */
class HelloServiceImpl : HelloService {
    override fun hello(name: String): String {
        return "HelloWorld, Hello $name"
    }

    override fun hello2(name: String) {
        println("HelloWorld4, Hello $name")
    }

    override fun sum(nums: List<Int>): Int {
        return nums.sum()
    }
}