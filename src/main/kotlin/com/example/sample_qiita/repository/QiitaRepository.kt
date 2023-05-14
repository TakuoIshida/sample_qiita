package com.example.sample_qiita.repository

import com.example.sample_qiita.model.Qiita
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface QiitaRepository: JpaRepository<Qiita, Long>