package io.sakurasou.service

/**
 * @author mashirot
 * 2024/2/25 14:37
 */
interface HelloService {
    fun hello(name: String): String
    fun hello2(name: String)
    fun sum(nums: List<Int>): Int
}