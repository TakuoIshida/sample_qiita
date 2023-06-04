package com.example.sample_qiita.controller

import com.example.sample_qiita.model.Qiita
import com.example.sample_qiita.repository.QiitaRepository
import jakarta.validation.Valid
import org.springframework.http.HttpMethod
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import org.springframework.web.client.RestTemplate


@RestController
@RequestMapping("/api")
class QiitaController(private val qiitaRepository: QiitaRepository) {

    private val restTemplate = RestTemplate()

    @GetMapping("/qiitas")
    fun getAllQiitas(): List<Qiita> {
        return qiitaRepository.findAll()
    }

    @PostMapping("/qiitas")
    fun createNewQiita(@Valid @RequestBody qiita: Qiita): Qiita =
        qiitaRepository.save(qiita)

    @GetMapping("/qiitas/{id}")
    fun getQiitaBiId(@PathVariable(value = "id") qiitaed: Long): ResponseEntity<Qiita> {
        println("IOIOIOIOIOOOIOIOIOIOOOI")



        return ResponseEntity.ok(
            Qiita(
                id = 1,
                title = "ダミータイトル",
                content = "ダミー内容"
            )
        )
//        return qiitaRepository.findById(qiitaed).map { qiita ->
//            ResponseEntity.ok(qiita)
//        }.orElse(ResponseEntity.notFound().build())
    }

    @PutMapping("/qiitas/{id}")
    fun updateQiitaById(
        @PathVariable(value = "id") qiitaed: Long,
        @Valid @RequestBody newQiita: Qiita
    ): ResponseEntity<Qiita> {

        return qiitaRepository.findById(qiitaed).map { existingQiita ->
            val updateQiita: Qiita = existingQiita
                .copy(title = newQiita.title, content = newQiita.content)
            ResponseEntity.ok().body(qiitaRepository.save(updateQiita))
        }.orElse(ResponseEntity.notFound().build())

    }

    @DeleteMapping("/qiitas/{id}")
    fun deleteQiitaById(@PathVariable(value = "id") qiitaed: Long): ResponseEntity<Void> {

        return qiitaRepository.findById(qiitaed).map { qiita ->
            qiitaRepository.delete(qiita)
            ResponseEntity<Void>(HttpStatus.OK)
        }.orElse(ResponseEntity.notFound().build())
    }

    @GetMapping("/external/events")
    fun getTokyoEvents(): ResponseEntity<Void> {
        println("events")

        fun fetchDataFromExternalApi(): String {
            val apiUrl = "https://catalog.data.metro.tokyo.lg.jp/dataset/t000003d0000000027" // 外部APIのエンドポイントURL

            // GETリクエストを送信してデータを取得
            val response: ResponseEntity<String> =
                restTemplate.exchange(apiUrl, HttpMethod.GET, null, String::class.java)

            if (response.statusCode.is2xxSuccessful) {
                println("response.body")
                println(response.body)
                return response.body ?: ""
            } else {
                throw RuntimeException("Failed to fetch data from the external API")
            }
        }

        fetchDataFromExternalApi()
        return TODO("Provide the return value")
    }
}