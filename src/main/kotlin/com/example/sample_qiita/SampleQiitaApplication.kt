package com.example.sample_qiita

import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.async
import kotlinx.coroutines.delay
import kotlinx.coroutines.runBlocking
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@SpringBootApplication
class SampleQiitaApplication

fun main(args: Array<String>) {
    runApplication<SampleQiitaApplication>(*args)
    println("Start")

    val resultDeferred = GlobalScope.async {
        delay(1000) // 1秒待つ（非同期処理）
        return@async 10 + 20 // 1秒後に計算結果を返す
    }
    println("Inprogress")

    // 非同期処理の結果を待機
    runBlocking {
        val result = resultDeferred.await()
        println("Result: $result") // 結果を表示
    }

    println("End")
}
