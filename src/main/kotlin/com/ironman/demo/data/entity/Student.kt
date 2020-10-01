package com.ironman.demo.data.entity

import javax.persistence.*

/**
 * 學生資料
 *
 * @author wei-xiang
 * @version 1.0
 * @date 2020/9/25
 */
@Entity
@Table
data class Student(
        @Id
        @GeneratedValue(strategy = GenerationType.SEQUENCE)
        val id: Int = 0,

        @Column
        val name: String = "",

        @Column
        val email: String = ""
)